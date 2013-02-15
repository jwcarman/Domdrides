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
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    public static final String ADD_MAP_ID = "add";
    public static final String GET_ALL_MAP_ID = "getAll";
    public static final String GET_BY_ID_MAP_ID = "getById";
    public static final String REMOVE_MAP_ID = "remove";
    public static final String UPDATE_MAP_ID = "update";
    public static final String SIZE_MAP_ID = "size";
    private Class<EntityType> entityClass;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public MybatisRepository(Class<EntityType> entityClass)
    {
        this.entityClass = entityClass;
    }

//----------------------------------------------------------------------------------------------------------------------
// Repository Implementation
//----------------------------------------------------------------------------------------------------------------------

    @Transactional()
    public EntityType add( EntityType entity )
    {
        getSqlSession().insert(getMapId(ADD_MAP_ID), entity);
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
        List<EntityType> allEntities = getSqlSession().selectList(getMapId(GET_ALL_MAP_ID));
        return new HashSet<EntityType>(allEntities);
    }

    @Transactional( readOnly = true )
    public EntityType getById( IdType id )
    {
        return getSqlSession().selectOne(getMapId(GET_BY_ID_MAP_ID), id);
    }

    @Transactional
    public void remove( EntityType entity )
    {
        getSqlSession().delete(getMapId(REMOVE_MAP_ID), entity.getId());
    }

    @Transactional(readOnly = true)
    public int size()
    {
        return ((Number)getSqlSession().selectOne(getMapId(SIZE_MAP_ID))).intValue();
    }

    @Transactional
    public EntityType update( EntityType entity )
    {
        getSqlSession().update(getMapId(UPDATE_MAP_ID), entity);
        return entity;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Given a base id, pass back the fully qualified version, for instance:<br/>
     * <br/>
     * &lt;mapper namespace="Person"&gt;<br/>
     * &nbsp;&nbsp;&lt;select id="findByName" ...&gt;<br/>
     * &lt;/mapper&gt;<br/>
     * <br/>
     * String mapId = getMapId("findByName");<br/>
     * //mapId will be Person.findByName
     *
     * @param baseMapId the namespaceless id of the query/insert mapping you wish to utilize
     * @return
     */
    protected String getMapId(String baseMapId)
    {
        final String simpleName = entityClass.getSimpleName();
        return simpleName + "." + baseMapId;
    }
}
