package work.yinli.tunnel.core;

import work.yinli.tunnel.Tunnel;

import java.lang.reflect.Method;

/**
 * @author yangji
 */
public class ResponseFactory {

    public static ResponseFactory parse(Tunnel tunnel, Method method) {
        final Class<?> returnType = method.getReturnType();

        return null;
    }
}
