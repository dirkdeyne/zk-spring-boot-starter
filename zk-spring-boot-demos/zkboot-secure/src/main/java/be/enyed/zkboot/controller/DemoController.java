package be.enyed.zkboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
  
  @GetMapping("/secure")
  public String secured() {
    return "secured";
  }
  
  @GetMapping("/admin")
  public String admin() {
    return "secured";
  }
  
}
