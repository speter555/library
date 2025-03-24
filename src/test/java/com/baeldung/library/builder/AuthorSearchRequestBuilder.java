package com.baeldung.library.builder;

import java.time.LocalDate;

import com.baeldung.library.domain.enums.OriginCountry;
import com.baeldung.library.dto.AuthorQueryRequest;
import com.baeldung.library.dto.PagingDto;

/**
 * Builder of {@link AuthorQueryRequest}
 * 
 * @author speter555
 * @since 0.1.0
 */
public class AuthorSearchRequestBuilder {

    private String name;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private OriginCountry originCountry;
    private Integer minBookCount;
    private Integer maxBookCount;

    public AuthorSearchRequestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AuthorSearchRequestBuilder birthDateFrom(LocalDate birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
        return this;
    }

    public AuthorSearchRequestBuilder birthDateTo(LocalDate birthDateTo) {
        this.birthDateTo = birthDateTo;
        return this;
    }

    public AuthorSearchRequestBuilder originCountry(OriginCountry originCountry) {
        this.originCountry = originCountry;
        return this;
    }

    public AuthorSearchRequestBuilder minBooks(Integer minBookCount) {
        this.minBookCount = minBookCount;
        return this;
    }

    public AuthorSearchRequestBuilder maxBooks(Integer maxBookCount) {
        this.maxBookCount = maxBookCount;
        return this;
    }

    public AuthorQueryRequest build() {
        return new AuthorQueryRequest(name, birthDateFrom, birthDateTo, originCountry, minBookCount, maxBookCount, new PagingDto(0, 10));
    }

}
