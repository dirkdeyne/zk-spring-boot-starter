package be.enyed.zk.autoconfigure;

import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.zkoss.web.util.resource.ClassWebResource;

/**
 * Configuration properties for ZK
 * 
 * @author Dirk
 *
 */
@ConfigurationProperties(prefix = "zk")
@Validated
public class ZkProperties {
  
  private static final Logger logger = LoggerFactory.getLogger(ZkProperties.class);

  
  public static final String AUTOMAPPING_VIEW_PREFIX= "/view/**";

  public static final String JAR_RESOURCE_LOCATION = "src/main/resources";
  
  public static final String UPDATE_SERVLET_MAPPING = "/zkau"; //servlet mapping for ZK's update servlet
  public static final String UPDATE_URI = UPDATE_SERVLET_MAPPING +"/*" ;
  
  public static final String WEB_CONFIG_BASE = "/WEB-INF";
  public static final String JAR_CONFIG_BASE = UPDATE_SERVLET_MAPPING + ClassWebResource.PATH_PREFIX;
  
  /**
   * Default is jar
   */
  @Pattern(regexp="(jar)|(war)")
  private String config = "jar";

  /**
   * @TODO needs additional configuration
   */
  @Pattern(regexp = "/([a-z]+/)+\\*", message="failed pattern match, must match /([a-z]+/)+\\*")
  private String richletUri = "/richlet/*";
  
  /**
   * Register an @InternalResourceViewResolver when enabled. 
   * Enabled by default, the prefix and suffix are configurable 
   * via zk.viewresolver.prefix and zk.viewresolver.suffix
   * 
   * Default value true
   */
  private Boolean viewreloverEnabled = true;
  
  /**
   * Base-folder location of your pages.
   * The final prefix is determined depending on zk.config (application's configuration)
   * example: 
   *   - a request to view `hello` and `zk.viewresolver-prefix = pages` 
   *     will result in /zkau/web/pages/hello.zul
   *  
   *   - a request to view `hello` and `zk.viewresolver-prefix = pages` and `zk-config = war`
   *     will result in /WEB-INF/pages/hello.zul
   */
  private String viewresolverPrefix = "";
  
  /**
   * Suffix used by viewresolver.
   * 
   * Default value .zul
   */
  @Pattern(regexp = "(\\.zul)||(\\.zhtml)", message="should be .zul or .zhtml")
  private String viewresolverSuffix = ".zul";
  
  /**
   * No value set, so you need to provide it yourself.
   * When set a @Controller will map a root requests to your homepage.
   * But you are allowed to use the spring-mvc mechanism 
   * to redirect ('redirect:homepage') or forward ('forward:homepage')
   */
  private String homepage;  
  
  /**
   * Enable auto-mapping URI to a view
   * When enabled then use view/your-zul-page to trigger auto-mapping.
   * 
   * Default value false
   */
  private Boolean viewAutomappingEnabled = false;
  
  /**
   * Disable/Enable Richlets.
   * 
   * Default value false
   */
  private Boolean richletEnabled = false;
  
  public String getRealPath(String path) {
    String page = path;
    if(page.startsWith("forward:") || page.startsWith("redirect:")) {
      page = page.replaceFirst("(forward:|redirect:)", "");
    }
    String zkau = getViewresolverPrefix() + page + getViewresolverSuffix();
    return zkau.replaceFirst(UPDATE_SERVLET_MAPPING, JAR_RESOURCE_LOCATION);
  }
  
  public String getConfig() {
    return config;
  }
  
  public void setConfig(String config) {
    this.config = config;
  }
  
  public String getUpdateUri() {
    return UPDATE_URI;
  }

  public String getRichletUri() {
    return richletUri;
  }

  public void setRichletUri(String richletUri) {
    this.richletUri = richletUri;
  }

  public String getViewresolverPrefix() {
    return resolvePrefix();
  }
  
  private String resolvePrefix() {
    String prefix = viewresolverPrefix;
    if (!prefix.startsWith("/")) {
      prefix = "/" + prefix;
    }
    if (!prefix.endsWith("/")) {
      prefix += "/";
    }
    return config.equals("jar") ? JAR_CONFIG_BASE + prefix: WEB_CONFIG_BASE + prefix;
  }

  public void setViewresolverPrefix(String viewresolverPrefix) {
    this.viewresolverPrefix = viewresolverPrefix;
  }

  public String getViewresolverSuffix() {
    return viewresolverSuffix;
  }

  public void setViewresolverSuffix(String viewresolverSuffix) {
    this.viewresolverSuffix = viewresolverSuffix;
  }

  public Boolean getRichletEnabled() {
    return richletEnabled;
  }

  public void setRichletEnabled(Boolean richletEnabled) {
    this.richletEnabled = richletEnabled;
  }

  public String getHomepage() {
    return homepage;
  }
  
  public void setHomepage(String homepage) {
    if(homepage.startsWith("/") || homepage.startsWith("redirect:/") || homepage.startsWith("forward:/"))  {
      String correction = homepage.replaceFirst("/", "");
      logger.warn(String.format("zk.homepage should not start with '/', changed homepage-property from '%s' to '%s'", homepage, correction));
      homepage = correction;
    }
    if(homepage.endsWith(".zul") || homepage.endsWith(".zhtml")) {
      String correction = homepage.substring(0, homepage.lastIndexOf("."));
      logger.warn(String.format("zk.homepage should not end with a '.zul' or '.zhtml',changed homepage-property from '%s' to '%s'", homepage, correction));
      homepage = correction;
    }
    this.homepage = homepage;
  }
  
  public Boolean getViewreloverEnabled() {
    return viewreloverEnabled;
  }
  
  public void setViewreloverEnabled(Boolean viewreloverEnabled) {
    this.viewreloverEnabled = viewreloverEnabled;
  }

  public Boolean getViewAutomappingEnabled() {
    return viewAutomappingEnabled;
  }
  
  public void setViewAutomappingEnabled(Boolean viewAutomappingEnabled) {
    this.viewAutomappingEnabled = viewAutomappingEnabled;
  }
}
