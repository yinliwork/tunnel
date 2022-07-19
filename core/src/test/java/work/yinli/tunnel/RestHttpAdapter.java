package work.yinli.tunnel;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import work.yinli.tunnel.core.HttpAdapter;
import work.yinli.tunnel.core.Request;
import work.yinli.tunnel.core.Response;

public class RestHttpAdapter implements HttpAdapter {
    @Override
    public Response call(Request request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(request.getBody(), headers);
        final ResponseEntity<String> exchange = restTemplate.exchange(request.getUrl(), HttpMethod.POST, httpEntity, String.class);
        return new Response(exchange.getBody());
    }
}
