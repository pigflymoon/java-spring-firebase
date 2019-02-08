package com.lucy.javaspring.javafirebase;

import com.lucy.javaspring.javafirebase.model.Event;
import com.lucy.javaspring.javafirebase.model.Group;
import com.lucy.javaspring.javafirebase.model.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Stream;

/**
 * Created by lucy on 8/02/19.
 */
@Component
class Initializer implements CommandLineRunner {
    private final GroupRepository repository;

    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(name ->
                        repository.save(new Group(name))
        );

        Group djug = repository.findByName("Denver");
        Event e = Event.builder().title("Full Stack React")
                .description("Reactive with Spring Boot")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();

        repository.findAll().forEach(System.out::println);
    }
}
