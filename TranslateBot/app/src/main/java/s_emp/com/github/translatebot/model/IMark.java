package s_emp.com.github.translatebot.model;

public interface IMark<T extends ITranslatable> {

    // Получить список закладок
    T getMark();

    // Удалить закладку по index
    void deleteMark(int index);

    //Добавить закладку
    void addMark(T mark);
}
