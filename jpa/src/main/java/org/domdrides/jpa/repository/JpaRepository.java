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

package org.domdrides.jpa.repository;

import org.domdrides.entity.Entity;
import org.domdrides.repository.PageableRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public abstract class JpaRepository<E extends Entity<I>, I extends Serializable> implements PageableRepository<E, I>
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    @PersistenceContext
    private EntityManager entityManager;
    private final Class<E> entityClass;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    protected JpaRepository(Class<E> entityClass)
    {
        this.entityClass = entityClass;
    }

//----------------------------------------------------------------------------------------------------------------------
// PageableRepository Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<E> list(final int first, final int max, final String sortProperty, final boolean ascending)
    {
        final String jpaql = "select x from " + entityClass.getName() + " x order by x." + sortProperty +
                (ascending ? " asc" : " desc");
        final Query query = getEntityManager().createQuery(jpaql);
        query.setFirstResult(first).setMaxResults(max);
        return query.getResultList();
    }

//----------------------------------------------------------------------------------------------------------------------
// Repository Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Transactional
    public E add(E entity)
    {
        getEntityManager().persist(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public boolean contains(E entity)
    {
        return getById(entity.getId()) != null;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Set<E> getAll()
    {
        final String jpaql = "select x from " + entityClass.getName() + " x";
        return queryForSet(jpaql);
    }

    @Transactional(readOnly = true)
    public E getById(I id)
    {
        return getEntityManager().find(entityClass, id);
    }

    @Transactional
    public void remove(E entity)
    {
        getEntityManager().remove(entity);
    }

    @Transactional(readOnly = true)
    public int size()
    {
        List results = getEntityManager().createQuery("select count(*) from " + entityClass.getName()).getResultList();
        return ((Number) results.get(0)).intValue();
    }

    @Transactional
    public E update(E entity)
    {
        return getEntityManager().merge(entity);
    }

//----------------------------------------------------------------------------------------------------------------------
// Getter/Setter Methods
//----------------------------------------------------------------------------------------------------------------------

    protected Class<E> getEntityClass()
    {
        return entityClass;
    }

    protected EntityManager getEntityManager()
    {
        return entityManager;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    private Set<E> queryForSet(String jpaql)
    {
        return new HashSet<E>(getEntityManager().createQuery(jpaql).getResultList());
    }
}
