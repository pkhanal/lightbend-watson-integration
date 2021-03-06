package actors;


import akka.actor.AbstractActor;
import akka.actor.Props;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import settings.Settings;
import settings.SettingsImpl;

public class Translator extends AbstractActor {

    private final LanguageTranslator translator;

    {
        final SettingsImpl settings =
                Settings.SettingsProvider.get(getContext().getSystem());

        // https://github.com/watson-developer-cloud/java-sdk/tree/develop/language-translator
        translator = new LanguageTranslator();
        translator.setUsernameAndPassword(settings.WATSON_TRANSLATOR_USERNAME, settings.WATSON_TRANSLATOR_PASSWORD);
    }

    public static Props getProps() {
        return Props.create(Translator.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, message -> {
                    Language from = fromString(message.from);
                    Language to = fromString(message.to);
                    TranslationResult result = translator.translate(message.text, from, to).execute();
                    getSender().tell(result.getFirstTranslation(), getSelf());
                })
                .build();
    }

    private static Language fromString(String text) {
        for (Language lang : Language.values()) {
            if (lang.toString().equalsIgnoreCase(text)) {
                return lang;
            }
        }
        return null;
    }

    // protocol
    public static class Message {
        private final String text;
        private final String from;
        private final String to;

        public Message(String text, String from, String to) {
            this.text = text;
            this.from = from;
            this.to = to;
        }

        public String toString() {
            return "Text: " + text + "\n" +
                    "From: " + from + "\n" +
                    "To: " + to + "\n";
        }
    }
}
