package com.baeldung.library.domain;

import java.io.Serializable;

/**
 * Id of {@link BookAuthor} table
 *
 * @author speter555
 * @since 0.1.0
 */
public class BookAuthorId implements Serializable {

    private String book;
    private String author;

    public BookAuthorId() {
    }

    public BookAuthorId(String book, String author) {
        this.book = book;
        this.author = author;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
