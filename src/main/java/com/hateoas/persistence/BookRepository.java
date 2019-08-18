package com.hateoas.persistence;
import com.hateoas.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByIsbn(final String isbn);

}
