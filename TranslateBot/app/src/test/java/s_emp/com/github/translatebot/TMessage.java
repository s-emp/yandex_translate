package s_emp.com.github.translatebot;

import s_emp.com.github.translatebot.model.ITranslatable;
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
        switch (typeMessage) {
            case TRANSLATE:
                translateText = text;
                break;
            default: break;
        }
        isFree = true;
    }

    @Override
    public void setMessageError(TypeMessage typeMessage, int code, String msg) {
        translateText = msg;
        isFree = true;
    }
}
