package be.enyed.zk.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration(classes= {ZkAutoConfiguration.class})
public class TestApplication {
  
  public static void main(String[] args) throws Exception {
    SpringApplication.run(TestApplication.class, args);
  }

}
