package s_emp.com.github.translatebot;

import s_emp.com.github.translatebot.presenter.ITranslatable;
import s_emp.com.github.translatebot.model.TypeMessage;

/**
 * Created by emp on 13.04.17.
 */

public class TMessage implements ITranslatable {
    public boolean isFree = true;
    public String sourceText = "";
    public String translateText = "";

    @Override
    public String getSourceText() {
        isFree = false;
        return sourceText;
    }

    @Override
    public void setTranslatedText(TypeMessage typeMessage, String text) {
        translateText = text;
        isFree = true;
    }

    @Override
    public void setMessageError(TypeMessage typeMessage, int code, String msg) {
        translateText = msg;
        isFree = true;
    }
}
