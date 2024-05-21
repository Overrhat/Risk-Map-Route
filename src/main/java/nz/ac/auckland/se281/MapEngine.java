package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Set<Country> countriesSet;
  private Map<Country, List<Country>> adjacenciesMap;

  public MapEngine() {
    // add other code here if you want
    this.countriesSet = new HashSet<>();
    this.adjacenciesMap = new HashMap<>();
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // Put the datas into the countriesSet
    for (String line : countries) {
      String[] parts = line.split(",");
      countriesSet.add(new Country(parts[0], parts[1], parts[2]));
    }

    // Put the datas into the adjacenciesMap
    for (String line : adjacencies) {
      String[] parts = line.split(",");

      // Making the List of values for each key
      List<Country> countriesValues = new ArrayList<>();
      for (int i = 1; i < parts.length; i++) {
        for (Country country : countriesSet) {
          if (parts[i].equals(country.getName())) {
            countriesValues.add(country);
          }
        }
      }

      // Get the country for this line
      Country countryKey = null;
      for (Country country : countriesSet) {
        if (parts[0].equals(country.getName())) {
          countryKey = country;
        }
      }

      // Put this line's data into countriesSet
      adjacenciesMap.put(countryKey, countriesValues);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // Read the input from the user until it gets a valid input
    Boolean isValid = false;
    String userInput;
    Country userCountry = null;

    while (!isValid) {
      // Print to ask for the country name
      MessageCli.INSERT_COUNTRY.printMessage();

      // Scans the input from the user
      userInput = Utils.scanner.nextLine();

      // by using try and catch determine if the input is correct or not
      try {
        userCountry = validCountry(userInput);
        isValid = true;
        break;
      } catch (InvalidCountryException e) {
        String invalidName = e.getCountryName();
        MessageCli.INVALID_COUNTRY.printMessage(invalidName);
      }
    }

    // After having the valid input of country print the info
    String countryName = userCountry.getName();
    String countryContinent = userCountry.getContinent();
    String countryTaxFee = Integer.toString(userCountry.getTaxFee());
    MessageCli.COUNTRY_INFO.printMessage(countryName, countryContinent, countryTaxFee);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}

  /**
   * this method is called from showInfoCountry method. This method will check if the inputed string
   * of the country name is valid. If it is then returns the country that has the name, if not then
   * throws InvalidCountryException.
   *
   * @param inputName inputName is a string which is the name of the country that user inputs.
   * @return the country with the inputed name if is a validCountry.
   */
  public Country validCountry(String inputName) {
    String originalName = inputName;
    inputName = Utils.capitalizeFirstLetterOfEachWord(inputName);

    // Find if there is a valid country
    for (Country country : countriesSet) {
      if (inputName.equals(country.getName())) {
        return country;
      }
    }

    // throw exception when it's invalid
    throw new InvalidCountryException(originalName);
  }
}
