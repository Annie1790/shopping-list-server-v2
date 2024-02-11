package net.igalex.grocery;

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
@Profile("!test")
public class ProdDbConfig extends AbstractR2dbcConfiguration {
    @Override
    @NonNull
    @Bean
    public ConnectionFactory connectionFactory() {
        var options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "192.168.1.133")
                .option(ConnectionFactoryOptions.DATABASE, "grocery_list_v2")
                .option(ConnectionFactoryOptions.USER, "postgres")
                .option(ConnectionFactoryOptions.PASSWORD, "CciLhaqEt4")
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
                new ClassPathResource("grocery_list.sql")
        ));
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }
}
