package de.arkadi.migration;

public interface Migration {

    void initialize();

    void clean();

    void migrate();

    void baseline();

    void info();
}
