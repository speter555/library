package com.baeldung.library.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.baeldung.library.domain.IIdentifiedEntity;

/**
 * BaseIdentifiedEntity class.
 *
 * @author speter555
 * @since 0.1.0
 */
@MappedSuperclass
public class BaseIdentifiedEntity extends BaseEntity implements IIdentifiedEntity<String> {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor, constructs a new object.
     */
    public BaseIdentifiedEntity() {
        super();
    }

    /**
     * Identity field of the entity
     */
    @Id
    @Column(name = "X__ID", length = 30)
    @GenericGenerator(name = "entity-id-generator", strategy = "com.baeldung.library.domain.util.EntityIdGenerator")
    @GeneratedValue(generator = "entity-id-generator", strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * Getter for the field {@code id}.
     *
     * @return id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Setter for the field {@code id}.
     *
     * @param id
     *            id
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
}
