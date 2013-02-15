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

package org.domdrides.example.repository.hibernate;

import org.domdrides.hibernate.repository.HibernateRepository;
import org.domdrides.example.entity.Person;
import org.domdrides.example.repository.PersonRepository;

/**
 * A hibernate-based implementation of a person repository.
 */
public class HibernatePersonRepository extends HibernateRepository<Person,String> implements PersonRepository
{
//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public HibernatePersonRepository()
    {
        super(Person.class);
    }
}
