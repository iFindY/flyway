package de.arkadi.migration;

public interface FlyWay {

    void initialize();

    void clean();

    void migrate();

    void baseline();

    void info();
}
