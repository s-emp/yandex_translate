package s_emp.com.github.translatebot.model.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateDTO {
    @SerializedName("code")
    private Integer code;
    @SerializedName("lang")
    private String lang;
    @SerializedName("text")
    private List<String> text = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        StringBuilder tmp = new StringBuilder();
        for (String s :
                text) {
            tmp.append(s);
        }
        return tmp.toString();
    }

    public void setText(List<String> text) {
        this.text = text;
    }

}
