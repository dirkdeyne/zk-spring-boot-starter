package be.enyed.zk.autoconfigure;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
  
  @GetMapping("/rest/hello")
  @ResponseBody
  public String hello() {
    return "hello";
  }
  
  @GetMapping("/hello")
  public String helloZul(Model model) {
    model.addAttribute("message","Hello ZK");
    return "test";
  }
  
}
