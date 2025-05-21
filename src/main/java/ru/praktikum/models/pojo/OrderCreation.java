package ru.praktikum.models.pojo;
//импортирую плагин lombok для уменьшения кода

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/*firstName	string
Имя заказчика, записывается в поле firstName таблицы Orders
lastName	string
Фамилия заказчика, записывается в поле lastName таблицы Orders
address	string
Адрес заказчика, записывается в поле adress таблицы Orders
metroStation	string
Ближайшая к заказчику станция метро, записывается в поле metroStation таблицы Orders
phone	string
Телефон заказчика, записывается в поле phone таблицы Orders
rentTime	number
Количество дней аренды, записывается в поле rentTime таблицы Orders
deliveryDate	string
Дата доставки, записывается в поле deliveryDate таблицы Orders
comment	string
Комментарий от заказчика, записывается в поле comment таблицы Orders
color необязательный	string[]
Предпочитаемые цвета, записываются в поле color таблицы Orders
 */

@Data
@AllArgsConstructor
public class OrderCreation {

    private String firstName = "Елена";
    private String lastName = "Лена";
    private String address = "Москва, ул. Арбат";
    private String metroStation = "Арбатская";
    private String phone = "89031111111";
    private int rentTime = 3;
    private String deliveryDate = "2025-05-26";
    private String comment = "Спасибо";
    private List<String> color;

    // Отдельный конструктор только для списка цветов
    public OrderCreation(List<String> color) {
        this.color = color;
    }
}

/*Остальной код не нужен, тк используем плагин lombok и его аннотации Data и AllArgsConstructor
public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMetroStation() {
        return metroStation;
    }
    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getRentTime() {
        return rentTime;
    }
    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }
    public String getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public List<String> getColor() {
        return color;
    }
    public void setColor(List<String> color) {
        this.color = color;
    }
}*/
