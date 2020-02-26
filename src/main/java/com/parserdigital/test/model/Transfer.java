/**
 * 
 */
package com.parserdigital.test.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * @author Alex
 *
 *         Transfer entity
 *
 */
@Entity
@Table(name = "transfers")
@IdClass(TransferPK.class)
@EntityListeners(AuditingEntityListener.class)
public class Transfer {

	@Id()
	@Column(name = "source", nullable = false)
	// Source Account Identifier
	private long source;

	@Id()
	@Column(name = "destination", nullable = false)
	// Destination Account Identifier
	private long destination;

	@Column(name = "amount", nullable = false)
	// amount Amount to be transferred from source to destination
	private double amount;

	@Column(name = "description", nullable = false)
	// Transfer description (p.e. “Salary November 2018”)
	private String description;

	@Id()
	@Column(name = "timestamp", nullable = false)
	// Date and Time of the Transfer
	private LocalDateTime timestamp;

	/**
	 * @return the source
	 */
	public long getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(long source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public long getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(long destination) {
		this.destination = destination;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
