package com.michelin.ns4kafka.testcontainers;


import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static java.lang.String.format;

/**
 * This class is a testcontainers implementation for the
 * <a href="Confluent Schema Registry">https://docs.confluent.io/current/schema-registry/index.html</a>
 * Docker container.
 */
public class SchemaRegistryContainer extends GenericContainer<SchemaRegistryContainer> {
    /**
     * Schema Registry port
     */
    private static final int SCHEMA_REGISTRY_INTERNAL_PORT = 8081;

    /**
     * Schema Registry network alias
     */
    private final String networkAlias = "schema-registry";

    /**
     * Constructor
     *
     * @param dockerImageName The Docker image name of the Schema Registry
     * @param bootstrapServers The bootstrap servers URL
     */
    public SchemaRegistryContainer(DockerImageName dockerImageName, String bootstrapServers) {
        super(dockerImageName);

        this
                .withEnv("SCHEMA_REGISTRY_HOST_NAME", "localhost")
                .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", bootstrapServers);

        withExposedPorts(SCHEMA_REGISTRY_INTERNAL_PORT);
        withNetworkAliases(networkAlias);

        waitingFor(Wait.forHttp("/subjects"));
    }

    /**
     * Get the current URL of the schema registry
     *
     * @return The current URL of the schema registry
     */
    public String getUrl() {
        return format("http://%s:%d", this.getContainerIpAddress(), this.getMappedPort(SCHEMA_REGISTRY_INTERNAL_PORT));
    }

    /**
     * Get the local url
     *
     * @return
     */
    public String getInternalUrl() {
        return format("http://%s:%d", networkAlias, SCHEMA_REGISTRY_INTERNAL_PORT);
    }
}

