package controllers;


import actors.ImageClassifier;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;

public class VisualRecognitionController extends Controller {

    final ActorRef imageClassifer;

    @Inject
    public VisualRecognitionController(ActorSystem system) {
        imageClassifer = system.actorOf(ImageClassifier.getProps());
    }

    public CompletionStage<Result> classifyImage(String imageUrl) {
        ImageClassifier.Message message = new ImageClassifier.Message(imageUrl);
        return FutureConverters.toJava(ask(imageClassifer, message, 5000L))
                .thenApply(response -> ok((String) response));
    }

}
