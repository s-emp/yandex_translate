package s_emp.com.github.translatebot.presenter;

import android.util.Log;

import s_emp.com.github.translatebot.model.TypeMessage;

public class Message implements ITranslatable {
    // Перевод текста
    private String translateText = "";

    private TypeMessage typeMessage;
    // Исходный текст
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
                break;
            case SUGGESTION:
                Log.println(Log.INFO, "TEST SUGGESTION: ", text);
                break;
            default: break;
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

    @Override
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    @Override
    public String getTranslateText() {
        return translateText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public Message(TypeMessage typeMessage, String sourceText) {
        this.typeMessage = typeMessage;
        this.sourceText = sourceText;
    }
}
