package demowebshop.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemoshopTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }

    @Test
    @DisplayName("Добавление товаров в корзину")
    public void test01() {
        String cookieValue = "971B3B701C129564617B2E462C2BBF54A329C1215FD6B9CD979649A78AA861C71F2D9FA16BB57A243DC6664BC" +
                "F7AF07E8DF8B2183752A6435BF18FC981D4D91B252A8F508485C76AC734AB7302347B76B4BE5F2B0F8EF0C19F8F26C04460C0" +
                "88B89BD2167CDE738E8A16EF19D9186FF7FE27A9EC34255AB49D3EEAD83FE5EB50B918A2ED2738C9FAF388AA6544264C9E;";
        Map<String, String> body = Map.of(
                "product_attribute_72_5_18", "53",
                "product_attribute_72_6_19", "54",
                "product_attribute_72_3_20", "57",
                "addtocart_72.EnteredQuantity", "1"
        );

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", cookieValue)
                .formParams(body).log().all()
                .when()
                .post("/addproducttocart/details/72/1")
                .then().log().all()
                .statusCode(200)
                .body("success", is(true));
    }
}