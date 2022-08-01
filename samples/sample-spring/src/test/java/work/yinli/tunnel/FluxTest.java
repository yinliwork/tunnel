package work.yinli.tunnel;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxTest {

    Flux<Object> getUser() {
        final Object user = new Object();
        System.out.println("getUser public:" + Thread.currentThread().getName());
        return Flux.just(user);
    }

    Flux<Object> getUser2() {
        return Flux.create(fluxSink -> {
            System.out.println("getUser2 public:" + Thread.currentThread().getName());
            fluxSink.next(new Object());
            fluxSink.complete();
        });
    }


    @Test
    public void textFlux() {
        getUser().publishOn(Schedulers.newSingle("thread 100"))
                .subscribeOn(Schedulers.newSingle("thread 101"))
                .subscribe(o -> System.out.println("getUser subscribe" + Thread.currentThread().getName()));

        getUser2()
                .publishOn(Schedulers.newSingle("thread 102"))
                .subscribeOn(Schedulers.newSingle("thread 103"))
                .subscribe(o -> System.out.println("getUser subscribe" + Thread.currentThread().getName()));
    }

}
