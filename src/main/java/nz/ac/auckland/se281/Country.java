package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String contenent;
  private int taxFee;

  public Country(String name, String contenent, String taxFee) {
    this.name = name;
    this.contenent = contenent;
    this.taxFee = Integer.parseInt(taxFee);
  }

  public String getName() {
    return name;
  }

  public String getContenent() {
    return contenent;
  }

  public int getTaxFee() {
    return taxFee;
  }
}
