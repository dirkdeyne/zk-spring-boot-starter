package be.enyed.zk.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class ZkAutoConfigurationTest {
  
  @Autowired
  private MockMvc mvc;
  
  @Test
  public void testRestCall() throws Exception {
    mvc.perform(get("/rest/hello"))
      .andExpect(status().isOk())
      .andExpect(content().string("hello"));
  }
  
  @Test
  public void testZul() throws Exception {
    MvcResult result = 
         mvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", is("Hello ZK")))
            .andReturn();
 
    assertThat(result.getModelAndView().getViewName()).isEqualTo("test");
    
    assertThat(result.getResponse().getForwardedUrl()).isEqualTo("/zkau/web/test.zul");
  }
}
