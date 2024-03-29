package com.hateoas.web.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.ResourceSupport;
import com.hateoas.web.controller.BookController;
import com.hateoas.web.controller.CartController;

public class RootResource extends ResourceSupport {

    public RootResource() {
        this.add(linkTo(BookController.class).withRel("books"));
        this.add(linkTo(CartController.class).withRel("cart"));
        // this.add(linkTo(methodOn(BookController.class).findAll()).withRel("books"));
    }


}
