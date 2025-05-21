package ru.praktikum.models.pojo;
//импортирую плагин lombok для уменьшения кода
import lombok.AllArgsConstructor;
import lombok.Data;


@Data                   // @Data от Lombok автоматически создаёт: -геттеры и сеттеры для всех полей, - методы toString(), equals(), hashCode().
@AllArgsConstructor     // @AllArgsConstructor создаёт конструктор со всеми полями: CourierCreation(String login, String password, String firstName)
public class CourierDelete {
    int id;
}

/*    public CourierDelete(int id) {
        id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }
} */
