package work.yinli.tunnel;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import work.yinli.tunnel.call.FluxCallAdapter;
import work.yinli.tunnel.core.*;

import java.lang.reflect.Proxy;

public class RequestFactoryTest {

    @Test
    void testRequestFactory() {
        NewsApi newsApi = (NewsApi) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{NewsApi.class}, (proxy, method, args) -> {
            RequestFactory request = RequestFactory.parseAnnotations(proxy, method);
            ResponseFactory response = ResponseFactory.parse(method);


            return null;
        });

        final Flux<BaseResult<NewsEntity>> byId = newsApi.getById(1);

    }

    void parse(RequestFactory requestFactory, Object[] args) {
        final Request.RequestBuilder requestBuilder = new Request.RequestBuilder(requestFactory.getHttpUrl(),
                requestFactory.getHttpMethod(),
                ContentType.APPLICATION_JSON,
                requestFactory.isFormEncoded(),
                requestFactory.isPostJson());

        ParameterHandler<?>[] handlers = requestFactory.getParameterHandlers();
        for (int i = 0; i < handlers.length; i++) {
            final Object arg = args[i];
            handlers[i].apply(requestBuilder,arg);
        }

    }
}
