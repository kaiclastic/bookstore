package com.user.demo.resolver;

import com.user.demo.domain.BookDoc;
import com.user.demo.domain.BookResponse;
import com.user.demo.service.BookService;
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
    public Mono<BookResponse> getBooksByTitle(@Argument String title) {
        return bookService.searchBooksByTitle(title);
    }


    @QueryMapping
    public Mono<BookResponse> getBooksByAuthor(@Argument String author) {
        return bookService.searchBooksByAuthor(author);
    }

    @QueryMapping
    public BookDoc getBookbyId(@Argument String id) {
        return bookService.searchBooksById(id);
    }


}