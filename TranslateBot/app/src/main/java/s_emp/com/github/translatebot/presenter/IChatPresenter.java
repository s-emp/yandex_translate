package s_emp.com.github.translatebot.presenter;

public interface IChatPresenter {

    void getHistory(int count);

    void getMenu();

    void sendMessage(String message);

    void updateData(ITranslatable translatedObj);
}
