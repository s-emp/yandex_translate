package s_emp.com.github.translatebot.model.bot;

public interface IBotSetting {

    // Задать автоопределение языка исходного текста
    void setAutoTranslate(boolean isAuto);

    // Получить, используется ли автоопределение языка
    boolean getAutoTranslate();

    void setFromLanguage(String language);
    String getFromLanguage();

    void setToLanguage(String language);
    String getToLanguage();

    // Информация о программе
    String getInfo();
}