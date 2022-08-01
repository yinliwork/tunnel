import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Comparator;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluxTest2 {


    public Mono<String> queryById(int id) {
        return Mono.create(monoSink -> {
            final int i = new Random().nextInt(5);
            try {
                Thread.sleep(i * 100);
            } catch (InterruptedException e) {
                monoSink.error(new RuntimeException(e));
                return;
            }
            monoSink.success("id > " + id);
        });
    }

    @Test
    public void test() throws InterruptedException {
        Flux.range(1, 100)
                .groupBy(i -> i % 2)
                .flatMap(Flux::buffer)
                .flatMap(data -> Flux.fromIterable(data)
                        .parallel(2)
                        .runOn(Schedulers.parallel())
                        .sequential()
                        .flatMap(this::queryById)
                        .take(5))
                        .collectList()
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(200000);
    }

    @Test
    public void testThread(){
        Flux.range(1, 10)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .collectSortedList(Comparator.comparingInt(o -> o))
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
