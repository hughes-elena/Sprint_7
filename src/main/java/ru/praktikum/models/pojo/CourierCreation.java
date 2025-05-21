package ru.praktikum.models.pojo;
//импортирую плагин lombok для уменьшения кода
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для представления данных нового курьера.
 * Аннотация @Data от Lombok автоматически создаёт:
 *   - геттеры и сеттеры для всех полей,
 *   - методы toString(), equals(), hashCode().
 * Аннотация @AllArgsConstructor создаёт конструктор со всеми полями:
 *   CourierCreation(String login, String password, String firstName)
 */

@Data
@AllArgsConstructor
public class CourierCreation {
    private String login;
    private String password;
    private String firstName;
}

    /* Тк я добавила @Data и @AllArgsConstructor, этот плагин lombok
    помог избежать громозкости кода. Сейчас код выглядит просто.
    Если бы я не использовала этот плагин - после кода, который есть, мне следовало написать
    следующий код:

    public CourierCreation(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
} */
