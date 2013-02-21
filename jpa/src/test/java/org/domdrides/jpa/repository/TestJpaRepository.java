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
import org.domdrides.repository.PageableRepositoryTestCase;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author James Carman
 * @since 1.0
 */
@ContextConfiguration(locations = "TestJpaRepository.xml")
public class TestJpaRepository extends PageableRepositoryTestCase
{
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    private JpaRepository<Person, String> getJpaPersonRepository()
    {
        return ((JpaRepository<Person, String>) personRepository);
    }

    @Test
    public void testGetEntityClass()
    {
        assertEquals(Person.class, getJpaPersonRepository().getEntityClass());
    }

    @Test
    public void testGetEntityManager()
    {
        assertNotNull(getJpaPersonRepository().getEntityManager());
    }
}
