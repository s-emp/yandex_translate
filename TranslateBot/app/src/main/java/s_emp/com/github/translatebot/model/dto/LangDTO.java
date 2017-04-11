package s_emp.com.github.translatebot.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LangDTO {
    @SerializedName("dirs")
    private List<String> dirs = null;
    @SerializedName("langs")
    private Map<String, String> langs = new HashMap<String, String>();

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public Map<String, String> getLangs() {
        return langs;
    }

    public void setLangs(Map<String, String> langs) {
        this.langs = langs;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < dirs.size(); i++) {
            tmp.append(dirs.get(i));
        }
        tmp.append(langs.toString());
        return tmp.toString();
    }
}
