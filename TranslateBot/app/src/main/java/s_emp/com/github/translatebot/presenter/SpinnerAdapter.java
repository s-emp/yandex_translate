package s_emp.com.github.translatebot.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import s_emp.com.github.translatebot.R;
import s_emp.com.github.translatebot.model.Language;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Language> languages;

    public SpinnerAdapter(Context context, ArrayList<Language> languages) {
        this.context = context;
        this.languages = languages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Object getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        Language lang = getLanguage(position);
        if (lang != null) {
            ((TextView) view.findViewById(R.id.spinner_item)).setText(lang.getNameLanguage());
        }

        return view;
    }

    private Language getLanguage(int position) {
        return (Language) getItem(position);
    }
}
