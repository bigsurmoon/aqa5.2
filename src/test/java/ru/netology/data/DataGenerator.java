package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.domain.RegistrationInfo;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private Faker faker = new Faker(new Locale("en"));

    static void setUpUser(RegistrationInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public RegistrationInfo setActiveUser() {
        RegistrationInfo activeUser = new RegistrationInfo(faker.name().firstName(), faker.internet().password(), "active");
        setUpUser(activeUser);
        return activeUser;
    }

    public RegistrationInfo setBlockedUser() {
        RegistrationInfo blockedUser = new RegistrationInfo(faker.name().firstName(), faker.internet().password(), "blocked");
        setUpUser(blockedUser);
        return blockedUser;
    }
}