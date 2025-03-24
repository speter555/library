package com.baeldung.library.domain.base;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.LoggerFactory;

import com.baeldung.library.domain.IVersionable;

/**
 * Base class for all entities.
 *
 * @author speter555
 * @since 0.1.0
 */
@MappedSuperclass
public class BaseEntity implements IVersionable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor, constructs a new object.
     */
    public BaseEntity() {
        super();
    }

    /**
     * The version of the entity for optimistic lock checking
     */
    @Column(name = "X__VERSION", precision = 20, scale = 0)
    @NotNull
    @Version
    private long version;

    /**
     * Transient interval version
     */
    @Transient
    private Long internalVersion;

    /**
     * Getter for the field {@code version}.
     *
     * @return version
     */
    @Override
    public long getVersion() {
        return version;
    }

    /**
     * Setter for the field {@code version}.
     *
     * @param version
     *            version
     */
    @Override
    public void setVersion(long version) {
        if (internalVersion == null || this.version != version) {
            internalVersion = this.version;
        }
        this.version = version;
    }

    /**
     * rollbackVersion.
     */
    public void rollbackVersion() {
        if (internalVersion != null) {
            version = internalVersion;
            internalVersion = null;
        }
    }

    /**
     * updateVersion.
     */
    public void updateVersion() {
        internalVersion = version;
    }

    /**
     * toString.
     */
    @Override
    public String toString() {
        ToStringBuilder s = new ToStringBuilder(this);
        for (PropertyDescriptor property : PropertyUtils.getPropertyDescriptors(this)) {

            String name = property.getName();
            Class<?> propertyType = property.getPropertyType();

            if (ClassUtils.isAssignable(propertyType, BaseEntity.class)) {
                // Do not traverse dependency relationships, do not lazy-load
                s.append(name, propertyType.getSimpleName());
            } else if (propertyType == byte[].class || propertyType == Blob.class || propertyType == Clob.class || propertyType == InputStream.class
                    || propertyType == OutputStream.class || propertyType == Reader.class || propertyType == Writer.class) {
                // Do not handle large amounts of data, do not hassle with streams.
                s.append(name, propertyType.getSimpleName());
            } else {
                try {
                    s.append(name, property.getReadMethod().invoke(this));
                } catch (Exception e) {
                    LoggerFactory.getLogger(getClass()).warn("Error in toString for property [{}]: [{}]", name, e.getLocalizedMessage());
                }
            }
        }
        return s.toString();
    }
}
