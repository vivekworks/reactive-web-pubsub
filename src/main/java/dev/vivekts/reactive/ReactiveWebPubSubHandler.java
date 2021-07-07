package dev.vivekts.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ReactiveWebPubSubHandler {

    private final ReactiveWebPubSubRepository reactiveWebPubSubRepository;

    public ReactiveWebPubSubHandler(ReactiveWebPubSubRepository reactiveWebPubSubRepository) {
        this.reactiveWebPubSubRepository = reactiveWebPubSubRepository;
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
                    return ServerResponse
                            .created(URI.create("/api/" + m.getId()))
                            .bodyValue(saved);
                });
    }
}
