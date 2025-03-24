package com.baeldung.library.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.baeldung.library.domain.Book;

/**
 * Repository of {@link com.baeldung.library.domain.Book} and Rest resource!
 *
 * @author speter555
 * @since 0.1.0
 */
@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, String> {
	//
}