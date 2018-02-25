package be.enyed.zkboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.enyed.zkboot.model.Country;
import be.enyed.zkboot.service.DemoService;

@Controller
public class DemoController {
  
  private final DemoService service;
  
  public DemoController(DemoService service) {
    this.service = service;
  }
  
  @GetMapping("/people")
  public String all(Model model) {
    model.addAttribute("people", service.all());
    return "interaction/people";
  }
  
  @GetMapping("/people/{country}")
  public String data(Model model, @PathVariable("country") Country country) {
    model.addAttribute("people", service.peopleBycountry(country))
         .addAttribute("country", country);
    return "interaction/people";
  }
  
}
