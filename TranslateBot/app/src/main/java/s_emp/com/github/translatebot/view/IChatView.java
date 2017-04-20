package s_emp.com.github.translatebot.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public interface IChatView {

    /* Отправить сообщение, событие из view */
    void sendMessage(View view);

    /* Поменять языки перевода */
    void switchLanguage(View view);

    void goToLangTo();

    /* Получить активити, нужно для работы с БД */
    AppCompatActivity getActivity();

    /* Получить определенный объект VIEW*/
    int getItemImageBot();
    EditText getItemMassage();
    ListView getChat();
    ListView getMenu();

    /* Очисть поле ввода */
    void clearTextField();
}
