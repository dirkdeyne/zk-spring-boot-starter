package be.enyed.zk.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;
import org.zkoss.zk.ui.http.RichletFilter;

@Configuration
@EnableConfigurationProperties({ZkProperties.class})
public class ZkAutoConfiguration {
  
  private static final Logger logger = LoggerFactory.getLogger(ZkAutoConfiguration.class);

  private final ZkProperties zkProperties;
  
  public ZkAutoConfiguration(ZkProperties zkProperties) {
    this.zkProperties = zkProperties;
  }

  @Bean
  public ServletRegistrationBean<DHtmlUpdateServlet> dHtmlUpdateServlet() {
    String updateUri = zkProperties.getUpdateUri();
    ServletRegistrationBean<DHtmlUpdateServlet> servletRegistrationBean = new ServletRegistrationBean<DHtmlUpdateServlet>(new DHtmlUpdateServlet(), updateUri);
    logger.debug("ServletRegistrationBean for DHtmlUpdateServlet, url mapped to "+updateUri);
    return servletRegistrationBean;
  }
 

  @Bean
  @ConditionalOnProperty(prefix = "zk", name = "richlet-enabled")
  public FilterRegistrationBean<RichletFilter> richletFilter() {
    String richletUri = zkProperties.getRichletUri();
    FilterRegistrationBean<RichletFilter> reg = new FilterRegistrationBean<RichletFilter>(new RichletFilter());
    reg.addUrlPatterns(richletUri);
    logger.debug("FilterRegistrationBean for RichletFilter with url pattern " + richletUri);
    return reg;
  }

  @Bean
  public HttpSessionListener httpSessionListener() {
    HttpSessionListener httpSessionListener = new HttpSessionListener();
    return httpSessionListener;
  }

}
