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
    private ImageView send;
    private TextView langTo;
    private TextView langFrom;
    private ImageView switchLang;
    private EditText message;

    private Map<String, Object> map;
    private ArrayList<Map<String, Object>> data;
    private MessageSimpleAdapter simpleAdapter;
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

        ArrayList<MessageDB> hist = db.getHist(100);
        data = new ArrayList<>();

        for (int i = 0; i < hist.size(); i++) {
            map = new HashMap<>();
            map.put(MESSAGE_TEXT, hist.get(i).getMessage());
            if (hist.get(i).getMessage().indexOf(Const.FLAG_PEOPLE) >= 0) {
                map.put(MESSAGE_IMAGE, null);
            } else {
                map.put(MESSAGE_IMAGE, imageBot);
            }
            data.add(map);
        }

        String[] from = {MESSAGE_TEXT, MESSAGE_IMAGE};
        int[] to = {R.id.message, R.id.imageBot};

        simpleAdapter = new MessageSimpleAdapter(this, data, R.layout.message, from, to);

        chat.setAdapter(simpleAdapter);
    }

    public void send(View v) {
        try {
            messageObj.setSourceText(message.getText().toString());
            TranslatorBot.getInstanse().translate(messageObj);
            db.addHist(new MessageDB(0, Const.FLAG_PEOPLE + message.getText().toString()));
            map = new HashMap<>();
            map.put(MESSAGE_TEXT, Const.FLAG_PEOPLE + message.getText().toString());
            map.put(MESSAGE_IMAGE, null);
            data.add(map);
            simpleAdapter.notifyDataSetChanged();
            message.setText("");
            chat.smoothScrollToPosition(data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
