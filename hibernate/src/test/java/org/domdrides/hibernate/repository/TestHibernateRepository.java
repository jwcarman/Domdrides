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
import org.domdrides.repository.PageableRepositoryTestCase;
import org.springframework.test.context.ContextConfiguration;
import static org.testng.Assert.assertSame;
import org.testng.annotations.Test;

/**
 *
 */
@ContextConfiguration(locations = "TestHibernateRepository.xml")
public class TestHibernateRepository extends PageableRepositoryTestCase
{
    @Test
    public void testGetAllAsSetByQuery()
    {
        ExtendedPersonRepository repo = (ExtendedPersonRepository) personRepository;
        repo.getAllAsSetByQuery();
    }

    @Test
    public void testGetAllAsListByQuery()
    {
        ExtendedPersonRepository repo = (ExtendedPersonRepository) personRepository;
        repo.getAllAsListByQuery();
    }

    @Test
    public void testGetAllAsSetByCriteria()
    {
        ExtendedPersonRepository repo = (ExtendedPersonRepository) personRepository;
        repo.getAllAsSetByCriteria();
    }

    @Test
    public void testGetAllAsListByCriteria()
    {
        ExtendedPersonRepository repo = (ExtendedPersonRepository) personRepository;
        repo.getAllAsListByCriteria();
    }

    @Test
    public void teetGetByIdUsingQuery()
    {
        ExtendedPersonRepository repo = (ExtendedPersonRepository) personRepository;
        final Person expected = new Person();
        expected.setFirst("Slappy");
        expected.setLast("White");
        expected.setSsn("123-45-6789");
        repo.add(expected);
        final Person actual = repo.getByIdUsingQuery(expected.getId());
        assertSame(actual, expected);
    }

}
