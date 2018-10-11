package de.arkadi.migration.flyway;

public interface FlyWay {

    void initialize();

    void clean();

    void migrate();

    void baseline();

    void info();
}
