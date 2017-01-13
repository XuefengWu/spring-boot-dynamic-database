package hello;

import java.util.concurrent.atomic.AtomicLong;

import dynamic.CustomerContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    @Autowired
    private CustomerRepository customers;

    @RequestMapping("/greeting")
    public Customer greeting(@RequestParam(value="name", defaultValue="SILVER") String name) {
        CustomerContextHolder.setCustomerType(CustomerContextHolder.CustomerType.valueOf(name.toUpperCase()));
        return customers.findOne(1L);
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


}