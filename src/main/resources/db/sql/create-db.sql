-- Users
DROP TABLE SN_USER;

CREATE TABLE SN_USER (
  user_id  VARCHAR(10) PRIMARY KEY,
  name VARCHAR(50),
  email  VARCHAR(50),
  team_id VARCHAR(10)
);

-- Team
DROP TABLE SN_TEAM;

CREATE TABLE SN_TEAM (
  team_id  VARCHAR(10) PRIMARY KEY,
  team_name VARCHAR(30),
  assignment_group VARCHAR(50),
  approval_manager VARCHAR(10),
  team_dl VARCHAR(20),
  team_dev_dl VARCHAR(20),
  team_qa_dl VARCHAR(20),
  db_assignment_group VARCHAR(100)
);

-- Business Service
DROP TABLE SN_BUSINESS_SVC;

CREATE TABLE SN_BUSINESS_SVC(
	business_svc_id INTEGER PRIMARY KEY,
	business_service VARCHAR(100),
	assignment_group VARCHAR(100)
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
  operation_url  VARCHAR(50),
  approval_required VARCHAR(1)
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
  access_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
  operation_id INTEGER,
  user_id  VARCHAR(10),
  access_date TIMESTAMP
);

-- incidents
DROP TABLE SN_INCIDENT;

CREATE TABLE SN_INCIDENT(
	incident_id	INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	short_description VARCHAR(2000),
	description VARCHAR(4000),
	requested_for VARCHAR(100),
	environment VARCHAR(10),
	business_service VARCHAR(100),
	assignment_group VARCHAR(100),
	user_id VARCHAR(10),
	status varchar(15)
);

-- Request
DROP TABLE SN_REQUEST;

CREATE TABLE SN_REQUEST (
  request_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
  short_description VARCHAR(1000),
  description  VARCHAR(3000),
  user_id VARCHAR(10),
  business_service INTEGER,
  assignment_group varchar(100),
  requested_resource varchar(1000),
  approval varchar(1),
  approving_manager varchar(10),
  status varchar(15)
);
