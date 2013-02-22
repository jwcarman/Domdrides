/*
 * Copyright (c) 2008, Carman Consulting, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.domdrides.hibernate.repository;

import org.domdrides.entity.Entity;
import org.domdrides.repository.PageableRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A <a href="http://www.hibernate.org">Hibernate</a>-based repository implementation.
 *
 * @author James Carman
 * @since 1.0
 */
@Repository
public abstract class HibernateRepository<E extends Entity<I>, I extends Serializable> extends HibernateDaoSupport implements PageableRepository<E, I>
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final String UNCHECKED = "unchecked";
    private static final String ASSOCIATION_ALIAS = "sp";
    private final Class<E> entityClass;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Constructs a repository which supports <code>entityClass</code>.
     *
     * @param entityClass the entity class
     */
    protected HibernateRepository(Class<E> entityClass)
    {
        this.entityClass = entityClass;
    }

//----------------------------------------------------------------------------------------------------------------------
// PageableRepository Implementation
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Returns one page of data from this repository.
     *
     * @param first        the first entity to return
     * @param max          the maximum number of entities to return
     * @param sortProperty the property to sort by
     * @param ascending    whether or not the sorting is asceding
     * @return one page of data from this repository
     */
    @Transactional(readOnly = true)
    public List<E> list(int first, int max, String sortProperty, boolean ascending)
    {
        Criteria c = createCriteria()
                .setMaxResults(max)
                .setFirstResult(first);
        final int ndx = sortProperty.lastIndexOf('.');
        if (ndx != -1)
        {
            final String associationPath = sortProperty.substring(0, ndx);
            final String propertyName = sortProperty.substring(ndx + 1);
            c = c.createAlias(associationPath, ASSOCIATION_ALIAS)
                    .addOrder(ascending ? Order.asc(ASSOCIATION_ALIAS + "." + propertyName) : Order.desc(ASSOCIATION_ALIAS + "." + propertyName));
        }
        else
        {
            c = c.addOrder(ascending ? Order.asc(sortProperty) : Order.desc(sortProperty));
        }
        return list(c);
    }

//----------------------------------------------------------------------------------------------------------------------
// Repository Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Transactional()
    public E add(E entity)
    {
        getSession().save(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public boolean contains(E entity)
    {
        return getSession(false).get(entityClass, entity.getId()) != null;
    }

    @Transactional(readOnly = true)
    public Set<E> getAll()
    {
        return set(createCriteria());
    }

    @Transactional(readOnly = true)
    public E getById(I id)
    {
        return uniqueResult(createCriteria().add(Restrictions.eq("id", id)));
    }

    @Transactional
    public void remove(E entity)
    {
        getSession().delete(entity);
    }

    @Transactional(readOnly = true)
    public int size()
    {
        return ((Number) createCriteria().setProjection(Projections.count("id")).uniqueResult()).intValue();
    }

    @Transactional
    public E update(E entity)
    {
        getSession().merge(entity);
        return entity;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Creates a {@link Criteria} object which returns the entity type.
     *
     * @return a {@link Criteria} object which returns the entity type
     */
    protected Criteria createCriteria()
    {
        return getSession().createCriteria(entityClass);
    }

    /**
     * Returns a list of entities based on the provided criteria.
     *
     * @param criteria the criteria
     * @return a list of entities based on the provided criteria
     */
    @SuppressWarnings(UNCHECKED)
    @Transactional(readOnly = true)
    protected List<E> list(Criteria criteria)
    {
        return new ArrayList<E>(criteria.list());
    }

    /**
     * Returns a list of entities based on the provided query.
     *
     * @param query the query
     * @return a list of entities based on the provided query
     */
    @SuppressWarnings(UNCHECKED)
    @Transactional(readOnly = true)
    protected List<E> list(Query query)
    {
        return new ArrayList<E>(query.list());
    }

    /**
     * Returns a set of entities based on the provided criteria.
     *
     * @param criteria the criteria
     * @return a set of entities based on the provided criteria
     */
    @SuppressWarnings(UNCHECKED)
    @Transactional(readOnly = true)
    protected Set<E> set(Criteria criteria)
    {
        return new HashSet<E>(criteria.list());
    }

    /**
     * Returns a set of entities based on the provided query.
     *
     * @param query the query
     * @return a set of entities based on the provided query
     */
    @SuppressWarnings(UNCHECKED)
    @Transactional(readOnly = true)
    protected Set<E> set(Query query)
    {
        return new HashSet<E>(query.list());
    }

    /**
     * Returns a unique result based on the provided criteria.
     *
     * @param criteria the criteria
     * @return a unique result based on the provided criteria
     */
    @Transactional(readOnly = true)
    protected E uniqueResult(Criteria criteria)
    {
        return entityClass.cast(criteria.uniqueResult());
    }

    /**
     * Returns a unique result based on the provided query.
     *
     * @param query the query
     * @return a unique result based on the provided query
     */
    @Transactional(readOnly = true)
    protected E uniqueResult(Query query)
    {
        return entityClass.cast(query.uniqueResult());
    }
}
