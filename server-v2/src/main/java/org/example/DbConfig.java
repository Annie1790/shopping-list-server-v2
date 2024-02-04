package org.example;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
@Configuration
public class DbConfig extends AbstractR2dbcConfiguration {
    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:postgresql://192.168.1.133:5432/grocery_list_v2");
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory factory) {
        var initalizer = new ConnectionFactoryInitializer();
        initalizer.setConnectionFactory(factory);
        var databasePopulator = new CompositeDatabasePopulator();
        databasePopulator.addPopulators(new ResourceDatabasePopulator(
                new ClassPathResource("grocery_list.sql")
        ));
        initalizer.setDatabasePopulator(databasePopulator);
        return initalizer;
    }
}
