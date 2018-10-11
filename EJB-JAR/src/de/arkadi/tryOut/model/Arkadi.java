package de.arkadi.tryOut.model;

import java.io.Serializable;

public class Arkadi implements Serializable {

  private String creator;

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
