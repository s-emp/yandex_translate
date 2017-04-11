package s_emp.com.github.translatebot.model;


import java.io.IOException;

public interface IHist<T extends ITranslatable> {

    // Добавить запись в историю
    void addHist(T obj) throws IOException;

    // Получить последние count записей, если -1 получить всю историю
    T getHist(int count);

    // Удалить начиная от и заканчивая до значений из истории
    void deleteHist(int from, int to);

    // Удалить значение из истории по index
    void deleteHist(int index);

    //Удалить всю историю
    void deleteHist();

}
