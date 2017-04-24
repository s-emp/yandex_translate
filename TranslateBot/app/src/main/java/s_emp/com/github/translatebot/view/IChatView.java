package s_emp.com.github.translatebot.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public interface IChatView {

    /* Поменять языки перевода */
    void switchLanguage();
    void defaultTranslateLanguage();

    /* Получить активити, нужно для работы с БД */
    AppCompatActivity getActivity();

    /* Получить определенный объект VIEW */
    int getItemImageBot();
    EditText getItemMassage();
    ListView getChat();
    Spinner getToLanguage();
    Spinner getFromLanguage();

    /* Очисть поле ввода */
    void clearTextField();

    void systemMessage(String message);
}
