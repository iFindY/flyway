package de.arkadi.decorator;

import de.arkadi.migration.Migration;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.util.List;


@Decorator
public class OutputDecorator implements Migration {


    @Inject
    @Delegate
    Migration migration;


    public void initialize() {

    }

    public void clean() {

    }

    public String migrate() {
        System.out.println("na".repeat(16) + "Batmen");
        String migrationCount = migration.migrate();
        return "Migrations Applied : " + migrationCount;
    }

    @Override
    public void baseline() {

    }

    @Override
    public List info() {
        return null;
    }
}
