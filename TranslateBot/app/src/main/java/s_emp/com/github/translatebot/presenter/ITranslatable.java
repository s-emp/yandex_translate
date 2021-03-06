package s_emp.com.github.translatebot.presenter;

import s_emp.com.github.translatebot.model.TypeMessage;

// Интерфейс который должен быть реализован для классов поддерживаемых перевод
public interface ITranslatable<T extends String> {

    // Метод возвращает текст который будем переводить
    T getSourceText();

    // Метод для записи переведенного текста
    void setTranslatedText(TypeMessage typeMessage, T text);

    /* Метод вызывается в случае какой либо ошибки,
    TypeMessage - ERROR - ошибка rest
                - ERROR_API - ошибка api yandex
     */
    void setMessageError(TypeMessage typeMessage, int code, String msg);

    /* Возвращает тип сообщения */
    TypeMessage getTypeMessage();

    String getTranslateText();
}