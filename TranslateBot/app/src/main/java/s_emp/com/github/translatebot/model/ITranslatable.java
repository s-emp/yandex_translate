package s_emp.com.github.translatebot.model;

// Интерфейс который должен быть реализован для классов поддерживаемых перевод
public interface ITranslatable<T extends String> {

    // Метод возвращаемый текст который будем переводить
    T getSourceText();

    // Метод для записи переведенного текста
    void setTranslatedText(T text);
}