package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.modelsPojo.CourierCreation;
import ru.praktikum.modelsPojo.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;


@DisplayName("Тесты API логина курьера")
public class CourierLoginTest {
    private final CourierSteps courierSteps = new CourierSteps();
    public static String login = "ElenaH";
    public static String password = "Qwerty123";
    public static String firstName = "Елена";

    @Before
    public void setUp() {
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData); // Если курьер был создан
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    public void authCourierWithCorrectData() {
        //Создаем курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(201);
        //Логинимся и проверяем ответ
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.loginCourier(loginData)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("id", instanceOf(Integer.class));
        // Удаляем созданного курьера после теста
        courierSteps.deleteCourierAfterLogin(loginData);
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Проверка, что курьеру нельзя авторизоваться под несуществующим пользователем")
    public void authCourierWithWrongLogin() {

        //Логинимся и проверяем ответ
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.loginCourier(loginData)
                .then()
                .statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьеру нельзя авторизоваться без ввода логина")
    public void authCourierWithoutLogin() {
        //Создаем курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(201);
        //Логинимся и проверяем ответ
        CourierLogin loginWithoutLogin = new CourierLogin(null, password);
        courierSteps.loginCourier(loginWithoutLogin)
                .then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
        // Удаляем созданного курьера после теста
        CourierLogin validCourierLogin = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(validCourierLogin);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьеру нельзя авторизоваться без ввода пароля")
    public void authCourierWithoutPassword() {
        //Создаем курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(201);
        //Логинимся и проверяем ответ
        CourierLogin loginDataWithoutPassword = new CourierLogin(login, null);
        courierSteps.loginCourier(loginDataWithoutPassword)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        // Удаляем созданного курьера после теста
        CourierLogin validCourierLogin = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(validCourierLogin);

    }
    @After
    public void cleanUp() {
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData); // Если курьер был создан
    }
}



