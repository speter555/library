package com.baeldung.library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.baeldung.library.domain.Book;
import com.baeldung.library.repo.BookRepository;

/**
 * Base application tests
 * 
 * @author speter555
 * @since 0.1.0
 */
@SpringBootTest
class LibraryApplicationTests {

    @Autowired
    private BookRepository bookRepo;

    @Test
    void contextLoads() {
        //
    }

    @Test
    void persistenceWorks() {
        Assertions.assertNotNull(bookRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Test
    void dataExists() {
        Page<Book> books = bookRepo.findAll(PageRequest.of(0, 10));
        Assertions.assertNotNull(books);

    }

}
