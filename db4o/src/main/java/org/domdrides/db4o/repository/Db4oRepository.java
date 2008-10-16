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

package org.domdrides.db4o.repository;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import org.domdrides.entity.Entity;
import org.domdrides.repository.Repository;
import org.springmodules.db4o.support.Db4oDaoSupport;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author James Carman
 */
public class Db4oRepository<EntityType extends Entity<IdType>, IdType extends Serializable> extends Db4oDaoSupport
        implements Repository<EntityType, IdType>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final Class<EntityType> entityClass;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public Db4oRepository( Class<EntityType> entityClass )
    {
        this.entityClass = entityClass;
    }

//**********************************************************************************************************************
// Repository Implementation
//**********************************************************************************************************************

    /**
     * Adds the entity to this repository.
     *
     * @param entity the entity
     * @return the entity (useful if adding entity to repository changes the entity, e.g. creates oid)
     */
    public EntityType add( EntityType entity )
    {
        getObjectContainer().set(entity);
        return entity;
    }

    /**
     * Returns whether or not the entity is contained within the repository.
     *
     * @param entity the entity
     * @return whether or not the entity is contained within the repository
     */
    public boolean contains( final EntityType entity )
    {
        return getById(entity.getId()) != null;
    }

    /**
     * Returns all entities in this repository as a set.
     *
     * @return all entities in this repository as a set
     */
    public Set<EntityType> getAll()
    {
        return new HashSet<EntityType>(getObjectContainer().query(entityClass));
    }

    /**
     * Returns the entity with the given id.
     *
     * @param id the id
     * @return the entity with the given id
     */
    public EntityType getById( final IdType id )
    {
        final ObjectSet<EntityType> os = getObjectContainer().query(new Predicate<EntityType>()
        {
            private static final long serialVersionUID = 6863777901504784000L;

            public boolean match( EntityType candidate )
            {
                return candidate.getId().equals(id);
            }
        });
        return os.isEmpty() ? null : os.next();
    }

    /**
     * Removes the entity from this repository.
     *
     * @param entity the entity
     */
    public void remove( EntityType entity )
    {
        getObjectContainer().delete(entity);
    }

    /**
     * Returns the size of the repository.
     *
     * @return the size of the repository
     */
    public int size()
    {
        return getObjectContainer().query(entityClass).size();
    }

    /**
     * Updates an entity within this repository.
     *
     * @param entity the entity
     * @return the entity (useful if adding entity to repository changes the entity, e.g. creates oid)
     */
    public EntityType update( EntityType entity )
    {
        getObjectContainer().set(entity);
        return entity;
    }
}
