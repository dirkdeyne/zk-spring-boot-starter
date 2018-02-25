package be.enyed.zkboot.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import be.enyed.zkboot.model.Country;
import be.enyed.zkboot.model.Person;

/**
 * Just for demo purpose: lets serve some data :)  
 *
 * @author Dirk
 *
 */

@Service
public class DemoService implements CommandLineRunner{
  
  private Set<Person> people;

  @Override
  public void run(String... args) throws Exception {
    people = Stream.of(
        "BELGIUM,Deyne,Kevin",
        "BELGIUM,Deyne,Dirk",
        "BELGIUM,Groosman,Nicole",
        "BELGIUM,De Vos,Joris",
        "NETHERLANDS,Vos,Luc",
        "NETHERLANDS,Vos,Miriam",
        "LUXEMBOURG,Leany,Laura",
        "LUXEMBOURG,Bourg,Maria",
        "LUXEMBOURG,Deyne,Mario")
        .map(this::mapper)
        .collect(Collectors.toSet());
   
  }

  private Person mapper(String person) {
    String[] data = person.split(",");
    return new Person(Country.valueOf(data[0]), data[1], data[2]);
  }
  
  public Set<Person> all(){
    return people;
  }
  
  public Set<Person> peopleBycountry(Country country){
    return people.stream().filter(p -> country.equals(p.getCountry())).collect(Collectors.toSet());
  }  
  
}
