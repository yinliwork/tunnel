package work.yinli.tunnel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import work.yinli.tunnel.api.common.BaseResult;
import work.yinli.tunnel.api.common.StatusCodeEnum;
import work.yinli.tunnel.api.entity.NewsEntity;
import work.yinli.tunnel.api.service.NewsService;
import work.yinli.tunnel.api.utils.FluxUtils;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
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
                .transform(FluxUtils.transformFlux());
    }

    @GetMapping
    public Flux<BaseResult<List<NewsEntity>>> getNewsList() {
        return newsService.getNews()
                .transform(FluxUtils.transformFlux());
    }

    @PostMapping
    public Flux<BaseResult<NewsEntity>> addNews(@RequestBody NewsEntity news) {
        return newsService.addNews(news)
                .transform(FluxUtils.transformFlux());
    }
    @GetMapping("mini/hello")
    public Mono<BaseResult<List<Integer>>> hello1() {
        return Flux.range(1, 5)
                .flatMapSequential(integer -> Mono.just(integer)
                        .delayElement(Duration.ofMillis(10 * new Random().nextInt(5)))
                        .subscribeOn(Schedulers.parallel())
                )
                .collectList()
                .transform(FluxUtils.transformMono());
    }

}
