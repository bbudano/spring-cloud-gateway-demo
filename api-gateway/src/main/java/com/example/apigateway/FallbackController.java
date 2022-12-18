package com.example.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/fallback")
public class FallbackController {

    @GetMapping
    String timeout() {
        return "Sorry, your request has timed out.";
    }

}
