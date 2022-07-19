package work.yinli.tunnel;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplate {
    @Test
    public void test() {

        final String url = "http://127.0.0.1:8090/news/get?id=1";
        final String body = null;
        final MediaType mediaType = MediaType.APPLICATION_JSON;
        final HttpMethod method = HttpMethod.GET;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<BaseResult> exchange = restTemplate
                .exchange(url, method, httpEntity, BaseResult.class);
    }
}
