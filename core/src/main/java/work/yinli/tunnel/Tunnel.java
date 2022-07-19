package work.yinli.tunnel;


import work.yinli.tunnel.annotations.BaseUrl;
import work.yinli.tunnel.core.HttpAdapter;
import work.yinli.tunnel.core.RequestFactory;
import work.yinli.tunnel.core.ResponseFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yangji
 */
public class Tunnel {

    private HttpAdapter httpAdapter;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 走的是 InvocationHandler的对象 toString，原因是 this
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                loadMethod(proxy, method);
                return null;
            }

            @Override
            public String toString() {
                return "234";
            }
        });
    }

    private void loadMethod(Object proxy, Method method) {
        RequestFactory request = RequestFactory.parseAnnotations(proxy, this, method);
        ResponseFactory response = ResponseFactory.parse(this, method);
    }

    public void setHttpAdapter(HttpAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
    }
}
