package s_emp.com.github.translatebot.presenter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.other.Const;

public class MessageSimpleAdapter extends SimpleAdapter {

    private int paddingBotLeft;
    private int paddingBot;

    private int paddingUser;

    public MessageSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        System.out.println("88gg404 size:" + size.x);
        double scale = (double)size.x / 1080;
        System.out.println("88gg404 scale:" + scale);
        paddingBotLeft = (int) (170 * scale) + 8;
        paddingBot = (int) (15 * scale) + 8;
        paddingUser = (int) (15 * scale) + 8;
    }

    @Override
    public void setViewText(TextView v, String text) {
        System.out.println("88gg404 bot left:" + paddingBotLeft);
        System.out.println("88gg404 bot:" + paddingBot);
        System.out.println("88gg404 user:" + paddingUser);
        if (v.getId() == R.id.message) {
            // Если это сообщение от пользователя
            if (text.contains(Const.FLAG_PEOPLE)) {
                super.setViewText(v, text.substring(text.indexOf(" ")));
                FrameLayout.LayoutParams layout =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                Gravity.RIGHT);
                layout.setMargins(100, 2, 2, 2);
                v.setPadding(paddingUser, paddingUser, paddingUser, paddingUser);
                v.setBackgroundResource(R.drawable.message_user);
                v.setLayoutParams(layout);
                // Если это показ закладок
            } else if (text.contains(Const.FLAG_MARK)) {
                super.setViewText(v, text.substring(text.trim().indexOf(" ") + 1));
                FrameLayout.LayoutParams layout =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                Gravity.CENTER_HORIZONTAL);
                v.setPadding(paddingBotLeft, paddingBot, paddingBot, paddingBot);
                layout.setMargins(2, 2, 2, 2);
                v.setPadding(paddingUser, paddingUser, paddingUser, paddingUser);
                v.setBackgroundResource(R.drawable.message_user);
                v.setLayoutParams(layout);
            } else {
                super.setViewText(v, text);
                FrameLayout.LayoutParams layout =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                Gravity.LEFT);
                layout.setMargins(2, 2, 100, 2);
                v.setPadding(paddingBotLeft, paddingBot, paddingBot, paddingBot);
                v.setBackgroundResource(R.drawable.message_bot);
                v.setLayoutParams(layout);
            }
        }
    }

    public void getWindowSize() {

    }
}
