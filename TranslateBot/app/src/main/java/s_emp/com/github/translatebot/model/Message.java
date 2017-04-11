package s_emp.com.github.translatebot.model;

import s_emp.com.github.translatebot.model.ITranslatable;

/**
 * Created by emp on 10.04.17.
 */

public class Message implements ITranslatable {

    private String message = "";

    @Override
    public String getSourceText() {
        return message;
    }

    @Override
    public void setTranslatedText(String text) {
        message = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
