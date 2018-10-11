-- PIMCORE-3077
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130083, 'Sale catalog - Menu Remove sequence', null, 130000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (250083, 'Purchase catalog - Menu Remove sequence', null, 250000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-3045
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (130028, 'Sale catalog Item Tab - Sequence', null, 130001, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (250028, 'Purchase catalog Item Tab - Sequence', null, 250001, 1, sysdate, sysdate, 1, 1);

INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 130028, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 130001;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 250028, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 250001;

-- PIMCORE-3036
CREATE TABLE WEB_CONFIGURATION(
    ID                  NUMBER(19)          NOT NULL,
    IDENTIFIER          VARCHAR2(254 CHAR)  NOT NULL,
    USERID              NUMBER(19)          NOT NULL,
    CONFIGTYPE          VARCHAR2(254 CHAR)  NOT NULL,
    TYPE                VARCHAR2(254 CHAR)  NOT NULL,
    JSON                CLOB                NOT NULL,
    CREATIONDATE        TIMESTAMP           NOT NULL,
    LASTMODIFIED        TIMESTAMP           NOT NULL,
    UPDATEUSER          NUMBER(19)          NOT NULL,
    CREATEUSER          NUMBER(19)          NOT NULL,
    CONSTRAINT PK_WEB_CFG PRIMARY KEY (ID),
    CONSTRAINT UC_WEB_CFG UNIQUE (IDENTIFIER),
    CONSTRAINT FK_WEB_CFG_CU FOREIGN KEY(UPDATEUSER)
        REFERENCES USERS(ID),
    CONSTRAINT FK_WEB_CFG_UU FOREIGN KEY(CREATEUSER)
        REFERENCES USERS(ID)
);

CREATE INDEX I_FK_WEB_CFG_1 ON WEB_CONFIGURATION (USERID, CONFIGTYPE, TYPE);

-- PIMCORE-3062
INSERT INTO ATTRIBUTE (ID, IDENTIFIER, DESCRIPTION, SCOPENAME, TYPE, MANDATORY, INHERITABLE, DIMENSION, GROUPNAME, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1088, 'TextManagementName', 1088, 'ATTRIBUTEAV', 3, 104, 0, 0, 158, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1088, 2, 1088, 'Text-Management: Attributname', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1588, 1, 1088, 'Text management: Attribute name', sysdate, sysdate, 1, 1);

INSERT INTO TYPE (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (222, 6466,80,'IGNORE_FALSE_VALUES',4, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6466,2,6466,'Wahrheitswert: Ignoriere Wert "false"', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6966,1,6466,'Boolean: Ignore value "false"', sysdate, sysdate, 1, 1);

-- PIMCORE-3054
INSERT INTO ATTRIBUTE (ID, IDENTIFIER, DESCRIPTION, SCOPENAME, TYPE, MANDATORY, GROUPNAME, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1089, 'META_HIDE_EMPTY', 1089, 'ATTRIBUTEAV', 6, 104, 158, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1089, 2, 1089, 'iPIM UI-Anzeige: Leere Meta-Attribut Definitionen nicht anzeigen', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (ID, LOCALE, TEXTID, TEXT, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (1589, 1, 1089, 'iPIM UI display: Do not show empty meta attribute definitions', sysdate, sysdate, 1, 1);

-- PIMCORE-3031
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER, PARENTID, ISMASTERDATA) VALUES (360001, 'Text setups - Configuration', null, sysdate, sysdate, 1, 1, 360000, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER, PARENTID, ISMASTERDATA) VALUES (360002, 'Text setups - Mappings', null, sysdate, sysdate, 1, 1, 360000, 1);

INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 360001, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 360000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 360002, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 360000;

INSERT INTO type_type (id, description, identifier, userright, creationdate, lastmodified, updateuser, createuser) VALUES (83,3091,'TEXT_SETUP_TYPE', 100005, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3091,2,3091,'Textkonfigurationstyp', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3591,1,3091,'Text setup type', sysdate, sysdate, 1, 1);

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (223,6467,83,'TEXT',1, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6467,2,6467,'Text', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6967,1,6467,'Text', sysdate, sysdate, 1, 1);

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (224,6468,83,'ATTRIBUTE',2, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6468,2,6468,'Attribut', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6968,1,6468,'Attribute', sysdate, sysdate, 1, 1);

CREATE TABLE TEXT_SETUP_LOCALES(
    ID                  NUMBER(19)          NOT NULL,
    TEXTSETUP           NUMBER(19)          NOT NULL,
    LOCALE              NUMBER(19)          NOT NULL,
    CREATIONDATE        TIMESTAMP           NOT NULL,
    LASTMODIFIED        TIMESTAMP           NOT NULL,
    UPDATEUSER          NUMBER(19)          NOT NULL,
    CREATEUSER          NUMBER(19)          NOT NULL,
    CONSTRAINT PK_TXTSU_LOC PRIMARY KEY (ID),
    CONSTRAINT UC_TXTSU_LOC UNIQUE (TEXTSETUP, LOCALE),
    CONSTRAINT FK_TXTSU_LOC_TS FOREIGN KEY(TEXTSETUP)
        REFERENCES TEXT_SETUP(ID)
        ON DELETE CASCADE,
    CONSTRAINT FK_TXTSU_LOC_LOCALE FOREIGN KEY(LOCALE)
        REFERENCES LOCALE(ID),
    CONSTRAINT FK_TXTSU_LOC_CU FOREIGN KEY(UPDATEUSER)
        REFERENCES USERS(ID),
    CONSTRAINT FK_TXTSU_LOC_UU FOREIGN KEY(CREATEUSER)
        REFERENCES USERS(ID)
);

CREATE INDEX I_FK_TXT_SL_1 ON TEXT_SETUP_LOCALES (TEXTSETUP);
CREATE INDEX I_FK_TXT_SL_2 ON TEXT_SETUP_LOCALES (LOCALE);

ALTER TABLE TEXT_SETUP ADD TYPE NUMBER(19);
ALTER TABLE TEXT_SETUP ADD CONSTRAINT FK_TXTSU_TYPE FOREIGN KEY(TYPE) REFERENCES TYPE(ID);

UPDATE TEXT_SETUP SET TYPE = 223 WHERE TYPE IS NULL;
ALTER TABLE TEXT_SETUP MODIFY TYPE NOT NULL;

ALTER TABLE TEXT_SETUP ADD ATTRIBUTE NUMBER(19);
ALTER TABLE TEXT_SETUP ADD CONSTRAINT FK_TXTSU_ATTRIBUTE FOREIGN KEY(ATTRIBUTE) REFERENCES ATTRIBUTE(ID);

ALTER TABLE TEXT_SETUP ADD CLIENT NUMBER(19);
ALTER TABLE TEXT_SETUP ADD CONSTRAINT FK_TXTSU_CLIENT FOREIGN KEY(CLIENT) REFERENCES CLIENT(ID);

ALTER TABLE TEXT_SETUP ADD ISAUTOCALCULATED NUMBER(1) DEFAULT 0 NOT NULL;

ALTER TABLE TEXT_SETUP ADD CONSTRAINT UC_TEXT_SETUP UNIQUE (IDENTIFIER);

-- PIMCORE-3032
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (141161, 'Product detail - General - Generate attribute text', null, 140000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-3074
ALTER TABLE JOB ADD MAXINACTIVEPERIOD NUMBER(19);

-- PIMCORE-3033
CREATE TABLE PROCESS_ARTICLE_QUEUE(
    ID                  NUMBER(19)          NOT NULL,
    ARTICLEID           NUMBER(19)          NOT NULL,
    PROCESSSTATE        NUMBER(19)          NOT NULL,
    PROCESS             VARCHAR2(254 CHAR)  NOT NULL,
    AREACHANGED         VARCHAR2(2048 CHAR),
    CREATIONDATE        TIMESTAMP           NOT NULL,
    LASTMODIFIED        TIMESTAMP           NOT NULL,
    CREATEUSER          NUMBER(19)          NOT NULL,
    UPDATEUSER          NUMBER(19)          NOT NULL,
    CONSTRAINT PK_PRO_ART_QUE PRIMARY KEY (ID),
    CONSTRAINT FK_PRO_ART_QUE_CU FOREIGN KEY(CREATEUSER)
        REFERENCES USERS(ID),
    CONSTRAINT FK_PRO_ART_QUE_UU FOREIGN KEY(UPDATEUSER)
        REFERENCES USERS(ID)
);

CREATE INDEX I_FK_PAQ_1 ON PROCESS_ARTICLE_QUEUE (ARTICLEID, PROCESSSTATE, PROCESS);
CREATE INDEX I_FK_PAQ_2 ON PROCESS_ARTICLE_QUEUE (PROCESSSTATE, PROCESS);

CREATE INDEX I_FK_EAQ_1 ON EXPORT_ARTICLE_QUEUE (ARTICLEID, EXPORTSTATE, DESTINATION);
CREATE INDEX I_FK_EAQ_2 ON EXPORT_ARTICLE_QUEUE (CATEGORYID, EXPORTSTATE, DESTINATION);
CREATE INDEX I_FK_EAQ_3 ON EXPORT_ARTICLE_QUEUE (ASSETID, EXPORTSTATE, DESTINATION);
CREATE INDEX I_FK_EAQ_4 ON EXPORT_ARTICLE_QUEUE (EXPORTSTATE, DESTINATION);

-- PIMCORE-3034
INSERT INTO JOB (ID, IDENTIFIER, DESCRIPTION, REPORTPARAMETERS, QUARTZJOB, TRIGGERMETHOD, TRIGGERPARAMETER, ENABLED, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (114, 'ProcessQueueGenerateTextJob', 406, 
'<?xml version="1.0" encoding="UTF-8"?> 
<java version="1.6.0_31" class="java.beans.XMLDecoder"> 
 <object class="com.novomind.ipim.common.pojo.WsReportParameter"> 
  <void property="parameter"> 
   <object class="java.util.ArrayList"> 
    <void method="add"> 
     <object class="com.novomind.ipim.common.pojo.WsReportParameterTemplate"> 
      <void property="parameterSequence"> 
       <int>1</int> 
      </void> 
      <void property="parameterValue"> 
       <string>TEXT_CONFIGURATION</string> 
      </void> 
     </object> 
    </void> 
   </object> 
  </void> 
 </object> 
</java>', 'com.novomind.ipim.core.util.scheduler.jobs.TextConfigurationProcessJob', 'makeImmediateTrigger', '', 0, sysdate, sysdate, 1, 1);

INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (406, 2, 406, 'Standard Job zur near time Berechnung der Text-Generierung', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (906, 1, 406, 'Standard job for near time calculation of text generation', sysdate, sysdate, 1, 1);

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.12', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
