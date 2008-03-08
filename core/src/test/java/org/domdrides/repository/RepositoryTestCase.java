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

import org.domdrides.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A test harness to test the {@link Repository} "contract"
 * 
 * @auothor James Carman
 */
public abstract class RepositoryTestCase extends AbstractTransactionalTestNGSpringContextTests
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private PersonRepository personRepository;

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    @Autowired
    public void setPersonRepository( PersonRepository personRepository )
    {
        this.personRepository = personRepository;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @SuppressWarnings( "unchecked" )
    private List<Person> createSortedPersonList( Collection<Person> people )
    {
        final List<Person> list = new ArrayList<Person>(people);
        Collections.sort(list, new PropertyComparator("ssn", true, true));
        return list;
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testGetAll()
    {
        assertCollectionsSame(addPersonsToRepository(10), personRepository.getAll());
    }

    @Test
    public void testAdd()
    {
        final Person p = new Person();
        p.setFirst("Slappy");
        p.setLast("White");
        p.setSsn("123-45-6789");
        personRepository.add(p);
        final Person queried = personRepository.getById(p.getId());
        assertNotNull(queried);
        assertEquals(p, queried);
    }

    @Test
    public void testContains()
    {
        final Person p = new Person();
        p.setFirst("Slappy");
        p.setLast("White");
        p.setSsn("123-45-6789");
        assertFalse(personRepository.contains(p));
        personRepository.add(p);
        assertTrue(personRepository.contains(p));
    }

    @Test
    public void testGetById()
    {
        final Person p = new Person();
        p.setFirst("Slappy");
        p.setLast("White");
        p.setSsn("123-45-6789");
        personRepository.add(p);
        final Person queried = personRepository.getById(p.getId());
        assertNotNull(queried);
        assertEquals(p, queried);
    }

    @Test
    public void testRemove()
    {
        final Person p = new Person();
        p.setFirst("Slappy");
        p.setLast("White");
        p.setSsn("123-45-6789");
        personRepository.add(p);
        personRepository.remove(p);
        assertNull(personRepository.getById(p.getId()));
    }

    @Test
    public void testUpdate()
    {
        final Person p = new Person();
        p.setFirst("Slappy");
        p.setLast("White");
        p.setSsn("123-45-6789");
        personRepository.add(p);

        p.setLast("Black");
        personRepository.update(p);
        final Person queried = personRepository.getById(p.getId());
        assertEquals("Black", queried.getLast());
    }

    private void assertCollectionsSame( Collection<Person> expected, Collection<Person> actual )
    {
        assertEquals(createSortedPersonList(expected), createSortedPersonList(actual));
    }

    protected List<Person> addPersonsToRepository( int n )
    {
        return addPersonsToRepository(n, "First", "Last", "SSN");
    }

    protected List<Person> addPersonsToRepository( int n, String firstPrefix, String lastPrefix, String ssnPrefix )
    {
        final List<Person> original = new ArrayList<Person>(n);
        for( int i = 0; i < n; ++i )
        {
            final Person p = new Person();
            p.setFirst(firstPrefix + i);
            p.setLast(lastPrefix + i);
            p.setSsn(ssnPrefix + i);
            personRepository.add(p);
            original.add(p);
        }
        return original;
    }
}
