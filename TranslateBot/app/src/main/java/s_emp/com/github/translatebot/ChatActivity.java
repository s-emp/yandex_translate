package s_emp.com.github.translatebot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import s_emp.com.github.translatebot.model.Message;
import s_emp.com.github.translatebot.model.TranslatorBot;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        System.out.println("Test2");
        Message message = new Message();
        message.setMessage("Привет мир!");
        try {
            String result  = TranslatorBot.getInstanse().getSourceLang(message);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message.getMessage());
    }


}
