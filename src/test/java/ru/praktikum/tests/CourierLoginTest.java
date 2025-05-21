package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.pojo.CourierCreation;
import ru.praktikum.models.pojo.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import com.github.javafaker.Faker;; // для генерации случайных данных

@DisplayName("Тесты API логина курьера")
public class CourierLoginTest {
    private final CourierSteps courierSteps = new CourierSteps();
    private final Faker faker = new Faker(); // Создаём объект Faker
    private  String login = faker.name().username();   // случайный логин
    private  String password = faker.internet().password(); // случайный пароль
    private final String firstName = faker.name().firstName();   // случайное имя

    @Before
    public void setUp() {
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData); //Удалим курьера, если был создан
        // Для каждого теста создаем нового курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными данными")
    public void authCourierWithCorrectData() {
        //Логинимся и проверяем ответ
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.loginCourier(loginData)
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat().body("id", instanceOf(Integer.class));
        /* Удаляем созданного курьера после теста
        courierSteps.deleteCourierAfterLogin(loginData);*/
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Проверка, что курьеру нельзя авторизоваться под несуществующим логином")
    public void authCourierWithWrongLogin() {
        // Поменяли логин до аннотации @Before
        login = "courier_does_not_exist_12345";
        //Логинимся и проверяем ответ
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.loginCourier(loginData)
                .then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Проверка, что курьеру нельзя авторизоваться под несуществующим паролем")
    public void authCourierWithWrongPassword() {
        // Поменяли пароль до аннотации @Before
        password = "password_does_not_exist_12345";
        //Логинимся и проверяем ответ
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.loginCourier(loginData)
                .then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьеру нельзя авторизоваться без ввода логина")
    public void authCourierWithoutLogin() {

        //Логинимся и проверяем ответ
        CourierLogin loginWithoutLogin = new CourierLogin(null, password);
        courierSteps.loginCourier(loginWithoutLogin)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
        /* Удаляем созданного курьера после теста
        CourierLogin validCourierLogin = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(validCourierLogin);*/
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьеру нельзя авторизоваться без ввода пароля")
    public void authCourierWithoutPassword() {

        //Логинимся и проверяем ответ
        CourierLogin loginDataWithoutPassword = new CourierLogin(login, null);
        courierSteps.loginCourier(loginDataWithoutPassword)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))                ;
        /* Удаляем созданного курьера после теста
        CourierLogin validCourierLogin = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(validCourierLogin);*/

    }

    @After
    public void cleanUp() {
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData); // Если курьер был создан
    }
}



