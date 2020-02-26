package com.parserdigital.test.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parserdigital.test.exception.ResourceNotFoundException;
import com.parserdigital.test.model.Account;
import com.parserdigital.test.repository.AccountRepository;
import com.parserdigital.test.repository.AccountSpecificationsBuilder;

/**
 * @author Alex
 * 
 *         Class AccountController
 * 
 *         Process REST API requests
 *
 */
@RestController
@RequestMapping("/")
public class AccountController {

	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * Create Account account.
	 *
	 * @param account the Account
	 * @return the Account created
	 */
	@PostMapping("/Account")
	public Account createAccount(@Valid @RequestBody Account account) {
		return accountRepository.save(account);
	}

	/**
	 * Get all accounts list.
	 *
	 * @return the list
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/Account")
	@ResponseBody
	public List<Account> search(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "orderby", required = false) String orderby) {
		AccountSpecificationsBuilder builder = new AccountSpecificationsBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Specification<Account> spec = builder.build();

		if (orderby != null && !orderby.isEmpty()) {
			// Allowed order properties
			Set<String> allowedProperties = new HashSet<String>();
			allowedProperties.add("iban");
			allowedProperties.add("balance");
			allowedProperties.add("currency");

			return accountRepository.findAll(spec, parseSortString(orderby, allowedProperties));
		} else
			return accountRepository.findAll(spec);
	}

	/**
	 * Gets accounts by id.
	 *
	 * @param accountId the account id
	 * @return the accounts by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/Account/{id}")
	public ResponseEntity<Account> getAccountsById(@PathVariable(value = "id") Long accountId)
			throws ResourceNotFoundException {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found on :: " + accountId));
		return ResponseEntity.ok().body(account);
	}

	/**
	 * Update account response entity.
	 *
	 * @param accountId      the account id
	 * @param accountDetails the account details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/Account/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
			@Valid @RequestBody Account accountDetails) throws ResourceNotFoundException {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found on :: " + accountId));
		account.setCurrency(accountDetails.getCurrency());
		final Account updatedAccount = accountRepository.save(account);
		return ResponseEntity.ok(updatedAccount);
	}

	/**
	 * Delete account.
	 *
	 * @param accountId the account id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/Account/{id}")
	public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId) throws Exception {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found on :: " + accountId));
		accountRepository.delete(account);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/**
	 * Parse sort string and generate Sort object
	 * 
	 * @param sortStr
	 * @param allowedProperties
	 * @return Sort
	 */
	private Sort parseSortString(String sortStr, Set<String> allowedProperties) {
		if (sortStr == null || sortStr.isEmpty()) {
			return null;
		}

		String[] split = sortStr.split(",");
		if (split.length == 1) {
			if (!allowedProperties.contains(split[0])) {
				return null;
			}
			log.info("Sort: [" + split[0] + "]");
			return Sort.by(split[0]);
		} else if (split.length == 2) {
			if (!allowedProperties.contains(split[0])) {
				return null;
			}
			log.info("Sort: [" + split[0] + "][" + split[1] + "]");
			return Sort.by(Sort.Direction.fromString(split[1]), split[0]);

		} else {
			return null;
		}
	}
}
