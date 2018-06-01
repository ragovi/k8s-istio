package samples.microservices.account.api;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import samples.microservices.account.data.AccountRepository;
import samples.microservices.account.model.Account;

@RestController
public class Api {
	
    private static final String HOSTNAME = parseContainerIdFromHostname(
            System.getenv().getOrDefault("HOSTNAME", "unknown")
    );
    
    static String parseContainerIdFromHostname(String hostname) {
    	System.out.println("Hostname: " + hostname);
    	System.out.println("version " + hostname.replaceAll("(\\Qaccount-service-v\\E)(\\d+)(.*)", "$1$2"));
        return hostname.replaceAll("(\\Qaccount-service-v\\E)(\\d+)(.*)", "$1$2");
    }
    
    static Boolean delay = Boolean.FALSE;
    
    static Boolean fail = Boolean.FALSE; 

	@Autowired
	AccountRepository repository;

	protected Logger logger = Logger.getLogger(Api.class.getName());
	
	@RequestMapping("/accounts/fail/{fail}")
	public String setFail(@PathVariable("fail") Boolean fail) {
		logger.info(String.format("Account.setFail(%s)", fail));
		Api.fail=fail;
		return Api.fail+","+HOSTNAME;
	}
	
	@RequestMapping("/accounts/fail")
	public String getFail() {
		logger.info(String.format("Account.getFail(%s)", fail));
		return Api.fail+","+HOSTNAME;
	}	
	
	@RequestMapping("/accounts/delay/{delay}")
	public String setDelay(@PathVariable("delay") Boolean delay) {
		logger.info(String.format("Account.setDelay(%s)", delay));
		Api.delay=delay;
		return Api.delay+","+HOSTNAME;
	}
	
	@RequestMapping("/accounts/delay")
	public String getDelay() {
		logger.info(String.format("Account.getDelay(%s)", delay));
		return Api.delay+","+HOSTNAME;
	}	

	@RequestMapping("/accounts/{number}")
	public Account findByNumber(@PathVariable("number") String number) {
		logger.info(String.format("Account.findByNumber(%s)", number));
		Account account = repository.findByNumber(number);
		account.setHostname(HOSTNAME);
		return account;
	}

	@RequestMapping("/accounts/customer/{customer}")
	public ResponseEntity<List<Account>> findByCustomer(@RequestHeader("x-version") String version, @PathVariable("customer") String customerId) {
		logger.info(String.format("Account.findByCustomer(%s)", customerId+" "+version));
		List<Account> accounts = repository.findByCustomerId(customerId);
		for (Account account: accounts) {
			account.setHostname(HOSTNAME);
		}
		if (fail) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		if (delay) {
			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.ok(accounts);
	}

	@RequestMapping("/accounts")
	public List<Account> findAll() {
		logger.info("Account.findAll()");
		List<Account> accounts = repository.findAll();
		for (Account account: accounts) {
			account.setHostname(HOSTNAME);
		}
		return accounts;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/accounts")
	public Account add(@RequestBody Account account) {
		logger.info(String.format("Account.add(%s)", account));
		Account accountSaved = repository.save(account);
		accountSaved.setHostname(HOSTNAME);
		return repository.save(accountSaved);
	}
	

	

}

