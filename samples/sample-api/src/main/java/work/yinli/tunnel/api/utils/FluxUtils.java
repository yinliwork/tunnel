package work.yinli.tunnel.api.utils;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.yinli.tunnel.api.common.BaseResult;

import java.util.function.Function;

/**
 * @author yangji
 */
public class FluxUtils {

    public static <T> Function<Flux<T>, Publisher<BaseResult<T>>> composeFlux() {
        return flux -> flux.map(baseResultFlux())
                .onErrorResume(baseResultFluxError());
    }

    public static <T> Function<Mono<T>, Publisher<BaseResult<T>>> composeMono() {
        return mono -> mono.map(BaseResult::success)
                .onErrorResume(throwable -> Mono.just(BaseResult.error(throwable)));
    }

    public static <T> Function<T, BaseResult<T>> baseResultFlux() {
        return BaseResult::success;
    }

    public static <T> Function<Throwable, Publisher<T>> baseResultFluxError() {
        return throwable -> Flux.just((T) BaseResult.error(throwable));
    }
}
