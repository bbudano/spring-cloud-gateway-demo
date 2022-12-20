package com.example.exampleservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
public class ExampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);

    @GetMapping("/hello-world")
    String hello() {
        return "Hello, World!";
    }

    @GetMapping("/delayed")
    ResponseEntity<Void> delayed(@RequestParam Long delay) {
        try {
            Thread.sleep(delay * 1000L);
        } catch (InterruptedException e) {
            LOGGER.error("error: ", e);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/random-uuid")
    String uuid() {
        return UUID.randomUUID().toString();
    }

    @GetMapping("/timestamp")
    String timestamp() {
        return "timestamp: " + Instant.now().toEpochMilli();
    }

    @GetMapping("/timestamp2")
    String timestamp2() {
        return "timestamp2: " + Instant.now().toEpochMilli();
    }

    @PostMapping(value = "/anything", produces = MediaType.APPLICATION_JSON_VALUE)
    Object anything(@RequestBody Object anything) {
        return anything;
    }

}
