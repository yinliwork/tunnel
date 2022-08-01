package work.yinli.tunnel.call;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import work.yinli.tunnel.BaseResult;
import work.yinli.tunnel.NewsApi;
import work.yinli.tunnel.NewsEntity;
import work.yinli.tunnel.core.RequestFactory;
import work.yinli.tunnel.core.ResponseFactory;

import java.lang.reflect.Proxy;

public class CallAdapterTest {
    @Test
    public void fluxAdapterTest() {

        NewsApi newsApi = (NewsApi) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{NewsApi.class}, (proxy, method, args) -> {
            RequestFactory request = RequestFactory.parseAnnotations(proxy, method);
            ResponseFactory response = ResponseFactory.parse(method);
            final boolean identify = FluxCallAdapter.create().identify(response.getReturnType());

            if (identify) {
                return aapt(request, response, args);
            }
            return null;
        });

        final Flux<BaseResult<NewsEntity>> byId = newsApi.getById(1);
    }

    Flux<?> aapt(RequestFactory request, ResponseFactory response, Object[] args) {
        return Flux.create(sink -> {

        });
    }


}
