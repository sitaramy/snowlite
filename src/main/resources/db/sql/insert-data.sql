-- Insert users
INSERT INTO SN_USER VALUES ('ssh150', 'Satyam Shandilya', 'sshandilya2@sapient.com');
INSERT INTO SN_USER VALUES ('syada3', 'Sitaram yadav', 'syadav12@sapient.com');
INSERT INTO SN_USER VALUES ('hsin46', 'Hemant Singh', 'hsingh46@sapient.com');

-- Insert Distribution list
INSERT INTO SN_DISTRIBUTION_LIST VALUES (1, 'Dev');
INSERT INTO SN_DISTRIBUTION_LIST VALUES (2, 'QA');
INSERT INTO SN_DISTRIBUTION_LIST VALUES (3, 'Manager');
INSERT INTO SN_DISTRIBUTION_LIST VALUES (4, 'DBA');

-- Insert user DL mapping
INSERT INTO SN_USER_DL VALUES ('ssh150', 1);
INSERT INTO SN_USER_DL VALUES ('syada3', 1);
INSERT INTO SN_USER_DL VALUES ('hsin46', 3);

-- Insert operations
INSERT INTO SN_OPERATION VALUES (1, 'My Incidents', 'myIncidents');
INSERT INTO SN_OPERATION VALUES (2, 'New Incident', 'newIncident');
INSERT INTO SN_OPERATION VALUES (3, 'Create DBR', 'createDBR');
INSERT INTO SN_OPERATION VALUES (4, 'AWS Non Prod Deploy', 'awsNonProdDeploy');
INSERT INTO SN_OPERATION VALUES (5, 'Bridge Request', 'bridgeRequest');
INSERT INTO SN_OPERATION VALUES (6, 'Approve Incidents', 'approveIncidents');

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

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (4, 3);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (5, 1);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (5, 2);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (5, 3);
INSERT INTO SN_OPERATION_DL_ACCESS VALUES (5, 4);

INSERT INTO SN_OPERATION_DL_ACCESS VALUES (6, 3);

-- Insert last few operations performed by the user
INSERT INTO SN_USER_OPERATIONS VALUES (1, 1, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (2, 2, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (3, 3, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -3, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (4, 3, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -2, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (5, 3, 'hsin46', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});

INSERT INTO SN_USER_OPERATIONS VALUES (6, 1, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (7, 2, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (8, 4, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (9, 4, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (10, 4, 'syada3', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});

INSERT INTO SN_USER_OPERATIONS VALUES (11, 1, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (12, 2, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (13, 5, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (14, 5, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});
INSERT INTO SN_USER_OPERATIONS VALUES (15, 5, 'ssh150', {fn TIMESTAMPADD(SQL_TSI_DAY, -1, CURRENT_TIMESTAMP)});


