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

package org.domdrides.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.VelocityContext;

/**
 * @goal generate-entity
 * @auothor James Carman
 */
public class GenerateEntityMojo extends AbstractGeneratorMojo
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    /**
     * @parameter expression="${entityName}"
     * @required
     */
    private String entityName;

    /**
     * @parameter expression="${superClass}" default-value="org.domdrides.entity.UuidEntity"
     */
    private String superClass;

//**********************************************************************************************************************
// Mojo Implementation
//**********************************************************************************************************************

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        final String entityPackage = getEntityPackage();
        try
        {
            final VelocityContext ctx = new VelocityContext();
            ctx.put("entityName", entityName);
            ctx.put("packageName", getEntityPackage());
            ctx.put("superClass", getClass(superClass));
            ctx.put("projectVersion", getProjectVersion());
            generateSourceFile("EntityTemplate.vm", ctx, entityPackage + "." + entityName);
        }
        catch( Exception e )
        {
            throw new MojoExecutionException("Unable to generate source file.", e);
        }
    }

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    public String getEntityName()
    {
        return entityName;
    }

    public void setEntityName( String entityName )
    {
        this.entityName = entityName;
    }

    public String getSuperClass()
    {
        return superClass;
    }

    public void setSuperClass( String superClass )
    {
        this.superClass = superClass;
    }
}
