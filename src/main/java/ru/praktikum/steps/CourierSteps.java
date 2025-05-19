package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.api.ApiEndpoints;
import ru.praktikum.modelsPojo.*;

import static io.restassured.RestAssured.given;

//Создаю класс, который содержит шаги Steps для работы с API курьеров.
//Нужен для создания, логина и удаления курьеров.

public class CourierSteps {

    public static RequestSpecification prepareRequest() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ApiEndpoints.BASE_URL);
    }

    @Step("Создать нового курьера") //Аннотация для Allure-отчета
    public Response createCourier(CourierCreation courierCreation) {
        return prepareRequest()
                .body(courierCreation)
                .post(ApiEndpoints.COURIER_POST_CREATE);
    }

    @Step("Залогинить курьера") //Аннотация для Allure-отчета
    public Response loginCourier(CourierLogin identityCourier) {
        return prepareRequest()
                .body(identityCourier)
                .when()
                .post(ApiEndpoints.COURIER_POST_LOGIN);
    }

    @Step("Удалить курьера по ID") //Аннотация для Allure-отчета
    public void deleteCourier(int courierId) {
        prepareRequest()
                .when()
                .delete(ApiEndpoints.COURIER_DELETE + courierId)
                .then()
                .statusCode(200);

    }

    @Step("Логин курьера, получение ID и его удаление") //Аннотация для Allure-отчета
    public void deleteCourierAfterLogin(CourierLogin identityCourier) {
        Response response = loginCourier(identityCourier); //Метод отправляет post-запрос на логин курьера
        if (response.getStatusCode() == 200) {
            CourierId courierId = response.as(CourierId.class);
            int courierIdNumber = courierId.getId();

            if (courierIdNumber != 0) {
                deleteCourier(courierIdNumber);
            }
        } else {
            System.out.println("Курьер не найден при логине — удаление не требуется");
        }; //удаляем курьера по Id
    }
}
