package com.user.demo.service;

import com.user.demo.domain.BookDoc;
import com.user.demo.domain.BookResponse;
import com.user.demo.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final WebClient.Builder webClientBuilder;
    private final BookRepository bookRepository;

    public BookService(WebClient.Builder webClientBuilder, BookRepository bookRepository) {
        this.webClientBuilder = webClientBuilder;
        this.bookRepository = bookRepository;
    }

    public Mono<BookResponse> searchBooksByTitle(String title) {
        return webClientBuilder.baseUrl("https://www.googleapis.com/books/v1/volumes")
                .build() // creates webclient instance
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("q", "intitle:" + title).build()) // Search books by title
                .retrieve() // retrieve response in the deseralised form of bookresponse
                .bodyToMono(BookResponse.class)
                .doOnNext(response -> logger.info("Response received: {}", response)) // successful log response
                .doOnError(error -> logger.error("Error occurred: {}", error.getMessage())) ; // error response log

    }

    public Mono<BookResponse> searchBooksByAuthor(String author) {
        return webClientBuilder.baseUrl("https://www.googleapis.com/books/v1/volumes")
                .build()
                .get()
                .uri(uriBuilder -> {
                    // Build the URI and log it
                    URI uri = uriBuilder.queryParam("q", "inauthor:" + author).build();
                    System.out.println(uri);
                    return uri;
                })
                .retrieve()
                .bodyToMono(BookResponse.class)
                .doOnNext(response -> logger.info("Response received by author: {}", response))
                .doOnError(error -> logger.error("Error occurred by author: {}", error.getMessage()));
    }



    public BookDoc searchBooksById(String id) {
        return webClientBuilder.baseUrl("https://www.googleapis.com/books/v1/volumes/{id}")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.build(id))
                .retrieve()
                .bodyToMono(BookDoc.class)
                .doOnNext(response -> {
                    logger.info("Response received by id: {}", response);

                    if ((!Objects.equals(response.id(), id))){
                        try {
                            bookRepository.save(response);
                            logger.info("Successfully saved book to Couchbase: {}", response);
                        } catch (Exception e) {
                            logger.error("Error  saving book to Couchbase: {}", e.getMessage());
                        }
                    } else{
                        logger.error("Book with id: {} exists in the database", id);
                    }

                })
                .block();
    }
}

