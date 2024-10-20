package old;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HelloTests {


    @Test
    public void testRestAssured() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Nikita");

        Response response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void withJsonPath() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Nikita");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = response.get("answer");
        System.out.println(answer);
    }

    @Test
    public void checkTypeGet() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Nikita");

        Response response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void checkTypePostParams() {

        Response response = RestAssured
                .given()
                .body("param1=value1&param2=value2")
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void checkTypePost() {

        Response response = RestAssured
                .given()
                .body("{\"param1\":\"value1\"}")
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void checkTypePostWithMap() {
        Map<String, String> body = new HashMap<>();
        body.put("param1", "value1");
        body.put("param2", "value2");
        body.put("param3", "value3");

        Response response = RestAssured
                .given()
                .body(body)
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.print();
    }

    @Test
    public void withStatusCode() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        int statusCode = response.getStatusCode();

        System.out.println(statusCode);
    }

    @Test
    public void statusCode500() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_500")
                .andReturn();

        int statusCode = response.getStatusCode();

        System.out.println(statusCode);
    }

    @Test
    public void statusCode404() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get00")
                .andReturn();

        int statusCode = response.getStatusCode();

        System.out.println(statusCode);
    }

    @Test
    public void statusCode303() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();

        System.out.println(statusCode);
    }

    @Test
    public void statusCode303AndRedirect() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        int statusCode = response.getStatusCode();

        System.out.println(statusCode);
    }

    @Test
    public void withHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "MyValue1");
        headers.put("myHeader2", "MyValue2");

        Response response = RestAssured
                .given()
                .headers(headers)
                .when()
                .get("https://playground.learnqa.ru/api/show_all_headers")
                .andReturn();

        response.prettyPrint();

        Headers resp = response.getHeaders();
        System.out.println(resp);
    }

    @Test
    public void withHeaders303() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "MyValue1");
        headers.put("myHeader2", "MyValue2");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();

        Headers resp = response.getHeaders();
        System.out.println(resp);

        String location = response.getHeader("Location");
        System.out.println(location);
    }

    @Test
    public void getAuthCookie() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\nPretty text");
        response.prettyPrint();

        System.out.println("\nPretty headers");
        Headers resp = response.getHeaders();
        System.out.println(resp);

        System.out.println("\nPretty cokkies");
        Map<String, String> cokkieMap = response.getCookies();
        System.out.println(cokkieMap);

        System.out.println("\nPretty authCokkie");
        String responseCookie = response.getCookie("auth_cookie");
        System.out.println(responseCookie);

    }

    @Test
    public void whenWrongCookie() {

        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass2");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        System.out.println("\nPretty text");
        response.prettyPrint();

        System.out.println("\nPretty headers");
        Headers resp = response.getHeaders();
        System.out.println(resp);

        System.out.println("\nPretty cokkies");
        Map<String, String> cokkieMap = response.getCookies();
        System.out.println(cokkieMap);
    }

    @Test
    public void whenGetCookieNext() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();


        System.out.println("\nPretty authCokkie");
        String responseCookie = response.getCookie("auth_cookie");

        Map<String, String> cookies = new HashMap<>();
        cookies.put("auth_cookie", responseCookie);

        Response req = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();

        req.print();


    }

    //
}