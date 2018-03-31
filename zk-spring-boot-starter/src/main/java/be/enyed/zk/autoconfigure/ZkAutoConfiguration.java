package be.enyed.zk.autoconfigure;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
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
  @ConditionalOnProperty(prefix="zk",name="config", havingValue="war")
  public ServletRegistrationBean<DHtmlLayoutServlet> dHtmlLayoutServlet() {
    logger.info("ServletRegistrationBean for DHtmlLayoutServlet due zk.config = " + zkProperties.getConfig());
    
    Map<String, String> params = new HashMap<>();
    params.put("update-uri", "/zkau");

    DHtmlLayoutServlet dHtmlLayoutServlet = new DHtmlLayoutServlet();
    
    ServletRegistrationBean<DHtmlLayoutServlet> servletRegistrationBean = new ServletRegistrationBean<>(dHtmlLayoutServlet, "*.zul");
    servletRegistrationBean.setLoadOnStartup(1);
    servletRegistrationBean.setInitParameters(params);
    return servletRegistrationBean;
  }
  
  @Bean
  public ServletRegistrationBean<DHtmlUpdateServlet> dHtmlUpdateServlet() {
    String updateUri = zkProperties.getUpdateUri();
    ServletRegistrationBean<DHtmlUpdateServlet> servletRegistrationBean = new ServletRegistrationBean<>(new DHtmlUpdateServlet(), updateUri);
    logger.info("ServletRegistrationBean for DHtmlUpdateServlet, url mapped to "+updateUri);
    return servletRegistrationBean;
  }
 

  @Bean
  @ConditionalOnProperty(prefix = "zk", name = "richlet-enabled")
  public FilterRegistrationBean<RichletFilter> richletFilter() {
    String richletUri = zkProperties.getRichletUri();
    FilterRegistrationBean<RichletFilter> reg = new FilterRegistrationBean<>(new RichletFilter());
    reg.addUrlPatterns(richletUri);
    logger.info("FilterRegistrationBean for RichletFilter with url pattern " + richletUri);
    return reg;
  }

  @Bean
  public HttpSessionListener httpSessionListener() {
    HttpSessionListener httpSessionListener = new HttpSessionListener();
    return httpSessionListener;
  }

}
