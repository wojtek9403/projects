package mainPackage;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

@EnableJpaRepositories(basePackages = "dbModelsnDAOs")
@EntityScan(basePackages = "dbModelsnDAOs")
@SpringBootApplication
public class socialmediademo3Application {

	public static void main(String[] args) {
		
		File userRepository = new File("userImg/");
		if (!userRepository.exists()){
			userRepository.mkdir();
		}
		
		ConfigurableApplicationContext context = SpringApplication.run(socialmediademo3Application.class, args);

		String[] beans = context.getBeanDefinitionNames();
		
		for(String x:beans)
		System.err.println(x);
		
//		System.out.println("------------wyjasnienia w interfejsie userRepository-------------");
//		
//		UserRepository ur = (UserRepository) context.getBean("userRepository");
//		
//		List<Object[]> lista = ur.joinNative();
//		
//		System.out.println(lista.size());
//		
//		Object[] o = lista.get(0);
//		System.out.println(o[0]);
//		System.out.println(o[0].getClass());
//		System.out.println(o[1]);
//		System.out.println(o[1].getClass());
//
//		List<Object[]> lista1 = ur.bigIntegerNative();
//		
//		Object[] o1 = lista1.get(0);
//		System.out.println(o1[0]);
//		System.out.println(o1[0].getClass());
//		
//		BigInteger integerro = (BigInteger) o1[0];
//		System.out.println(integerro);
//		
//		System.out.println(o1[1]);
//		System.out.println(o1[1].getClass());
//		
//		System.out.println("----user z domyslej metody userRepository----");
//		
//		List<User> xxx = ur.xd();	
//		System.out.println(xxx.get(0).getRole() + " " + xxx.get(0).getSurname() + " " + xxx.get(0).getEmail());
		
	}

}
