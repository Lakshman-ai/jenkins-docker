package ServiceA.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/a")
public class ServiceAController {

	@Autowired
	private RestTemplate restTemplate;
	
	
	private static final String BASE_URL = 
			"http://localhost:8282/";
	
	private static final String SERVICE_A = "serviceA";
	
	int count =1;

	
	@GetMapping
	//@CircuitBreaker(name= SERVICE_A, fallbackMethod = "serviceAfallback")
	//@Retry(name = SERVICE_A)
	@RateLimiter(name = SERVICE_A, fallbackMethod = "serviceAfallback" )
	public String serviceA() {
		
		
		String url = BASE_URL+ "b";
		System.out.println("Retry method called"+ count++ +
				" times at "+new Date());
		
		
		return restTemplate.getForObject(url, String.class);
		
	}
	
	
	public String serviceAfallback(Exception e) {
		
		return "This is a fallback method from Service A";
		
	}
	
}
