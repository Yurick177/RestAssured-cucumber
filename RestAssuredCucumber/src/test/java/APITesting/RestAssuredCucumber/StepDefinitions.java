package APITesting.RestAssuredCucumber;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;

import SupportClasses.Info;
import SupportClasses.Posts;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;

import static org.hamcrest.Matchers.*;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class StepDefinitions {

	/**
	 * given() set cookies, add authorization, add parameters, setting header info
	 * when() GET, POST, PUT, DELETE..etc 
	 * then() validate status code, extract, response, extract headers, cookies, body
	 */

	public Response response;
	public RequestSpecification request;
	public ValidatableResponse json;
	public String URL;
	
	/*
	 * @Before 
	 * public static void init() { 
	 * 
	 * RestAssured.baseURI = "http://localhost";
	 * RestAssured.port = 3000; 
	 * RestAssured.basePath = "/posts"; 
	 * 
	 * }
	 */

	@Given("^the endpoint \"([^\"]*)\" is up$")
	public void endPointUp(String arg1) throws Throwable {

		URL = arg1;

		response = given().when().get(URL);

		Assert.assertEquals(200, response.getStatusCode());
	}

	@When("^I make a request to \"([^\"]*)\"$")
	public void requestURL(String arg1) throws Throwable {
		URL = URL + arg1;
	}

	@When("^I want to (?:patch|put|delete) data on id \"([^\"]*)\"$")
	public void updateOnEndPoint(String arg1) throws Throwable {
		URL = URL + arg1;
	}

	@When("^I pass in the parameters$")
	public void parameters(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		request = given()
				.param(data.get(0).get(0), data.get(0).get(1))
				.param(data.get(1).get(0), data.get(1).get(1));
	}

	@When("^I want to make a post with data$")
	public void postWithData(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		Info info = new Info();
		info.setPublisher(data.get(3).get(1));
		info.setIsbn(data.get(4).get(1));
		info.setCatalogNumber(data.get(5).get(1));

		Posts newPost = new Posts();
		newPost.setId(data.get(0).get(1));
		newPost.setTitle(data.get(1).get(1));
		newPost.setAuthor(data.get(2).get(1));
		newPost.setInfo(new Info[] { info });

		request = given().when().body(newPost).contentType(ContentType.JSON);
	}

	@When("^I want to (?:patch|put) with data$")
	public void putWithData(DataTable arg1) throws Throwable {

		java.util.List<java.util.List<String>> data = arg1.raw();

		Posts newPost = new Posts();
		newPost.setTitle(data.get(0).get(1));
		newPost.setAuthor(data.get(1).get(1));

		request = given().when().body(newPost).contentType(ContentType.JSON);

	}

	@Then("^the weather description is \"([^\"]*)\"$")
	public void weatherDescription(String arg1) throws Throwable {

		response = request.get(URL);

		String weatherDescription = response.then()
				.contentType(ContentType.JSON)
				.extract()
				.path("weather[0].description");

		Assert.assertEquals(arg1, weatherDescription);

	}

	@Then("^the status code is \"([^\"]*)\"$")
	public void statusCode(int arg1) throws Throwable {

		Assert.assertEquals(arg1, response.getStatusCode());
	}

	@Then("^the body contains the key \"([^\"]*)\" with value \"([^\"]*)\"$")
	public void bodyContainsKeyAndValue(String arg1, String arg2) throws Throwable {

		/*
		 * Using Hamcrest: request.expect().body(arg1, equalTo(arg2)).when().get(URL);
		 */

		response = request.get(URL);

		String actualValue = response.then()
				.contentType(ContentType.JSON)
				.extract()
				.path(arg1);

		Assert.assertEquals(arg2, actualValue);

	}

	@Then("^I make the post$")
	public void post() throws Throwable {

		response = request.post(URL);
	}

	@Then("^I make the put$")
	public void put() throws Throwable {

		response = request.put(URL);
	}

	@Then("^I make the patch$")
	public void patch() throws Throwable {

		response = request.patch(URL);
	}

	@Then("^I make the delete$")
	public void delete() throws Throwable {

		response = given().when().delete(URL);
	}

}
