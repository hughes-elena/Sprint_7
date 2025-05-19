package ru.praktikum.api;

//Создаю константы с URL-эндпоинтами для запросов к серверу
public class ApiEndpoints {

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru"; //основной сайт
    public static final String COURIER_POST_CREATE = "/api/v1/courier"; // ручка для создания курьера
    public static final String COURIER_POST_LOGIN = "/api/v1/courier/login"; // ручка для логина курьера
    public static final String ORDER_POST_CREATE = "/api/v1/orders"; //ручка для создания заказа
    public static final String ORDER_GET_LIST = "/api/v1/orders"; //ручка для получения списка заказов
    //Extra
    public static final String COURIER_DELETE = "/api/v1/courier/"; //ручка для удаления курьера
    public static final String ORDER_PUT_ACCEPT = "/api/v1/orders/accept/:id"; //ручка для принятия заказа
    public static final String ORDER_GET_TRACK = "/api/v1/orders/track"; //ручка для получения заказа по его номеру

}
