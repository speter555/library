package com.baeldung.library.dto;

import java.time.LocalDate;

import com.baeldung.library.domain.enums.OriginCountry;

/**
 * Author dto
 * 
 * @param id
 *            id of {@link com.baeldung.library.domain.Author}
 * @param name
 *            name of {@link com.baeldung.library.domain.Author}
 * @param birthDate
 *            birthDate of {@link com.baeldung.library.domain.Author}
 * @param originCountry
 *            originCountry of {@link com.baeldung.library.domain.Author}
 * 
 * @author speter555
 * @since 0.1.0
 */
public record AuthorDTO(String id, String name, LocalDate birthDate, OriginCountry originCountry) {
}
