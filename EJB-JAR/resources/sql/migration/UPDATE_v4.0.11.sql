-- Correction to PIMCORE-2497
update PLUGIN set identifier =  'StandardXMLExport', prefix = 'dataexport.standardxml' where id = 18;
update locale_lookuptext set text = 'Standard XML-Export-Plugin' where id = 10017;
update locale_lookuptext set text = 'Standard XML export plugin' where id = 10517;

-- PIMCORE-3043
INSERT INTO ATTRIBUTE (ID, IDENTIFIER, DESCRIPTION, SCOPENAME, TYPE, MANDATORY, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 1087, 'XMLExportAttributeValueOption', 1087, 'ATTRIBUTEAV', 7, 104, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) SELECT 1087, 2, 1087, 'Standard XML-Export Wertemenge-Option', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) SELECT 1587, 1, 1087, 'Standard XML export lookup option', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;

INSERT INTO ATTRIBUTE_VALUE (ID, ATTRIBUTE, IDENTIFIER, DESCRIPTION, DEFAULTVALUE, FREETEXT, NULLVALUE, SEQUENCENO, IMAGE, CLIENT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2100, 1087, 'Description', 2100, 0, 0, 0, 2, null, null, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2100, 2, 2100, 'Beschreibung', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2600, 1, 2100, 'Description', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;

INSERT INTO ATTRIBUTE_VALUE (ID, ATTRIBUTE, IDENTIFIER, DESCRIPTION, DEFAULTVALUE, FREETEXT, NULLVALUE, SEQUENCENO, IMAGE, CLIENT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2101, 1087, 'Identifier', 2101, 0, 0, 0, 2, null, null, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2101, 2, 2101, 'Identifier', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2601, 1, 2101, 'Identifier', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;

INSERT INTO ATTRIBUTE_VALUE (ID, ATTRIBUTE, IDENTIFIER, DESCRIPTION, DEFAULTVALUE, FREETEXT, NULLVALUE, SEQUENCENO, IMAGE, CLIENT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2102, 1087, 'Both', 2102, 0, 0, 0, 2, null, null, sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2102, 2, 2102, 'Identifier und Beschreibung', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) SELECT 2602, 1, 2102, 'Identifier and description', sysdate, sysdate, 1, 1 FROM PLUGIN WHERE IDENTIFIER = 'StandardXMLExport' AND ENABLED = 1;

-- PIMCORE-3035
UPDATE USER_RIGHT SET IDENTIFIER = 'Product detail - Product Texts Single View' WHERE ID = 140010;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (140180, 'Product detail - Product Texts View', null, 140000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 140180, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 140010;

-- PIMCORE-3004
ALTER TABLE ARTICLE_CSG_REL ADD CONSTRAINT UC_CSG_ART_REL UNIQUE (ARTICLE, TARGETARTICLE, TYPE, DIMENSION, TARGETDIMENSION);
ALTER TABLE NODE_ARTICLE_REL ADD CONSTRAINT UC_NAR UNIQUE (NODE, ARTICLE, CLIENT, DIMENSION);

-- PIMCORE-2980
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (310036, 'Maintenance - Toggle server maintenance mode', null, 310000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (310037, 'Maintenance - Invalidate all current user sessions', null, 310000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2971
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (160013, 'Bulk input - Send to', null, 160000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2919
INSERT INTO ATTRIBUTE (ID, IDENTIFIER, DESCRIPTION, SCOPENAME, TYPE, MANDATORY, INHERITABLE, DIMENSION, GROUPNAME, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1086, 'ishop_export_localized_boolean', 1086, 'ATTRIBUTEAV', 6, 104, 0, 0, 158, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1086, 2, 1086, 'iSHOP Export: Wahrheitswert lokalisiert exportieren', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1586, 1, 1086, 'iSHOP Export: Export boolean value localized', sysdate, sysdate, 1, 1);

-- PIMCORE-2944
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (221,6465,7,'CSV_FORCED_QUOTE',1, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6465,2,6465,'Quoted CSV', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6965,1,6465,'Quoted CSV', sysdate, sysdate, 1, 1);

-- PIMCORE-2911
CREATE TABLE NODE_ATTRIBUTE_REL_AV (
    ID                  NUMBER(19)          NOT NULL,
    RELATION            NUMBER(19)          NOT NULL,
    ATTRIBUTE           NUMBER(19)          NOT NULL,
    CLIENT              NUMBER(19)          NOT NULL,
    ATTRIBUTEVALUE      NUMBER(19),
    FREETEXT            NUMBER(19),
    UOM                 NUMBER(19),
    PLAINVALUE          VARCHAR2(1024 CHAR),
    BOOLEANVALUE        NUMBER(1),
    DECIMALVALUE        NUMBER(10,2),
    NUMBERVALUE         NUMBER(10),
    DATEVALUE           TIMESTAMP,
    SEQUENCENO          NUMBER(19),
    CREATIONDATE        TIMESTAMP           NOT NULL,
    LASTMODIFIED        TIMESTAMP           NOT NULL,
    UPDATEUSER          NUMBER(19)          NOT NULL,
    CREATEUSER          NUMBER(19)          NOT NULL,
    CONSTRAINT PK_NODE_ATTRIBUTE_REL_AV PRIMARY KEY (ID),
    CONSTRAINT UC_NODEATTRIBUTERELAV_REL_ATTR UNIQUE (RELATION, ATTRIBUTE, CLIENT),
    CONSTRAINT FK_NATTRAV_RELATION FOREIGN KEY(RELATION)
        REFERENCES NODE_ATTRIBUTE_REL(ID)
        ON DELETE CASCADE,
    CONSTRAINT FK_NATTRAV_ATTRIBUTE FOREIGN KEY(ATTRIBUTE)
        REFERENCES ATTRIBUTE(ID),
    CONSTRAINT FK_NATTRAV_AV FOREIGN KEY(ATTRIBUTEVALUE)
        REFERENCES ATTRIBUTE_VALUE(ID),
    CONSTRAINT FK_NATTRAV_CLIENT FOREIGN KEY(CLIENT)
        REFERENCES CLIENT(ID),
    CONSTRAINT FK_NATTRAV_UU FOREIGN KEY(UPDATEUSER)
        REFERENCES USERS(ID),
    CONSTRAINT FK_NATTRAV_CU FOREIGN KEY(CREATEUSER)
        REFERENCES USERS(ID)
);

-- PIMCORE-2965
ALTER TABLE ATTRIBUTE ADD CONSTRAINT FK_ATTR_DEF_UOM FOREIGN KEY(DEFAULTUOM) REFERENCES UOM(ID);
-- If this fails, check if you have invalid data and manually delete it
-- select * from attribute where defaultuom is not null and defaultuom not in (select id from uom);

-- PIMCORE-2959
UPDATE REPORT SET
CUSTOMRENDERER = 'Number,Number,Number,Number,Number,Client',
QUERY = 'SELECT
  -1 as id,
  sum(case when no_status_green = 4 then 1 else 0 end) as green4,
  sum(case when no_status_green = 3 then 1 else 0 end) as green3,
  sum(case when no_status_green = 2 then 1 else 0 end) as green2,
  sum(case when no_status_green = 1 then 1 else 0 end) as green1,
  sum(case when no_status_green = 0 then 1 else 0 end) as green0,
  client as client
FROM (
  SELECT
    case when status1 = 2 then 1 else 0 end +
    case when status2 = 2 then 1 else 0 end +
    case when status3 = 2 then 1 else 0 end +
    case when status4 = 2 then 1 else 0 end as no_status_green,
    client
  FROM #SCHEMA#.article
  WHERE articletype in (1, 10) AND deleted = 0) tmp group by client'
WHERE IDENTIFIER = 'PortalCountByStatus';

UPDATE locale_lookuptext SET TEXT = '4 x Grün,3 x Grün,2 x Grün,1 x Grün,0 x Grün,Mandant' WHERE ID = 370018;
UPDATE locale_lookuptext SET TEXT = '4 x green,3 x green,2 x green,1 x green,0 x green,Client' WHERE ID = 370518;

UPDATE REPORT SET
CUSTOMRENDERER = 'Number,Number,Number,Number,Client',
QUERY = 'SELECT
  -1 as id,
  coalesce(sum(type_product), 0) as type_product,
  coalesce(sum(type_article), 0) as type_article,
  coalesce(sum(type_set), 0) as type_set,
  count(distinct type_dimension) as type_dimesion,
  client as client
FROM (
  SELECT
    case when articletype = 1 then 1 else 0 end as type_product,
    case when articletype = 2 then 1 else 0 end as type_article,
    case when articletype = 10 then 1 else 0 end as type_set,
    dimension as type_dimension,
    client
  FROM #SCHEMA#.article
  WHERE articletype IN (1, 2, 10) AND deleted = 0) tmp group by client'
WHERE IDENTIFIER = 'PortalCount';

UPDATE locale_lookuptext SET TEXT = 'Produkte,Artikel,Sets,Farbebenen,Mandant' WHERE ID = 370021;
UPDATE locale_lookuptext SET TEXT = 'Products,Items,Sets,Color levels,Client' WHERE ID = 370521;

-- PIMCORE-3021
update user_right set identifier = 'Worklist - Menu iPIM apps' where id = 110028;
update user_right set identifier = 'Event - Menu iPIM apps' where id = 120006;
update user_right set identifier = 'Product detail - General - iPIM apps' where id = 140170;
update user_right set identifier = 'Reports - Button iPIM apps' where id = 150012;
update user_right set identifier = 'Search - Menu iPIM apps' where id = 170012;

-- PIMCORE-3064 (migrate all event_details which might be assigned to maintenance levels and update the client to the corresponding root client)
update event_detail d set client = (select rootclient from client where id = d.client) where d.client != (select rootclient from client where id = d.client);

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.11', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
