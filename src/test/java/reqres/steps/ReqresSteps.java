package reqres.steps;

import io.qameta.allure.Step;
import reqres.data.models.*;
import reqres.data.specs.Specifications;

import java.util.List;

import static io.restassured.RestAssured.given;
import static reqres.data.Endpoints.*;

public class ReqresSteps {

    @Step("Получить список пользователей")
    public static List<UserData> getUsers() {
        return given()
                .spec(Specifications.requestSpec())
                .when().log().all()
                .get(LIST_USERS)
                .then().log().all()
                .spec(Specifications.responseSpec200())
                .extract()
                .body().jsonPath().getList("data", UserData.class);
    }

    @Step("Успешная регистрация нового пользователя")
    public static SuccessfulRegistration successfulRegistration(RegistrationInfo regInfo) {
        return given()
                .spec(Specifications.requestSpec())
                .body(regInfo)
                .when().log().all()
                .post(REGISTER)
                .then().log().all()
                .spec(Specifications.responseSpec200())
                .extract().as(SuccessfulRegistration.class);
    }

    @Step("Неуспешная регистрация пользователя")
    public static UnsuccessfulRegistration unsuccessfulRegistration(RegistrationInfo regInfo) {
        return given()
                .spec(Specifications.requestSpec())
                .body(regInfo)
                .when().log().all()
                .post(REGISTER)
                .then().log().all()
                .spec(Specifications.responseSpec400())
                .extract().as(UnsuccessfulRegistration.class);
    }

    @Step("Получение списка ресурсов")
    public static List<ListResource> getResources() {
        return given()
                .spec(Specifications.requestSpec())
                .when().log().all()
                .get(RESOURCES)
                .then().log().all()
                .spec(Specifications.responseSpec200())
                .extract().body().jsonPath().getList("data", ListResource.class);
    }

    @Step("Удаление пользователя")
    public static void deleteUser(int userId) {
        given()
                .spec(Specifications.requestSpec())
                .when().log().all()
                .delete(DELETE + userId)
                .then().log().all()
                .spec(Specifications.responseSpec204());
    }

    @Step("Изменение пользователя")
    public static UpdatedUser updateUserInfo(UpdateUserInfo updateUserInfo, int userId) {
        return given()
                .spec(Specifications.requestSpec())
                .body(updateUserInfo).log().all()
                .when()
                .put(UPDATE + userId)
                .then().log().all()
                .spec(Specifications.responseSpec200())
                .extract().as(UpdatedUser.class);
    }

    @Step("Получить пользователя по id")
    public static SingleUser getUserById(int id) {
        return given()
                .spec(Specifications.requestSpec())
                .when()
                .get(SINGLE_USER + id)
                .then().log().all()
                .spec(Specifications.responseSpec200())
                .extract().as(SingleUser.class);
    }
}
