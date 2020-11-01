package it.gualtierotesta.vertx.reactive;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RestVerticle extends AbstractVerticle {

    @Override
    public Completable rxStart() {

        final Router router = Router.router(vertx);
        router.route("/api/hello").handler(this::handleApiHello);

        final HttpServer server = vertx.createHttpServer();
        log.info("PID " + ProcessHandle.current().pid());
        return server.requestHandler(router).rxListen(8080).toCompletable();
    }


    private static <T> BiConsumer<T, Throwable> writeTextResponse(final RoutingContext context, final int status) {
        return (res, err) -> {
            if (err != null) {
                if (err instanceof NoSuchElementException) {
                    context.response().setStatusCode(404).end(err.getMessage());
                } else {
                    context.fail(err);
                }
            } else {
                context.response().setStatusCode(status)
                        .end((String) res);
            }
        };
    }

    static <T> BiConsumer<T, Throwable> ok(final RoutingContext rc) {
        return writeTextResponse(rc, 200);
    }

    private void handleApiHello(final RoutingContext pRoutingContext) {
        log.info("got request");
        Single.just("ciao")
                .delay(200, TimeUnit.MILLISECONDS)
                .subscribe(ok(pRoutingContext));
    }
}
