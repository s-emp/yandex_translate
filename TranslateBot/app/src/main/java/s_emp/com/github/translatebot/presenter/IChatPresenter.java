package s_emp.com.github.translatebot.presenter;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import s_emp.com.github.translatebot.model.Language;
import s_emp.com.github.translatebot.model.TypeMessage;
import s_emp.com.github.translatebot.model.database.MessageDB;

public interface IChatPresenter {

    void getHistory(int count);

    void sendMessage(TypeMessage translate, String message);

    void updateData(boolean isBot, String text, boolean isHist, boolean isMark);

    void switchLanguage();

    void determiteSuccess(String lang);

    void determiteError(Integer code, String message);

    void translateSuccess(MessageDB text);

    void translateError(Integer code, MessageDB text);

    void updateListLanguage(ArrayList<Language> fromLanguage, ArrayList<Language> toLanguage);

    void setFromLanguage(int position);

    void setToLanguage(int position);

    void addMark();

    void downChat();

    void deleteHistory();

    void deleteMark(MenuItem item);

    void showAllMark();

    int getPositionFromLanguage(String ui);

    int getPositionToLanguage(String ui);
}
