package com.baeldung.library.domain.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.baeldung.library.domain.IIdentifiedEntity;
import com.baeldung.library.util.RandomUtil;

/**
 * Entity identifier generator.
 *
 * @author speter555
 * @since 0.1.0
 */
public class EntityIdGenerator implements IdentifierGenerator {

    /**
     * Default constructor, constructs a new object.
     */
    public EntityIdGenerator() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (object instanceof IIdentifiedEntity<?>) {
            IIdentifiedEntity<?> entity = (IIdentifiedEntity<?>) object;
            if (entity.getId() == null) {
                return generateId();
            }
            return entity.getId();
        }
        return generateId();
    }

    /**
     * generate id
     *
     * @return entityId
     */
    public static String generateId() {
        return RandomUtil.generateId();
    }

}
