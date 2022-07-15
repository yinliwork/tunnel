package work.yinli.tunnel;

import work.yinli.tunnel.annotations.*;

import java.util.List;
import java.util.Map;

@BaseUrl("http://127.0.0.1:8090")
public interface NewsApi {

    @Get("news")
    BaseResult<List<NewsEntity>> getNews();

    @Get("news/get")
    BaseResult<NewsEntity> getNewsById(@Query("id") Integer id);

    @ApplicationJson
    @Post("news")
    BaseResult<NewsEntity> addNews(@BodyMap Map<String, String> entity);

}