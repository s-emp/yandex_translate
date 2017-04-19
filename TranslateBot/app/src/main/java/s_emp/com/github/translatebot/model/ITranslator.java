package s_emp.com.github.translatebot.model;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import s_emp.com.github.translatebot.presenter.ChatPresenter;
import s_emp.com.github.translatebot.presenter.ITranslatable;

public interface ITranslator<T extends ITranslatable> {

    // Перевод текста
    void translate(ITranslatable translatedObj, ChatPresenter chatPresenter) throws IOException;

    // Получение кода языка по тексту
    void getSourceLang(T TranslatedObj) throws IOException;

    // Получение списка кодов языков
    Map<String, String> getLangs();

    // Получение название языка по коду
    String getNameLang(String ui);

    // Список возможных языков
    List<String> getListLangsTranslate(String ui);
}
