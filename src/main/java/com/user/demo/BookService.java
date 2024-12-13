package com.user.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final WebClient.Builder webClientBuilder;

    public BookService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<BookResponse> searchBooksByTitle(String title) {
        return webClientBuilder.baseUrl("https://www.googleapis.com/books/v1/volumes")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("q", "intitle:" + title).build()) // Search books by title
                .retrieve()
                .bodyToMono(BookResponse.class)
                .doOnNext(response -> logger.info("Response received: {}", response))
                .doOnError(error -> logger.error("Error occurred: {}", error.getMessage()));

    }
}
