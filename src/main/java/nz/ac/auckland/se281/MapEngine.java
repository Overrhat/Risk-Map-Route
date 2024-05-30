package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Set<Country> countriesSet;
  private Map<Country, List<Country>> adjacenciesMap;

  /**
   * Class constructor for the MapEngine class. Initialize the instance and load the Map by calling
   * loadMap method.
   */
  public MapEngine() {
    // add other code here if you want
    this.countriesSet = new HashSet<>();
    this.adjacenciesMap = new LinkedHashMap<>();
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
      List<Country> countriesValues = new LinkedList<>();
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
    // Print to ask for the country name
    MessageCli.INSERT_COUNTRY.printMessage();

    // calling getValidCountryFromUser method to get a valid input
    Country userCountry = getValidCountryFromUser();

    // After having the valid input of country print the info
    String countryName = userCountry.getName();
    String countryContinent = userCountry.getContinent();
    String countryTaxFee = Integer.toString(userCountry.getTaxFee());
    MessageCli.COUNTRY_INFO.printMessage(countryName, countryContinent, countryTaxFee);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // Print to ask for the country name for source
    MessageCli.INSERT_SOURCE.printMessage();

    // calling getValidCountryFromUser method to get a valid input for source
    Country userStartCountry = getValidCountryFromUser();

    // Print to ask for the country name for destination
    MessageCli.INSERT_DESTINATION.printMessage();

    // calling getValidCountryFromUser method to get a valid input for destination
    Country userEndCountry = getValidCountryFromUser();

    // If the user inputs the equal country for the both inputs
    if (userStartCountry.equals(userEndCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // Use BFS to find the shortest path from userStartCountry to userEndCountry
    Map<Country, Country> parentMap = new HashMap<>();
    Queue<Country> queue = new LinkedList<>();
    Set<Country> visited = new HashSet<>();
    LinkedList<Country> path = new LinkedList<>();

    queue.add(userStartCountry);
    visited.add(userStartCountry);
    parentMap.put(userStartCountry, null);

    while (!queue.isEmpty()) {
      Country current = queue.poll();
      if (current.equals(userEndCountry)) {
        // Reconstruct the path from userStartCountry to userEndCountry

        Country step = userEndCountry;
        while (step != null) {
          path.addFirst(step);
          step = parentMap.get(step);
        }
        break;
      }
      for (Country neighbor : adjacenciesMap.get(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          parentMap.put(neighbor, current);
          queue.add(neighbor);
        }
      }
    }

    // Calculate the total tax and the continents visited
    int totalTax = 0;
    List<String> continentsVisited = new ArrayList<>();
    String lastContinent = null;
    for (Country country : path) {
      String currentContinent = country.getContinent();
      if (!currentContinent.equals(lastContinent)) {
        continentsVisited.add(currentContinent);
        lastContinent = currentContinent;
      }
      if (!country.equals(userStartCountry)) {
        totalTax += country.getTaxFee();
      }
    }

    // Print the path
    String[] countryNames = new String[path.size()];
    for (int i = 0; i < path.size(); i++) {
      countryNames[i] = path.get(i).getName();
    }
    // Use array to print the path
    MessageCli.ROUTE_INFO.printMessage(Arrays.toString(countryNames));

    // Print the continents visited
    StringBuilder continentsNamesBuilder = new StringBuilder("[");
    for (int i = 0; i < continentsVisited.size(); i++) {
      continentsNamesBuilder.append(continentsVisited.get(i));
      if (i != continentsVisited.size() - 1) {
        continentsNamesBuilder.append(", ");
      } else {
        continentsNamesBuilder.append("]");
      }
    }
    MessageCli.CONTINENT_INFO.printMessage(continentsNamesBuilder.toString());

    // Print the total cross-border taxes to pay
    MessageCli.TAX_INFO.printMessage(Integer.toString(totalTax));
  }

  /**
   * this method is called from showInfoCountry method. This method will check if the inputed string
   * of the country name is valid. If it is then returns the country that has the name, if not then
   * throws InvalidCountryException.
   *
   * @param inputName inputName is a string which is the name of the country that user inputs.
   * @return the country with the inputed name if is a validCountry.
   */
  private Country validCountryCheck(String inputName) {
    inputName = Utils.capitalizeFirstLetterOfEachWord(inputName);

    // Find if there is a valid country
    for (Country country : countriesSet) {
      if (inputName.equals(country.getName())) {
        return country;
      }
    }

    // throw exception when it's invalid
    throw new InvalidCountryException(inputName);
  }

  /**
   * This method returns a country if the input is valid or throws an InvalidCountryException when
   * the input is invalid. It repeats asking for the input until the input is valid.
   *
   * @return the country that is equivalent to the user's input when it's valid.
   */
  private Country getValidCountryFromUser() {
    Boolean isValid = false;
    String userInput;
    Country userCountry = null;

    while (!isValid) {
      // Scans the input from the user
      userInput = Utils.scanner.nextLine();

      // by using try and catch determine if the input is correct or not
      try {
        userCountry = validCountryCheck(userInput);
        isValid = true;
      } catch (InvalidCountryException e) {
        String invalidName = e.getCountryName();
        MessageCli.INVALID_COUNTRY.printMessage(invalidName);
      }
    }

    return userCountry;
  }
}
