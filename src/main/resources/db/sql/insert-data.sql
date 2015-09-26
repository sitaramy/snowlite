-- Insert users
INSERT INTO SN_USER VALUES ('ssh150', 'Satyam Shandilya', 'sshandilya2@sapient.com', 'FIRST');
INSERT INTO SN_USER VALUES ('syada3', 'Sitaram Yadav', 'syadav12@sapient.com', 'FIRST');
INSERT INTO SN_USER VALUES ('hsin46', 'Hemant Singh', 'hsingh46@sapient.com', 'FIRST');

-- Insert team details
INSERT INTO SN_TEAM VALUES ('FIRST', 'Fixed Income Rapid Solutions', 'Fixed Income Rapid Solutions', 'hsin46', '#FIRST', '#DEV_FIRST', '#QA_FIRST', 'IA Database');

-- Insert business service details
INSERT INTO SN_BUSINESS_SVC VALUES (1, 'FIRS Batch Utilities', 'Fixed Income Rapid Solutions');
INSERT INTO SN_BUSINESS_SVC VALUES (2, 'Active Directory', 'Wintel Support');
INSERT INTO SN_BUSINESS_SVC VALUES (3, 'Unix Administration', 'Solution Center');
INSERT INTO SN_BUSINESS_SVC VALUES (4, 'Production Support', 'Production Support');
INSERT INTO SN_BUSINESS_SVC VALUES (5, 'Amazon AWS Infrastructure', 'AWS Services');
INSERT INTO SN_BUSINESS_SVC VALUES (6, 'Virtual Desktop', 'Solution Center');
INSERT INTO SN_BUSINESS_SVC VALUES (7, 'Database Services', 'Database Operations');
INSERT INTO SN_BUSINESS_SVC VALUES (8, 'Operations', 'Solution Center');

-- Insert Applications
INSERT INTO SN_APPLICATION VALUES (1, 'Relco', 'Relco');
INSERT INTO SN_APPLICATION VALUES (2, 'Capital IQ', 'Capital IQ');
INSERT INTO SN_APPLICATION VALUES (3, 'CRS', 'CRS');
INSERT INTO SN_APPLICATION VALUES (4, 'APT', 'APT');
INSERT INTO SN_APPLICATION VALUES (5, 'ABS', 'ABS');

-- Insert Distribution list
INSERT INTO SN_DISTRIBUTION_LIST VALUES (1, 'Dev');
INSERT INTO SN_DISTRIBUTION_LIST VALUES (2, 'QA');
INSERT INTO SN_DISTRIBUTION_LIST VALUES (3, 'Manager');
INSERT INTO SN_DISTRIBUTION_LIST VALUES (4, 'DBA');

-- Insert user DL mapping
INSERT INTO SN_USER_DL VALUES ('ssh150', 3);
INSERT INTO SN_USER_DL VALUES ('syada3', 1);
INSERT INTO SN_USER_DL VALUES ('hsin46', 3);

-- Insert operations
INSERT INTO SN_OPERATION VALUES (1, 'My Incidents/Requests', 'myIncidents', 'N');
INSERT INTO SN_OPERATION VALUES (2, 'New Incident', 'newIncident', 'N');
INSERT INTO SN_OPERATION VALUES (3, 'New Database Release', 'newDatabaseRelease', 'N');
INSERT INTO SN_OPERATION VALUES (4, 'Create DBR', 'createDBR', 'N');
INSERT INTO SN_OPERATION VALUES (5, 'AWS Non Prod Deploy', 'awsNonProdDeploy', 'N');
INSERT INTO SN_OPERATION VALUES (6, 'Operations Bridge Request', 'bridgeRequest', 'N');
INSERT INTO SN_OPERATION VALUES (7, 'Request New Unix Account', 'unixAccount', 'Y');
INSERT INTO SN_OPERATION VALUES (8, 'Sudo Access', 'sudoAccess', 'Y');
INSERT INTO SN_OPERATION VALUES (9, 'Access to shared folder', 'sharedFolderAccess', 'Y');
INSERT INTO SN_OPERATION VALUES (10, 'Pending Items', 'pendingItems', 'N');

