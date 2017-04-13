package s_emp.com.github.translatebot;

import org.junit.Test;

import s_emp.com.github.translatebot.model.ApiTranslate;
import s_emp.com.github.translatebot.model.Message;
import s_emp.com.github.translatebot.model.TranslatorBot;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BotUnitTest {

    public void pause(TMessage msg) throws Exception {
        while (!msg.isFree) {
            Thread.sleep(100);
        }
    }

    @Test
    public void translateHi() throws Exception {
        TMessage msg = new TMessage();
        msg.sourceText = "Привет";
        TranslatorBot.getInstanse().setAutoLang(true);
        TranslatorBot.getInstanse().translate(msg);
        pause(msg);
        if (!msg.translateText.equals("Hi")) {
            fail("Translate Привет = " + msg.translateText);
        }
        msg.sourceText = "Hi";
        TranslatorBot.getInstanse().setToLang("ru");
        TranslatorBot.getInstanse().translate(msg);

        pause(msg);
        if (!msg.translateText.equals("Привет")) {
            fail("Translate Hi = " + msg.translateText);
        }
    }

    @Test
    public void translateHiEn() throws Exception {

    }


}