package ru.praktikum.tests;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.models.pojo.CourierCreation;
import ru.praktikum.models.pojo.CourierLogin;
import ru.praktikum.steps.CourierSteps;
import static org.apache.http.HttpStatus.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import com.github.javafaker.Faker; // для генерации случайных данных

@DisplayName("Тесты API создания курьера")
public class CourierCreationTest {
    // Инициализируем шаги для работы с API курьеров
    private final CourierSteps courierSteps = new CourierSteps();
    private final Faker faker = new Faker(); // Создаём объект Faker

    // Создаем случайные тестовые данные курьера
    private  String login = faker.name().username();
    private  String password = faker.internet().password();
    private final String firstName = faker.name().firstName();
    private final CourierLogin loginData = new CourierLogin(login, password);

    @After
    public void tearDown() {
        courierSteps.deleteCourierAfterLogin(loginData);
    }
    /**
     * Тест на успешное создание курьера с валидными данными.
     * Проверяем: 1. Код ответа 201 (Created) 2. Наличие поля "ok: true" в ответе 3. После теста удаляем этого курьера
     */
    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка, что система создаёт курьера с валидными данными")
    public void successfulCreationCourier() {
        // Создаем объект курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData);
        //Отправляем запрос на создание курьера и проверки:
        courierSteps.createCourier(courier) //создаем нового курьера
                .then()
                .statusCode(SC_CREATED)
                .and()
                .assertThat().body("ok", equalTo(true));

    }

    /**
     * Тест на создание двух одинаковых курьеров. Проверяем: 1. Первое создание должно быть успешным (201)
     * 2. Второе создание должно вернуть ошибку 409 (Conflict) 3. Сообщение об ошибке должно соответствовать ожидаемому
     * 4. После теста удаляем данного курьера
     */
    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка, что система не создаёт курьера с одинаковыми данными")
    public void creationSameCourier() {
        // Подготовка тестовых данных
        CourierCreation courier = new CourierCreation(login, password, firstName);
        CourierLogin loginData = new CourierLogin(login, password);
        courierSteps.deleteCourierAfterLogin(loginData);

        // Первое создание курьера (должно быть успешным)
        courierSteps.createCourier(courier)
                .then()
                .statusCode(SC_CREATED)
                .and()
                .assertThat().body("ok", equalTo(true));
        // Второе создание такого же курьера (должно вернуть ошибку)
        courierSteps.createCourier(courier)  //второе создание
                .then()
                .statusCode(SC_CONFLICT)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        // Удаляем созданного курьера
        courierSteps.deleteCourierAfterLogin(loginData); //удаляем
    }

    /**
     * Тест создания курьера без указания логина. Проверяем:1. Код ответа 400 (Bad Request)
     * 2. Сообщение об ошибке
     */
    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка, что система не создает курьера без заполнения обязательных полей")

    public void creationCourierWithoutLogin() {
        // Создаем курьера без логина (null)
        CourierCreation courier = new CourierCreation(null, password, null);

        // Отправляем запрос и проверяем ошибки
        courierSteps.createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    /**
     * Тест создания курьера без указания пароля. Проверяем:1. Код ответа 400 (Bad Request)
     * 2. Сообщение об ошибке
     */
    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка, что система не создает курьера без заполнения обязательных полей")

    public void creationCourierWithoutPassword() {
        // Создаем курьера без пароля (null)
        CourierCreation courier = new CourierCreation(login, null, null);
        // Отправляем запрос и проверяем ошибки
        courierSteps.createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}