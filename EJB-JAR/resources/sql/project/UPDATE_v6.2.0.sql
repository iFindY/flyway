-- PIMCORE-3208
ALTER TABLE LOCALE_FREETEXT DROP CONSTRAINT FK_LFT_ARTICLE;
ALTER TABLE LOCALE_FREETEXT DROP CONSTRAINT FK_LFT_ATTRIBUTE;
ALTER TABLE LOCALE_FREETEXT DROP CONSTRAINT FK_LOCALE_FREETEXT_CLIENT;

-- PIMCORE-3176
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1090, 2, 1090, 'iMARKET-Export: Einheiten-Umwandlung', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1590, 1, 1090, 'iMARKET-Export: Unit of measurement conversion', sysdate, sysdate, 1, 1);
INSERT INTO ATTRIBUTE (ID, IDENTIFIER, DESCRIPTION, SCOPENAME, TYPE, MANDATORY, INHERITABLE, DIMENSION, GROUPNAME, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1090, 'IMARKET_UOM_CONVERSION', 1090, 'ATTRIBUTEAV', 6, 104, 0, 0, 158, sysdate, sysdate, 1, 1);

-- PIMCORE-3157
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (141162, 'Product detail - General - Remove from worklist', null, 140000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-3078
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130091, 'Sale catalog Item Tab - Copy item', null, 130001, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130092, 'Sale catalog Item Tab - Cut item', null, 130001, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130093, 'Sale catalog Item Tab - Paste item', null, 130001, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-3149
UPDATE USER_RIGHT SET IDENTIFIER = 'Translation - Main migration - Change attributes' WHERE ID = 350017;
UPDATE USER_RIGHT SET IDENTIFIER = 'Translation - Main migration - Change types' WHERE ID = 350018;
UPDATE USER_RIGHT SET IDENTIFIER = 'Translation - Main migration - Change attribute values' WHERE ID = 350019;
UPDATE USER_RIGHT SET IDENTIFIER = 'Translation - Main migration - Change unit of measure' WHERE ID = 350020;
UPDATE USER_RIGHT SET IDENTIFIER = 'Translation - Main migration - Change text module' WHERE ID = 350021;
UPDATE USER_RIGHT SET IDENTIFIER = 'Translation - Main migration - Change category migration' WHERE ID = 350022;

INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350023, 'Translation - Asset migration - View asset migration', 350009, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350023, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350009;

INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350024, 'Translation - Asset relation migration - View asset relation migration', 350013, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350024, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350013;

INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350025, 'Translation - Main migration - View attributes', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350026, 'Translation - Main migration - View types', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350027, 'Translation - Main migration - View attribute values', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350028, 'Translation - Main migration - View unit of measure', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350029, 'Translation - Main migration - View text module', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 350030, 'Translation - Main migration - View category migration', 350002, 1, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'Translation' AND ENABLED = 1;

INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350025, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350017;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350026, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350018;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350027, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350019;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350028, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350020;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350029, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350021;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 350030, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 350022;

-- PIMCORE-3180
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130094, 'Sale catalog Attribute Tab - Menu Copy attributes', null, 130002, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130095, 'Sale catalog Attribute Tab - Menu Paste attributes', null, 130002, 1, sysdate, sysdate, 1, 1);

INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (250084, 'Purchase catalog Attribute Tab - Menu Copy attributes', null, 250002, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (250085, 'Purchase catalog Attribute Tab - Menu Paste attributes', null, 250002, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-3198
ALTER TABLE WORKLIST ADD PUBLICWORKLIST NUMBER(1) DEFAULT 1 NOT NULL;
UPDATE WORKLIST SET PUBLICWORKLIST = 0 WHERE TYPE = 208;

INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (110032, 'Worklist - Menu Toggle worklist state', null, 110000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-3215
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200032, 'Attributes - Button Map sizes target based', null, 200000, 1, sysdate, sysdate, 1, 1);

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.2.0', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
