package samples.microservices.customer.intercomm;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import samples.microservices.customer.model.Account;

@FeignClient(name = "account-service", url = "http://account-service:2222")
public interface AccountClient {

	@RequestMapping(method = RequestMethod.GET, value = "/accounts/customer/{customerId}")
	List<Account> getAccounts(@RequestHeader("x-version") String version, @PathVariable("customerId") String customerId);

}
