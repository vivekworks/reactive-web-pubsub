package dev.vivekts.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
public class ReactiveWebPubSubRepository {

    public final Map<String, ReactiveWebPubSub> map = new HashMap<>();

    public ReactiveWebPubSubRepository() {
        map.put("1", new ReactiveWebPubSub("1", "Vivek"));
        map.put("2", new ReactiveWebPubSub("2", "Yoda"));
        map.put("3", new ReactiveWebPubSub("3", "Oogway"));
    }

    public ReactiveWebPubSub get(String id) {
        log.info("Id {}", id);
        return map
                .get(id);
    }

    public List<ReactiveWebPubSub> getAll() {
        return new ArrayList<>(map.values());
    }

    public ReactiveWebPubSub save(ReactiveWebPubSub reactiveWebPubSub) {
        log.info("to save {}", reactiveWebPubSub);
        map.put(reactiveWebPubSub.getId(), reactiveWebPubSub);
        return reactiveWebPubSub;
    }
}
