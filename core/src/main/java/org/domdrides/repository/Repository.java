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

package org.domdrides.repository;

import org.domdrides.entity.Entity;

import java.io.Serializable;
import java.util.Set;

/**
 * A repository represents a collection of a specific type of objects.  Typically,
 * the implementation of a repository involves a database or an ORM mapping tool,
 * but simple {@link Set}s can be used also for testing purposes.
 *
 * @author James Carman
 * @since 1.0
 */
public interface Repository<EntityType extends Entity<IdType>, IdType extends Serializable>
{
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Adds the entity to this repository.
     *
     * @param entity the entity
     * @return the entity (useful if adding entity to repository changes the entity, e.g. creates oid)
     */
    EntityType add( EntityType entity );

    /**
     * Returns whether or not the entity is contained within the repository.
     *
     * @param entity the entity
     * @return whether or not the entity is contained within the repository
     */
    boolean contains( EntityType entity );

    /**
     * Returns all entities in this repository as a set.
     *
     * @return all entities in this repository as a set
     */
    Set<EntityType> getAll();

    /**
     * Returns the entity with the given id.
     *
     * @param id the id
     * @return the entity with the given id
     */
    EntityType getById( IdType id );

    /**
     * Removes the entity from this repository.
     *
     * @param entity the entity
     */
    void remove( EntityType entity );

    /**
     * Returns the size of the repository.
     * @return the size of the repository
     */
    int size();

    /**
     * Updates an entity within this repository.
     *
     * @param entity the entity
     * @return the entity (useful if adding entity to repository changes the entity, e.g. creates oid)
     */
    EntityType update( EntityType entity );
}
