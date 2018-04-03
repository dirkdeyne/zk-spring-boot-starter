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
public class HomePageControllerConfiguration {
  
  private static final Logger logger = LoggerFactory.getLogger(HomePageControllerConfiguration.class);
  

  private final String homepage;
  
  public HomePageControllerConfiguration(ZkProperties zkProperties) {
    homepage = zkProperties.getHomepage();
    logger.info(String .format("HomePageController created with '%s' as your homepage (resolved to '%s').", homepage, zkProperties.getRealPath(homepage)));
  }

  @GetMapping("/")
  public String home() {
    return homepage;
  }
  
}
