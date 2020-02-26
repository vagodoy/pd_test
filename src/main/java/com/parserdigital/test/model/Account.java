package com.parserdigital.test.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import javax.persistence.*;

/**
 * @author Alex
 * 
 *         Account entity
 *
 */

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Account {

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountid", nullable = false)
	// Account Identifier
	private long accountId;

	@Column(name = "iban", nullable = false)
	// Account IBAN (2 chars + 22 digits)
	private String iban;

	@Column(name = "balance", nullable = false)
	// Account balance
	private double balance;

	@Column(name = "currency", nullable = false)
	// Account currency
	private String currency;

	@Column(name = "opendate", nullable = false)
	// Account opening date
	private LocalDate openDate;

	@Column(name = "active", nullable = false)
	// Is the Account active?
	private boolean active;

	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the openDate
	 */
	public LocalDate getOpenDate() {
		return openDate;
	}

	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(LocalDate openDate) {
		if (openDate == null)
			openDate = LocalDate.now();
		this.openDate = openDate;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}