package samples.microservices.customer.api;

import java.util.List;
import java.util.logging.Logger;

import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.steps.optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import samples.microservices.customer.data.CustomerRepository;
import samples.microservices.customer.intercomm.AccountClient;
import samples.microservices.customer.model.Account;
import samples.microservices.customer.model.Customer;

@RestController
public class Api {
	
	@Autowired
	private AccountClient accountClient;
	
	@Autowired
	CustomerRepository repository;
	
	protected Logger logger = Logger.getLogger(Api.class.getName());
		
	@RequestMapping("/customers")
	public List<Customer> findAll() {
		logger.info("Customer.findAll()");
		return repository.findAll();
	}
	
	@RequestMapping("/customers/{id}")
	public Customer findById(@RequestHeader(name="x-version", required=false) String version, @PathVariable("id") String id) {
		logger.info(String.format("Customer.findById(%s)", id));
		Customer customer = repository.findById(id);
		if (version==null) {
			version = "v" + ((int)(Math.random() * 2) + 1);
		}
		List<Account> accounts =  accountClient.getAccounts(version, id);
		customer.setAccounts(accounts);
		return customer;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/customers")
	public Customer add(@RequestBody Customer customer) {
		logger.info(String.format("Customer.add(%s)", customer));
		return repository.save(customer);
	}
	
	public static void main(String[] args) {
		for (int i =0;i<10;i++) {
			System.out.println((int)(Math.random() * 2) + 1);
			
			
		}
	}
	
}
