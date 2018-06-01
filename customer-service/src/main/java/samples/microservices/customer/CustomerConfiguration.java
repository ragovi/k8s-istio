package samples.microservices.customer;

//import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("samples.microservices.customer")
public class CustomerConfiguration {

//	@Bean
//	public AlwaysSampler defaultSampler() {
//	  return new AlwaysSampler();
//	}
//	
}
