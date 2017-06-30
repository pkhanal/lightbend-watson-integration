package actors;


import akka.actor.AbstractActor;
import akka.actor.Props;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import settings.Settings;
import settings.SettingsImpl;

import javax.inject.Singleton;

@Singleton
public class ImageClassifier extends AbstractActor {

    private final VisualRecognition service;

    {
        final SettingsImpl settings =
                Settings.SettingsProvider.get(getContext().getSystem());

        // https://github.com/watson-developer-cloud/java-sdk/tree/develop/language-translator
        service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey(settings.WATSON_VISUAL_RECOGNITION_KEY);
    }

    public static Props getProps() {
        return Props.create(ImageClassifier.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, message -> {
                    ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                            .url(message.imageUrl)
                            .build();
                    VisualClassification result = service.classify(options).execute();
                    getSender().tell(result.toString(), getSelf());
                })
                .build();
    }

    // protocol
    public static class Message {
        private final String imageUrl;

        public Message(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String toString() {
            return "Image Url: " + imageUrl;
        }
    }
}
