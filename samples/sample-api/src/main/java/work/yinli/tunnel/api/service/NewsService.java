package work.yinli.tunnel.api.service;

import reactor.core.publisher.Flux;
import work.yinli.tunnel.api.entity.NewsEntity;

import java.util.List;

/**
 * @author yangji
 */
public interface NewsService {

    Flux<List<NewsEntity>> getNews();

    Flux<NewsEntity> getNewsById(Integer id);

    Flux<NewsEntity> addNews(NewsEntity news);
}
