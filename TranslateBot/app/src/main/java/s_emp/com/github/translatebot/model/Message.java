package s_emp.com.github.translatebot.model;

import android.util.Log;

public class Message implements ITranslatable {

    private String translateText = "";
    private String sourceText = "";

    @Override
    public synchronized String getSourceText() {
        return sourceText;
    }

    @Override
    public synchronized void setTranslatedText(TypeMessage typeMessage, String text) {
        switch (typeMessage){
            case TRANSLATE:
                translateText = text;
                //TODO: метод показа сообщения в UI
                break;
            case SUGGESTION: break;
        }
    }

    @Override
    public synchronized void setMessageError(TypeMessage typeMessage, int code, String msg) {
        switch (typeMessage) {
            case ERROR:
                Log.println(Log.ERROR, "Rest error ", code + " " + msg);
                break;
            case ERROR_API:
                Log.println(Log.ERROR, "API error ", code + " " + msg);
                break;
        }
    }

    public String getTranslateText() {
        return translateText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }
}
