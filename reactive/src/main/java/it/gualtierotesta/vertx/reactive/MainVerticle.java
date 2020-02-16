package it.gualtierotesta.vertx.reactive;

import io.reactivex.Completable;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.reactivex.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

    public static void main(final String[] args) {
        Launcher.executeCommand("run", MainVerticle.class.getName());
    }

    @Override
    public Completable rxStart() {

        vertx.rxDeployVerticle(new RestVerticle(), new DeploymentOptions())
                .flatMap(x ->
                        vertx.rxDeployVerticle(new OtherVerticle(), new DeploymentOptions()))
                .subscribe();

        return Completable.complete();
    }

}
