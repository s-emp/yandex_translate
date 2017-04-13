package s_emp.com.github.translatebot.model.database;

import s_emp.com.github.translatebot.model.ITranslatable;

public class MessageDB {

    private int id;
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

    // region Get

    public int getId() {
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

    public void setId(int id) {
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
