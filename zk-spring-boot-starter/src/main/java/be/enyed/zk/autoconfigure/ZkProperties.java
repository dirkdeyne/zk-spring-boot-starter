package be.enyed.zk.autoconfigure;

import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "zk")
@Validated
public class ZkProperties {
  
  /**
   * default value: '/zkau/*'
   * You probably should not change this value  
   */
  @Pattern(regexp = "/[a-z]+/\\*")
  private String updateUri = "/zkau/*";

  /**
   * default value: '/richlet/*'
   */
  @Pattern(regexp = "/([a-z]+/)+\\*")
  private String richletUri = "/richlet/*";
  
  /**
   * default value is '/zkau/web/'
   */
  @Pattern(regexp = "(/)||(/([a-z]+/)+)")
  private String viewresolverPrefix = "/zkau/web/";
  
  /**
   * default value is '.zul'
   */
  private String viewresolverSuffix = ".zul";
  
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

}
