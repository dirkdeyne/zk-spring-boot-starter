package be.enyed.zkboot.viewmodel;

import java.util.Arrays;
import java.util.Set;

import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.ListModelList;

import be.enyed.zkboot.model.Country;
import be.enyed.zkboot.model.Person;

public class PeopleViewmodel {
  
  private ListModelList<Person> people;
  private ListModelList<Country> countries = new ListModelList<>(Arrays.asList(Country.values()));
  private Country country;
  
  @Init
  public void init(@ExecutionParam("people") Set<Person> people, @ExecutionParam("country") Country country) {
    this.country = country;
    this.people = new ListModelList<>(people);
  }
  
  public Country getCountry() {
    return country;
  }
  
  public void setCountry(Country country) {
    this.country = country;
  }
  
  public ListModelList<Country> getCountries() {
    return countries;
  }
  
  public ListModelList<Person> getPeople() {
    return people;
  }
  
}
