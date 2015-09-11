-- Users
DROP TABLE SN_USER;

CREATE TABLE SN_USER (
  user_id  VARCHAR(10) PRIMARY KEY,
  name VARCHAR(30),
  email  VARCHAR(50)
);

-- Distribution list
DROP TABLE SN_DISTRIBUTION_LIST;

CREATE TABLE SN_DISTRIBUTION_LIST (
  DL_ID  INTEGER PRIMARY KEY,
  DL_NAME VARCHAR(30)
);

-- User DL mapping
DROP TABLE SN_USER_DL;

CREATE TABLE SN_USER_DL(
	user_id  VARCHAR(10),
	DL_ID	INTEGER
);

-- Supported Operations
DROP TABLE SN_OPERATION;

CREATE TABLE SN_OPERATION (
  operation_id INTEGER PRIMARY KEY,
  operation_name VARCHAR(30),
  operation_url  VARCHAR(50)
);

-- Permitted operations for User
DROP TABLE SN_OPERATION_DL_ACCESS;

CREATE TABLE SN_OPERATION_DL_ACCESS (
  operation_id INTEGER,
  DL_ID  INTEGER
);

-- Operations performed by the user
DROP TABLE SN_USER_OPERATIONS;

CREATE TABLE SN_USER_OPERATIONS (
  access_id INTEGER PRIMARY KEY,
  operation_id INTEGER,
  user_id  VARCHAR(10),
  access_date TIMESTAMP
);
