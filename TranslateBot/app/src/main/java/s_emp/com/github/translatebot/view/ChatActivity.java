package s_emp.com.github.translatebot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.presenter.Message;
import s_emp.com.github.translatebot.model.TranslatorBot;
import s_emp.com.github.translatebot.model.database.DBHelper;
import s_emp.com.github.translatebot.model.database.MessageDB;
import s_emp.com.github.translatebot.other.Const;

public class ChatActivity extends AppCompatActivity {

    private ListView chat;
    private ListView itemsAction;
    private ImageView send;
    private TextView langTo;
    private TextView langFrom;
    private ImageView switchLang;
    private EditText message;

    private Map<String, Object> map;
    private ArrayList<Map<String, Object>> dataChat;
    private ArrayList<Map<String, Object>> dataItems;
    private MessageSimpleAdapter chatAdapter;
    private MessageSimpleAdapter itemsAdapter;
    private int imageBot;
    private DBHelper db;

    final static String MESSAGE_TEXT = "message";
    final static String MESSAGE_IMAGE = "imageBot";

    private Message messageObj = new Message();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TranslatorBot.getInstanse();

        imageBot = R.drawable.bot;
        db = new DBHelper(this);

        chat = (ListView) findViewById(R.id.listChat);
        send = (ImageView) findViewById(R.id.ivSend);
        langFrom = (TextView) findViewById(R.id.tvLangFrom);
        langTo = (TextView) findViewById(R.id.tvLangTo);
        switchLang = (ImageView) findViewById(R.id.ivSwitchLang);
        message = (EditText) findViewById(R.id.etMessage);
        itemsAction = (ListView) findViewById(R.id.listAction);

        ArrayList<MessageDB> hist = db.getHist(100);
        dataChat = new ArrayList<>();

        for (int i = 0; i < hist.size(); i++) {
            map = new HashMap<>();
            map.put(MESSAGE_TEXT, hist.get(i).getMessage());
            if (hist.get(i).getMessage().indexOf(Const.FLAG_PEOPLE) >= 0) {
                map.put(MESSAGE_IMAGE, null);
            } else {
                map.put(MESSAGE_IMAGE, imageBot);
            }
            dataChat.add(map);
        }

        dataItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            map = new HashMap<>();
            map.put(MESSAGE_IMAGE, imageBot);
            dataItems.add(map);
        }

        String[] fromChat = {MESSAGE_TEXT, MESSAGE_IMAGE};
        int[] toChat = {R.id.message, R.id.imageBot};
        chatAdapter = new MessageSimpleAdapter(this, dataChat, R.layout.message, fromChat, toChat);
        chat.setAdapter(chatAdapter);

        String[] fromIntems = {MESSAGE_IMAGE};
        int[] toItems = {R.id.imageAction};
        itemsAdapter = new MessageSimpleAdapter(this, dataItems, R.layout.item_action, fromIntems, toItems);
        itemsAction.setAdapter(itemsAdapter);

    }

    public void send(View v) {
        try {
            messageObj.setSourceText(message.getText().toString());
            TranslatorBot.getInstanse().translate(messageObj);
            db.addHist(new MessageDB(0, Const.FLAG_PEOPLE + message.getText().toString()));
            map = new HashMap<>();
            map.put(MESSAGE_TEXT, Const.FLAG_PEOPLE + message.getText().toString());
            map.put(MESSAGE_IMAGE, null);
            dataChat.add(map);
            chatAdapter.notifyDataSetChanged();
            message.setText("");
            chat.smoothScrollToPosition(dataChat.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
