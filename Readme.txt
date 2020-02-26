private static final Logger log = LoggerFactory.getLogger(.class);

## Parser Digital Java Technical Test (Ref: PDJTT02/2019)

This Rest API has been developed with Spring Boot

It utilices H2 database, in memory for persistence of data.

The H2 console has been activated in the following url: http://localhost:8080/h2-console

The JDBC URL is jdbc:h2:mem:testdb

The following script (src/main/resources/data.sql) is executed to create the startup structure.

	DROP TABLE IF EXISTS accounts;
	DROP TABLE IF EXISTS transfers;
	 
	CREATE TABLE accounts (
	  accountid INT AUTO_INCREMENT PRIMARY KEY,	 // Account Identifier
	  iban VARCHAR(24),				 // Account IBAN (2 chars + 22 digits)
	  balance DECIMAL(7,2) NOT NULL,		 // Account balance
	  currency VARCHAR(3) NOT NULL,			 // Account currency
	  openDate DATE NOT NULL DEFAULT CURRENT_DATE(), // Account opening date
	  active BOOLEAN DEFAULT FALSE			 // Is the Account active 
	);
	 
	CREATE TABLE transfers (
		source INT NOT NULL,		 // Source Account Identifier
		destination INT NOT NULL,        // Destination Account Identifier
		amount DECIMAL(7,2) NOT NULL,    //Amount to be transferred from source to destination
		description VARCHAR(3) NOT NULL, //Transfer description (p.e. “Salary November 2018”)
		timestamp TIMESTAMP NOT NULL,	 // Date and Time of the Transfer
		FOREIGN KEY (source) REFERENCES accounts(accountid),
		FOREIGN KEY (destination) REFERENCES accounts(accountid)
	);


The API specifications are the following.

For Account:
============

Method: 	GET
URL:		http://localhost:8080/Account 
Service:    List all Accounts
Filter:		?search=field:value    
Order:		?orderby=field,order_type (where order type is "asc" or "desc")


Method: 	GET
URL:		http://localhost:8080/Account/{id}
Service:    List Account with account id equal to {id} 


Method: 	POST
URL:		http://localhost:8080/Account 
Service:	Create new Account 
Input: 		JSon representation of Account record


Method: 	PUT
URL:		http://localhost:8080/Account 
Service:	Update Account
Input: 		JSon representation of Account record


Method: 	DELETE
URL:		http://localhost:8080/Account/{id} 
Service: 	Delete Account with account id equal to {id}


For Transfer:
============

Method: 	GET
URL:		http://localhost:8080/Transfer 
Service:    List all Transfers
Filter:		?search=field:value    
Order:		?orderby=field,order_type (where order type is "asc" or "desc")

Method: 	GET
URL:		http://localhost:8080/Transfer/{id}
Service:    List Account with account id equal to {id} 


Method: 	POST
URL:		http://localhost:8080/Transfer 
Service:	Create new Transfer 
Input: 		JSon representation of Transfer record


General Search and Order Specs
==============================

Search
For specifying the search the operators contemplated are ":" (equals), ">" (greater than), "<" (less than)

Order:
For specifying the order the options are "asc" and "desc"






