package netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    public static RequestSpecification requstSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void validLogin(DataHelper.AuthInfo user) {
        given()
                .spec(requstSpec)
                .body(user)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }

    public static String getToken(DataHelper.InfoForToken data) {

        String token =
                given()
                        .spec(requstSpec)
                        .body(data)
                        .when()
                        .post("/api/auth/verification")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");
        return token;
    }

    public static void transferFrom(DataHelper.TransferInfo transferInfo, String token, int status) {
        given()
                .headers(
                        "Authorization",
                        "Bearer " + token)
                .spec(requstSpec)
                .body(transferInfo)
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(status);
    }

    public static int getBalance(String id, String token) {
        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + token)
                        .spec(requstSpec)
                        .when()
                        .get("/api/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        if (response.path("[0].id").equals(id)) {
            return response.path("[0].balance");
        } else return response.path("[1].balance");
    }

    public static int generateValidAmount(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(1000);
    }
}
