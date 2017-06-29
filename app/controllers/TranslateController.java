package controllers;


import actors.Translator;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;


@Singleton
public class TranslateController extends Controller {

    final ActorRef translator;

    @Inject
    public TranslateController(ActorSystem system) {
        translator = system.actorOf(Translator.getProps());
    }

    public CompletionStage<Result> translate(String text, String from, String to) {
        Translator.Message message = new Translator.Message(text, from, to);
        return FutureConverters.toJava(ask(translator, message, 5000L))
                .thenApply(response -> ok((String) response));
    }

}
