package be.enyed.zk.autoconfigure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ZkPropertiesTest {
  
  private final ZkProperties zkProperties = new ZkProperties();
  String[] homepage = { "welcome", "/welcome", "welcome.zul", "/welcome.zul", "/welcome.zhtml", 
                        "forward:welcome", "forward:/welcome", "forward:/welcome.zhtml",  
                        "redirect:welcome", "redirect:/welcome", "redirect:/welcome.zul"};
  
  @Test
  public void testHomepageCorrection() throws Exception {
    for (String page : homepage) {
      //when
      zkProperties.setHomepage(page);
      
      //then
      String welcome = zkProperties.getHomepage().replaceFirst("(forward:|redirect:)", ""); //we can ignore Spring Redirects
      
      assertEquals("leading / and zul- or zhtml-extensions should be ignored when setting the homepage-property", "welcome", welcome);
    }
  }
  
  @Test
  public void testJarConfigHomepageToRealPath() throws Exception {
    //given
    //use default jar config
    
    for (String page : homepage) {
      //when
      zkProperties.setHomepage(page);
      
      //then
      String configedHomepage = zkProperties.getHomepage();
      assertEquals("Path to page should not display Spring MVC 'forward:' or 'redirect:'", "src/main/resources/web/welcome.zul", zkProperties.getRealPath(configedHomepage));
    }
  }
  
  @Test
  public void testWarConfigHomepageToRealPath() throws Exception {
    //given
    zkProperties.setConfig("war");
    
    for (String page : homepage) {
      //when
      zkProperties.setHomepage(page);
   
      //then
      String configedHomepage = zkProperties.getHomepage();
      assertEquals("Path to page should not display Spring MVC 'forward:' or 'redirect:'", "/WEB-INF/welcome.zul", zkProperties.getRealPath(configedHomepage));
    }
  }
  
}