-- Insert User operation access
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (1, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (1, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (1, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (1, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (2, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (2, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (2, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (2, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (3, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (3, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (3, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (3, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (4, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (4, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (4, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (4, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (5, 3);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (6, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (6, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (6, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (6, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (7, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (7, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (7, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (7, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (8, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (8, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (8, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (8, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (9, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (9, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (9, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (9, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (10, 3);

-- Insert last few operations performed by the user
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (2, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (3, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -3, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (3, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -2, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (3, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});

INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (2, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (4, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (4, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (4, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});

INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (2, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (5, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (5, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS(operation_id, user_id, access_date) VALUES (5, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});

-- DB Release
INSERT INTO SN_DB_RELEASE(description, user_id, assignment_group, application, status) VALUES ('Sample 1', 'ssh150', 'IA Database', 2, 'OPEN');
INSERT INTO SN_DB_RELEASE(description, user_id, assignment_group, application, status) VALUES ('Sample 2', 'hsin46', 'IA Database', 1, 'OPEN');
INSERT INTO SN_DB_RELEASE(description, user_id, assignment_group, application, status) VALUES ('Sample 3', 'syada3', 'IA Database', 3, 'OPEN');

-- Incidents
INSERT INTO SN_INCIDENT(short_description,description,requested_for,environment,business_service,assignment_group,user_id,status) VALUES ('IDR test is down','IDR test is down','Sitaram Yadav','Test','IA Database Support','DB Support - Non Prod', 'syada3', 'Open');
INSERT INTO SN_INCIDENT(short_description,description,requested_for,environment,business_service,assignment_group,user_id,status) VALUES ('VDI not working','VDI not working','Sitaram Yadav','Prod','VDI Support','Solutions Center','syada3','Open');
INSERT INTO SN_INCIDENT(short_description,description,requested_for,environment,business_service,assignment_group,user_id,status) VALUES ('IDR test is down','IDR test is down','Satyam Shandilya','Test','IA Database Support','DB Support - Non Prod', 'ssh150', 'Open');
INSERT INTO SN_INCIDENT(short_description,description,requested_for,environment,business_service,assignment_group,user_id,status) VALUES ('VDI not working','VDI not working','Satyam Shandilya','Prod','VDI Support','Solutions Center','ssh150','Open');
INSERT INTO SN_INCIDENT(short_description,description,requested_for,environment,business_service,assignment_group,user_id,status) VALUES ('IDR test is down','IDR test is down','Hemant Singh','Test','IA Database Support','DB Support - Non Prod', 'hsin46', 'Open');
INSERT INTO SN_INCIDENT(short_description,description,requested_for,environment,business_service,assignment_group,user_id,status) VALUES ('VDI not working','VDI not working','Hemant Singh','Prod','VDI Support','Solutions Center','hsin46','Open');

-- Service requests
insert into SN_REQUEST(short_description,description,user_id,business_service,assignment_group,requested_resource,approval,approving_manager,status) values ('Bridge Request','Open bridge for FIRS job abend','syada3',8,'Solutions center','Tarang Jain','Y','vorab','Open');
insert into SN_REQUEST(short_description,description,user_id,business_service,assignment_group,requested_resource,approval,approving_manager,status) values ('Bridge Request','Open bridge for FIRS job abend','ssh150',8,'Solutions center','Tarang Jain','Y','vorab','Open');
insert into SN_REQUEST(short_description,description,user_id,business_service,assignment_group,requested_resource,approval,approving_manager,status) values ('Bridge Request','Open bridge for FIRS job abend','hsin46',8,'Solutions center','Tarang Jain','Y','vorab','Open');


