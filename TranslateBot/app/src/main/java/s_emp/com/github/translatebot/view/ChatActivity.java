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
import s_emp.com.github.translatebot.presenter.ChatPresenter;
import s_emp.com.github.translatebot.presenter.Message;
import s_emp.com.github.translatebot.model.TranslatorBot;
import s_emp.com.github.translatebot.model.database.MessageDB;
import s_emp.com.github.translatebot.other.Const;
import s_emp.com.github.translatebot.presenter.MessageSimpleAdapter;

public class ChatActivity extends AppCompatActivity implements IChatView {

    private ChatPresenter presenter;

    private ListView chat;
    private ListView menu;
    private ImageView send;
    private TextView langTo;
    private TextView langFrom;
    private ImageView switchLang;
    private EditText message;
    private int imageBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        imageBot = R.drawable.bot;
        chat = (ListView) findViewById(R.id.listChat);
        send = (ImageView) findViewById(R.id.ivSend);
        langFrom = (TextView) findViewById(R.id.tvLangFrom);
        langTo = (TextView) findViewById(R.id.tvLangTo);
        switchLang = (ImageView) findViewById(R.id.ivSwitchLang);
        message = (EditText) findViewById(R.id.etMessage);
        menu = (ListView) findViewById(R.id.listAction);

        presenter = new ChatPresenter(this);
        presenter.getHistory(100);
    }

    @Override
    public void sendMessage(View view) {
        presenter.sendMessage(Const.FLAG_PEOPLE + message.getText().toString());
        clearTextField();
    }

    @Override
    public void clearTextField() {
        message.setText("");
    }

    @Override
    public void goToLangTo() {

    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public int getItemImageBot() {
        return imageBot;
    }

    @Override
    public EditText getItemMassage() {
        return message;
    }

    @Override
    public ListView getChat() {
        return chat;
    }

    @Override
    public ListView getMenu() {
        return menu;
    }

    @Override
    public void switchLanguage(View view) {
        presenter.switchLanguage();
    }
}