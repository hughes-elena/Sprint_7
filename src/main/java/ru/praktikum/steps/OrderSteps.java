package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.api.ApiEndpoints;
import ru.praktikum.models.pojo.CourierId;
import ru.praktikum.models.pojo.CourierLogin;
import ru.praktikum.models.pojo.OrderCreation;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

//Создаю класс, который содержит шаги Steps для работы с API заказов.
//Нужен для создания заказа

public class OrderSteps {

    // Подготавливаем базовый запрос со всеми нужными параметрами
    public static RequestSpecification prepareRequest() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ApiEndpoints.BASE_URL);
    }
    /** Шаг создает новый заказ с указанными цветами.@param colors список цветов (может быть null, если цвет не указан)      * @return Response объект с ответом от сервера*/

    @Step("Создать новый заказ") //Аннотация для Allure-отчета
    public Response createOrder(List<String> colors) {
        //Создаем объект заказа с переданными цветами
        OrderCreation order = new OrderCreation(colors);

        //Отправляем POST-запрос на создание заказа
        return prepareRequest() // Начинаем построение запроса
                .body(order) // Передаем тело запроса
                .when() // Указываем, что дальше идет действие
                .post(ApiEndpoints.ORDER_POST_CREATE); // Отправляем POST на эндпоинт

    }

    /** Шаг проверяет, что в ответе на создание заказа есть track-номер. @param response ответ от сервера для проверки*/


    @Step("Отменить заказ по track-номеру")
    public Response cancelOrder(int track) {
       return prepareRequest()
               .queryParam("track", track)
               .when()
               .put("/api/v1/orders/cancel");
    }

}

