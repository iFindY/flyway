package com.novomind.ipim.core.util.arkadi.migration;


import java.util.List;

public interface Migration {

    void initialize();

    void clean();

    String migrate();

    void baseline();

    List info();
}
