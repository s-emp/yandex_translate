package s_emp.com.github.translatebot.model.dto;

import com.google.gson.annotations.SerializedName;

public class DetermiteLangDTO {
    @SerializedName("code")
    private Integer code;
    @SerializedName("lang")
    private String lang;

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
    @Override
    public String toString() {
        return lang;
    }
}
