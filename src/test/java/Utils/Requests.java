package Utils;

import Constants.EmployeeEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Requests {
    private String baseUrl;
    public Requests(String _baseUrl){
        baseUrl=_baseUrl;
    }
    public  Response get(String endpoint){
        RestAssured.baseURI= baseUrl;
        Response response = RestAssured
                .when().get(endpoint);
        response.then().log().body();
        return response;
    }
    public  Response getWithId(String endpoint , String id){
        RestAssured.baseURI= baseUrl;
        Response response = RestAssured
                .given().pathParam("id",id)
                .when().get(endpoint);
        response.then().log().body();
        return response;
    }
    public  Response post(String endpoint , String payload){
        RestAssured.baseURI= baseUrl;
        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .when().post(endpoint);

        response.then().log().body();
        return response;
    }
    public  Response put(String endpoint, String id, String payload){
        RestAssured.baseURI= baseUrl;
        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .and().pathParam("id",id)
                .when().put(endpoint);

        response.then().log().body();
        return response;
    }
    public  Response delete(String endpoint, String id){
        RestAssured.baseURI= baseUrl;
        Response response = RestAssured
                .given().pathParam("id",id)
                .when().delete(endpoint);
        response.then().log().body();
        return response;
    }
}