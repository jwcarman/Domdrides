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

import org.domdrides.repository.PersonRepository;
import org.domdrides.entity.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ExtendedPersonRepository extends PersonRepository
{

    @Transactional(readOnly = true)
    List<Person> getAllAsListByCriteria();

    @Transactional(readOnly = true)
    List<Person> getAllAsListByQuery();

    @Transactional(readOnly = true)
    Set<Person> getAllAsSetByCriteria();

    @Transactional(readOnly = true)
    Set<Person> getAllAsSetByQuery();

    @Transactional(readOnly = true)
    Person getByIdUsingQuery( String id );
}
