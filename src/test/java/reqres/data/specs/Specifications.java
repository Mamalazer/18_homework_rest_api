package reqres.data.specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static reqres.config.Prop.PROP;

public class Specifications {

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(PROP.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(withCustomTemplates())
                .build();
    }

    public static ResponseSpecification responseSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpec204() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }

    public static ResponseSpecification responseSpec400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

}
