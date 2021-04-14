package sample.other.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "sample.other.configuration.OtherConfiguration")
@ComponentScan(basePackages = "sample.other")
public class OtherConfiguration {

}
