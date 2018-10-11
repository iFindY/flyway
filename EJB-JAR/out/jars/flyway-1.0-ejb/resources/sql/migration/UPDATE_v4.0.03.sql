-- PIMCORE-2431
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (140160, 'Product detail - Dimension Tab View', null, 140000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (141160, 'Product detail - Dimension Tab Edit', null, 140000, 1, sysdate, sysdate, 1, 1);

CREATE SEQUENCE SEQ_DIM_NO start with 8000000000 increment BY 1 nominvalue nomaxvalue nocycle cache 100 order;

CREATE INDEX I_FK_ARTICLE_27 ON ARTICLE(DIMENSION);
CREATE INDEX ARTICLE_AV_IDX_5 ON ARTICLE_AV(DIMENSION);
CREATE INDEX IDX_ND_ART_RL_4 ON NODE_ARTICLE_REL(DIMENSION);
CREATE INDEX IDX_ART_ASSET_REL_3 ON ARTICLE_ASSET_REL(DIMENSION);
CREATE INDEX IDX_ADT_03 ON ARTICLE_DETAIL_TAB(DIMENSION);
CREATE INDEX I_FK_ARTICLE_CSG_REL_147 ON ARTICLE_CSG_REL(DIMENSION);

-- PIMCORE-2539
UPDATE NODE_NODE_REL SET MODIFIER = 'AND' WHERE MODIFIER IS NULL;
ALTER TABLE NODE_NODE_REL MODIFY MODIFIER NOT NULL;

-- PIMCORE-2496
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (33,6451,1,'H',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6451,2,6451,'Zeichenkette, HEX', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6951,1,6451,'Character string, HEX', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (34,6452,1,'CMYK',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6452,2,6452,'Zeichenkette, CMYK', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6952,1,6452,'Character string, CMYK', sysdate, sysdate, 1, 1);

-- PIMCORE-2462
ALTER TABLE DATAFEED_ROW_MAPPING ADD TARGETUOM NUMBER(19);
ALTER TABLE DATAFEED_ROW_MAPPING ADD CONSTRAINT FK_DRM_TUOM FOREIGN KEY(TARGETUOM) REFERENCES UOM(ID) ON DELETE CASCADE;

-- PIMCORE-2525
UPDATE PLUGIN SET IDENTIFIER = 'StandardXMLExport', PREFIX = 'dataexport.standardxml' WHERE ID = 18; 
UPDATE LOCALE_LOOKUPTEXT SET TEXT = 'Standard XML-Export-Plugin' WHERE ID = 10017;
UPDATE LOCALE_LOOKUPTEXT SET TEXT = 'Standard XML export plugin' WHERE ID = 10517;

-- PIMCORE-2530
CREATE INDEX I_PARTNER_EXTID ON PARTNER(EXTERNALID);

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.03', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
