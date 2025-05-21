package ru.praktikum.models.pojo;
//импортирую плагин lombok для уменьшения кода
import lombok.AllArgsConstructor;
import lombok.Data;

//Создала отдельный класс на получение id курьера

@Data
@AllArgsConstructor
public class CourierId {
    private int id;
}

/* Использую плагин lombok и его аннотации Data AllArgsConstructor, чтобы они автоматом написали след код:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}*/
