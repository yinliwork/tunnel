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

    public static <T> Function<T, BaseResult<T>> toBaseResult() {
        return BaseResult::success;
    }

    public static <T> Function<Mono<T>, Publisher<BaseResult<T>>> transformMono() {
        return result -> result
                .map(FluxUtils.toBaseResult())
                .onErrorResume(throwable -> Mono.just(BaseResult.error(throwable)));
    }

    public static <T> Function<Flux<T>, Publisher<BaseResult<T>>> transformFlux() {
        return result -> result
                .map(FluxUtils.toBaseResult())
                .onErrorResume(throwable -> Mono.just(BaseResult.error(throwable)));
    }

}
