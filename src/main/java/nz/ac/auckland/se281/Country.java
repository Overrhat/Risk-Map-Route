package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private int taxFee;

  public Country(String name, String continent, String taxFee) {
    this.name = name;
    this.continent = continent;
    this.taxFee = Integer.parseInt(taxFee);
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getTaxFee() {
    return taxFee;
  }
}
