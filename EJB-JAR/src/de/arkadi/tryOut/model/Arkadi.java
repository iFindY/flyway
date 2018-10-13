package de.arkadi.tryOut.model;


public class Arkadi {
    private String creator;

    public Arkadi(String creator) {
        this.creator = creator;
    }


    public void sayKu() {

        System.out.println("KU!");
    }

    public void init() {

        System.out.println("Arkadi is online");
    }

    public void setCreator(String creator) {

        this.creator = creator;
    }

    public void getCreator() {

        System.out.println(creator);
    }
}
