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

import org.domdrides.entity.Person;
import org.domdrides.repository.PersonRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author James Carman
 */
public class HibernatePersonRepository extends HibernateRepository<Person, String> implements PersonRepository
{
//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public HibernatePersonRepository()
    {
        super(Person.class);
    }

//**********************************************************************************************************************
// PersonRepository Implementation
//**********************************************************************************************************************

    @Transactional(readOnly = true)
    public List<Person> getAllAsListByCriteria()
    {
        final Criteria criteria = createCriteria();
        return list(criteria);
    }

    @Transactional(readOnly = true)
    public List<Person> getAllAsListByQuery()
    {
        final Query q = getSession(false).createQuery("select x from Person x");
        return list(q);
    }

    @Transactional(readOnly = true)
    public Set<Person> getAllAsSetByCriteria()
    {
        final Criteria criteria = createCriteria();
        return set(criteria);
    }

    @Transactional(readOnly = true)
    public Set<Person> getAllAsSetByQuery()
    {
        final Query q = getSession(false).createQuery("select x from Person x");
        return set(q);
    }

    @Transactional(readOnly = true)
    public Person getByIdUsingQuery( String id )
    {
        final Query q = getSession(false).createQuery("select x from Person x where x.id = :id");
        q.setString("id", id);
        return uniqueResult(q);
    }


}
