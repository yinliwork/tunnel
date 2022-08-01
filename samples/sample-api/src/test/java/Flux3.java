import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Flux3 {

    Consumer<FluxSink<Integer>> consumer = fluxSink -> {

    };

    FluxSink<Integer> fluxSink;

    @Test
    public void test() throws Exception {
        final Flux<Integer> push = Flux.push(sink -> {
            fluxSink = sink;
        });

        push
                .collectList()
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) {
                        fluxSink.next(1);
                        fluxSink.complete();
                    }
                })
                .doOnNext(System.out::println)
                .subscribe();

    }


    <T> Mono<List<Object>> collectList(Flux<T> flux) {
        return Flux.push(sink -> {
            List<T> list = new ArrayList<>();
            flux.subscribe(new CoreSubscriber<T>() {
                @Override
                public void onSubscribe(Subscription s) {
                    System.out.println("onSubscribe");
                }

                @Override
                public void onNext(T t) {
                    System.out.println("onNext");
                    list.add(t);
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("onError");
                    sink.error(t);
                }

                @Override
                public void onComplete() {
                    System.out.println("onComplete");
                    sink.next(list);
                    sink.complete();
                }
            });
        }).collectList();
    }


    class A {
        final String name;

        A(String name) {
            this.name = name;
        }
    }

    class B {
        final A a;

        B(A a) {
            this.a = a;
        }

    }

    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<List<Integer>> lists = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));
        List<List<Integer>> lists2 = Arrays.asList(Arrays.asList(5, 6), Arrays.asList(7, 8));
        System.out.println(Stream.of(lists, lists2)
                .flatMap(Collection::stream).collect(Collectors.toList()));

        final List<Integer> collect = Stream.of(lists, lists2)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println(collect);


        final List<String> collect1 = Stream.of(lists, lists2)
                .flatMap(lists1 -> lists1.stream().flatMap(Collection::stream).map(num -> "str:" + num))
                .collect(Collectors.toList());

        System.out.println(collect1);
    }


}
