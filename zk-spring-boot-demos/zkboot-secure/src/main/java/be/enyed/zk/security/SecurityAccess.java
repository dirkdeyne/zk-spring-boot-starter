package be.enyed.zk.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityAccess {
  
  private static final Logger logger = LoggerFactory.getLogger(SecurityAccess.class);

  public static Boolean isAuthenticated() {
    Authentication authentication = getAuthentication();
    logger.debug(" => " + (null != authentication ? authentication.getAuthorities() : "not authenticated!" ));
    return null != authentication && !authentication.getAuthorities().isEmpty();
  }
  
  public static Boolean isAnonymous() {
    return hasRole("ROLE_ANONYMOUS");
  }
  
  public static Boolean hasRole(String roles) {
    return hasAnyOfRoles(roles);
  }
  
  public static Boolean hasAnyOfRoles(String role) {
    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    authorities.retainAll(getAuthorities());
    return !authorities.isEmpty();
  }
  
  public static Boolean hasAllOfRoles(String role) {
    return getAuthorities().containsAll(AuthorityUtils.commaSeparatedStringToAuthorityList(role));
  }
  
  public static Boolean hasNoneOfRoles(String role) {
    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    return Collections.disjoint(authorities, getAuthorities());
  }
  
  public static String getPrincipal(){
    Authentication authentication = getAuthentication();
    return authentication != null ? authentication.getName() : "UNKNOWN";
  }
  
  public static List<String> getRoles(){
    return getAuthorities().stream()
          .map(a -> a.getAuthority())
          .collect(Collectors.toList());
    
  }
  
  protected static Authentication getAuthentication(){
    return SecurityContextHolder.getContext().getAuthentication();
  }
  
  protected static List<GrantedAuthority> getAuthorities(){
    List<GrantedAuthority> authorities = new ArrayList<>();
    Authentication authentication = getAuthentication();
    if(null != authentication) {
      authorities.addAll(authentication.getAuthorities());
    }
    return authorities;
  }
  
}
