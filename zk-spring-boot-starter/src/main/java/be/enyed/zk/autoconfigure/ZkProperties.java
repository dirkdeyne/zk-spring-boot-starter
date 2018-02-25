package be.enyed.zk.autoconfigure;

import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "zk")
@Validated
public class ZkProperties {
  
  /**
   * You probably should not change this value.
   * 
   * Default value /zkau/*
   */
  @Pattern(regexp = "/[a-z]+/\\*")
  private String updateUri = "/zkau/*";

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
   * Prefix used by viewresolver.
   * 
   * Default value /zkau/web/
   */
  @Pattern(regexp = "(/)||(/([a-z]+/)+)", message="should start and end with a / like /zkau/web/")
  private String viewresolverPrefix = "/zkau/web/";
  
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
   * to redirect ('redirect:/hompage') or forward ('forward:/homepage')
   */
  private String homepage;  
  
  /**
   * Enable auto-mapping URI to a view
   * When enabled then use view/your-zul-page to trigger auto-mapping.
   * 
   * Default value false
   */
  private Boolean viewAutomapping = false;
  
  /**
   * Disable/Enable Richlets.
   * 
   * Default value false
   */
  private Boolean richletEnabled = false;

  public String getUpdateUri() {
    return updateUri;
  }

  public void setUpdateUri(String updateUri) {
    this.updateUri = updateUri;
  }

  public String getRichletUri() {
    return richletUri;
  }

  public void setRichletUri(String richletUri) {
    this.richletUri = richletUri;
  }

  public String getViewresolverPrefix() {
    return viewresolverPrefix;
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
    this.homepage = homepage;
  }
  
  public Boolean getViewreloverEnabled() {
    return viewreloverEnabled;
  }
  
  public void setViewreloverEnabled(Boolean viewreloverEnabled) {
    this.viewreloverEnabled = viewreloverEnabled;
  }
  
  public Boolean getViewAutomapping() {
    return viewAutomapping;
  }
  
  public void setViewAutomapping(Boolean viewAutomapping) {
    this.viewAutomapping = viewAutomapping;
  }
}
