package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.given;
import org.junit.Test;
import ru.praktikum.api.ApiEndpoints;
import ru.praktikum.steps.OrderSteps;

import static org.hamcrest.Matchers.*;
import static ru.praktikum.api.ApiEndpoints.ORDER_GET_LIST;

@DisplayName("Тесты получения списка заказов")
public class OrderListTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что API возвращает список заказов")

    public void shouldReturnListOfOrders() {
            given().log().all()
                    .baseUri(ApiEndpoints.BASE_URL)
                    .get(ORDER_GET_LIST)
                    .then()
                    .assertThat().body("orders", notNullValue())
                    .and()
                    .statusCode(200);
    }
}
