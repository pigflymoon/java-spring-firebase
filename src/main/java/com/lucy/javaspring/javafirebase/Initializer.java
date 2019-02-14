package com.lucy.javaspring.javafirebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.lucy.javaspring.javafirebase.model.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

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
    public void run(String... strings) throws IOException {
        // [START initialize]

//        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
//                "Richmond JUG").forEach(name ->
//                        repository.save(new Group(name))
//        );
//
//        Group djug = repository.findByName("Denver");
//        Event e = Event.builder().title("Full Stack React")
//                .description("Reactive with Spring Boot")
//                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
//                .build();
//
//        repository.findAll().forEach(System.out::println);

        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            System.out.println("###############options"+options);

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }
    }
}
