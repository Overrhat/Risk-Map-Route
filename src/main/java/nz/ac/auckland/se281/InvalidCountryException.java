package nz.ac.auckland.se281;

/**
 * This class is unchecked Exception where it is called when the user input was an invalid country
 * name.
 */
public class InvalidCountryException extends RuntimeException {
  private String countryName;

  /**
   * This is the constructor of the InvalidCountryException.
   *
   * @param countryName the name that the user inputed.
   */
  public InvalidCountryException(String countryName) {
    this.countryName = countryName;
  }

  /**
   * This method is the getter method for the name inputed.
   *
   * @return the name of the country that was invalid.
   */
  public String getCountryName() {
    return this.countryName;
  }
}
