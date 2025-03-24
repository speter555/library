package com.baeldung.library.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.baeldung.library.domain.base.BaseIdentifiedEntity;

/**
 * Book table
 *
 * @author speter555
 * @since 0.1.0
 */
@Entity
@Table(name = "BOOK")
public class Book extends BaseIdentifiedEntity {

    /**
     * Isbn of book
     */
    @NotNull
    @Column(nullable = false, length = 20)
    private String isbn;

    /**
     * Title of book
     */
    @NotNull
    @Column(nullable = false)
    private String title;

    /**
     * BookAuthor link table to authors
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookAuthor> bookAuthors = new HashSet<>();

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<BookAuthor> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(Set<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
