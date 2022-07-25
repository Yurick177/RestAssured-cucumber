package stepDefinitions;

import com.jayway.restassured.RestAssured;
import io.cucumber.java.Before;


public class Hooks {

    @Before("@setupLocal")
    public static void initLocal() {
//        RestAssured.baseURI = "https://sellers.dev.webdad.by";
//        RestAssured.port = 443;
//        RestAssured.basePath = "/posts";
    }

}
