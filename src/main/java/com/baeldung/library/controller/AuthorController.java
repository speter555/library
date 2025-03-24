package com.baeldung.library.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.library.converter.AuthorConverter;
import com.baeldung.library.domain.Author;
import com.baeldung.library.dto.AuthorDTO;
import com.baeldung.library.dto.AuthorQueryRequest;
import com.baeldung.library.dto.AuthorWithBookCountDTO;
import com.baeldung.library.dto.AuthorWithoutIdDTO;
import com.baeldung.library.service.AuthorService;

/**
 * Authors controller
 *
 * @author speter555
 * @since 0.0.1
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorConverter authorConverter;

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * Get {@link Author} by id
     * 
     * @param id
     *            id of {@link Author}
     * @return found {@link Author}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is blank!");
        }
        return ResponseEntity.ok(authorConverter.convertAuthorToAuthorDTO(authorService.findById(id)));
    }

    /**
     * Create {@link Author}
     * 
     * @param authorDTO
     *            dto for {@link Author}
     * @return created {@link Author}
     */
    @PostMapping
    public ResponseEntity<AuthorDTO> postAuthorById(@RequestBody AuthorWithoutIdDTO authorDTO) {
        if (authorDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is blank!");
        }
        authorDTO.validate();
        Author savedEntity = transactionTemplate
                .execute(transactionStatus -> authorService.save(authorConverter.convertAuthorDTOToAuthor(authorDTO)));
        return ResponseEntity.ok(authorConverter.convertAuthorToAuthorDTO(savedEntity));
    }

    /**
     * Create {@link Author}
     * 
     * @param id
     *            id of {@link Author}
     * @return founded {@link Author} that is deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDTO> deleteAuthorById(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is blank!");
        }

        Author entity = authorService.findById(id);
        transactionTemplate.executeWithoutResult(transactionStatus -> authorService.delete(entity));
        return ResponseEntity.ok(authorConverter.convertAuthorToAuthorDTO(entity));
    }

    /**
     * Modified {@link Author}
     * 
     * @param id
     *            id of {@link Author}
     * @param authorDTO
     *            modified data dto
     * @return {@link Author} that is modified
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> putAuthorById(@PathVariable("id") String id, @RequestBody AuthorWithoutIdDTO authorDTO) {
        if (StringUtils.isBlank(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is blank!");
        }
        Author entity = authorService.findById(id);
        entity.setName(authorDTO.name());
        entity.setBirthDate(authorDTO.birthDate());
        entity.setOriginCountry(authorDTO.originCountry());
        Author modifiedEntity = transactionTemplate.execute(transactionStatus -> authorService.save(entity));
        return ResponseEntity.ok(authorConverter.convertAuthorToAuthorDTO(modifiedEntity));
    }

    /**
     * Query {@link Author} by search request
     * 
     * @param request
     *            search request
     * @return found authors
     */
    @PostMapping("/query")
    public ResponseEntity<Page<AuthorWithBookCountDTO>> searchAuthors(@RequestBody AuthorQueryRequest request) {
        Page<AuthorWithBookCountDTO> authors = authorService.findAuthors(request);
        return ResponseEntity.ok(authors);
    }
}
