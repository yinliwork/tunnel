package work.yinli.tunnel.core;

/**
 * @author yangji
 */
public interface CallAdapter {

    Object adapt(HttpAdapter httpAdapter, Request request);

}
