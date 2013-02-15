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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.LinkedList;
import java.util.Properties;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

/**
 * @requiresDependencyResolution runtime
 *
 */
public abstract class AbstractGeneratorMojo extends AbstractMojo
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     */
    private File srcDirectory;

    /**
     * @parameter expression="${basePackage}"
     * @required
     */
    private String basePackage;
    /**
     * @parameter expression="${project.runtimeClasspathElements}"
     * @required
     * @readonly
     */
    private List<String> classpathElements;

    private ClassLoader enclosingProjectClassLoader;

    /**
     * @parameter expression="${project.version}"
     */
    private String projectVersion;

//----------------------------------------------------------------------------------------------------------------------
// Getter/Setter Methods
//----------------------------------------------------------------------------------------------------------------------

    public String getBasePackage()
    {
        return basePackage;
    }

    public void setBasePackage( String basePackage )
    {
        this.basePackage = basePackage;
    }

    public List<String> getClasspathElements()
    {
        return classpathElements;
    }

    public void setClasspathElements( List<String> classpathElements )
    {
        this.classpathElements = classpathElements;
    }

    private ClassLoader getEnclosingProjectClassLoader() throws MojoExecutionException
    {
        if( enclosingProjectClassLoader == null )
        {
            enclosingProjectClassLoader = createClassLoader();
        }
        return enclosingProjectClassLoader;
    }

    private ClassLoader createClassLoader() throws MojoExecutionException
    {
        try
        {
            final List<URL> urls = new LinkedList<URL>();
            for( String classpathElement : classpathElements )
            {
                final File file = new File(classpathElement);
                urls.add(file.toURL());
            }
            return new URLClassLoader(urls.toArray(new URL[urls.size()]), ClassLoader.getSystemClassLoader());
        }
        catch( MalformedURLException e )
        {
            throw new MojoExecutionException("Invalid classpath element.", e);
        }
    }

    public File getSrcDirectory()
    {
        return srcDirectory;
    }

    public void setSrcDirectory( File srcDirectory )
    {
        this.srcDirectory = srcDirectory;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    protected VelocityEngine createVelocityEngine() throws MojoExecutionException
    {
        try
        {
            final Properties props = new Properties();
            props.setProperty("resource.loader", "classpath");
            props.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            VelocityEngine engine = new VelocityEngine(props);
            engine.init();
            return engine;
        }
        catch( Exception e )
        {
            throw new MojoExecutionException("Unable to initialize velocity context.", e);
        }
    }

    protected void generateSourceFile( String templateName, VelocityContext context, String className )
            throws MojoExecutionException
    {
        try
        {
            final File file = new File(srcDirectory, className.replace('.', '/') + ".java");
            file.getParentFile().mkdirs();
            getLog().info("Generating source file " + file.getAbsolutePath() + "...");
            VelocityEngine engine = createVelocityEngine();
            final Template template = engine.getTemplate(templateName);
            final FileWriter fw = new FileWriter(file);
            template.merge(context, fw);
            fw.close();
        }
        catch( Exception e )
        {
            throw new MojoExecutionException("Unable to generate source file for class " + className + ".", e);
        }
    }

    protected Class getClass( String name ) throws MojoExecutionException
    {
        try
        {
            return getEnclosingProjectClassLoader().loadClass(name);
        }
        catch( ClassNotFoundException e )
        {
            throw new MojoExecutionException("Class " + name + " not found!", e);
        }
    }

    protected String getEntityPackage()
    {
        return basePackage + ".entity";
    }

    protected String getProjectVersion()
    {
        final int ndx = projectVersion.lastIndexOf("-SNAPSHOT");
        return ndx >= 0 ? projectVersion.substring(0, ndx) : projectVersion;
    }

    protected String getRepositoryPackage()
    {
        return basePackage + ".repository";
    }
}
