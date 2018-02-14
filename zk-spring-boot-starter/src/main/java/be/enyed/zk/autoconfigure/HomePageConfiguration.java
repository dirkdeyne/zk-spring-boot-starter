package be.enyed.zk.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@Controller
@ConditionalOnProperty(prefix="zk", name="homepage")
@EnableConfigurationProperties({ZkProperties.class})
public class HomePageConfiguration {
  
  private static final Logger logger = LoggerFactory.getLogger(HomePageConfiguration.class);
  

  private final String homepage;
  
  public HomePageConfiguration(ZkProperties zkProperties) {
    homepage = zkProperties.getHomepage();
    logger.debug("HomePageController created with {} as your homepage.", homepage);
  }

  @GetMapping("/")
  public String home() {
    return homepage;
  }
  
}
