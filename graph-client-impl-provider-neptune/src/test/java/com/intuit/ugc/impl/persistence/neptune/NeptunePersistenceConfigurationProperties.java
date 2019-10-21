package com.intuit.ugc.impl.persistence.neptune;

import com.intuit.ugc.api.PersistenceException;
import com.toddfast.util.preconditions.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Properties;

import static java.util.Objects.isNull;

public class NeptunePersistenceConfigurationProperties extends Properties implements NeptunePersistenceConfiguration  {

    private static final Logger LOG = LoggerFactory.getLogger(NeptunePersistenceConfigurationProperties.class);

    public NeptunePersistenceConfigurationProperties() {
        super();
    }

    public NeptunePersistenceConfigurationProperties(Properties defaults) {
        super(defaults);
    }

    public NeptunePersistenceConfigurationProperties(String resourceName) throws IOException, PersistenceException {
        this(resourceName, null);
    }

    public NeptunePersistenceConfigurationProperties(String resourceName, ClassLoader classLoader)
            throws IOException, PersistenceException {
        super();
        load(resourceName, classLoader);
    }

    public final void load(String resourceName, ClassLoader classLoader) throws IOException, PersistenceException {
        Preconditions.argumentNotNull(resourceName, "resourceName");
        if (isNull(classLoader)) {
            classLoader = NeptunePersistenceConfigurationProperties.class.getClassLoader();
        }
        try {
            super.load(classLoader.getResource(resourceName).openStream());
            LOG.info("Successfully loaded DSE provider configuration " + "from resource \"{}\"", resourceName);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("Could not load configuration from resource \"" + resourceName + "\"", e);
        }
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        super.load(inStream);
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        super.load(reader);
    }

    /**
     * Denotes if a Key is required
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Required {
    }

    /**
     * The property keys used to back the configuration property methods
     *
     */
    public static enum Key {
        @Required host, @Required port, @Required graph, @Required keyfile
    }

    @Override
    public String getHost() {
        return getProperty(Key.host.toString());
    }

    @Override
    public int getPort() {
        return Integer.parseInt(getProperty(Key.port.toString()));
    }

    @Override
    public String getGraph() {
        return getProperty(Key.graph.toString());
    }

    @Override
    public String getKeyFile() { return getProperty(Key.keyfile.toString()); }

}
