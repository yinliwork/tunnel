package work.yinli.tunnel.core;

import lombok.Getter;
import work.yinli.tunnel.Tunnel;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author yangji
 */
@Getter
public class ResponseFactory {
    private Class<?> returnType;
    private Type genericReturnType;

    public static ResponseFactory parse(Method method) {
        ResponseFactory result = new ResponseFactory();
        result.returnType = method.getReturnType();
        result.genericReturnType = method.getGenericReturnType();
        return result;
    }
}
