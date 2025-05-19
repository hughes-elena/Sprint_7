package ru.praktikum.tests;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.api.ApiEndpoints;
import ru.praktikum.modelsPojo.CourierCreation;
import ru.praktikum.modelsPojo.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("Тесты API создания курьера")
public class CourierTest {
    private final CourierSteps courierSteps = new CourierSteps();
    public static String login = "ElenaH";
    public static String password = "Qwerty123";
    public static String firstName = "Елена";



    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка, что система создаёт курьера с валидными данными")
    public void successfulCreationCourier() {
        CourierCreation courier = new CourierCreation(login, password, firstName);


        courierSteps.createCourier(courier) //создаем нового курьера
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData); //удаляем
    }


    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка, что система не создаёт курьера с одинаковыми данными")
    public void creationSameCourier() {
        CourierCreation courier = new CourierCreation(login, password, firstName);
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.createCourier(courier)  //первое создание
                .then()
                .statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
        courierSteps.createCourier(courier)  //второе создание
                .then()
                .statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierSteps.deleteCourierAfterLogin(loginData); //удаляем
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка, что система не создает курьера без заполнения обязательных полей")

    public void creationCourierWithoutLogin() {
        CourierCreation courier = new CourierCreation(null, password, firstName);

        courierSteps.createCourier(courier)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка, что система не создает курьера без заполнения обязательных полей")

    public void creationCourierWithoutPassword() {
        CourierCreation courier = new CourierCreation(login, null, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


}
