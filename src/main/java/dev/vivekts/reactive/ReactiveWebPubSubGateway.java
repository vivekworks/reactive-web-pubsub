package dev.vivekts.reactive;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ReactiveWebPubSubGateway {

    @Gateway(requestChannel = ReactiveWebPubSubConstants.PUBSUB_CHANNEL)
    void publishMessage(ReactiveWebPubSub reactiveWebPubSub);
}
