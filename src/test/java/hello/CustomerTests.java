package hello;

import dynamic.CustomerContextHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
@DataJpaTest
public class CustomerTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customers;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private String DROP_TABLE = "DROP TABLE IF EXISTS  DATABASECHANGELOG;DROP TABLE IF EXISTS CUSTOMER;";

    @Test
    public void testDataSourceRoutingGold() throws Exception {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.CustomerType.GOLD);
        entityManager.createNativeQuery(DROP_TABLE + "CREATE TABLE CUSTOMER(id int,firstName varchar(255),lastName varchar(255))").executeUpdate();
        Customer customer = new Customer(1L, "GOLD", "last");
        entityManager.persist(customer);
        assertThat(customers.count()).isEqualTo(1);
        List<Customer> findByLastName = customers.findByLastName(customer.getLastName());
        assertThat(findByLastName.get(0).getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    public void testDataSourceRoutingSilver() throws Exception {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.CustomerType.SILVER);
        entityManager.createNativeQuery(DROP_TABLE + "CREATE TABLE CUSTOMER(id int,firstName varchar(255),lastName varchar(255))").executeUpdate();
        Customer customer = new Customer(1L, "SILVER", "last");
        entityManager.persist(customer);
        assertThat(customers.count()).isEqualTo(1);
        List<Customer> findByLastName = customers.findByLastName(customer.getLastName());
        assertThat(findByLastName.get(0).getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    public void testDataSourceRoutingDefault() throws Exception {
        CustomerContextHolder.clearCustomerType();
        entityManager.createNativeQuery(DROP_TABLE + "CREATE TABLE CUSTOMER(id int,firstName varchar(255),lastName varchar(255))").executeUpdate();
        entityManager.persist(new Customer(1L, "Default1", "last1"));
        entityManager.persist(new Customer(2L, "Default2", "last2"));
        assertThat(customers.count()).isEqualTo(2);
        List<Customer> findByLastName = customers.findByLastName("last1");
        assertThat(findByLastName.get(0).getLastName()).isEqualTo("last1");

    }

}
