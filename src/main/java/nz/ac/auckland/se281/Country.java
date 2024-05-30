package nz.ac.auckland.se281;

/** This class is the class which has the information about the specific country. */
public class Country {
  private String name;
  private String continent;
  private int taxFee;

  /** Class constructor for the Country class. */
  public Country(String name, String continent, String taxFee) {
    this.name = name;
    this.continent = continent;
    this.taxFee = Integer.parseInt(taxFee);
  }

  /**
   * getter method which returns the name of the country.
   *
   * @return name of this instance of the country.
   */
  public String getName() {
    return name;
  }

  /**
   * getter method which returns the continent of the country.
   *
   * @return continent of this instance of the country.
   */
  public String getContinent() {
    return continent;
  }

  /**
   * getter method which returns the taxFee of the country.
   *
   * @return taxFee of this instance of the country.
   */
  public int getTaxFee() {
    return taxFee;
  }
}
