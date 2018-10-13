package de.arkadi.tryOut.server;

public class HelloUserBean {

    private String creator;

    public HelloUserBean(String creator) {
        this.creator = creator;
    }

    public void sayHello(String s) {

        System.out.println(s);
    }

    public void setCreator(String creator) {

        this.creator = creator;
    }

    public void printCreator() {

        System.out.println(creator);
    }
}
