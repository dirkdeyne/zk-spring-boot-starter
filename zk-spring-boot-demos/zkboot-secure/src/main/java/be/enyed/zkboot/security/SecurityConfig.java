package be.enyed.zkboot.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    // zk specific -- maybe disable CSRF set by default via auto-configuration? --
    http.csrf().disable() // can be disabled safely; ZK unique desktop ID generation prevents Cross-Site Request Forgery attacks
        
        // application specific
        .authorizeRequests()
          .mvcMatchers("/secure").hasRole("USER")
          .mvcMatchers("/admin").hasRole("ADMIN")
          .antMatchers("/zkau/web/**/**.zul").denyAll() // calling a zul-page directly is not allowed -- should we put this in the auto-configuration to? --
          .anyRequest().permitAll()
        .and()
          .formLogin().loginPage("/login").defaultSuccessUrl("/")
        .and()  
          .logout().clearAuthentication(true).logoutSuccessUrl("/");
  }
  
}