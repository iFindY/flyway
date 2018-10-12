-- PIMCORE-2828
CREATE TABLE DATAFEED_PARTNER_REL(
    ID                  NUMBER(19)          NOT NULL,
    DATAFEED            NUMBER(19)          NOT NULL,
    PARTNER             NUMBER(19)          NOT NULL,
    CREATIONDATE        TIMESTAMP           NOT NULL,
    LASTMODIFIED        TIMESTAMP           NOT NULL,
    UPDATEUSER          NUMBER(19)          NOT NULL,
    CREATEUSER          NUMBER(19)          NOT NULL,
    CONSTRAINT PK_DATAFEED_PARTNER_REL PRIMARY KEY (ID),
    CONSTRAINT UC_DATAFEED_PARTNER UNIQUE (DATAFEED, PARTNER),
    CONSTRAINT FK_DPR_PARTNER FOREIGN KEY(PARTNER)
        REFERENCES PARTNER(ID)
        ON DELETE CASCADE,
    CONSTRAINT FK_DPR_DATAFEED FOREIGN KEY(DATAFEED)
        REFERENCES DATAFEED(ID)
        ON DELETE CASCADE,
    CONSTRAINT FK_DPR_CU FOREIGN KEY(CREATEUSER)
        REFERENCES USERS(ID),
    CONSTRAINT FK_DPR_UU FOREIGN KEY(UPDATEUSER)
        REFERENCES USERS(ID)
);

INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER, PARENTID, ISMASTERDATA) VALUES (300009, 'Datafeeds - Tab Partner', null, sysdate, sysdate, 1, 1, 300000, 1);

-- PIMCORE-2895
ALTER TABLE ATTRIBUTE_VALUE_AV_REL ADD CONSTRAINT UC_AVAVREL UNIQUE (ATTRIBUTE, ATTRIBUTEVALUE, RELATEDATTRIBUTE, RELATEDAV);

-- PIMCORE-2860
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (140170, 'Product detail - General - Applications', null, 140000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (150012, 'Reports - Button Applications', null, 150000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2854
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (310035, 'Maintenance - Workflow definition', null, 310000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2815
INSERT INTO type_type (id, description, identifier, userright, creationdate, lastmodified, updateuser, createuser) VALUES (82,3090,'DATAFEED_IDENTIFIER_MAPPING_TYPE', 100005, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3090,2,3090,'Datenfeed-Mapping-Typ-Zielspalte', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3590,1,3090,'Datafeed mapping type target column', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (218,6461,82,'CONSTANT',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6461,2,6461,'Konstante', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6961,1,6461,'Constant', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (219,6462,82,'ATTRIBUTE_ID',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6462,2,6462,'Attribut-ID', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6962,1,6462,'Attribute ID', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (220,6463,82,'DESCRIPTION',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6463,2,6463,'Bezeichnung', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6963,1,6463,'Description', sysdate, sysdate, 1, 1);

alter table datafeed_row_mapping add identifiermappingtype number(19);
alter table datafeed_row_mapping add identifier2mappingtype number(19);
alter table datafeed_row_mapping add identifier2 varchar2(254 char);

alter table datafeed_row_mapping add constraint fk_datafeed_idmappingtype foreign key (identifiermappingtype) references type (id);
alter table datafeed_row_mapping add constraint fk_datafeed_id2mappingtype foreign key (identifier2mappingtype) references type (id);

update datafeed_row_mapping set identifiermappingtype = 218;

alter table datafeed_row_mapping modify identifiermappingtype not null;
alter table datafeed_row_mapping modify identifier null;

-- PIMCORE-2824
INSERT INTO TYPE (ID, description, TYPE, IDENTIFIER, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (184,6464,9,'CATEGORY_ATTRIBUTE_AUTO',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6464,2,6464,'Kategorieattribut (auto)', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6964,1,6464,'Category attribute (auto)', sysdate, sysdate, 1, 1);

-- PIMCORE-2844
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200022, 'Attributes - Attributes Tab View', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200023, 'Attributes - Table Tab View', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200024, 'Attributes - Configuration fields edit', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200025, 'Attributes - Configuration - Table lookup values edit', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200026, 'Attributes - Configuration - Value properties', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200027, 'Attributes - Configuration - Table lookup values image column edit', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200028, 'Attributes - Names - Table value names image column edit', null, 200000, 1, sysdate, sysdate, 1, 1);

INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200022, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200023, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200024, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200025, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200026, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200027, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 200028, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 200000;

-- PIMCORE-2840
CREATE TABLE DELETED_NODE_ARTICLE_REL(
    ID                  NUMBER(19)          NOT NULL,
    NODE                NUMBER(19)          NOT NULL,
    ARTICLE             NUMBER(19)          NOT NULL,
    CLIENT              NUMBER(19)          NOT NULL,
    CREATIONDATE        TIMESTAMP           NOT NULL,
    LASTMODIFIED        TIMESTAMP           NOT NULL,
    UPDATEUSER          NUMBER(19)          NOT NULL,
    CREATEUSER          NUMBER(19)          NOT NULL,
    CONSTRAINT PK_DEL_NAR PRIMARY KEY(ID),
    CONSTRAINT FK_DEL_NAR_NOD FOREIGN KEY(NODE)
        REFERENCES CATEGORY_NODE(ID)
        ON DELETE CASCADE,
    CONSTRAINT FK_DEL_NAR_ART FOREIGN KEY(ARTICLE)
        REFERENCES ARTICLE(ID)
        ON DELETE CASCADE,
    CONSTRAINT FK_DEL_NAR_CLIENT FOREIGN KEY (CLIENT)
        REFERENCES CLIENT(ID),
    CONSTRAINT FK_DEL_NAR_UU FOREIGN KEY(UPDATEUSER)
        REFERENCES USERS(ID),
    CONSTRAINT FK_DEL_NAR_CU FOREIGN KEY(CREATEUSER)
        REFERENCES USERS(ID)
);

CREATE INDEX IDX_DNAR_NAC ON DELETED_NODE_ARTICLE_REL (NODE, ARTICLE, CLIENT);

-- PIMCORE-2877
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200029, 'Attributes - Button Export Dependent Values', null, 200000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (200030, 'Attributes - Button Import Dependent Values', null, 200000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2915
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER)  VALUES (261007, 'Category source based mapping - Run category mapping', null, 261000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_ROLE_USER_RIGHT_REL select seq_basic_id.nextval, userrole, 261007, sysdate, sysdate, 1, 1 FROM USER_ROLE_USER_RIGHT_REL WHERE USERRIGHT = 261000;

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.10', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
