import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FluxTest {


    Flux<Integer> findAll1() {
        return Flux.create(fluxSink -> {
            System.out.println("构建数据");
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.next(4);
            fluxSink.next(5);
            fluxSink.next(6);
            fluxSink.complete();
        });
    }

    Flux<List<String>> findById(List<Integer> ids) {
        return Flux.create(fluxSink -> {
            System.out.println("findById");
            List<String> list = new ArrayList<>();
            ids.forEach(id -> list.add("result: " + id));
            fluxSink.next(list);
            fluxSink.complete();
        });
    }

    Flux<List<String>> listMono;

    private Flux<List<String>> listMono() {
        return listMono = listMono != null ? listMono : findAll1()
                .groupBy(i -> i %= 3)
                .flatMap(data -> data.buffer(2, Integer.MAX_VALUE))
                .flatMap(this::findById)
                .cache(Duration.ofSeconds(1));
    }

    @Test
    public void test() throws InterruptedException {
        listMono()
                .collectList()
                .map(data -> {
                    System.out.println("处理排序等业务");
                    final List<String> arrayList = new ArrayList<>();
                    data.forEach(arrayList::addAll);
                    return arrayList;
                })
                .doOnNext(System.out::println).subscribe();

        Thread.sleep(2000);

        listMono()
                .collectList()
                .map(data -> {
                    System.out.println("处理排序等业务");
                    final List<String> arrayList = new ArrayList<>();
                    data.forEach(arrayList::addAll);
                    return arrayList;
                })
                .doOnNext(System.out::println).subscribe();

        Thread.sleep(500);
        listMono()
                .collectList()
                .map(data -> {
                    System.out.println("处理排序等业务");
                    final List<String> arrayList = new ArrayList<>();
                    data.forEach(arrayList::addAll);
                    return arrayList;
                })
                .doOnNext(System.out::println).subscribe();

    }


    Flux<Integer> cacheFlux = Flux.just(1);
    Mono<Integer> cacheMono = Mono.just(1);

    public <T> Flux<T> cacheFlux(T data) {
        return cacheFlux.map(it -> data);
    }

    public <T> Mono<T> cacheMono(T data) {
        return cacheMono.map(it -> data);
    }
}
