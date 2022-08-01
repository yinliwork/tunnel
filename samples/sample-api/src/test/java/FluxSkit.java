import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FluxSkit {

    @Test
    public void test() throws InterruptedException {
        Flux.range(1, 10)
                .publishOn(Schedulers.single())
                .doOnNext(b -> System.out.println(Thread.currentThread().getName()))
                .groupBy(i -> i % 2)
                .flatMap(data -> data.buffer().flatMap(this::getTake).subscribeOn(Schedulers.parallel()))
                .collectList()
                .doOnNext(d -> System.out.println("完成"))
                .doOnNext(b -> System.out.println(Thread.currentThread().getName()))
                .subscribeOn(Schedulers.parallel())
                .doOnNext(d -> System.out.println("完成2"))
                .doOnNext(b -> System.out.println(Thread.currentThread().getName()))
                .publishOn(Schedulers.single())
                .doOnNext(d -> System.out.println("完成3"))
                .doOnNext(b -> System.out.println(Thread.currentThread().getName()))
                .subscribe();
        Thread.sleep(5000);
    }

    private Flux<String> getTake(List<Integer> beans) {
        AtomicInteger i = new AtomicInteger();
        return Flux.fromIterable(beans)
                .flatMap(this::findById)
                .doOnNext(bean -> i.getAndIncrement())
                .takeWhile(s -> i.get() <= 10);
    }

    private Flux<String> findById(Integer i) {
        return Flux.<String>create(fluxSink -> {
            fluxSink.next("result:" + i);
            fluxSink.complete();
        });
        //.subscribeOn(Schedulers.newParallel("querySid", 4, false));
    }
}
