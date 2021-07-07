package dev.vivekts.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.google.cloud.spring.pubsub.support.converter.PubSubMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageHandler;

@EnableIntegration
@Configuration
@Slf4j
public class ReactiveWebPubSubConfiguration {

    @Bean
    public PubSubMessageConverter messageConverter(@Autowired ObjectMapper mapper) {
        return new JacksonPubSubMessageConverter(mapper);
    }

    @Bean
    @ServiceActivator(inputChannel = ReactiveWebPubSubConstants.PUBSUB_CHANNEL)
    public MessageHandler messageHandler(PubSubTemplate pubSubTemplate) {
        return new PubSubMessageHandler(pubSubTemplate, ReactiveWebPubSubConstants.PUBSUB_TOPIC);
    }
}
