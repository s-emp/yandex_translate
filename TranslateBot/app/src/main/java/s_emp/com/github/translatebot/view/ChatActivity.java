package s_emp.com.github.translatebot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.model.Message;
import s_emp.com.github.translatebot.model.TranslatorBot;
import s_emp.com.github.translatebot.model.database.DBHelper;
import s_emp.com.github.translatebot.model.database.MessageDB;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.addHist(new MessageDB(0, "test"));
            dbHelper.addHist(new MessageDB(1, "test1"));

            ArrayList<MessageDB> hist = dbHelper.getHist(0);
            for (MessageDB msg :
                    hist) {
                System.out.println("SUCCESS MY123: " + msg.getId() + " " + msg.getIsBot() + " " + msg.getMessage());
            }
        } catch (IOException e) {
            System.out.println("ERROR MY123: ");
            e.printStackTrace();
        }
    }


}
