package s_emp.com.github.translatebot.presenter;

import s_emp.com.github.translatebot.model.TypeMessage;
import s_emp.com.github.translatebot.view.ILangView;

public interface ILangPresenter {

    /* Нажата кнопка смены языков друг на друга */
    void switchLang(ILangView view);
    /* Задается настройка с какого языка идет перевод */
    void setFromLang(String lang, ILangView view);
    /* Задается настройка на какой язык идет перевод */
    void setToLang(String lang, ILangView view);
    /* Получить весь список доступных языков */
    void getListLangs(ILangView view);
    /* Получить список доступных языков перевода с определенного языка */
    void getListLangs(ILangView view, String toLang);
    /* Отправить сообщение боту */
    void sendMessage(TypeMessage typeMessage, String message, ILangView view);

}
