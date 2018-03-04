package be.enyed.zkboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  private static String currentUser = System.getProperty("user.name");
  private static BCryptPasswordEncoder bcrypt= new BCryptPasswordEncoder(16);
  private static String password = bcrypt.encode("password");
  
  @Override
  protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
      .inMemoryAuthentication()
        .withUser("user").password(password).roles("USER")
      .and()
        .withUser(currentUser).password(password).roles("USER","ADMIN")
      .and()
        .passwordEncoder(bcrypt);
  } 
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      //zk specific? can we do this in our autoconfiguration 
//      .headers()
//        .httpStrictTransportSecurity().maxAgeInSeconds(20)
//      .and()
//        .frameOptions().disable()
//      .and()
        //ZK does not need crsf check
       // .csrf().disable()
        .authorizeRequests()
        //allow zk to call js & css
        //.regexMatchers("(/zkau){0,1}/web/[A-Za-z0-9]+/(js/zul.lang.wpd|zul/css/zk.wcs)").permitAll()
        //we don't allow the user to call a zul directly
        //.regexMatchers(".*\\.zul(\\?.*)?").denyAll()     
        
        //application specific sound be done by user
        //.antMatchers("/","/welcome","~./common/**","/view/common/**","~./css/**.css","**/favicon.ico","/logout").permitAll()
        .mvcMatchers("/secure").hasRole("USER")
        .mvcMatchers("/admin").hasRole("ADMIN")
        .mvcMatchers("/zkau/**").denyAll() //we don't want the user to call
        .anyRequest().permitAll()
      .and()
        .logout().clearAuthentication(true).logoutSuccessUrl("/")
      .and()  
       // .anonymous().disable()
        .httpBasic();
  }
  
  public static void main(String[] args) {
    String regex= "(/zkau){0,1}/web/[A-Za-z0-9]+/(js/zul.lang.wpd|zul/css/zk.wcs)";
    String[] tests = {"/zkau/web/195bd3b4/js/zul.lang.wpd","/zkau/web/195bd/b4/js/zul.lang.wpd","/web/195bd3b4/zul/css/zk.wcs","/zkau/web/abbd195BD3b4/js/zul.lang.wpd"};
    for (String test : tests) {
      System.err.println(String.format("request '%s' matches regex '%s' => %s", test, regex , test.matches(regex)));
    }
  }
  
}