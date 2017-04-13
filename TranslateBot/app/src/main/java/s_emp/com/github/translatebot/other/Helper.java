package s_emp.com.github.translatebot.other;



public class Helper {
    // Получение максимальной длины переводимого текста
    public static int getLengthTranslateText(String str) {
        return str.length() >= Const.MAX_LENGTH_TEXT ? Const.MAX_LENGTH_TEXT : str.length();
    }
}
