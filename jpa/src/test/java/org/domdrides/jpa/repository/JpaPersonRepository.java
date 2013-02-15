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

import org.domdrides.entity.Person;
import org.domdrides.repository.PersonRepository;
import org.springframework.stereotype.Repository;

/**
 * A <a href="http://java.sun.com/javaee/technologies/persistence.jsp">Java Persistence API</a>-based
 * repository implementation.
 *
 * @author James Carman
 * @since 1.0
 */
@Repository
public class JpaPersonRepository extends JpaRepository<Person, String> implements PersonRepository
{
//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public JpaPersonRepository()
    {
        super(Person.class);
    }
}
