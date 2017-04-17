package s_emp.com.github.translatebot.view;

import android.view.View;

public interface IChat {

    /* Отправить сообщение, событие из view */
    void sendMessage(View view);

    /* Получить перевод сообщения, вызывается из presenter */
    void getTranslation(String msg);

    void goToLangTo();
}
