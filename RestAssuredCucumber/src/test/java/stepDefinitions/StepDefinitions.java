package stepDefinitions;

import SupportClasses.Post;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StepDefinitions {

    public Response response;
    public ResponseSpecification responseSpec;
    public ResponseSpecBuilder responseBuilder;
    public ValidatableResponse VResponse;
    public RequestSpecification requestSpec;
    public RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
    public List<String> value = null;
    public String member;

    @Given("the endpoint is up")
    public void endPointUp() {
        VResponse = RestAssured.given().when().get().then();
        VResponse.statusCode(200);
    }

    @When("I want to get data on id {string}")
    public void updateOnEndPoint(String arg1) {
        requestBuilder.setBasePath(arg1);
    }

    @When("I want to put data on id {string}")
    public void putOnEndPoint(String arg1) {
        requestBuilder.setBasePath(arg1);
    }

    @When("I want to patch data on id {string}")
    public void patchOnEndPoint(String arg1) {
        requestBuilder.setBasePath(arg1);
    }

    @When("I want to delete data on id {string}")
    public void deleteOnEndPoint(String arg1) {
        requestBuilder.setBasePath(arg1);
    }


    @When("I pass in the parameters")
    public void parameters(DataTable arg1) {

        java.util.List<java.util.List<String>> data = arg1.cells();

        requestBuilder
                .addParam(data.get(0).get(0), data.get(0).get(1))
                .addParam(data.get(1).get(0), data.get(1).get(1));
    }

    @When("I want to make a post with data")
    public void postWithData(DataTable arg1) {

        List<List<String>> data = arg1.cells();

        Post post = new Post();
        post.setId(data.get(0).get(1));
        post.setTitle(data.get(1).get(1));
        post.setAuthor(data.get(2).get(1));


        requestBuilder.setBody(post).setContentType(ContentType.JSON);
    }

    @When("I want to put with data")
    public void putWithData(DataTable arg1) {

        java.util.List<java.util.List<String>> data = arg1.cells();

        Post post = new Post();
        post.setTitle(data.get(0).get(1));
        post.setAuthor(data.get(1).get(1));

        requestBuilder.setBody(post).setContentType(ContentType.JSON);
    }

    @When("I want to patch with data")
    public void patchWithData(DataTable arg1) {

        java.util.List<java.util.List<String>> data = arg1.cells();

        Post post = new Post();
        post.setTitle(data.get(0).get(1));
        post.setAuthor(data.get(1).get(1));

        requestBuilder.setBody(post).setContentType(ContentType.JSON);
    }

    @When("I save data at key \"([^\"]*)\"")
    public void saveDataAtPath(String arg1) {
        value = VResponse.extract().path(arg1);
    }

    @When("I want to get data on member {string}")
    public void Member(String arg1) {
        member = arg1;
    }

    @When("there exists arrays for name \"([^\"]*)\" with value \"([^\"]*)\"")
    public void searchKeysForValue(String arg1, String arg2) {

        value = VResponse.extract().path(member + ".findAll{it." + arg1 + "=='" + arg2 + "'}");
        assertThat(value.isEmpty(), is(false));
    }

    @Then("the status code is {int}")
    public void statusCode(int arg1) {
        VResponse.statusCode(arg1);
    }

    @Then("the body contains the key \"([^\"]*)\" with value \"([^\"]*)\"")
    public void bodyContainsKeyAndValue(String arg1, String arg2) {

        String value = VResponse.extract().path(arg1);
        Assert.assertEquals(arg2, value);
    }

    @Then("the data arrays are greater than {int}")
    public void dataArraysGreaterThan(int arg1) {

        ExtractableResponse<Response> response = VResponse.extract();
        int sizeOfData = response.path(member + ".size()");
        assertThat(sizeOfData, greaterThan(arg1));
    }

    @Then("I make the get")
    public void get() {
        requestSpec = requestBuilder.build();
        VResponse = RestAssured.given().spec(requestSpec).when().get().then();
    }

    @Then("I make the post")
    public void post() {
        requestSpec = requestBuilder.build();
        VResponse = RestAssured.given().spec(requestSpec).when().post().then();
    }

    @Then("I make the put")
    public void put() {
        requestSpec = requestBuilder.build();
        VResponse = RestAssured.given().spec(requestSpec).when().put().then();
    }

    @Then("I make the patch")
    public void patch() {
        requestSpec = requestBuilder.build();
        VResponse = RestAssured.given().spec(requestSpec).when().patch().then();
    }

    @Then("I make the delete")
    public void delete() {
        requestSpec = requestBuilder.build();
        VResponse = RestAssured.given().spec(requestSpec).when().delete().then();
    }
}
