package net.igalex.grocery.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@Profile("test")
public class TestDbConfig extends AbstractR2dbcConfiguration {

    @Override
    @NonNull
    @Bean
    public ConnectionFactory connectionFactory() {
        final String HOST = System.getenv("POSTGRES_HOST");
        final String USER = System.getenv("POSTGRES_USER");
        final String DB = System.getenv("POSTGRES_DB");
        final String PASSWORD = System.getenv("POSTGRES_PASSWORD");
        var options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, HOST)
                .option(ConnectionFactoryOptions.DATABASE, DB)
                .option(ConnectionFactoryOptions.USER, USER)
                .option(ConnectionFactoryOptions.PASSWORD, PASSWORD)
                .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                .build();
        return ConnectionFactories.get(options);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory factory) {

        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(factory);
        var databasePopulator = new CompositeDatabasePopulator();
        databasePopulator.addPopulators(new ResourceDatabasePopulator(
                new ClassPathResource("grocery_list.sql"),
                new ClassPathResource("test_data.sql")
        ));
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }
}
