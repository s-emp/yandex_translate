package s_emp.com.github.translatebot.model.database;

import java.io.IOException;
import java.util.ArrayList;

public interface IHistory {

    // Добавить запись в историю
    void addHist(MessageDB obj) throws IOException;

    // Получить последние count записей, если -1 получить всю историю
    ArrayList<MessageDB> getHist(int count);

    // Получить от n до m записей из истории
    ArrayList<MessageDB> getHist(int from, int to);

    // Удалить начиная от и заканчивая до значений из истории
    //void deletehist(int from, int to);

    // Удалить значение из истории по index
    void deleteHist(int index);

    //Удалить всю историю
    void deleteHist();



}
