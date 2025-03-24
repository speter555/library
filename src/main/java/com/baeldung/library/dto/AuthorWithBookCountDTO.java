package com.baeldung.library.dto;

import java.time.LocalDate;

import com.baeldung.library.domain.enums.OriginCountry;

/**
 * Author dto with book count
 *
 * @param id
 *            id of {@link com.baeldung.library.domain.Author}
 * @param name
 *            name of {@link com.baeldung.library.domain.Author}
 * @param birthDate
 *            birthDate of {@link com.baeldung.library.domain.Author}
 * @param originCountry
 *            originCountry of {@link com.baeldung.library.domain.Author}
 * @param bookCount
 *            count of Books that are connected to current {@link com.baeldung.library.domain.Author}
 * 
 * @author speter555
 * @since 0.1.0
 */
public record AuthorWithBookCountDTO(String id, String name, LocalDate birthDate, OriginCountry originCountry, Long bookCount) {
}
