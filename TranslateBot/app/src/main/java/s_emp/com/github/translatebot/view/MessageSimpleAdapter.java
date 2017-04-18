package s_emp.com.github.translatebot.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.other.Const;

public class MessageSimpleAdapter extends SimpleAdapter {

    public MessageSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
        if (v.getId() == R.id.message) {
            if (text.indexOf(Const.FLAG_PEOPLE) >= 0) {
                FrameLayout.LayoutParams layout =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                Gravity.RIGHT);
                layout.setMargins(100, 5, 10, 5);
                v.setLayoutParams(layout);
                v.setPadding(10, 5, 10, 5);
                v.setBackgroundResource(R.drawable.message_user);
            }
        }
    }
}
