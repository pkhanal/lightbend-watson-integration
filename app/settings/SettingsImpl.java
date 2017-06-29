package settings;

import akka.actor.Extension;
import com.typesafe.config.Config;


public class SettingsImpl implements Extension {

    public final String WATSON_TRANSLATOR_USERNAME;
    public final String WATSON_TRANSLATOR_PASSWORD;
    public final String WATSON_VISUAL_RECOGNITION_KEY;

    public SettingsImpl(Config config) {
        WATSON_TRANSLATOR_USERNAME = config.getString("watson.translation.username");
        WATSON_TRANSLATOR_PASSWORD = config.getString("watson.translation.password");
        WATSON_VISUAL_RECOGNITION_KEY = config.getString("watson.visual_recognition.key");
    }

}