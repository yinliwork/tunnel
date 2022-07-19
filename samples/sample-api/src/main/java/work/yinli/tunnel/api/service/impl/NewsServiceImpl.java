package work.yinli.tunnel.api.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import work.yinli.tunnel.api.common.BaseResult;
import work.yinli.tunnel.api.common.StatusCodeEnum;
import work.yinli.tunnel.api.entity.NewsEntity;
import work.yinli.tunnel.api.service.NewsService;
import work.yinli.tunnel.api.utils.FluxException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final List<NewsEntity> newsEntities = new ArrayList<NewsEntity>() {{
        add(new NewsEntity(1, "Spring boot", "Spring boot for gradle"));
        add(new NewsEntity(2, "Spring boot 1", "Spring boot for gradle"));
        add(new NewsEntity(3, "Spring boot 2", "Spring boot for gradle2"));
        add(new NewsEntity(4, "Spring boot 3", "Spring boot for gradle3"));
        add(new NewsEntity(5, "Spring boot 4", "Spring boot for gradle4"));

    }};

    @Override
    public Flux<List<NewsEntity>> getNews() {
        return Flux.just(newsEntities);
    }

    @Override
    public Flux<NewsEntity> getNewsById(Integer id) {
        final List<NewsEntity> collect = newsEntities.stream().filter(entity -> entity.getId() == id).collect(Collectors.toList());
        if (collect.size() > 0) {
            return Flux.just(collect.get(0));
        }
        return Flux.error(new FluxException(3000, "数据不存在"));
    }

    @Override
    public Flux<NewsEntity> addNews(NewsEntity news) {
        return Flux.just(news)
                .doOnNext(news1 -> {
                    news.setId(newsEntities.size());
                    newsEntities.add(news);
                });
    }
}
