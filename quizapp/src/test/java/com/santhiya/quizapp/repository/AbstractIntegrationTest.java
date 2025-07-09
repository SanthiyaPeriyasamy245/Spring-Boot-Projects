package com.santhiya.quizapp.repository;

import com.github.dockerjava.api.model.PortBinding;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
public class AbstractIntegrationTest {


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withReuse(true)
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password")
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> {
                cmd.getHostConfig().withPortBindings(PortBinding.parse("5435:5432"));
            });

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}