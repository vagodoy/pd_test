package com.parserdigital.test.repository;

/**
 * @author Alex
 *
 */
public class SearchCriteria {
	private String key;
	private String operation;
	private Object value;

	/**
	 * Default constructor
	 */
	public SearchCriteria() {

	}

	/**
	 * @param key
	 * @param operation
	 * @param value
	 */
	public SearchCriteria(String key, String operation, Object value) {
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return if the predicate is ored
	 */
	public boolean isOrPredicate() {
		// TODO Auto-generated method stub
		return false;
	}

}
