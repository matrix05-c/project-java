package project.bibliotheque.models;


public class Gender {
  public static Gender man = new Gender("man", "");
  public static Gender woman = new Gender("woman", "");

  private String name;
  private String icon;

  private Gender(String name, String icon) {
    this.name = name;
    this.icon = icon;
  }

  public String toString() {
    return this.icon + " " + this.name;
  }

  public String getName() {
    return this.name;
  }

  public String getIcon() {
    return this.icon;
  }
}
