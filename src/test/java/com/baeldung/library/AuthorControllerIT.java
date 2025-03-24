package com.baeldung.library;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.baeldung.library.builder.AuthorSearchRequestBuilder;
import com.baeldung.library.controller.AuthorController;
import com.baeldung.library.domain.enums.OriginCountry;
import com.baeldung.library.dto.AuthorQueryRequest;
import com.baeldung.library.dto.AuthorWithBookCountDTO;
import com.baeldung.library.dto.AuthorWithoutIdDTO;

/**
 * {@link AuthorController} test
 * 
 * @author speter555
 * @since 0.1.0
 */
@SpringBootTest
class AuthorControllerIT {

    @Autowired
    private AuthorController authorController;

    @Test
    void getAuthor() {
        var author = authorController.getAuthorById("4");
        Assertions.assertNotNull(author);
        Assertions.assertNotNull(author.getBody());
        Assertions.assertEquals("Teszt Elek", author.getBody().name());
        Assertions.assertEquals("4", author.getBody().id());
        Assertions.assertEquals(LocalDate.of(1920, 1, 2), author.getBody().birthDate());
        Assertions.assertEquals(OriginCountry.HU, author.getBody().originCountry());
    }

    @Test
    void postAuthor() {
        // given
        var request = new AuthorWithoutIdDTO("Teszt Emma", LocalDate.now(), OriginCountry.HU);

        // when
        var author = authorController.postAuthorById(request);

        // then
        Assertions.assertNotNull(author);
        Assertions.assertNotNull(author.getBody());
        Assertions.assertNotNull(author.getBody().id());
        Assertions.assertEquals("Teszt Emma", author.getBody().name());
        Assertions.assertEquals(LocalDate.now(), author.getBody().birthDate());
        Assertions.assertEquals(OriginCountry.HU, author.getBody().originCountry());
    }

    @Test
    void deleteAuthor() {
        // given
        var author = authorController.getAuthorById("5").getBody();

        // when
        var deletedAuthor = authorController.deleteAuthorById(author.id());

        // then
        Assertions.assertNotNull(deletedAuthor);
        Assertions.assertNotNull(deletedAuthor.getBody());
        Assertions.assertEquals(author.id(), deletedAuthor.getBody().id());
        Assertions.assertEquals(author.name(), deletedAuthor.getBody().name());
        Assertions.assertEquals(author.birthDate(), deletedAuthor.getBody().birthDate());
        Assertions.assertEquals(author.originCountry(), deletedAuthor.getBody().originCountry());
    }

    @Test
    void modifyAuthor() {
        // given
        var author = authorController.getAuthorById("1").getBody();
        var modifyData = new AuthorWithoutIdDTO("Teszt Emma", LocalDate.now(), OriginCountry.HU);
        // when
        var modifyAuthor = authorController.putAuthorById(author.id(), modifyData);

        // then
        Assertions.assertNotNull(modifyAuthor);
        Assertions.assertNotNull(modifyAuthor.getBody());
        Assertions.assertEquals(author.id(), modifyAuthor.getBody().id());
        Assertions.assertEquals(modifyData.name(), modifyAuthor.getBody().name());
        Assertions.assertEquals(modifyData.birthDate(), modifyAuthor.getBody().birthDate());
        Assertions.assertEquals(modifyData.originCountry(), modifyAuthor.getBody().originCountry());
    }

    @ParameterizedTest
    @MethodSource("getAuthorSearchRequestList")
    void query(AuthorQueryRequest request) {
        // given

        // when
        ResponseEntity<Page<AuthorWithBookCountDTO>> response = authorController.searchAuthors(request);

        // then
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getContent());
        Assertions.assertTrue(response.getBody().getTotalElements() > 0);
        Assertions.assertTrue(response.getBody().getTotalPages() > 0);
        Assertions.assertFalse(response.getBody().getContent().isEmpty());
    }

    private static Stream<Arguments> getAuthorSearchRequestList() {

        return Stream.of(
                Arguments.arguments(new AuthorSearchRequestBuilder().build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().name("Teszt").build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().birthDateFrom(LocalDate.of(1900, 1, 1)).build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().birthDateTo(LocalDate.now()).build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().birthDateFrom(LocalDate.of(1900, 1, 1)).birthDateTo(LocalDate.now()).build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().minBooks(0).build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().maxBooks(10).build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().minBooks(0).maxBooks(10).build()), //
                Arguments.arguments(new AuthorSearchRequestBuilder().originCountry(OriginCountry.HU).build()));

    }

}
