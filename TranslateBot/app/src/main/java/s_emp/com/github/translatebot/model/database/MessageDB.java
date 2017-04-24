package s_emp.com.github.translatebot.model.database;

import s_emp.com.github.translatebot.presenter.ITranslatable;

public class MessageDB {

    private long id;
    private int isBot;
    private String message;

    public MessageDB(int isBot, String message) {
        this.isBot = isBot;
        this.message = message;
    }

    public MessageDB(int isBot, ITranslatable message) {
        this.isBot = isBot;
        this.message = message.getSourceText();
    }

    public MessageDB(int id, int isBot, String message) {
        this.id = id;
        this.isBot = isBot;
        this.message = message;
    }

    public MessageDB(int id, int isBot, ITranslatable message) {
        this.id = id;
        this.isBot = isBot;
        this.message = message.getSourceText();
    }

    // region Get

    public long getId() {
        return id;
    }

    public int getIsBot() {
        return isBot;
    }


    public String getMessage() {
        return message;
    }


    // endregion

    // region Set

    public void setId(long id) {
        this.id = id;
    }

    public void setIsBot(int isBot) {
        this.isBot = isBot;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    // endregion


}
