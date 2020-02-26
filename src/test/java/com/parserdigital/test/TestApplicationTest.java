package com.parserdigital.test;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.parserdigital.test.model.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestApplicationTest {

	private static final Logger log = LoggerFactory.getLogger(TestApplicationTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	public TestApplicationTest() {
	}	

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllAccounts() {
		log.info("testGetAllAccounts");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/Account", HttpMethod.GET, entity,
				String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetAccountById() {
		log.info("testGetAccountById");
		Account account = restTemplate.getForObject(getRootUrl() + "/Account/1", Account.class);
		System.out.println(account.getAccountId());
		Assert.assertNotNull(account);
	}

	@Test
	public void testCreateAccount() {
		log.info("testCreateAccount");
		Account account = new Account();
		account.setIban("ES0213456789547856985235");
		account.setBalance(1000.50);
		account.setCurrency("EUR");
		account.setActive(true);

		ResponseEntity<Account> postResponse = restTemplate.postForEntity(getRootUrl() + "/Account", account,
				Account.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testDeleteAccount() {
		log.info("testDeleteAccount");
		int id = 2;
		Account account = restTemplate.getForObject(getRootUrl() + "/Account/" + id, Account.class);
		Assert.assertNotNull(account);
		restTemplate.delete(getRootUrl() + "/Account/" + id);
		try {
			account = restTemplate.getForObject(getRootUrl() + "/Account/" + id, Account.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
