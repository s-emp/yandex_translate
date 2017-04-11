package s_emp.com.github.translatebot.model;

import java.util.Map;


public interface IListLangs {

    // Метод вызывается после обновления списка языков
    void listLangs(Map<String, String> langs);
}
