package s_emp.com.github.translatebot.model.database;

import java.util.ArrayList;

// Для работы с закладками
public interface IMark {

    // Получить список закладок
    ArrayList<MessageDB> getMark();

    // Удалить закладку по index
    void deleteMark(int index);

    //Добавить закладку
    void addMark(MessageDB mark);
}
