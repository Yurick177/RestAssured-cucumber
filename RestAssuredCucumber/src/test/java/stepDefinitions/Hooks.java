package stepDefinitions;

import com.jayway.restassured.RestAssured;
import io.cucumber.java.Before;


public class Hooks {

    @Before("@setupLocal")
    public static void initLocal() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        RestAssured.basePath = "/posts";
    }

}
