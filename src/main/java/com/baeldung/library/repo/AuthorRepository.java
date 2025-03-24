package com.baeldung.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.library.domain.Author;

/**
 * Repository of {@link com.baeldung.library.domain.Author}
 * 
 * @author speter555
 * @since 0.1.0
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, String>, PagingAndSortingRepository<Author, String> {
    //
}
