import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.scheduling.annotation.Schedules;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;
import java.util.function.Function;

public class FluxThreadTest {

    @Test
    public void testThread() throws InterruptedException {

        Flux.range(1, 20)
                .flatMapSequential(this::orderById)
                .take(5)
                .collectList()
                .doOnNext(System.out::println)
                .subscribe();

        Thread.sleep(50000);
    }

    private Mono<Integer> orderById(Integer integer) {
        return Mono.just(integer)
                .map(integer1 -> {
                    final int i = new Random().nextInt(5) * 10;
                    try {
                        Thread.sleep(i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " " + integer);
                    return integer1;
                })
                .subscribeOn(Schedulers.parallel());
    }
}
