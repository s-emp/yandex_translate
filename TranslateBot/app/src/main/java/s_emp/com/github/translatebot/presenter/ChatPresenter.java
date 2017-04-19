package s_emp.com.github.translatebot.presenter;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.model.TranslatorBot;
import s_emp.com.github.translatebot.model.TypeMessage;
import s_emp.com.github.translatebot.model.database.MessageDB;
import s_emp.com.github.translatebot.other.Const;
import s_emp.com.github.translatebot.view.IChatView;

import static s_emp.com.github.translatebot.model.TypeMessage.TRANSLATE;

public class ChatPresenter implements IChatPresenter {

    private IChatView view;
    private TranslatorBot bot;
    private Map<String, Object> map;

    // Чат
    private ArrayList<Map<String, Object>> dataChat = new ArrayList<>();
    private MessageSimpleAdapter chatAdapter;


    // Боковое меню
    private ArrayList<Map<String, Object>> dataItems = new ArrayList<>();
    private MessageSimpleAdapter itemsMenuAdapter;

    //TODO не уверен что это должно тут находиться
    final static String MESSAGE_TEXT = "message";
    final static String MESSAGE_IMAGE = "imageBot";

    public ChatPresenter(IChatView view) {
        this.view = view;
        bot = new TranslatorBot(view);
        String[] fromChat = {MESSAGE_TEXT, MESSAGE_IMAGE};
        int[] toChat = {R.id.message, R.id.imageBot};
        chatAdapter = new MessageSimpleAdapter(view.getActivity(), dataChat,
                R.layout.message, fromChat, toChat);

        String[] fromIntems = {MESSAGE_IMAGE};
        int[] toItems = {R.id.imageAction};
        itemsMenuAdapter = new MessageSimpleAdapter(view.getActivity(), dataItems,
                R.layout.item_action, fromIntems, toItems);

        view.getChat().setAdapter(chatAdapter);
        view.getMenu().setAdapter(itemsMenuAdapter);
    }

    @Override
    public void getHistory(int count) {
        ArrayList<MessageDB> history = bot.getDataBase().getHist(count);
        for (int i = 0; i < history.size(); i++) {
            map = new HashMap<>();
            map.put(MESSAGE_TEXT, history.get(i).getMessage());
            if (history.get(i).getMessage().indexOf(Const.FLAG_PEOPLE) >= 0) {
                map.put(MESSAGE_IMAGE, null);
            } else {
                map.put(MESSAGE_IMAGE, view.getItemImageBot());
            }
            dataChat.add(map);
        }
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMenu() {
        for (int i = 0; i < 3; i++) {
            map = new HashMap<>();
            map.put(MESSAGE_IMAGE, view.getItemImageBot());
            dataItems.add(map);
        }
    }

    @Override
    public void sendMessage(String message) {
        Message msg = parsingMessage(message);
        switch (msg.getTypeMessage()) {
            case TRANSLATE:
                Map<String, Object> tmp = new HashMap<>();
                tmp.put(MESSAGE_TEXT, Const.FLAG_PEOPLE + msg.getSourceText());
                tmp.put(MESSAGE_IMAGE, null);
                dataChat.add(tmp);
                chatAdapter.notifyDataSetChanged();
                try {
                    bot.translate(msg, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void updateData(ITranslatable translatedObj) {
        map = new HashMap<>();
        map.put(MESSAGE_TEXT, translatedObj.getTranslateText());
        map.put(MESSAGE_IMAGE, view.getItemImageBot());
        dataChat.add(map);
        chatAdapter.notifyDataSetChanged();
    }

    private Message parsingMessage(String message) {
        if (message.indexOf("/") == 0) {
            String tmp = message.substring(0, message.indexOf(" "));
            switch (tmp) {
                case "/mark": return new Message(TypeMessage.MARK, message.substring(message.indexOf(" ")));
                case "/setting": return new Message(TypeMessage.SETTING, message.substring(message.indexOf(" ")));
                case "/hist": return new Message(TypeMessage.HIST, message.substring(message.indexOf(" ")));
                default: return new Message(TRANSLATE, message.substring(message.indexOf(" ")));
            }
        } else {
            return new Message(TRANSLATE, message);
        }
    }
}
