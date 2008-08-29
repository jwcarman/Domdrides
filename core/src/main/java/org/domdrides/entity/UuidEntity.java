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

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * An entity class that uses a {@link UUID}'s String value as
 * its id.
 *
 *
 * @since 1.0
 */
@MappedSuperclass
public class UuidEntity extends AbstractEntity<String>
{
    public UuidEntity()
    {
        setId(UUID.randomUUID().toString());
    }

    @Id
    public String getId()
    {
        return super.getId();
    }

    protected void setId(String id)
    {
        super.setId(id);
    }
}
