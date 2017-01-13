package hello;

import dynamic.CustomerContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class Application {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {

			System.out.println(repository.findOne(1L));

			CustomerContextHolder.setCustomerType(CustomerContextHolder.CustomerType.GOLD);
			//entityManager.persist(new Customer(1L,"GOLD", "GOLD"));
			System.out.println(repository.findOne(1L));

			CustomerContextHolder.setCustomerType(CustomerContextHolder.CustomerType.SILVER);
			//entityManager.persist(new Customer(1L,"SILVER", "SILVER"));
			System.out.println(repository.findOne(1L));
		};
	}

}
