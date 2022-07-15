package work.yinli.tunnel;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TunnelTest {

    @Test
    public void testCreate() {
        final NewsApi newsApi = new Tunnel().create(NewsApi.class);
        newsApi.addNews(new HashMap<>());
        newsApi.getNews();
        newsApi.getNewsById(1);
    }
}