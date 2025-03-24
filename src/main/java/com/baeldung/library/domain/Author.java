package com.baeldung.library.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.baeldung.library.domain.base.BaseIdentifiedEntity;
import com.baeldung.library.domain.enums.OriginCountry;

/**
 * Author table
 * 
 * @author speter555
 * @since 0.1.0
 */
@Entity
@Table(name = "AUTHOR")
public class Author extends BaseIdentifiedEntity {

    /**
     * + Name of author
     */
    @NotNull
    @Column(nullable = false)
    private String name;

    /**
     * Birthday of author
     */
    @Column(nullable = false)
    private LocalDate birthDate;

    /**
     * OriginCountry of author
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OriginCountry originCountry;

    /**
     * BookAuthor link table to books
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookAuthor> bookAuthors = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public OriginCountry getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(OriginCountry originCountry) {
        this.originCountry = originCountry;
    }

    public Set<BookAuthor> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(Set<BookAuthor> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
