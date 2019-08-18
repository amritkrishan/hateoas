package com.hateoas.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;

import com.hateoas.model.Book;
import com.hateoas.web.resource.BookResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hateoas.model.BookView;
import com.hateoas.persistence.BookRepository;
import com.hateoas.web.error.Checks;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookRepository repo;

    @RequestMapping("/{isbn}")
    public BookResource findByIsbn(@PathVariable final String isbn) {
        System.out.println("Find Book By ISBN");
        final Book book = Checks.checkEntityExists(repo.findByIsbn(isbn), "No book found for isbn = " + isbn);
        final BookResource bookResource = new BookResource(book);
        bookResource.add(ControllerLinkBuilder.linkTo(methodOn(CartController.class).addBookToCart(bookResource)).withRel("add-to-cart"));
        return bookResource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BookResource> findAll() {
        System.out.println("Find All Books");
        final List<Book> books = (List<Book>) repo.findAll();
        final List<BookResource> bookResources = books.stream().map(BookResource::new).collect(Collectors.toList());
        return bookResources;
    }

    @JsonView(BookView.Summary.class)
    @RequestMapping(method = RequestMethod.GET, params="summary")
    public List<BookResource> findAllSummary() {
        System.out.println("Find All Summary");
        final List<Book> books = (List<Book>) repo.findAll();
        final List<BookResource> bookResources = books.stream().map(BookResource::new).collect(Collectors.toList());
        return bookResources;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody BookResource newBook) {
        System.out.println("Create Book");
        repo.save(newBook.getBook());
    }

}
