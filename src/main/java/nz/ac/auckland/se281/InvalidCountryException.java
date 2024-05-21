package nz.ac.auckland.se281;

public class InvalidCountryException extends RuntimeException {
  private String countryName;

  public InvalidCountryException(String countryName) {
    this.countryName = countryName;
  }

  public String getCountryName() {
    return this.countryName;
  }
}
