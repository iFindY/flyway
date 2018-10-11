-- PIMCORE-2772
ALTER TABLE JOB_HISTORY ADD FAILEDVALIDATIONINFO VARCHAR2(1024 CHAR);

-- PIMCORE-2766
ALTER TABLE JOB ADD INTERVAL VARCHAR2(60 CHAR);
UPDATE JOB SET INTERVAL = TRIGGERPARAMETER, TRIGGERPARAMETER = NULL WHERE TRIGGERPARAMETER IS NOT NULL AND TRIGGERMETHOD IN ('makeMinutelyTrigger','makeHourlyTrigger','makeSecondlyTrigger');  

-- PIMCORE-2779
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (215,6458,77,'IPIM_SUPPLY_REST_DATAFEED',1, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6458,2,6458,'iPIM Supply Datenfeed (REST)', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6958,1,6458,'iPIM Supply datafeed (REST)', sysdate, sysdate, 1, 1);

-- PIMCORE-2643
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350017, 'Translation - Main data - Change attributes', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350018, 'Translation - Main data - Change types', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350019, 'Translation - Main data - Change attribute values', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350020, 'Translation - Main data - Change unit of measure', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350021, 'Translation - Main data - Change text module', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350022, 'Translation - Main data - Change category data', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;

INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350017, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350002;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350018, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350002;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350019, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350002;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350020, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350002;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350021, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350002;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350022, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350002;

-- PIMCORE-2802
INSERT INTO TYPE (ID, description, TYPE, IDENTIFIER, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (216,6459,7,'XLS_Attributsmodell',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6459,2,6459,'XLS + Attributsmodell', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6959,1,6459,'XLS + attribute model', sysdate, sysdate, 1, 1);
INSERT INTO TYPE (ID, description, TYPE, IDENTIFIER, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (217,6460,7,'XLSX_Attributsmodell',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6460,2,6460,'XLSX + Attributsmodell', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6960,1,6460,'XLSX + attribute model', sysdate, sysdate, 1, 1);

-- PIMCORE-2783
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (800000, 'License Management', 100000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2807
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (230019, 'Supplier - Catalog - Configuration Edit', null, 230001, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (230020, 'Supplier - Catalog - Button Import', null, 230001, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (230021, 'Supplier - Catalog - Assignments', null, 230001, 1, sysdate, sysdate, 1, 1);

INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 230019, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 230001;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 230020, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 230001;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 230021, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 230001;

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.09', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
