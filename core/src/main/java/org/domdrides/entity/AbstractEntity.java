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

import java.io.Serializable;

/**
 * A useful superclass for implementing persistent entity classes.  The {@link #equals(Object)} and {@link #hashCode()}
 * methods are based solely on the id value and not upon any "business" properties.  
 *
 * @author James Carman
 * @since 1.0
 */
public abstract class AbstractEntity<IdType extends Serializable> implements Entity<IdType>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private static final long serialVersionUID = -4570499400476247966L;
    private IdType id;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public AbstractEntity()
    {
    }

    protected AbstractEntity(IdType id)
    {
        this.id = id;
    }

//**********************************************************************************************************************
// Entity Implementation
//**********************************************************************************************************************

    public IdType getId()
    {
        return id;
    }

    public int hashCode()
    {
        return ( id != null ? id.hashCode() : 0 );
    }

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    protected void setId( IdType id )
    {
        this.id = id;
    }

//**********************************************************************************************************************
// Canonical Methods
//**********************************************************************************************************************

    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( !( o instanceof AbstractEntity ) )
        {
            return false;
        }

        AbstractEntity that = ( AbstractEntity ) o;

        return !( id != null ? !id.equals(that.id) : that.id != null );
    }
}
