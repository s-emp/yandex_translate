package s_emp.com.github.translatebot;

public class BotUnitTest {

//    Bot bot;
//
//    public void pause(TMessage msg) throws Exception {
//        while (!msg.isFree) {
//            Thread.sleep(100);
//        }
//    }
//
//    @Before
//    public void init() {
////        bot = new Bot(new Context());
//    }
//    @Test
//    public void translateHi() throws Exception {
//        TMessage msg = new TMessage();
//        msg.sourceText = "Привет";
//        Bot.getInstanse().setAutoLang(true);
//        Bot.getInstanse().translate(msg);
//        pause(msg);
//        if (!msg.translateText.equals("Hi")) {
//            fail("Translate Привет = " + msg.translateText);
//        }
//        msg.sourceText = "Hi";
//        Bot.getInstanse().setToLang("ru");
//        Bot.getInstanse().translate(msg);
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
//        Bot.getInstanse().getLanguageText(msg);
//        pause(msg);
//        if (!msg.translateText.equals("ru")) {
//            fail("GetLang ru = " + msg.translateText);
//        }
//
//        msg.sourceText = "Hi";
//        Bot.getInstanse().getLanguageText(msg);
//        pause(msg);
//        if (!msg.translateText.equals("en")) {
//            fail("GetLang en = " + msg.translateText);
//        }
//    }
//
//    @Test
//    public void getListLang() throws Exception {
//        Bot.getInstanse();
//        Thread.sleep(2000);
//        Map<String, String> langs = Bot.getInstanse().getLangs();
//        if (langs.isEmpty()) {
//            fail("GetListLang is Empty");
//        }
//    }
//
//    @Test
//    public void getLanguageText() throws Exception {
//        Bot.getInstanse();
//        Thread.sleep(2000);
//        TMessage msg = new TMessage();
//        msg.sourceText = "привет";
//        Bot.getInstanse().getLanguageText(msg);
//        pause(msg);
//        if (!msg.translateText.equals("ru")) {
//            fail("GetSourceLang ru = " + msg.translateText);
//        }
//
//        msg.sourceText = "hi";
//        Bot.getInstanse().getLanguageText(msg);
//        pause(msg);
//        if (!msg.translateText.equals("en")) {
//            fail("GetSourceLang en = " + msg.translateText);
//        }
//    }

}