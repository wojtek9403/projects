package mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "dbModelsnDAOs")
@EntityScan(basePackages = "dbModelsnDAOs")
@SpringBootApplication
public class socialmediademo3Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(socialmediademo3Application.class, args);

		String[] beans = context.getBeanDefinitionNames();
		
		for(String x:beans)
		System.err.println(x);
		
		
	}

}
