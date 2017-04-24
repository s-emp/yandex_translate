package s_emp.com.github.translatebot.presenter;

import android.view.MenuItem;
import android.widget.AdapterView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.model.Language;
import s_emp.com.github.translatebot.model.bot.Bot;
import s_emp.com.github.translatebot.model.TypeMessage;
import s_emp.com.github.translatebot.model.database.MessageDB;
import s_emp.com.github.translatebot.other.Const;
import s_emp.com.github.translatebot.other.Helper;
import s_emp.com.github.translatebot.view.IChatView;

public class ChatPresenter implements IChatPresenter {

    private IChatView view;
    private Bot bot;
    private Map<String, Object> map;

    // Чат
    private ArrayList<Map<String, Object>> dataChat = new ArrayList<>();
    private MessageSimpleAdapter chatAdapter;

    // Языки
    private ArrayList<Language> dataToLanguage = new ArrayList<>();
    private SpinnerAdapter toLanguageAdadpter;

    private ArrayList<Language> dataFromLanguage = new ArrayList<>();
    private SpinnerAdapter fromLanguageAdadpter;

    //TODO не уверен что это должно тут находиться
    private final static String MESSAGE_TEXT = "message";
    private final static String MESSAGE_IMAGE = "imageBot";
    private final static String SYS_IS_BOT = "bot";
    private final static String SYS_IS_HIST = "hist";
    private final static String SYS_IS_MARK = "mark";
    private final static String SYS_ID = "id";

    // Последний выполненый перевод, для закладок
    private String userText;
    private String botText;

    public ChatPresenter(IChatView view) {
        this.view = view;
        bot = new Bot(view, this);
        String[] fromChat = {MESSAGE_TEXT, MESSAGE_IMAGE};
        int[] toChat = {R.id.message, R.id.imageBot};
        chatAdapter = new MessageSimpleAdapter(view.getActivity(), dataChat,
                R.layout.message, fromChat, toChat);

        view.getChat().setAdapter(chatAdapter);

        toLanguageAdadpter = new SpinnerAdapter(view.getActivity(), dataToLanguage);
        view.getToLanguage().setAdapter(toLanguageAdadpter);

        fromLanguageAdadpter = new SpinnerAdapter(view.getActivity(), dataFromLanguage);
        view.getFromLanguage().setAdapter(fromLanguageAdadpter);


    }

    @Override
    public void getHistory(int count) {
        ArrayList<MessageDB> history = bot.getDataBase().getHist(count);
        for (int i = 0; i < history.size(); i++) {
            map = new HashMap<>();
            map.put(SYS_ID, history.get(i).getId());
            map.put(MESSAGE_TEXT, history.get(i).getMessage());
            map.put(MESSAGE_IMAGE, view.getItemImageBot());
            map.put(SYS_IS_BOT, true);
            map.put(SYS_IS_HIST, true);
            map.put(SYS_IS_MARK, false);
            dataChat.add(map);
        }
        chatAdapter.notifyDataSetChanged();
        view.getChat().smoothScrollToPosition(dataChat.size());
    }

