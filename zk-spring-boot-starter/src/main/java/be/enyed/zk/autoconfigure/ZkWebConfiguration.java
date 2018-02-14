package be.enyed.zk.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableConfigurationProperties({ZkProperties.class})
public class ZkWebConfiguration implements WebMvcConfigurer {
  
 
  private static final Logger logger = LoggerFactory.getLogger(ZkWebConfiguration.class);
 
  private final ZkProperties zkProperties;
  
  public ZkWebConfiguration(ZkProperties zkProperties) {
    this.zkProperties = zkProperties;
    logger.debug("ZkWebConfiguration created.");
  }
  
  /**/
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    String updateUri = zkProperties.getUpdateUri();
    registry.addViewController(updateUri);
    logger.debug("ViewControllerRegistry map view controller to "+updateUri);
  }
  /**/
  
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(zkViewResolver());
  }
  
  protected ViewResolver zkViewResolver() {
    String prefix = zkProperties.getViewresolverPrefix();
    String suffix = zkProperties.getViewresolverSuffix();
    //InternalResourceViewResolver resolver = new InternalResourceViewResolver(prefix, suffix);
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    
    resolver.setPrefix(prefix);
    resolver.setSuffix(suffix);
    resolver.setOrder(InternalResourceViewResolver.LOWEST_PRECEDENCE);
    logger.debug("InternalResourceViewResolver to {}your_view_name{}",prefix,suffix);
    return resolver;
  }
  
}
