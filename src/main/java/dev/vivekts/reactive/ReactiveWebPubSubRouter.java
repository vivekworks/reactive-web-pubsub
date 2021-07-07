package dev.vivekts.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReactiveWebPubSubRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(@Autowired ReactiveWebPubSubHandler handler) {
        return route()
                .GET("/api/{id}", handler::get)
                .GET("/api", handler::getAll)
                .POST("/api", handler::post)
                .build();
    }
}
