package de.arkadi.migration;


import java.util.List;

public interface Migration {

    void initialize();

    void clean();

    int migrate();

    void baseline();

    List info();
}
