package work.yinli.tunnel;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import work.yinli.tunnel.api.NewsApi;
import work.yinli.tunnel.core.HttpProxy;
import work.yinli.tunnel.result.NewsEntity;

@Slf4j
public class TestHttp {

    @Test
    public void testNewsList() {
        log.info(JSON.toJSONString(HttpProxy.create(NewsApi.class)
                .getNews()));
    }

    @Test
    public void testNewsById() {
        log.info(JSON.toJSONString(HttpProxy.create(NewsApi.class)
                .getNewsById(1)));
    }

    @Test
    public void testAddNews() {
        final NewsEntity entity = new NewsEntity();
        entity.setContent("test");
        entity.setTitle("test");
        final BaseResult<NewsEntity> result = HttpProxy.create(NewsApi.class)
                .addNews(entity);
        log.info(JSON.toJSONString(result));
    }
}

