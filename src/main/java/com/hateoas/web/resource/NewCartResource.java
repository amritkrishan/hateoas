package com.hateoas.web.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.ArrayList;
import java.util.List;

import com.hateoas.model.Book;
import com.hateoas.web.controller.NewCartController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;


public class NewCartResource extends ResourceSupport {

    private final Resources<Book> books;

    private boolean purchased;

    public NewCartResource(final Iterable<Book> books, final boolean purchased) {
        super();
        this.books = new Resources<Book>(books, new ArrayList<>());
        this.purchased = purchased;
        this.add(ControllerLinkBuilder.linkTo(methodOn(NewCartController.class).seeYourCart()).withSelfRel());
    }

    public NewCartResource(final Iterable<Book> books, final List<Link> links, final boolean purchased) {
        super();
        this.books = new Resources<Book>(books, links);
        this.purchased = purchased;
        this.add(ControllerLinkBuilder.linkTo(methodOn(NewCartController.class).seeYourCart()).withSelfRel());
    }

    public Resources<Book> getBooks() {
        return books;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

}
