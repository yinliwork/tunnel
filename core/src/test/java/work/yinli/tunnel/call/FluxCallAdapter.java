package work.yinli.tunnel.call;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.yinli.tunnel.core.CallAdapter;
import work.yinli.tunnel.core.HttpAdapter;
import work.yinli.tunnel.core.Request;


/**
 * @author yangji
 */
public class FluxCallAdapter implements CallAdapter {

    private FluxCallAdapter() {
    }

    public static FluxCallAdapter create() {
        return new FluxCallAdapter();
    }

    @Override
    public Object adapt(HttpAdapter httpAdapter, Request request) {
        return Flux.create((fluxSink -> fluxSink.next(httpAdapter.call(request))));
    }

    @Override
    public boolean identify(Class<?> returnType) {
        return Flux.class.isAssignableFrom(returnType) || Mono.class.isAssignableFrom(returnType);
    }
}
