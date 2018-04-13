package be.enyed.zk.autoconfigure;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller that handles common pages automatically.
 * Enabled when property zk.view-automapping is set to true.
 * 
 * @author Dirk
 */
@Configuration
@Controller
@ConditionalOnProperty(prefix="zk",name="view-automapping-enabled")
@EnableConfigurationProperties({ZkProperties.class})
public class AutoViewMappingControllerConfiguration {
 
  private static final Logger logger = LoggerFactory.getLogger(AutoViewMappingControllerConfiguration.class);

  private static final String AUTOMAPPING = "view/**";
  
  
  @GetMapping(AUTOMAPPING)
  public String autoViewmapping(HttpServletRequest request) {
    String page = request.getRequestURI().replaceAll("/view", "");
    logger.debug("auto mapping to view: " + page);
    return page;
  }
  
}
