package s_emp.com.github.translatebot.model.bot;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import s_emp.com.github.translatebot.presenter.ChatPresenter;

public interface IBotTranslate {

    // Перевод текста
    void translate(String text, ChatPresenter chatPresenter) throws IOException;

    // Получение кода языка по тексту
    void getLanguageText(String text, ChatPresenter chatPresenter) throws IOException;

    // Получить возможные языки перевода с определенного языка
    List<String> getListLanguageTranslate(String ui);

    // Получить название языка по коду
    String getNameLanguage(String ui);

    // Получить ui по названию языка
    String getUiLanguage(String name);

    // Получить весь список языков
    Map<String, String> getLanguages();
}
