package com.baeldung.library.dto;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.library.domain.enums.OriginCountry;

/**
 * Author dto without id
 * 
 * @param name
 *            name of {@link com.baeldung.library.domain.Author}
 * @param birthDate
 *            birthDate of {@link com.baeldung.library.domain.Author}
 * @param originCountry
 *            originCountry of {@link com.baeldung.library.domain.Author}
 */
public record AuthorWithoutIdDTO(String name, LocalDate birthDate, OriginCountry originCountry) {

    /**
     * Validate the dto's data
     */
    public void validate() {
        if (StringUtils.isBlank(name) || birthDate == null || originCountry == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "name, birthDate and originCountry are required!");
        }
    }
}
