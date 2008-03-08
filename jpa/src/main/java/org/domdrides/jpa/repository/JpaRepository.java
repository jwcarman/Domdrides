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

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.domdrides.entity.Entity;
import org.domdrides.repository.Repository;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

public abstract class JpaRepository<EntityType extends Entity<IdType>, IdType extends Serializable> extends JpaDaoSupport implements Repository<EntityType, IdType>
{
    private final Class<EntityType> entityClass;

    protected JpaRepository(Class<EntityType> entityClass)
    {
        this.entityClass = entityClass;
    }

    public EntityType add(EntityType entity)
    {
        getJpaTemplate().persist(entity);
        return entity;
    }

    public boolean contains(EntityType entity)
    {
        return getById(entity.getId()) != null;
    }

    @SuppressWarnings("unchecked")
    public Set<EntityType> getAll()
    {
        final String jpaql = "select x from " + entityClass.getName() + " x";
        return queryForSet(jpaql);
    }

    @SuppressWarnings("unchecked")
    private HashSet<EntityType> queryForSet(String jpaql)
    {
        return new HashSet<EntityType>(getJpaTemplate().find(jpaql));
    }

    public EntityType getById(IdType id)
    {
        return getJpaTemplate().find(entityClass, id);
    }

    public void remove(EntityType entity)
    {
        getJpaTemplate().remove(entity);
    }

    public EntityType update(EntityType entity)
    {
        return getJpaTemplate().merge(entity);
    }
}
