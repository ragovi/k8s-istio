package samples.microservices.customer.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import samples.microservices.customer.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public Customer findById(String id);
	
}
