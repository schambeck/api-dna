package com.schambeck.dna.web.util;

import org.testcontainers.containers.PostgreSQLContainer;

public class DnaPostgresqlContainer extends PostgreSQLContainer<DnaPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:12.9";
    private static DnaPostgresqlContainer container;

    private DnaPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static DnaPostgresqlContainer getInstance() {
        if (container == null) {
            container = new DnaPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}
