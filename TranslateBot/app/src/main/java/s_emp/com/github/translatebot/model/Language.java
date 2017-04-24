package s_emp.com.github.translatebot.model;

public class Language{
    private String ui;
    private String nameLanguage;

    public Language(String ui, String nameLanguage) {
        this.ui = ui;
        this.nameLanguage = nameLanguage;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }

}
