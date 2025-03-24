package com.baeldung.library.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.library.domain.Author;
import com.baeldung.library.domain.Author_;
import com.baeldung.library.domain.BookAuthor;
import com.baeldung.library.domain.base.BaseIdentifiedEntity_;
import com.baeldung.library.dto.AuthorQueryRequest;
import com.baeldung.library.dto.AuthorWithBookCountDTO;

/**
 * Service of {@link com.baeldung.library.domain.Author}
 * 
 * @author speter555
 * @since 0.1.0
 */
@Service
public class AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

    /**
     * Extended persistence context
     */
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    /**
     * Find entity by id and class.
     *
     * @param id
     *            String type id of the entity
     * @return entity
     */
    public Author findById(String id) {
        if (StringUtils.isBlank(id)) {
            log.warn("Entity Id is blank skipped to load!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "id is null!");
        }
        log.trace(">> BaseService.findById(id: [{}], class: [{}])", id, Author.class.getCanonicalName());
        Author entity = null;
        try {
            try {
                entity = entityManager.find(Author.class, id);
            } catch (Exception e) {
                String msg = MessageFormat.format(
                        "Error occured in finding class: [{0}] by id: [{1}]: [{2}] ",
                        Author.class.getCanonicalName(),
                        id,
                        e.getLocalizedMessage());
                log.error(msg, e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
            }
            if (entity == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MessageFormat.format("Entity id: [{0}] for class: [{1}] not found", id, Author.class.getCanonicalName()));
            }
            return entity;
        } finally {
            log.trace("<< BaseService.findById(id: [{}], class: [{}])", id, Author.class.getCanonicalName());
        }
    }

    /**
     * Transaction required! Save
     *
     * @param entity
     *            entity to save
     * @return saved entity
     */
    public Author save(Author entity) {
        if (entity == null) {
            log.warn("Entity is null skipped to save!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "entity is null!");
        }
        String entityName = entity.getClass().getSimpleName();
        log.debug(">> save([{}]: [{}]", entityName, entity);
        Author savedEntity = null;
        try {
            savedEntity = entityManager.merge(entity);
            entityManager.flush();
            entityManager.refresh(savedEntity);
            log.debug("[{}] entity has been saved", entityName);
            return savedEntity;
        } catch (OptimisticLockException e) {
            String msg = MessageFormat.format("Optimistic Lock Error in saving [{0}]: [{1}]", entityName, e.getLocalizedMessage());
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, msg, e);
        } catch (Exception e) {
            String msg = MessageFormat.format("Error in saving [{0}]: [{1}]", entityName, e.getLocalizedMessage());
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg, e);
        } finally {
            log.debug("<< save([{}]: [{}]", entityName, savedEntity == null ? entity : savedEntity);
        }
    }

    /**
     * Delete entity. Transaction required!
     *
     * @param entity
     *            entity to delete
     */
    public void delete(Author entity) {
        if (entity == null) {
            log.warn("Entity is null skipped to delete!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "entity is null!");
        }
        String entityName = entity.getClass().getSimpleName();
        log.debug(">> delete([{}]: [{}]", entityName, entity);
        try {
            entityManager.remove(entity);
            entityManager.flush();
            log.debug("[{}] entity has been deleted", entityName);
        } catch (OptimisticLockException e) {
            String msg = MessageFormat.format("Optimistic Lock Error in deleting [{0}]: [{1}]", entityName, e.getLocalizedMessage());
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, msg, e);
        } catch (Exception e) {
            String msg = MessageFormat.format("Error in deleting [{0}]: [{1}]", entityName, e.getLocalizedMessage());
            log.error(msg, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg, e);
        } finally {
            log.debug("<< delete([{}]: [{}]", entityName, entity);
        }
    }

    /**
     * Query {@link Author} by search request
     *
     * @param request
     *            search request
     * @return found authors
     */
    public Page<AuthorWithBookCountDTO> findAuthors(AuthorQueryRequest request) {
        Long count = getQueryCount(request).getSingleResult();

        Pageable pageable = PageRequest.of(0, 10);
        if (request.getPaging() != null) {
            pageable = PageRequest.of(request.getPaging().pageNumber(), request.getPaging().pageSize());
        }

        TypedQuery<AuthorWithBookCountDTO> typedQuery = getQuery(request);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }

    private TypedQuery<AuthorWithBookCountDTO> getQuery(AuthorQueryRequest request) {
        return buildQuery(request, false, AuthorWithBookCountDTO.class);
    }

    protected TypedQuery<Long> getQueryCount(AuthorQueryRequest request) {
        return buildQuery(request, true, Long.class);
    }

    private <O> TypedQuery<O> buildQuery(AuthorQueryRequest request, boolean countQuery, Class<O> clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<O> query = cb.createQuery(clazz);
        Root<Author> root = query.from(Author.class);
        Join<Author, BookAuthor> bookAuthorJoin = root.join(Author_.BOOK_AUTHORS);

        if (countQuery) {
            query.select((Selection<? extends O>) cb.count(root.get(BaseIdentifiedEntity_.ID)));
            query.groupBy(root.get(BaseIdentifiedEntity_.ID));
        } else {
            query.select(
                    (Selection<? extends O>) cb.construct(
                            AuthorWithBookCountDTO.class,
                            root.get(BaseIdentifiedEntity_.ID),
                            root.get(Author_.NAME),
                            root.get(Author_.BIRTH_DATE),
                            root.get(Author_.ORIGIN_COUNTRY),
                            cb.count(bookAuthorJoin)));
            query.groupBy(root.get(BaseIdentifiedEntity_.ID));
        }
        // min and max books
        if (request.getMinBooks() != null && request.getMaxBooks() != null) {
            query.having(cb.between(cb.count(bookAuthorJoin), Long.valueOf(request.getMinBooks()), Long.valueOf(request.getMaxBooks())));
        }

        if (request.getMinBooks() != null && request.getMaxBooks() == null) {
            query.having(cb.ge(cb.count(bookAuthorJoin), Long.valueOf(request.getMinBooks())));
        }

        if (request.getMinBooks() == null && request.getMaxBooks() != null) {
            query.having(cb.le(cb.count(bookAuthorJoin), Long.valueOf(request.getMaxBooks())));
        }

        List<Predicate> predicates = getPredicates(cb, root, request);
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(Predicate[]::new)));
        }

        return entityManager.createQuery(query);

    }

    private List<Predicate> getPredicates(CriteriaBuilder cb, Root<Author> root, AuthorQueryRequest request) {

        List<Predicate> predicates = new ArrayList<>();

        // name
        if (StringUtils.isNotBlank(request.getName())) {
            predicates.add(cb.like(root.get(Author_.NAME), "%" + request.getName() + "%"));
        }

        // birthDate from and to
        if (request.getBirthDateFrom() != null) {
            predicates.add(cb.greaterThan(root.get(Author_.BIRTH_DATE), request.getBirthDateFrom()));
        }
        if (request.getBirthDateTo() != null) {
            predicates.add(cb.lessThan(root.get(Author_.BIRTH_DATE), request.getBirthDateTo()));
        }

        // originCountry
        if (request.getOriginCountry() != null) {
            predicates.add(cb.equal(root.get(Author_.ORIGIN_COUNTRY), request.getOriginCountry()));
        }
        return predicates;

    }

}
