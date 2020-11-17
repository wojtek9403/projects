package mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// jezeli chcemy wywalić encje i repos do innego pakietu to piszemy te 2 @ i wskazujemy pakiet z encjami !!!
@EnableJpaRepositories(basePackages = "dbModelsnDAOs")
@EntityScan(basePackages = "dbModelsnDAOs")
@SpringBootApplication
public class SocialMediaDemo3Application {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaDemo3Application.class, args);
	}

}
