import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;
import java.util.function.Function;

public class FluxZip {

    Mono<String> getDetails(int id) {
        return Mono.just("详情")
                .delayElement(Duration.ofSeconds(3))
                .doOnNext(d-> System.out.println("getDetails "+Thread.currentThread().getName()));
        //.subscribeOn(Schedulers.parallel());
    }

    Mono<Double> getPrice(int id) {
        return Mono.just(299.99)
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(d-> System.out.println("getPrice "+Thread.currentThread().getName()));
        //.subscribeOn(Schedulers.parallel());
    }

    Mono<List<Object>> getCoupons(int id) {
        return Mono.just(Arrays.asList(new Object(), new Object()))
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(d-> System.out.println("getCoupons "+Thread.currentThread().getName()));
        //.subscribeOn(Schedulers.parallel());
    }


    Mono<Integer> getProductIdByName(String name) {
        return Mono.just(1)
                .delayElement(Duration.ofSeconds(1));
                //.subscribeOn(Schedulers.parallel());
    }

    @Test
    public void testZip() throws InterruptedException {
        long time = System.currentTimeMillis();

        getProductIdByName("xxx")
                .flatMap(id -> Mono.zip(getDetails(id),
                        getPrice(id),
                        getCoupons(id)))
                .map(objects -> {
                    final String details = objects.getT1();
                    final Double price = objects.getT2();
                    final List<Object> t3 = objects.getT3();
                    return "完成了";
                })
                .doOnNext(System.out::println)
                .doOnSuccess(s -> System.out.println(System.currentTimeMillis() - time))
                .subscribe();

        Thread.sleep(20_000);
    }
}