    @Override
    public void sendMessage(TypeMessage typeMessage, String message) {
        switch (typeMessage) {
            case TRANSLATE:
                updateData(false, Const.FLAG_PEOPLE + message, true, false);
                setUserText(message);
                try {
                    // Если изначально не было инета, попробуем получить список языков
                    if (dataFromLanguage.size() < 1) {
                        bot.refreshMapLangs(this);
                    }
                    bot.translate(message, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default: updateData(false, Const.FLAG_PEOPLE + message, false, false);
                updateData(true, view.getActivity().getString(R.string.FAQ), false, false);
        }
    }

    @Override
    synchronized public void updateData(boolean isBot, String text, boolean isHist, boolean isMark) {
        map = new HashMap<>();
        if (isBot) {
            map.put(MESSAGE_TEXT, Helper.messageBot(text));
            map.put(MESSAGE_IMAGE, view.getItemImageBot());
        } else {
            map.put(MESSAGE_TEXT, text);
            map.put(MESSAGE_IMAGE, null);
        }
        map.put(SYS_IS_HIST, isHist);
        map.put(SYS_IS_MARK, isMark);
        map.put(SYS_IS_BOT, isBot);

        dataChat.add(map);
        chatAdapter.notifyDataSetChanged();
        view.getChat().smoothScrollToPosition(dataChat.size());
    }

    synchronized public void updateData(boolean isBot, MessageDB text, boolean isHist, boolean isMark) {
        map = new HashMap<>();
        map.put(SYS_ID, text.getId());
        if (isBot) {
            map.put(MESSAGE_TEXT, Helper.messageBot(text.getMessage()));
            map.put(MESSAGE_IMAGE, view.getItemImageBot());
        } else {
            map.put(MESSAGE_TEXT, text);
            map.put(MESSAGE_IMAGE, null);
        }
        map.put(SYS_IS_HIST, isHist);
        map.put(SYS_IS_MARK, isMark);
        map.put(SYS_IS_BOT, isBot);

        dataChat.add(map);
        chatAdapter.notifyDataSetChanged();
        view.getChat().smoothScrollToPosition(dataChat.size());
    }

    @Override
    public void switchLanguage() {
        if (!bot.isAutoLang()) {
            bot.setToLang(bot.getFromLang());
            view.switchLanguage();
        }
    }

    @Override
    public void determiteSuccess(String lang) {
        updateData(true, " Возможно исходный текст: " + bot.getNameLanguage(lang), false, false);
    }

    @Override
    public void determiteError(Integer code, String message) {
        updateData(true, "Упс что то пошло не так: \n" + message, false, false);
    }

    @Override
    public void translateSuccess(MessageDB text) {
        updateData(true, text.getMessage(), true, false);
        setBotText(text.getMessage());
    }

    @Override
    public void translateError(Integer code, MessageDB text) {
        updateData(true, "Упс... " + text.getMessage(), false, false);
    }

    @Override
    public void updateListLanguage(ArrayList<Language> fromLanguage, ArrayList<Language> toLanguage) {
        dataFromLanguage.clear();
        dataToLanguage.clear();

        for (Language lang: fromLanguage) {
            dataFromLanguage.add(lang);
        }
        for (Language lang: toLanguage) {
            dataToLanguage.add(lang);
        }

        toLanguageAdadpter.notifyDataSetChanged();
        fromLanguageAdadpter.notifyDataSetChanged();

        view.defaultTranslateLanguage();

        // Изменим язык перевода на отображаемый
        if (fromLanguage.size() >= 0) {
            bot.setFromLang(fromLanguage.get(0).getUi());
        }
        if (toLanguage.size() >= 0) {
            bot.setToLang(toLanguage.get(0).getUi());
        }

    }

    @Override
    public void setFromLanguage(int position) {
        bot.setFromLang(dataFromLanguage.get(position).getUi());
    }

    @Override
    public void setToLanguage(int position) {
        bot.setToLang(dataToLanguage.get(position).getUi());
    }

    @Override
    public void addMark() {
        if (userText != null && botText != null) {
            bot.addMark(new MessageDB(1, "Исх: " + userText + "\nПер:" +
                    botText.substring(botText.indexOf("Пер:")+4)));
            view.systemMessage("Закладка успешно добавлена");
            userText = null;
            botText = null;
        } else {
            view.systemMessage("К сожалению нельзя добавить закладку");
        }
    }

    @Override
    public void downChat() {
        view.getChat().smoothScrollToPosition(dataChat.size());
    }

    @Override
    public void deleteHistory() {
        bot.deleteHist();
        dataChat.clear();
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteMark(MenuItem item) {
        if (item == null) {
            bot.deleteMark(-1);
            view.systemMessage("Все закладки удалены");
        } else {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            if (info.position >= dataChat.size()) return;
            Map<String, Object> tmp = dataChat.get(info.position);
            if (tmp.get(SYS_IS_MARK) != null) {
                if ((boolean) tmp.get(SYS_IS_MARK)) {
                    if (tmp.get(SYS_ID) != null) {
                        bot.deleteMark((int) ((long) tmp.get(SYS_ID)));
                        view.systemMessage(view.getActivity().getString(R.string.successDeleteMark));
                        dataChat.remove(info.position);
                        chatAdapter.notifyDataSetChanged();
                    }
                } else {
                    view.systemMessage(view.getActivity().getString(R.string.errorDeleteMessage));
                }
            }
        }
    }

    @Override
    public void showAllMark() {
        updateData(true, Const.FLAG_MARK + view.getActivity().getString(R.string.showAllMarks), false, false);
        ArrayList<MessageDB> marks = bot.getMark();
        for (MessageDB msg :
                marks) {
            updateData(true, msg, false, true);
        }
    }

    public void setUserText(String userText) {
        this.userText = userText;
        botText = null;
    }

    public void setBotText(String botText) {
        this.botText = botText;
    }

    @Override
    public int getPositionFromLanguage(String ui) {
        for (int i = 0; i < dataFromLanguage.size(); i++) {
            if (dataFromLanguage.get(i).getUi().equals(ui)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getPositionToLanguage(String ui) {
        for (int i = 0; i < dataToLanguage.size(); i++) {
            if (dataToLanguage.get(i).getUi().equals(ui)) {
                return i;
            }
        }
        return -1;
    }
}
