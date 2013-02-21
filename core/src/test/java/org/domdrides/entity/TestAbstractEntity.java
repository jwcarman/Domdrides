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

package org.domdrides.entity;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author James Carman
 * @version 1.0
 */
public class TestAbstractEntity
{
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testEquals()
    {
        final Mammal m1 = new Mammal();
        m1.setId("1");
        assertEquals(m1, m1, "Entities should be equal to themselves.");
        final Mammal m2 = new Mammal();
        m2.setId("1");
        assertEquals(m1, m2, "Entities of same exact type with same id should be equal.");
        final Cat c1 = new Cat();
        c1.setId("1");
        assertEquals(m1, c1, "Objects of the subclass with the same id should be equal.");
        assertFalse(m1.equals("Hello, World!"), "Entities shouldn't be equal to non-entities.");
        assertFalse(m1.equals(new Mammal()), "Entities with different ids should not be equal.");

        final Cat c2 = new Cat();
        c2.setId(null);
        assertFalse(c1.equals(c2), "Entities with ids should not be equal to other entities without ids.");
        c1.setId(null);
        assertTrue(c1.equals(c2), "Entities with no ids of the same exact type should be equal.");
        c2.setId("2");
        assertFalse(c1.equals(c2), "Entities with no id should not be equal to entities with ids.");
    }

    @Test
    public void testHashCode()
    {
        final Mammal m1 = new Mammal();
        m1.setId("1");
        final Mammal m2 = new Mammal();
        m2.setId("1");
        assertEquals(m1.hashCode(), m2.hashCode(), "Entities of the same type with the same id should have the same hashCode.");
        m1.setId(null);
        assertEquals(m1.hashCode(), 0, "Entities with no id should have a 0 hashCode().");
    }

    @Test
    public void testIdProperty()
    {
        final Mammal m = new Mammal();
        assertNotNull(m.getId());
        m.setId("12345");
        assertEquals(m.getId(), "12345");
    }

    @Test
    public void testDefaultConstructor()
    {
        SimpleEntity entity = new SimpleEntity();
        assertNull(entity.getId());
    }

    private static class SimpleEntity extends AbstractEntity<String>
    {
        @Override
        public void setId(String id)
        {
            super.setId(id);
        }
    }
}
