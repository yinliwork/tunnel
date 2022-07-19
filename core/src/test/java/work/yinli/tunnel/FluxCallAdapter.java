package work.yinli.tunnel;

import reactor.core.publisher.Flux;
import work.yinli.tunnel.core.CallAdapter;
import work.yinli.tunnel.core.HttpAdapter;
import work.yinli.tunnel.core.Request;


/**
 * @author yangji
 */
public class FluxCallAdapter implements CallAdapter {
    @Override
    public Object adapt(HttpAdapter httpAdapter, Request request) {
        return Flux.create((fluxSink -> fluxSink.next(httpAdapter.call(request))));
    }
}
