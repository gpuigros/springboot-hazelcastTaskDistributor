-- ******************** --
-- H2 --
-- ******************** --
--CREATE TABLE sample (
--  ID         INTEGER PRIMARY KEY,
--  FIRST_NAME VARCHAR(50),
--  LAST_NAME VARCHAR(50),
--  EMAIL  VARCHAR(50)
--);

-- ******************** --
-- HSQL --
-- ******************** --
CREATE TABLE SAMPLE
(
  ID              			NUMBER(8)              	NOT NULL,
  FIRSTNAME            		VARCHAR2(2000 CHAR)    	NOT NULL,
  LASTNAME           		VARCHAR2(30 CHAR)      	NOT NULL,
  EMAIL           		 	VARCHAR2(30 CHAR) 		NOT NULL,
  CONSTRAINT SAMPLE_PK PRIMARY KEY (ID)
);