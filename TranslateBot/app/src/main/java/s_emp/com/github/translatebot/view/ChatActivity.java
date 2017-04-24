package s_emp.com.github.translatebot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.model.TypeMessage;
import s_emp.com.github.translatebot.presenter.ChatPresenter;

public class ChatActivity extends AppCompatActivity implements IChatView {

    final static int CONT_DELETE_ID = 0;

    private ChatPresenter presenter;

    private ListView chat;
    private ImageView send;
    private Spinner langTo;
    private Spinner langFrom;
    private ImageView switchLang;
    private EditText message;
    private int imageBot;
    private View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        imageBot = R.drawable.bot;
        chat = (ListView) findViewById(R.id.listChat);
        send = (ImageView) findViewById(R.id.ivSend);
        footer = getLayoutInflater().inflate(R.layout.footer, null);
        chat.addFooterView(footer);
        registerForContextMenu(chat);

        langFrom = (Spinner) findViewById(R.id.tvLangFrom);
        langFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setFromLanguage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        langTo = (Spinner) findViewById(R.id.tvLangTo);
        langTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.setToLanguage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        langFrom.setPrompt(getString(R.string.fromLanguage));
        langTo.setPrompt(getString(R.string.toLanguage));
        switchLang = (ImageView) findViewById(R.id.ivSwitchLang);
        message = (EditText) findViewById(R.id.etMessage);

        presenter = new ChatPresenter(this);
        presenter.getHistory(100);
    }

    public void sendMessage(View view) {
        String tmp = message.getText().toString().trim().replaceAll("/", "");
        if (tmp.length() > 0) {
            presenter.sendMessage(TypeMessage.TRANSLATE, tmp);
        }
        clearTextField();
    }

    public void addMark(View view) {
        presenter.addMark();
    }

    public void deleteHistory(View view) {
        presenter.deleteHistory();
    }

    public void deleteMark(View view) {
        presenter.deleteMark(null);
    }

    public void showAllMark(View view) {
        presenter.showAllMark();
    }

    public void downChat(View view) {
        presenter.downChat();
    }

    @Override
    public void systemMessage(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void clearTextField() {
        message.setText("");
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

    public void switchLanguage(View view) {
        presenter.switchLanguage();
    }

    @Override
    public void switchLanguage() {
        int tmp = langFrom.getSelectedItemPosition();
        langFrom.setSelection(langTo.getSelectedItemPosition());
        langTo.setSelection(tmp);
    }

    @Override
    public Spinner getToLanguage() {
        return langTo;
    }

    @Override
    public Spinner getFromLanguage() {
        return langFrom;
    }

    @Override
    public void defaultTranslateLanguage() {
        if (presenter.getPositionFromLanguage("en") > 0) {
            langFrom.setSelection(presenter.getPositionFromLanguage("en"));
        }
        if (presenter.getPositionToLanguage("ru") > 0) {
            langTo.setSelection(presenter.getPositionToLanguage("ru"));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CONT_DELETE_ID, 0, R.string.deleteMark);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONT_DELETE_ID) {
            presenter.deleteMark(item);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}