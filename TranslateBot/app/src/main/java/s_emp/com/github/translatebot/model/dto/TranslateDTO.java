package s_emp.com.github.translatebot.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateDTO {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
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
