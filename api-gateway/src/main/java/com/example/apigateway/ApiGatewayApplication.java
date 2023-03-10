package com.example.apigateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build())
                .build());
    }

    @Bean
    public KeyResolver keyResolver() {
        return exchange -> Mono.just("1");
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        // This route takes a String as a request body, trims it and passes it
        // wrapped in a SampleRequest object to the /anything endpoint
        return builder.routes()
                .route("modify-request-body-route", predicateSpec -> predicateSpec
                        .path("/modify-body")
                        .filters(filter -> filter
                                .modifyRequestBody(String.class, SampleRequest.class, MediaType.APPLICATION_JSON_VALUE,
                                        (serverWebExchange, s) -> Mono.just(new SampleRequest(s.trim())))
                                .setPath("/anything"))
                        .uri("http://localhost:8081"))
                .build();
    }

}
