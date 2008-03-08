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
import org.domdrides.repository.Repository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author James Carman
 * @since 1.0
 */
public abstract class HibernateRepository<EntityType extends Entity<IdType>, IdType extends Serializable> extends HibernateDaoSupport implements Repository<EntityType, IdType>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final Class<EntityType> entityClass;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    /**
     * Constructs a repository which supports <code>entityClass</code>.
     *
     * @param entityClass the entity class
     */
    protected HibernateRepository( Class<EntityType> entityClass )
    {
        this.entityClass = entityClass;
    }

//**********************************************************************************************************************
// Repository Implementation
//**********************************************************************************************************************

    @Transactional()
    public EntityType add( EntityType entity )
    {
        getSession().save(entity);
        return entity;
    }

    @Transactional( readOnly = true )
    public boolean contains( EntityType entity )
    {
        return getSession(false).get(entityClass, entity.getId()) != null;
    }

    @Transactional( readOnly = true )
    public Set<EntityType> getAll()
    {
        return set(createCriteria());
    }

    @Transactional( readOnly = true )
    public EntityType getById( IdType id )
    {
        return uniqueResult(createCriteria().add(Restrictions.eq("id", id)));
    }

    @Transactional
    public void remove( EntityType entity )
    {
        getSession().delete(entity);
    }

    @Transactional
    public EntityType update( EntityType entity )
    {
        getSession().update(entity);
        return entity;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    /**
     * Returns a list of entities based on the provided criteria.
     *
     * @param criteria the criteria
     * @return a list of entities based on the provided criteria
     */
    @SuppressWarnings( "unchecked" )
    @Transactional( readOnly = true )
    protected List<EntityType> list( Criteria criteria )
    {
        return new ArrayList<EntityType>(criteria.list());
    }

    /**
     * Returns a list of entities based on the provided query.
     *
     * @param query the query
     * @return a list of entities based on the provided query
     */
    @SuppressWarnings( "unchecked" )
    @Transactional( readOnly = true )
    protected List<EntityType> list( Query query )
    {
        return new ArrayList<EntityType>(query.list());
    }

    /**
     * Returns a set of entities based on the provided criteria.
     *
     * @param criteria the criteria
     * @return a set of entities based on the provided criteria
     */
    @SuppressWarnings( "unchecked" )
    @Transactional( readOnly = true )
    protected Set<EntityType> set( Criteria criteria )
    {
        return new HashSet<EntityType>(criteria.list());
    }

    /**
     * Returns a set of entities based on the provided query.
     *
     * @param query the query
     * @return a set of entities based on the provided query
     */
    @SuppressWarnings( "unchecked" )
    @Transactional( readOnly = true )
    protected Set<EntityType> set( Query query )
    {
        return new HashSet<EntityType>(query.list());
    }

    /**
     * Returns a unique result based on the provided criteria.
     *
     * @param criteria the criteria
     * @return a unique result based on the provided criteria
     */
    @Transactional( readOnly = true )
    protected EntityType uniqueResult( Criteria criteria )
    {
        return entityClass.cast(criteria.uniqueResult());
    }

    /**
     * Returns a unique result based on the provided query.
     *
     * @param query the query
     * @return a unique result based on the provided query
     */
    @Transactional( readOnly = true )
    protected EntityType uniqueResult( Query query )
    {
        return entityClass.cast(query.uniqueResult());
    }

    /**
     * Creates a {@link Criteria} object which returns the entity type.
     *
     * @return a {@link Criteria} object which returns the entity type
     */
    protected Criteria createCriteria()
    {
        return getSession().createCriteria(entityClass);
    }
}
