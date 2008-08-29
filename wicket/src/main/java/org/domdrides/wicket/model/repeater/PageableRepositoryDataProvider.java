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

package org.domdrides.wicket.model.repeater;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.domdrides.entity.Entity;
import org.domdrides.repository.PageableRepository;
import org.domdrides.wicket.model.LoadableDetachableEntityModel;

import java.io.Serializable;
import java.util.Iterator;

/**
 * A {@link PageableRepository}-based implementation of {@link IDataProvider}.  Each @{Entity} returned by this
 * data provider will be wrapped with a {@link LoadableDetachableEntityModel}.
 *
 * @since 1.1
 */
public class PageableRepositoryDataProvider<EntityType extends Entity<IdType>, IdType extends Serializable> extends SortableDataProvider<EntityType>
{
    private final PageableRepository<EntityType, IdType> repository;

    /**
     * Constructs a data provider which returns records from the specified {@link PageableRepository} sorted by the
     * specified sort property.
     * @param repository the repository
     * @param sortProperty the sort property
     * @param ascending whether or not to sort ascending
     */
    public PageableRepositoryDataProvider(PageableRepository<EntityType, IdType> repository, String sortProperty, boolean ascending)
    {
        this.repository = repository;
        setSort(new SortParam(sortProperty, ascending));
    }

    /**
     * Constructs a data provider which returns records from the specified {@link PageableRepository} sorted by the
     * specified sort property (ascending)
     * @param repository the repository
     * @param sortProperty the sort property
     */
    public PageableRepositoryDataProvider(PageableRepository<EntityType, IdType> repository, String sortProperty)
    {
        this.repository = repository;
        setSort(new SortParam(sortProperty, true));
    }

    public Iterator<? extends EntityType> iterator(int first, int max)
    {
        return repository.list(first, max, getSort().getProperty(), getSort().isAscending()).iterator();
    }

    public int size()
    {
        return repository.size();
    }

    public IModel<EntityType> model(EntityType entity)
    {
        return new LoadableDetachableEntityModel<EntityType,IdType>(repository, entity);
    }

}
