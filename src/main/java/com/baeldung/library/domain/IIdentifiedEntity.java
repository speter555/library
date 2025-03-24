package com.baeldung.library.domain;

import java.io.Serializable;

/**
 * Interface for the identified audit entities. Contains setters, getters for id, version, and for the basic audit fields.
 *
 * @param <ID>
 *            The type of the entity id
 * @author speter555
 * @since 0.1.0
 */
public interface IIdentifiedEntity<ID extends Serializable> extends IVersionable {

    /**
     * Returns the id of the entity
     *
     * @return the id of the entity
     */
    ID getId();

    /**
     * Sets the id of the entity
     *
     * @param id
     *            the new id
     */
    void setId(ID id);

}
