package dev.vivekts.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class ReactiveWebPubSubHandler {

    private final ReactiveWebPubSubRepository reactiveWebPubSubRepository;
    private final ReactiveWebPubSubGateway reactiveWebPubSubGateway;

    public ReactiveWebPubSubHandler(ReactiveWebPubSubRepository reactiveWebPubSubRepository, ReactiveWebPubSubGateway reactiveWebPubSubGateway) {
        this.reactiveWebPubSubRepository = reactiveWebPubSubRepository;
        this.reactiveWebPubSubGateway = reactiveWebPubSubGateway;
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        return ServerResponse
                .ok()
                .bodyValue(reactiveWebPubSubRepository.get(request.pathVariable("id")));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse
                .ok().bodyValue(reactiveWebPubSubRepository.getAll());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        return request
                .bodyToMono(ReactiveWebPubSub.class)
                .flatMap(m -> {
                    ReactiveWebPubSub saved = reactiveWebPubSubRepository.save(m);
                    log.info("Saved {}", m);
                    try {
                        reactiveWebPubSubGateway.publishMessage(saved);
                    } catch (Exception e) {
                        log.error("Exception thrown in PubSub", e);
                    }
                    return ServerResponse
                            .created(URI.create("/api/" + m.getId()))
                            .bodyValue(saved);
                });
    }
}
