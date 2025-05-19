package ru.praktikum.modelsPojo;

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

    @DisplayName("Тесты API логина курьера")
    public class CourierLoginTest {
        private final CourierSteps courierSteps = new CourierSteps();
        public static String login = "ElenaH";
        public static String password = "Qwerty123";
        public static String firstName = "Елена";

@Test
        @DisplayName("Авторизация курьера")
        @Description("Проверка, что курьер")

    }