package work.yinli.tunnel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import work.yinli.tunnel.api.common.BaseResult;
import work.yinli.tunnel.api.common.StatusCodeEnum;
import work.yinli.tunnel.api.entity.NewsEntity;
import work.yinli.tunnel.api.service.NewsService;
import work.yinli.tunnel.api.utils.FluxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yangji
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("get")
    public Flux<BaseResult<NewsEntity>> getNewsById(@RequestParam("id") int id) {
        return newsService.getNewsById(id)
                .map(FluxUtils.baseResultFlux())
                .onErrorResume(FluxUtils.baseResultFluxError());
    }

    @GetMapping
    public Flux<BaseResult<List<NewsEntity>>> getNewsList() {
        return newsService.getNews()
                .map(FluxUtils.baseResultFlux())
                .onErrorResume(FluxUtils.baseResultFluxError());
    }

    @PostMapping
    public Flux<BaseResult<NewsEntity>> addNews(@RequestBody NewsEntity news) {
        return newsService.addNews(news)
                .map(FluxUtils.baseResultFlux())
                .onErrorResume(FluxUtils.baseResultFluxError());
    }

}
