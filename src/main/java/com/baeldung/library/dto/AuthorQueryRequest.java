package com.baeldung.library.dto;

import java.time.LocalDate;

import com.baeldung.library.domain.enums.OriginCountry;

/**
 * {@link com.baeldung.library.domain.Author} query request
 * 
 * @author speter555
 * @since 0.1.0
 */
public class AuthorQueryRequest {
    private String name;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private OriginCountry originCountry;
    private Integer minBooks;
    private Integer maxBooks;
    private PagingDto paging;

    public AuthorQueryRequest() {

    }

    public AuthorQueryRequest(String name, LocalDate birthDateFrom, LocalDate birthDateTo, OriginCountry originCountry, Integer minBooks,
            Integer maxBooks, PagingDto paging) {
        this.name = name;
        this.birthDateFrom = birthDateFrom;
        this.birthDateTo = birthDateTo;
        this.originCountry = originCountry;
        this.minBooks = minBooks;
        this.maxBooks = maxBooks;
        this.paging = paging;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDateFrom() {
        return birthDateFrom;
    }

    public void setBirthDateFrom(LocalDate birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
    }

    public LocalDate getBirthDateTo() {
        return birthDateTo;
    }

    public void setBirthDateTo(LocalDate birthDateTo) {
        this.birthDateTo = birthDateTo;
    }

    public OriginCountry getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(OriginCountry originCountry) {
        this.originCountry = originCountry;
    }

    public Integer getMinBooks() {
        return minBooks;
    }

    public void setMinBooks(Integer minBooks) {
        this.minBooks = minBooks;
    }

    public Integer getMaxBooks() {
        return maxBooks;
    }

    public void setMaxBooks(Integer maxBooks) {
        this.maxBooks = maxBooks;
    }

    public PagingDto getPaging() {
        return paging;
    }

    public void setPaging(PagingDto paging) {
        this.paging = paging;
    }
}
