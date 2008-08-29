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

package org.domdrides.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.domdrides.entity.Entity;
import org.domdrides.repository.Repository;

import java.io.Serializable;

/**
 * An implementation of {@link LoadableDetachableModel} which loads an entity from a repository.
 *
 * @since 1.1
 */
public class LoadableDetachableEntityModel<EntityType extends Entity<IdType>, IdType extends Serializable> extends LoadableDetachableModel<EntityType>
{
    private final Repository<EntityType,IdType> repository;
    private final IdType id;

    /**
     * Creates a model that lazily loads an entity by id from the specified repository.
     * @param repository the repository
     * @param id the id
     */
    public LoadableDetachableEntityModel(Repository<EntityType, IdType> repository, IdType id)
    {
        this.repository = repository;
        this.id = id;
    }

    /**
     * Creates a model pre-loaded with the specified entity.
     * @param repository the repository
     * @param entity the entity
     */
    public LoadableDetachableEntityModel(Repository<EntityType,IdType> repository, EntityType entity)
    {
       super(entity);
        this.repository = repository;
        this.id = entity.getId();
    }

    /**
     * Loads the entity from the repository.
     *
     * @return the entity
     */
    protected EntityType load()
    {
        return repository.getById(id);   
    }
}
