package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.modelsPojo.CourierCreation;
import ru.praktikum.modelsPojo.CourierLogin;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Тесты API удаления курьера")
public class CourierDeleteTest {
    // Инициализируем шаги для работы с API курьеров
    private final CourierSteps courierSteps = new CourierSteps();
    // Тестовые данные курьера
    public static String login = "ElenaH";
    public static String password = "Qwerty123";
    public static String firstName = "Елена";

    @Before
    public void setUp() {
            CourierLogin loginData = new CourierLogin(login, password);
            courierSteps.deleteCourierAfterLogin(loginData);
        // Для каждого теста создаем нового курьера
        CourierCreation courier = new CourierCreation(login, password, firstName);
        courierSteps.createCourier(courier)
                .then()
                .statusCode(201);
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
                    .statusCode(200)
                    .and()
                    .assertThat().body("id", instanceOf(Integer.class));
            // Удаляем созданного курьера после теста
            courierSteps.deleteCourierAfterLogin(loginData);
        }

        @Test
        @DisplayName("Удаление курьера без id")
        @Description("Проверка, что при попытке удалить курьера без id возвращается 400")
        public void deleteCourierWithoutId() {
            courierSteps.prepareRequest()
                    .delete("/api/v1/courier/")
                    .then()
                    .statusCode(400)
                    .and()
                    .body("message", equalTo("Недостаточно данных для удаления курьера"));
        }

        @Test
        @DisplayName("Удаление несуществующего курьера")
        @Description("Проверка, что при удалении несуществующего курьера возвращается 404")
        public void deleteNonExistentCourier() {
            courierSteps.prepareRequest()
                    .delete("/api/v1/courier/999999")
                    .then()
                    .statusCode(404)
                    .and()
                    .body("message", equalTo("Курьера с таким id нет."));
        }
    }
