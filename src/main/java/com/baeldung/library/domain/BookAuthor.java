package com.baeldung.library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Book and Author link table
 *
 * @author speter555
 * @since 0.1.0
 */
@Entity
@Table(name = "BOOK_AUTHOR")
@IdClass(BookAuthorId.class)
public class BookAuthor {

    /**
     * Book
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * Author
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
