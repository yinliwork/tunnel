package work.yinli.tunnel.sample.controller;

import org.springframework.web.bind.annotation.*;
import work.yinli.tunnel.sample.common.BaseResult;
import work.yinli.tunnel.sample.common.StatusCodeEnum;
import work.yinli.tunnel.sample.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController {

    List<NewsEntity> newsEntities = new ArrayList<NewsEntity>() {{
        add(new NewsEntity(1, "Spring boot", "Spring boot for gradle"));
        add(new NewsEntity(2, "Spring boot 1", "Spring boot for gradle"));
        add(new NewsEntity(3, "Spring boot 2", "Spring boot for gradle2"));
        add(new NewsEntity(4, "Spring boot 3", "Spring boot for gradle3"));
        add(new NewsEntity(5, "Spring boot 4", "Spring boot for gradle4"));

    }};

    @GetMapping("get")
    public BaseResult<NewsEntity> getNewsById(@RequestParam("id") int id) {
        final List<NewsEntity> collect = newsEntities.stream().filter(entity -> entity.getId() == id).collect(Collectors.toList());
        if (collect.size() > 0) {
            return BaseResult.success(collect.get(0));
        }
        return BaseResult.error(StatusCodeEnum.FAILED);
    }

    @GetMapping
    public BaseResult<List<NewsEntity>> getNewsList() {
        return BaseResult.success(newsEntities);
    }

    @PostMapping
    public BaseResult<NewsEntity> addNews(@RequestBody NewsEntity news) {
        news.setId(newsEntities.size());
        newsEntities.add(news);
        return BaseResult.success(news);
    }

}
