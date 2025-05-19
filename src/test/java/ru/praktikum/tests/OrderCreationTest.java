package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.steps.OrderSteps;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Тесты создания заказа с разными цветами")
public class OrderCreationTest {
    private final OrderSteps orderSteps = new OrderSteps(); // 1. Инициализация
    private List<String> colours;


    public OrderCreationTest(List<String> colours, String testDescription) {
        this.colours = colours;
    }

    @Parameterized.Parameters(name = "Цвет самоката - {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {List.of("BLACK"), "Создание заказа с черным цветом"},
                {List.of("GREY"), "Создание заказа с серым цветом"},
                {List.of("BLACK", "GREY"), "Создание заказа с двумя цветами"},
                {List.of(), "Создание заказа без указания цвета"}
        };
    }

    /** Тест проверяет создание заказа с разными вариантами цветов.Проверяем: успешность создания (код 201) и наличие track в ответе
     */

    @Test
    @DisplayName("Создание заказа с разными цветами")
    @Description("Проверка создания заказа с различными вариантами цветов")
    public void shouldCreateOrderWithDifferentColours() {
        // Создаем заказ с указанными цветами
        orderSteps.createOrder(colours)
                // Проверяем ответ
                .then()
                .statusCode(201) // Ожидаем код 201 (Created)
                .body("track", notNullValue()); // Проверяем наличие track-номера
    }
}
