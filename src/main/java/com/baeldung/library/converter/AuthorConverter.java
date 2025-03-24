package com.baeldung.library.converter;

import org.springframework.stereotype.Service;

import com.baeldung.library.domain.Author;
import com.baeldung.library.dto.AuthorDTO;
import com.baeldung.library.dto.AuthorWithoutIdDTO;

/**
 * {@link Author} entity converter
 * 
 * @author speter555
 * @since 0.0.1
 */
@Service
public class AuthorConverter {

    /**
     * Convert {@link Author} entity to {@link AuthorDTO} dto
     * 
     * @param author
     *            entity
     * @return converted dto
     */
    public AuthorDTO convertAuthorToAuthorDTO(Author author) {
        return new AuthorDTO(author.getId(), author.getName(), author.getBirthDate(), author.getOriginCountry());
    }

    /**
     * Convert {@link AuthorWithoutIdDTO} dto to {@link Author} entity
     *
     * @param authorDto
     *            dto
     * @return converted entity
     */
    public Author convertAuthorDTOToAuthor(AuthorWithoutIdDTO authorDto) {
        Author author = new Author();
        author.setName(authorDto.name());
        author.setBirthDate(authorDto.birthDate());
        author.setOriginCountry(authorDto.originCountry());
        return author;
    }
}
