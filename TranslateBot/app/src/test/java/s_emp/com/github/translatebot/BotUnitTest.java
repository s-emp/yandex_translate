package s_emp.com.github.translatebot;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import s_emp.com.github.translatebot.model.TranslatorBot;

import static org.junit.Assert.*;

public class BotUnitTest {

//    TranslatorBot bot;
//
//    public void pause(TMessage msg) throws Exception {
//        while (!msg.isFree) {
//            Thread.sleep(100);
//        }
//    }
//
//    @Before
//    public void init() {
////        bot = new TranslatorBot(new Context());
//    }
//    @Test
//    public void translateHi() throws Exception {
//        TMessage msg = new TMessage();
//        msg.sourceText = "Привет";
//        TranslatorBot.getInstanse().setAutoLang(true);
//        TranslatorBot.getInstanse().translate(msg);
//        pause(msg);
//        if (!msg.translateText.equals("Hi")) {
//            fail("Translate Привет = " + msg.translateText);
//        }
//        msg.sourceText = "Hi";
//        TranslatorBot.getInstanse().setToLang("ru");
//        TranslatorBot.getInstanse().translate(msg);
//
//        pause(msg);
//        if (!msg.translateText.equals("Привет")) {
//            fail("Translate Hi = " + msg.translateText);
//        }
//    }
//
//    @Test
//    public void getLang() throws Exception {
//        TMessage msg = new TMessage();
//        msg.sourceText = "Привет";
//        TranslatorBot.getInstanse().getSourceLang(msg);
//        pause(msg);
//        if (!msg.translateText.equals("ru")) {
//            fail("GetLang ru = " + msg.translateText);
//        }
//
//        msg.sourceText = "Hi";
//        TranslatorBot.getInstanse().getSourceLang(msg);
//        pause(msg);
//        if (!msg.translateText.equals("en")) {
//            fail("GetLang en = " + msg.translateText);
//        }
//    }
//
//    @Test
//    public void getListLang() throws Exception {
//        TranslatorBot.getInstanse();
//        Thread.sleep(2000);
//        Map<String, String> langs = TranslatorBot.getInstanse().getLangs();
//        if (langs.isEmpty()) {
//            fail("GetListLang is Empty");
//        }
//    }
//
//    @Test
//    public void getSourceLang() throws Exception {
//        TranslatorBot.getInstanse();
//        Thread.sleep(2000);
//        TMessage msg = new TMessage();
//        msg.sourceText = "привет";
//        TranslatorBot.getInstanse().getSourceLang(msg);
//        pause(msg);
//        if (!msg.translateText.equals("ru")) {
//            fail("GetSourceLang ru = " + msg.translateText);
//        }
//
//        msg.sourceText = "hi";
//        TranslatorBot.getInstanse().getSourceLang(msg);
//        pause(msg);
//        if (!msg.translateText.equals("en")) {
//            fail("GetSourceLang en = " + msg.translateText);
//        }
//    }

}