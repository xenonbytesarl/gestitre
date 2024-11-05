package cm.xenonbyte.gestitre.infrastructure;

import io.quarkus.test.junit.QuarkusTestProfile;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
public class ITProfile implements QuarkusTestProfile {
    static PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.3-alpine")
            .withDatabaseName("gestitredb")
            .withUsername("gestitre")
            .withPassword("gestitrepassword");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "quarkus.datasource.jdbc.url", POSTGRESQL_CONTAINER.getJdbcUrl(),
                "quarkus.datasource.username", POSTGRESQL_CONTAINER.getUsername(),
                "quarkus.datasource.password", POSTGRESQL_CONTAINER.getPassword()
        );
    }
}
