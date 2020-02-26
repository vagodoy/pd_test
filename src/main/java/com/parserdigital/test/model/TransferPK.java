/**
 * 
 */
package com.parserdigital.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Alex
 * 
 *         Transfer entity Primary Key
 *
 */
public class TransferPK implements Serializable {
	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	// Source Account Identifier
	private long source;

	// Destination Account Identifier
	private long destination;

	// Date and Time of the Transfer
	private LocalDateTime timestamp;

	/**
	 * Default Constructor
	 */
	public TransferPK() {

	}

	/**
	 * Constructor
	 * 
	 * @param source
	 * @param destination
	 * @param timestamp
	 */
	public TransferPK(long source, long destination, LocalDateTime timestamp) {
		this.source = source;
		this.destination = destination;
		this.timestamp = timestamp;
	}

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

	/**
	 * Compare with other object
	 * 
	 * @param object
	 */
	public boolean equals(Object object) {
		if (object instanceof TransferPK) {
			TransferPK pk = (TransferPK) object;
			return source == pk.source && destination == pk.destination && timestamp == pk.timestamp;
		} else {
			return false;
		}
	}

	/**
	 * Generate Hash code
	 */
	public int hashCode() {
		return (int) (source + destination + timestamp.hashCode());
	}
}
