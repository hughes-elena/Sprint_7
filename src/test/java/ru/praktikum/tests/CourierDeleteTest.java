package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.pojo.CourierCreation;
import ru.praktikum.models.pojo.CourierLogin;
import ru.praktikum.steps.CourierSteps;
import static org.apache.http.HttpStatus.*;

import com.github.javafaker.Faker;; // для генерации случайных данных

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Тесты API удаления курьера")
public class CourierDeleteTest {
    // Инициализируем шаги для работы с API курьеров
    private final CourierSteps courierSteps = new CourierSteps();

    private final Faker faker = new Faker(); // Создаём объект Faker
    private  String login = faker.name().username();   // случайный логин
    private  String password = faker.internet().password(); // случайный пароль
    private final String firstName = faker.name().firstName();   // случайное имя


    @Before
    public void setUp() {
            CourierLogin loginData = new CourierLogin(login, password);
            courierSteps.deleteCourierAfterLogin(loginData);
        // Для каждого теста создаем нового курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(SC_CREATED);
    }

        @Test
        @DisplayName("Успешное удаление курьера")
        @Description("Создание, логин и удаление курьера. Проверка, что статус 200 и тело содержит ok=true")
        public void deleteCourierSuccessfully() {
            //Создаем курьера в аннотации Before

            //Логинимся и проверяем ответ
            CourierLogin loginData = new CourierLogin(login, password);
            courierSteps.loginCourier(loginData)
                    .then()
                    .statusCode(SC_OK)
                    .and()
                    .assertThat().body("id", instanceOf(Integer.class));
            // Удаляем созданного курьера после теста
            courierSteps.deleteCourierAfterLogin(loginData);
        }

        @Test
        @DisplayName("Удаление курьера без id")
        @Description("Проверка, что при попытке удалить курьера без id возвращается 400")
        public void deleteCourierWithoutId() {
            courierSteps.deleteCourierWithoutId()
                    .then()
                    .statusCode(SC_BAD_REQUEST)
                    .and()
                    .body("message", equalTo("Недостаточно данных для удаления курьера"));
        }

        @Test
        @DisplayName("Удаление несуществующего курьера")
        @Description("Проверка, что при удалении несуществующего курьера возвращается 404")
        public void deleteNonExistentCourier() {
        int nonExistentId = 999999;
            courierSteps.deleteNonExistentCourier(nonExistentId)
                    .then()
                    .statusCode(SC_NOT_FOUND)
                    .and()
                    .body("message", equalTo("Курьера с таким id нет."));
        }
    }
