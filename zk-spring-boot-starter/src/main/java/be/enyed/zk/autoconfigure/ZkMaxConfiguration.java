package be.enyed.zk.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value= {org.zkoss.zkmax.Version.class})
public class ZkMaxConfiguration {

}
