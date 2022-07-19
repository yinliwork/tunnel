package work.yinli.tunnel.api;


import reactor.core.publisher.Mono;
import work.yinli.tunnel.annotations.Body;
import work.yinli.tunnel.annotations.Get;
import work.yinli.tunnel.annotations.Post;
import work.yinli.tunnel.annotations.Query;
import work.yinli.tunnel.BaseResult;
import work.yinli.tunnel.result.NewsEntity;

import java.util.List;

public interface NewsApi {

    @Get("http://127.0.0.1:8090/news")
    BaseResult<List<NewsEntity>> getNews();

    @Get("http://127.0.0.1:8090/news/get")
    BaseResult<NewsEntity> getNewsById(@Query("id") Integer id);

    @Post("http://127.0.0.1:8090/news")
    BaseResult<NewsEntity> addNews(@Body NewsEntity entity);

    @Get("http://127.0.0.1:8090/news")
    Mono<BaseResult<List<NewsEntity>>> getNewsFlux();

    @Get("http://127.0.0.1:8090/news/get")
    Mono<BaseResult<NewsEntity>> getNewsByIdFlux(@Query("id") Integer id);

    @Post("http://127.0.0.1:8090/news")
    Mono<BaseResult<NewsEntity>> addNewsFlux(@Body NewsEntity entity);

}

