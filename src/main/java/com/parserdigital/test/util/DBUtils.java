package com.parserdigital.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.parserdigital.test.model.Account;
import com.parserdigital.test.model.Transfer;
import com.parserdigital.test.repository.AccountRepository;
import com.parserdigital.test.repository.TransferRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex
 *
 */
@Component
public class DBUtils {

	private static final Logger log = LoggerFactory.getLogger(DBUtils.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private Environment environment;

	@EventListener
	public void loadData(ApplicationReadyEvent event) {
		// Load input folder and file name from application.properties
		String dataInputFolder = environment.getProperty("data.input.folder");
		String dataAccountsCsv = environment.getProperty("data.accounts.csv");
		String dataTransfersCsv = environment.getProperty("data.transfers.csv");
		// Construct complete filepath
		String dataAccountsCsvFile = dataInputFolder + File.separator + dataAccountsCsv;
		String dataTransfersCsvFile = dataInputFolder + File.separator + dataTransfersCsv;

		// Log application.properties loaded
		log.info(dataInputFolder);
		log.info(dataAccountsCsvFile);
		log.info(dataTransfersCsvFile);

		log.info("Init: AccountsCsv upload");
		// Read CSV file. For each row, instantiate and collect `Account`.
		BufferedReader readerAccounts;
		try {
			readerAccounts = new BufferedReader(new FileReader(dataAccountsCsvFile));
			Iterable<CSVRecord> records;
			CSVFormat fmt = CSVFormat.EXCEL.withDelimiter(';');
			records = fmt.withFirstRecordAsHeader().parse(readerAccounts);
			for (CSVRecord record : records) { // accountId;balance;currency
				Account account = new Account();
				account.setAccountId(Long.parseLong(record.get("accountId")));
				account.setBalance(Double.parseDouble(record.get("balance")));
				account.setCurrency(record.get("currency"));
				account.setActive(true);
				account.setOpenDate(LocalDate.now());
				accountRepository.save(account);
			}
			readerAccounts.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("End: AccountsCsv upload");

		log.info("Init: TransfersCsv upload");
		// Read CSV file. For each row, instantiate and collect `Transfer`.
		BufferedReader readerTransfers;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // 2018/11/01 09:03:56
		try {
			readerTransfers = new BufferedReader(new FileReader(dataTransfersCsvFile));
			Iterable<CSVRecord> records;
			CSVFormat fmt = CSVFormat.EXCEL.withDelimiter(';');
			records = fmt.withFirstRecordAsHeader().parse(readerTransfers);
			for (CSVRecord record : records) { // source;destination;amount;description;timestamp
				Transfer transfer = new Transfer();
				transfer.setSource(Long.parseLong(record.get("source")));
				transfer.setDestination(Long.parseLong(record.get("destination")));
				transfer.setAmount(Double.parseDouble(record.get("amount")));
				transfer.setDescription(record.get("description"));
				transfer.setTimestamp(LocalDateTime.parse(record.get("timestamp"), formatter));
				transferRepository.save(transfer);
			}
			readerTransfers.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("End: TransfersCsv upload");
	}

}
