package be.enyed.zk.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
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
  
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(zkViewResolver());
  }
  
  protected ViewResolver zkViewResolver() {
    String prefix = zkProperties.getViewresolverPrefix();
    String suffix = zkProperties.getViewresolverSuffix();
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix(prefix);
    resolver.setSuffix(suffix);
    resolver.setOrder(InternalResourceViewResolver.LOWEST_PRECEDENCE);
    logger.debug(String.format("InternalResourceViewResolver to %syour_view_name%s, "
        + "example using default settings: "
        + "viewname 'common/about' will be resolved to '/zkau/web/common/about.zul' "
        + "that will look for the file /src/main/resources/web/common/about.zul"
        ,prefix,suffix,suffix));
    return resolver;
  }
  
}