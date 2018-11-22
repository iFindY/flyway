package de.arkadi.decorator;

import de.arkadi.migration.Migration;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;


@Decorator
public class OutputDecorator implements Migration {


    @Inject
    @Delegate
    @Any
    Migration migration;


    @Override
    public void initialize() {

    }

    @Override
    public void clean() {

    }

    @Override
    public String migrate() {
        String migrationCount = migration.migrate();
        //TODO remove
        System.out.println("has been decorated:: returned " + migrationCount);
        return "Migrations Applied " + migrationCount;
    }

    @Override
    public void baseline() {

    }

    @Override
    public List info() {
        return null;
    }
}
