package work.yinli.tunnel;

import org.junit.jupiter.api.Test;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class FluxTest {

    @Test
    public void testFlux() {
        Flux.create(new Consumer<FluxSink<? extends Object>>() {
            @Override
            public void accept(FluxSink<?> fluxSink) {

            }
        })
    }
}
