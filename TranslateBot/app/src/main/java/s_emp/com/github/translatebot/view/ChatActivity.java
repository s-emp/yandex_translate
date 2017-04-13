package s_emp.com.github.translatebot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.model.Message;
import s_emp.com.github.translatebot.model.TranslatorBot;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Message message = new Message();
        message.setSourceText("Привет мир!");
        try {
            TranslatorBot.getInstanse().getSourceLang(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message.getTranslateText());
    }


}
