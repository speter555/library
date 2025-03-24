package com.baeldung.library.domain;

/**
 * Interface for version getter setter.
 *
 * @author speter555
 * @since 0.1.0
 */
public interface IVersionable {

    /**
     * Returns the version of the entity
     *
     * @return the version of the entity
     */
    long getVersion();

    /**
     * Sets the version of the entity
     *
     * @param version
     *            the new version
     */
    void setVersion(long version);

}
