package com.parserdigital.test.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.parserdigital.test.exception.ResourceNotFoundException;
import com.parserdigital.test.exception.TransferInactiveAccountException;
import com.parserdigital.test.model.Account;
import com.parserdigital.test.model.Transfer;
import com.parserdigital.test.repository.AccountRepository;
import com.parserdigital.test.repository.TransferSpecificationsBuilder;
import com.parserdigital.test.repository.TransferRepository;
import com.parserdigital.test.util.ConversionUtil;

/**
 * @author Alex
 * 
 *         Class TransferController
 * 
 *         Process REST API requests
 *
 */
@RestController
@RequestMapping("/")
public class TransferController {

	private static final Logger log = LoggerFactory.getLogger(TransferController.class);

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * Create transfer transfer.
	 *
	 * @param transfer the transfer
	 * @return the transfer
	 * @throws ResourceNotFoundException
	 * @throws TransferInactiveAccountException
	 */
	@PostMapping("/Transfer")
	public Transfer createTransfer(@Valid @RequestBody Transfer transfer)
			throws ResourceNotFoundException, TransferInactiveAccountException {

		Account originAccount = accountRepository.findById(transfer.getSource())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found on :: " + transfer.getSource()));
		Account destinationAccount = accountRepository.findById(transfer.getDestination()).orElseThrow(
				() -> new ResourceNotFoundException("Account not found on :: " + transfer.getDescription()));

		if (!originAccount.isActive())
			throw new TransferInactiveAccountException("Transfer origin account is not active");

		if (!destinationAccount.isActive())
			throw new TransferInactiveAccountException("Transfer destination account is not active");

		double rate = 1.0;
		if (!originAccount.getCurrency().equals(destinationAccount.getCurrency())) {
			ConversionUtil conversionUtil = new ConversionUtil(originAccount.getCurrency(),
					destinationAccount.getCurrency());
			rate = conversionUtil.getConversionRate();
		}

		double newBalance = originAccount.getBalance() - transfer.getAmount();
		originAccount.setBalance(newBalance);
		accountRepository.save(originAccount);

		newBalance = destinationAccount.getBalance() + rate * transfer.getAmount();
		destinationAccount.setBalance(newBalance);
		accountRepository.save(destinationAccount);

		// If timestamp is not provided, set current date and time
		if (transfer.getTimestamp() == null)
			transfer.setTimestamp(LocalDateTime.now());

		return transferRepository.save(transfer);
	}

	/**
	 * Get all transfers list.
	 *
	 * @return the list
	 */
	/**
	 * Get all accounts list.
	 *
	 * @return the list
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/Transfer")
	@ResponseBody
	public List<Transfer> search(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "orderby", required = false) String orderby) {
		TransferSpecificationsBuilder builder = new TransferSpecificationsBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Specification<Transfer> spec = builder.build();

		if (orderby != null && !orderby.isEmpty()) {
			// Allowed order properties
			Set<String> allowedProperties = new HashSet<String>();
			allowedProperties.add("source");
			allowedProperties.add("destination");
			allowedProperties.add("datetime");

			return transferRepository.findAll(spec, parseSortString(orderby, allowedProperties));
		} else
			return transferRepository.findAll(spec);
	}

	/**
	 * Gets transfers by id.
	 *
	 * @param transferId the transfer id
	 * @return the transfers by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/Transfer/{id}")
	public ResponseEntity<Transfer> gettransfersById(@PathVariable(value = "id") Long transferId)
			throws ResourceNotFoundException {
		Transfer transfer = transferRepository.findById(transferId)
				.orElseThrow(() -> new ResourceNotFoundException("Transfer not found on :: " + transferId));
		return ResponseEntity.ok().body(transfer);
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
