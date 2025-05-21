package ru.praktikum.models.pojo;
//импортирую плагин lombok для уменьшения кода
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CourierLogin {
    private String login;
    private String password;
}

/*    Использую плагин lombok и его аннотации Data AllArgsConstructor, чтобы они автоматом написали след код:
public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
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
}*/
