package be.enyed.zk.autoconfigure;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZkPropertiesTest {
  
  private ZkProperties props = new ZkProperties();
  
  @Test
  public void testDefaultsViewResolverPrefix() throws Exception {
    String expected = "/zkau/web/";
    String given = props.getViewresolverPrefix();
    assertEquals(failMessage(given, expected), expected , props.getViewresolverPrefix());
    
    expected = "/WEB-INF/";
    props.setConfig("war");
    assertEquals(failMessage(given, expected), expected , props.getViewresolverPrefix());
  }
  
  @Test
  public void testJarViewResolverPrefix() throws Exception {
    String expected = "/zkau/web/pages/";
    String given = "pages";
    
    props.setViewresolverPrefix(given);
    assertEquals(failMessage(given, expected), expected , props.getViewresolverPrefix());
    
    given = "/pages/" ;
    props.setViewresolverPrefix(given);
    assertEquals(failMessage(given, expected), expected, props.getViewresolverPrefix());
  }
  
  @Test
  public void testWarViewResolverPrefix() throws Exception {
    String expected = "/WEB-INF/pages/";
    String given = "pages";
    props.setConfig("war");
    
    props.setViewresolverPrefix(given);
    assertEquals(failMessage(given, expected), expected , props.getViewresolverPrefix());
    
    given = "/pages/" ;
    props.setViewresolverPrefix(given);
    assertEquals(failMessage(given, expected), expected, props.getViewresolverPrefix());
  }
  
  private String failMessage(String given, String expected) {
    return String.format("'%s' should resolve to '%s'", given, expected);
  }
  
}
