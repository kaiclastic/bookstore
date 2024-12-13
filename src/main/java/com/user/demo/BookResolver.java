package com.user.demo;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;


@Controller
public class BookResolver {

    private final BookService bookService;

    public BookResolver(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public Mono<BookResponse> getBooks(@Argument String title) {
        return bookService.searchBooksByTitle(title);
    }
}