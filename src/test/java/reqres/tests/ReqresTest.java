package reqres.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reqres.data.models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reqres.config.Prop.PROP;
import static reqres.steps.ReqresSteps.*;

public class ReqresTest {

    @Test
    @DisplayName("Проверка аватаров пользователей")
    public void checkAvatarAndId() {
        List<UserData> users = getUsers();

        users.forEach(x -> assertTrue(x.getAvatar().contains(x.getId().toString()),
                "Аватар не содержит id пользователя"));

        assertTrue(users.stream()
                .allMatch(x -> x.getEmail().endsWith("reqres.in")),
                "Email не заканчивается на reqres.in");
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void successRegistration() {
        RegistrationInfo regInfo = new RegistrationInfo(PROP.getEmail(), PROP.getPassword());

        SuccessfulRegistration successfulRegistration = successfulRegistration(regInfo);

        assertEquals(4, successfulRegistration.getId(), "Неверный Id");
        assertEquals(PROP.getToken(), successfulRegistration.getToken(), "Неверный токен");
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя")
    public void failedRegistration() {
        RegistrationInfo reginfo = new RegistrationInfo(PROP.getEmail(), "");

        UnsuccessfulRegistration unsuccessfulRegistration = unsuccessfulRegistration(reginfo);

        assertEquals("Missing password", unsuccessfulRegistration.getError(),
                "Неверный текст ошибки или отсутствие ошибки при регистрации");
    }

    @Test
    @DisplayName("Проверка сортировки по году")
    public void checkOrderOfYearsInResources() {

        List<ListResource> resources = getResources();

        List<Integer> actual = resources.stream()
                .map(ListResource::getYear)
                .collect(Collectors.toList());

        List<Integer> expected = resources.stream()
                .map(ListResource::getYear)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(expected, actual, "Неверная сортировка годов в ответе");
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void delUser() {

        deleteUser(2);
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    public void updateUser() {

        UpdateUserInfo updateUserInfo = new UpdateUserInfo("morpheus", "zion resident");

        UpdatedUser updatedUser = updateUserInfo(updateUserInfo, 2);

        assertTrue(updatedUser.getUpdatedAt().contains(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                "Дата обновления информации о пользователе не совпадает с текущей");

    }

    @Test
    @DisplayName("Получение пользователя по id")
    public void getSingleUser() {

        UserData userData = new UserData(2, "janet.weaver@reqres.in", "Janet", "Weaver",
                "https://reqres.in/img/faces/2-image.jpg");

        SingleUser singleUser = getUserById(2);

        Assertions.assertEquals(userData, singleUser.getData(), "Возвращён неверный user");
    }
}
