DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS transfers;
 
CREATE TABLE accounts (
  accountid INT AUTO_INCREMENT PRIMARY KEY,
  iban VARCHAR(24),
  balance DECIMAL(7,2) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  openDate DATE NOT NULL DEFAULT CURRENT_DATE(),
  active BOOLEAN DEFAULT FALSE
);
 
CREATE TABLE transfers (
	source INT NOT NULL,
	destination INT NOT NULL,
	amount DECIMAL(7,2) NOT NULL,
	description VARCHAR(128) NOT NULL,
	timestamp TIMESTAMP NOT NULL,
	FOREIGN KEY (source) REFERENCES accounts(accountid) ON DELETE CASCADE,
	FOREIGN KEY (destination) REFERENCES accounts(accountid) ON DELETE CASCADE
);