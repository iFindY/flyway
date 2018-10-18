-- PIMCORE-2467
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (140039, 'Product detail - General - Export via datafeed', null, 140000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2386
ALTER TABLE WORKLIST ADD TYPE NUMBER(19);
ALTER TABLE WORKLIST ADD CONSTRAINT FK_WORKLIST_TYPE FOREIGN KEY(TYPE) REFERENCES TYPE(ID);

ALTER TABLE SEARCH_TEMPLATE ADD WORKLIST NUMBER(19);
ALTER TABLE SEARCH_TEMPLATE ADD CONSTRAINT FK_ST_WORKLIST FOREIGN KEY(WORKLIST) REFERENCES WORKLIST(ID);

CREATE INDEX I_FK_WORKLIST_55 ON WORKLIST(USERID, CLIENT, TYPE);

INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (110030, 'Worklist - Menu Refresh entries', null, 110000, 1, sysdate, sysdate, 1, 1);

INSERT INTO type_type (id, description, identifier, userright, creationdate, lastmodified, updateuser, createuser) VALUES (79,3087,'WORKLIST_TYPE', 100005, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3087,2,3087,'Arbeitslisten-Typ', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3587,1,3087,'Worklist type', sysdate, sysdate, 1, 1);

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (207, 6114,79,'STANDARD_WORKLIST',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6114,2,6114,'Standard-Arbeitsliste', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6614,1,6114,'Standard worklist', sysdate, sysdate, 1, 1);

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (208, 6115,79,'DYNAMIC_WORKLIST',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6115,2,6115,'Dynamische Arbeitsliste', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6615,1,6115,'Dynamic worklist', sysdate, sysdate, 1, 1);

UPDATE WORKLIST SET TYPE = 207 WHERE TYPE IS NULL;

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.0', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
