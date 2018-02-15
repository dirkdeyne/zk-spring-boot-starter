package be.enyed.zk.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zkoss.zkmax.au.websocket.WebSocketFilter;
import org.zkoss.zkmax.ui.comet.CometAsyncServlet;

/*
 * This file is basically a copy of ZKEEConfig by Robert Wenzel 
 * Reference: https://github.com/zkoss-demo/zk-spring-boot/blob/master/src/main/java/zk/springboot/config/ZKEEConfig.java 
 *
 */

@Configuration
@ConditionalOnClass(value= {org.zkoss.zkmax.Version.class})
public class ZkMaxConfiguration {
  
  @Bean
  public ServletContextInitializer manualServletConfigInit () {
    return servletContext -> {
      //required to avoid duplicate installing of the CometAsyncServlet
      //startup sequence in spring boot is different to a normal servlet webapp
      servletContext.setAttribute("org.zkoss.zkmax.ui.comet.async.installed", true);
      //servletContext.setAttribute("org.zkoss.zkmax.ws.filter.installed", true); //when FR ZK-3799 is ready (8.5.1 ?)
    };
  }

  @Bean
  public ServletRegistrationBean<CometAsyncServlet> cometAsyncServlet() {
    ServletRegistrationBean<CometAsyncServlet> reg = new ServletRegistrationBean<>(new CometAsyncServlet(), "/zkcomet/*");
    reg.setAsyncSupported(true);
    return reg;
  }

  //optional: use when websockets are enabled in zk.xml
  @Bean
  public FilterRegistrationBean<WebSocketFilter> wsFilter() {
    FilterRegistrationBean<WebSocketFilter> reg = new FilterRegistrationBean<>(new WebSocketFilter());
    reg.addUrlPatterns(SpringBootWebSocketWebAppInit.getWebSocketUrl() + "/*");
    //reg.addUrlPatterns(WebSocketWebAppInit.getWebSocketUrl() + "/*"); //when FR ZK-3799 is ready (8.5.1 ?)
    return reg;
  }
  
}
