DROP TABLE IF EXISTS TRANSFER;

CREATE TABLE TRANSFER (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  AMOUNT DECIMAL(10,2) NOT NULL,
  FEE_AMOUNT DECIMAL(10,2) NOT NULL,
  CREATED_DATE  DATE NOT NULL,
  TRANSFER_DATE DATE NOT NULL,
  ORIGIN_ACCOUNT VARCHAR(20) NOT NULL,
  DESTINATION_ACCOUNT VARCHAR(20) NOT NULL,
  TYPE VARCHAR(1) NOT NULL
);

