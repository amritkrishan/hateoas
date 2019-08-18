package com.hateoas.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;

import com.hateoas.web.resource.NewBookResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hateoas.model.Book;
import com.hateoas.model.BookView;
import com.hateoas.persistence.BookRepository;
import com.hateoas.web.error.Checks;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping(value = "/new/books")
public class NewBookController {

    @Autowired
    private BookRepository repo;

    @RequestMapping("/{isbn}")
    public MappingJacksonValue findByIsbn(@PathVariable final String isbn) {
        final Book book = Checks.checkEntityExists(repo.findByIsbn(isbn), "No book found for isbn = " + isbn);

        final NewBookResource bookResource = new NewBookResource(book);
        bookResource.add(linkTo(methodOn(CartController.class).addNewBookToCart(bookResource)).withRel("add-to-cart"));

        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("bookFilter", SimpleBeanPropertyFilter.serializeAll());
        final MappingJacksonValue wrapper = new MappingJacksonValue(bookResource);
        wrapper.setFilters(filterProvider);

        return wrapper;
    }

    @RequestMapping(value = "/{isbn}", params = "fields")
    public MappingJacksonValue findByIsbnFiltered(@RequestParam String fields, @PathVariable final String isbn) {
        final Book book = Checks.checkEntityExists(repo.findByIsbn(isbn), "No book found for isbn = " + isbn);

        final NewBookResource bookResource = new NewBookResource(book);
        bookResource.add(linkTo(methodOn(CartController.class).addNewBookToCart(bookResource)).withRel("add-to-cart"));

        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("bookFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        final MappingJacksonValue wrapper = new MappingJacksonValue(bookResource);
        wrapper.setFilters(filterProvider);

        return wrapper;
    }

    @JsonView(BookView.Summary.class)
    @RequestMapping(method = RequestMethod.GET)
    public List<NewBookResource> findAll() {
        final List<Book> books = (List<Book>) repo.findAll();
        final List<NewBookResource> bookResources = books.stream().map(NewBookResource::new).collect(Collectors.toList());
        return bookResources;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody NewBookResource newBook) {
        repo.save(newBook.getBook());
    }

}
