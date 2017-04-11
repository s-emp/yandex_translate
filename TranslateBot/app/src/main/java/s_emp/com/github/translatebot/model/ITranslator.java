package s_emp.com.github.translatebot.model;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ITranslator<T extends ITranslatable> {

    // Перевод текста
    void translate (T TranslatedObj) throws IOException;

    // Получение кода языка по тексту
    String getSourceLang(T TranslatedObj) throws IOException;

    // Получение списка кодов языков
    List<String> getLangs();

    // Получение название языка по коду
    String getNameLang(String ui);

    // Получение возможных переводов с языка на язык
    Map<String, String> getTranslateLangs();
}
