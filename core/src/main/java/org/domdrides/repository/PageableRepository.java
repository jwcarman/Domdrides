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
import java.util.List;

/**
 * A useful interface for repositories which support "paging."
 *
 * @since 1.1
 */
public interface PageableRepository<E extends Entity<I>, I extends Serializable> extends Repository<E, I>
{
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
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
    List<E> list(int first, int max, String sortProperty, boolean ascending);
}
