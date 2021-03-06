package it.gualtierotesta.vertx.reactive;

import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtherVerticle extends AbstractVerticle {

  @Override
  public Completable rxStart() {
    log.info("OTHER VERTICLE");
    return super.rxStart();
  }
}
