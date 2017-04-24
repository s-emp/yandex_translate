package s_emp.com.github.translatebot.other;



public class Helper {
    // Получение максимальной длины переводимого текста
    public static int getLengthTranslateText(String str) {
        return str.length() >= Const.MAX_LENGTH_TEXT ? Const.MAX_LENGTH_TEXT : str.length();
    }

    // Преобразует сообщение в вид от бота
    public static String messageBot(String text) {
        if (text.trim().indexOf("/") == 0) {
            if (text.trim().indexOf(" ") > 0) {
                return text.trim().substring(text.trim().indexOf(" "));
            } else {
                return "";
            }
        }
        return text;
    }
}
