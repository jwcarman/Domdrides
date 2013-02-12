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

package org.domdrides.mybatis.repository;

import org.domdrides.entity.Entity;
import org.domdrides.repository.Repository;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An <a href="http://mybatis.org">myBATIS</a>-based repository implementation
 *  
 * @since 1.7
 */
public class MybatisRepository<EntityType extends Entity<IdType>, IdType extends Serializable> extends
        SqlSessionDaoSupport implements Repository<EntityType, IdType>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private String addId;
    private String getAllId;
    private String getByIdId;
    private String removeId;
    private String updateId;
    private String sizeId;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public MybatisRepository(Class<EntityType> entityClass)
    {
        final String simpleName = entityClass.getSimpleName();
        this.addId = simpleName + ".add";
        this.removeId = simpleName + ".remove";
        this.updateId = simpleName + ".update";
        this.getByIdId = simpleName + ".getById";
        this.getAllId = simpleName + ".getAll";
        this.sizeId = simpleName + ".size";
    }

//**********************************************************************************************************************
// Repository Implementation
//**********************************************************************************************************************

    @Transactional()
    public EntityType add( EntityType entity )
    {
        getSqlSession().insert(addId, entity);
        return entity;
    }

    @Transactional( readOnly = true )
    public boolean contains( EntityType entity )
    {
        return getById(entity.getId()) != null;
    }

    @Transactional( readOnly = true )
    public Set<EntityType> getAll()
    {
        List<EntityType> allEntities = getSqlSession().selectList(getAllId);
        return new HashSet<EntityType>(allEntities);
    }

    @Transactional( readOnly = true )
    public EntityType getById( IdType id )
    {
        return getSqlSession().selectOne(getByIdId, id);
    }

    @Transactional
    public void remove( EntityType entity )
    {
        getSqlSession().delete(removeId, entity.getId());
    }

    @Transactional
    public EntityType update( EntityType entity )
    {
        getSqlSession().update(updateId, entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public int size()
    {
        return ((Number)getSqlSession().selectOne(sizeId)).intValue();
    }

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    public String getSizeId()
    {
        return sizeId;
    }

    public void setSizeId(String sizeId)
    {
        this.sizeId = sizeId;
    }

    public String getAddId()
    {
        return addId;
    }

    public void setAddId( String addId )
    {
        this.addId = addId;
    }

    public String getGetAllId()
    {
        return getAllId;
    }

    public void setGetAllId( String getAllId )
    {
        this.getAllId = getAllId;
    }

    public String getGetByIdId()
    {
        return getByIdId;
    }

    public void setGetByIdId( String getByIdId )
    {
        this.getByIdId = getByIdId;
    }

    public String getRemoveId()
    {
        return removeId;
    }

    public void setRemoveId( String removeId )
    {
        this.removeId = removeId;
    }

    public String getUpdateId()
    {
        return updateId;
    }

    public void setUpdateId( String updateId )
    {
        this.updateId = updateId;
    }
}
