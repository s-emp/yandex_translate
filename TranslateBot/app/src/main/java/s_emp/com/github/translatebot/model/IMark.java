package s_emp.com.github.translatebot.model;

// Для работы с закладками
public interface IMark<T extends ITranslatable> {

    //Загрузить список закладок
    void loadMark();

    // Получить список закладок
    T getMark();

    // Удалить закладку по index
    void deleteMark(int index);

    //Добавить закладку
    void addMark(T mark);
}
