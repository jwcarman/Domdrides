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
import org.testng.annotations.Test;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * A test harness to test the {@link PageableRepository} "contract"
 *
 *
 */

public abstract class PageableRepositoryTestCase extends RepositoryTestCase
{
    @Test
    @SuppressWarnings("unchecked")
    public void testListMethod()
    {
        final int pageSize = 10;
        final int nPages = 10;
        List<Person> allPeople = addPersonsToRepository(pageSize * nPages);
        Collections.sort(allPeople, new Comparator<Person>()
        {
            public int compare(Person o1, Person o2)
            {
                return o1.getLast().compareTo(o2.getLast());
            }
        });
        // Test ascending sorts...
        final PageableRepository<Person,String> pageablePersonRepository = (PageableRepository<Person,String>)personRepository;
        for(int pageNumber = 0; pageNumber < nPages; ++pageNumber)
        {
            final List<Person> actual = pageablePersonRepository.list(pageNumber * pageSize, pageSize, "last", true);
            final List<Person> expected = allPeople.subList(pageNumber * pageSize, (pageNumber + 1) * pageSize);
            assertCollectionsSame(expected, actual);
        }
        // Test descending sorts too...
        Collections.reverse(allPeople);
        for(int pageNumber = 0; pageNumber < nPages; ++pageNumber)
        {
            final List<Person> actual = pageablePersonRepository.list(pageNumber * pageSize, pageSize, "last", false);
            final List<Person> expected = allPeople.subList(pageNumber * pageSize, (pageNumber + 1) * pageSize);
            assertCollectionsSame(expected, actual);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListWithNestedPropertySort()
    {
        ((PageableRepository<Person,String>)personRepository).list(0, 10, "spouse.last", true);
        ((PageableRepository<Person,String>)personRepository).list(0, 10, "spouse.first", false);
    }
}
