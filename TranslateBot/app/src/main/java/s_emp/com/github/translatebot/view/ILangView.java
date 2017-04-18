package s_emp.com.github.translatebot.view;

import java.util.List;

import s_emp.com.github.translatebot.model.TypeMessage;

/**
 * Created by emp on 17.04.17.
 */

public interface ILangView {
    /* Метод обновления отображения языков с какого на какой идет перевод */
    void updateLang(String langFrom, String langTo);
    /* Метод получения списка языков */
    void getListLangs(List<String> listLangs);
    /* Метод получения ответа от бота на посланное сообщение */
    void getAnswer(TypeMessage typeMessage, String message);
}
