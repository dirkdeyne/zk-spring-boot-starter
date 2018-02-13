package be.enyed.zk.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;
import org.zkoss.zk.ui.http.RichletFilter;

@Configuration
@EnableConfigurationProperties({ZkProperties.class})
public class ZkAutoConfiguration extends WebMvcConfigurerAdapter {
  
  private static final Logger logger = LoggerFactory.getLogger(ZkAutoConfiguration.class);

  private final ZkProperties zkProperties;
  private final WebMvcProperties webMvcProperties;
  
  public ZkAutoConfiguration(ZkProperties zkProperties, WebMvcProperties webMvcProperties) {
    this.zkProperties = zkProperties;
    this.webMvcProperties = webMvcProperties;
  }

  @Bean
  public ServletRegistrationBean dHtmlUpdateServlet() {
    return new ServletRegistrationBean(new DHtmlUpdateServlet(), zkProperties.getUpdateUri());
  }
 
  private ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix(zkProperties.getViewresolverPrefix());
    resolver.setSuffix(zkProperties.getViewresolverSuffix());
    return resolver;
  }
  
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    super.configureViewResolvers(registry);
    if(webMvcProperties.getView().getPrefix() == null ) {
      logger.debug("zk-viewresolver");
      registry.viewResolver(viewResolver());
    } else {
      logger.debug("custom-viewresolver");
    }
  }

  @Bean
  @ConditionalOnProperty(prefix = "zk", name = "richlet-enabled")
  public FilterRegistrationBean richletFilter() {
    FilterRegistrationBean reg = new FilterRegistrationBean(new RichletFilter());
    reg.addUrlPatterns(zkProperties.getRichletUri());
    return reg;
  }

  @Bean
  public HttpSessionListener httpSessionListener() {
    return new HttpSessionListener();
  }

}
