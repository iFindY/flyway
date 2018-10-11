-- PIMCORE-2585
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (480000, 'Price management', null, 100000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (480001, 'Price management - Add price', null, 480000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (480002, 'Price management - Remove price', null, 480000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (480003, 'Price management - Menu Button Copy to all', null, 480000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (480004, 'Price management - Menu Button Copy to selected rows', null, 480000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (480005, 'Price management - Export', null, 480000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2595
ALTER TABLE NODE_ARTICLE_REL DROP CONSTRAINT UC_NOD_ART_REL_NK;
DROP INDEX UC_NOD_ART_REL_NK;

-- PIMCORE-2490
ALTER TABLE PROGRESS_TRACKER ADD STOPPABLE NUMBER(1);

-- PIMCORE-2684
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490000, 'Attribute profiles', null, 100000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490001, 'Attribute profiles - Add attribute profile', null, 490000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490002, 'Attribute profiles - Rename attribute profile', null, 490000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490003, 'Attribute profiles - Remove attribute profile', null, 490000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490004, 'Attribute profiles - Duplicate attribute profile', null, 490000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490005, 'Attribute profiles - Menu Button Add standard attributes', null, 490000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490006, 'Attribute profiles - Menu Button Add category attributes', null, 490000, 1, sysdate, sysdate, 1, 1);
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (490007, 'Attribute profiles - Menu Button Send to', null, 490000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2688
ALTER TABLE CATEGORY_NODE ADD DIMENSIONCOUNT NUMBER(10);
UPDATE CATEGORY_NODE SET DIMENSIONCOUNT = 0;
ALTER TABLE CATEGORY_NODE MODIFY DIMENSIONCOUNT NOT NULL;

-- PIMCORE-2702
UPDATE USER_RIGHT SET IDENTIFIER = 'Worklist - Menu Button Remove from version' WHERE ID = 110019;
INSERT INTO USER_RIGHT (ID, IDENTIFIER, DESCRIPTION, PARENTID, ISMASTERDATA, CREATIONDATE, LASTMODIFIED, UPDATEUSER, CREATEUSER) VALUES (110031, 'Worklist - Menu Button Remove from categories', null, 110000, 1, sysdate, sysdate, 1, 1);

-- PIMCORE-2714
update report set
CUSTOMRENDERER = 'Number,Number,Number,Number',
query = 'SELECT
  -1 as id,
  coalesce(sum(type_product), 0) as type_product,
  coalesce(sum(type_article), 0) as type_article,
  coalesce(sum(type_set), 0) as type_set,
  count(distinct type_dimension) as type_dimesion
FROM (
  SELECT
    case when articletype = 1 then 1 else 0 end as type_product,
    case when articletype = 2 then 1 else 0 end as type_article,
    case when articletype = 10 then 1 else 0 end as type_set,
    dimension as type_dimension    
  FROM #SCHEMA#.article
  WHERE articletype IN (1, 2, 10) and deleted = 0) tmp'
where identifier = 'PortalCount';

update locale_lookuptext set text = 'Produkte,Artikel,Sets,Farbebenen' where id = 370021;
update locale_lookuptext set text = 'Products,Items,Sets,Color levels' where id = 370521;

-- PIMCORE-2442
update report set query = 'select a.id, a.articleNo, lft.text from NodeArticleRelation nar, Article a, ArticleAV av, LocaleFreetext lft where nar.node.id = ?1 and a.id = nar.article.id and av.article.id = a.id and av.attribute.identifier = ?2 and lft.textId = av.freetext and lft.locale.id = ?3 and a.client.id = ?4 and nar.dimension is null and not exists (select nart.article.id from NodeArticleRelation nart, CategoryNode cnt where nart.article.id = nar.article.id and nart.dimension is null and cnt.id = nart.node.id and cnt.rootNode.id = ?5)' where identifier = 'CoverageCategoryMapping';

update report set query = 'select art.id, art.productNo, lft.text, art.status1, art.status2, art.status3, art.status4, art.creationDate 
from #SCHEMA#.article art 
join #SCHEMA#.article_av aav 
on aav.article = art.id 
join #SCHEMA#.locale_freetext lft 
on lft.textId = aav.freetext 
inner join #SCHEMA#.node_article_rel nar 
on art.id = nar.article 
where aav.attribute = 2 and lft.locale = ?2 and art.articletype in (1, 10, 11) 
and not exists (select article from #SCHEMA#.article_asset_rel where type = 132 and article = art.id and dimension is null) and #LIST_PARAM#nar.node in (?1)#LIST_PARAM#
order by 1, 2, 3' where identifier = 'ReportProdukteOhneHauptbild';

update report set query = 'select art.id, art.productNo, lft.text, art.validuntil, art.status1, art.status2, art.status3, art.status4, art.creationDate 
from #SCHEMA#.article art 
inner join #SCHEMA#.article_av aav 
on aav.article = art.id 
inner join #SCHEMA#.locale_freetext lft 
on lft.textid = aav.freetext 
inner join #SCHEMA#.node_article_rel nar 
on nar.article = art.id 
where art.articletype = 1 and (art.validuntil is null or art.validuntil between trunc(sysdate) and ?1) and aav.attribute = 2 and lft.locale = ?3 and #LIST_PARAM#nar.node in (?2)#LIST_PARAM# 
and not exists (select article from #SCHEMA#.node_article_rel nodeartrel 
inner join #SCHEMA#.category_node catnode on nodeartrel.node = catnode.id 
inner join #SCHEMA#.category_node catnode2 on catnode.rootnode = catnode2.id 
inner join #SCHEMA#.version v on v.rootnode = catnode2.id 
inner join #SCHEMA#.publication p on p.id = v.publication 
where p.channeltype = 68 and nodeartrel.article = art.id and nodeartrel.dimension is null)' where identifier = 'ReportProdukteOhneVertriebskanal';

update report set query = 'select art.id, art.productNo, lft.text, art.status1, art.status2, art.status3, art.status4, art.creationDate 
from #SCHEMA#.article art 
inner join #SCHEMA#.article_av aav 
on aav.article = art.id 
join #SCHEMA#.article_detail_tab tab 
on tab.article = art.id 
join #SCHEMA#.locale_text lt 
on lt.textId = tab.content 
join #SCHEMA#.locale_freetext lft 
on lft.textId = aav.freetext 
where aav.attribute = 2 and tab.dimension is null and tab.tabname = ?1 and lt.type = ?2 and lft.locale = ?3 and art.articletype in (1, 10, 11) and tab.deleted = 0' where identifier = 'ReportTextstatus';

update report set query = 'select art.id, art.productNo, lft.text, art.status1, art.status2, art.status3, art.status4, art.creationDate 
from #SCHEMA#.article art 
inner join #SCHEMA#.article_av aav 
on aav.article = art.id 
join #SCHEMA#.article_detail_tab tab 
on tab.article = art.id 
join #SCHEMA#.locale_text lt 
on lt.textId = tab.content 
join #SCHEMA#.locale_freetext lft 
on lft.textId = aav.freetext 
where aav.attribute = 2 and tab.dimension is null and tab.tabname = ?1 and lt.type = ?2 and lft.locale = ?3 and art.articletype in (1, 10, 11) and tab.deleted = 0
and not exists (select 1 from #SCHEMA#.locale_text t where t.locale = ?4 and t.type = ?2 and t.textid = tab.content)' where identifier = 'ReportTextstatusUebersetzung';

update report set query = 'SELECT decode(art.articletype, 2, art.parentarticle, art.id) as id, art.productNo,
(select min(text) from #SCHEMA#.locale_freetext t, #SCHEMA#.article_av av where av.article = decode(art.articletype, 2, art.parentarticle, art.id) and av.attribute = 2 and t.textid = av.freetext and t.locale = ?2) as Produktname,
decode(art.articletype, 2, ''Artikel'', ''Produkt'') as Ebene,
(select l.text from #SCHEMA#.locale_lookuptext l where l.textid = atr.description and l.locale = ?2) as Attribut,
(select l.text from #SCHEMA#.locale_freetext l where l.textid = aav.freetext and l.locale = ?2) as Attributtext
FROM #SCHEMA#.Article art, #SCHEMA#.Article_AV aav, #SCHEMA#.Locale_Freetext lft, #SCHEMA#.Node_Article_Rel nar, #SCHEMA#.Attribute atr
WHERE aav.article = art.id
AND aav.dimension IS NULL
AND decode(art.articletype, 2, art.parentarticle, art.id) = nar.article
AND atr.id = aav.attribute
AND atr.scopename in (''ALL'', ''CATEGORY'', ''SUPPLIER'')
AND lft.textId = aav.freetext
AND lft.locale = ?2
AND lft.text is not null
AND #LIST_PARAM#nar.node in (?1)#LIST_PARAM#
AND NOT EXISTS (select 1 from #SCHEMA#.locale_freetext l where l.textid = aav.freetext and l.locale = ?3 and l.text is not null)' where identifier = 'ReportNichtLokalisierteAttributtexte';

update report set query = 'select art.id, art.productNo, lft.text, art.status1, art.status2, art.status3, art.status4, art.creationDate 
from #SCHEMA#.article art 
inner join #SCHEMA#.article_av aav 
on aav.article = art.id 
join #SCHEMA#.locale_freetext lft 
on lft.textId = aav.freetext 
join #SCHEMA#.node_article_rel rel 
on rel.article = art.id 
where aav.attribute = 2 and #LIST_PARAM#rel.node in (?1)#LIST_PARAM# and rel.status = 0 and rel.dimension is null and lft.locale = ?2 and art.articletype in (1, 10, 11)' where identifier = 'ReportSalesChannelQuality';

-- PIMCORE-2582
ALTER TABLE TEXT_SETUP_MAPPING ADD PREFIX VARCHAR2(32 CHAR);
ALTER TABLE TEXT_SETUP_MAPPING ADD DELIMITER VARCHAR2(32 CHAR);
ALTER TABLE TEXT_SETUP_MAPPING ADD CONFIGURATION NUMBER(19);
ALTER TABLE TEXT_SETUP_MAPPING ADD UOM NUMBER(1) DEFAULT 0 NOT NULL;
ALTER TABLE TEXT_SETUP_MAPPING ADD ENDING NUMBER(19);

ALTER TABLE TEXT_SETUP_MAPPING ADD CONSTRAINT FK_TXTSU_MAP_CF FOREIGN KEY(CONFIGURATION) REFERENCES TYPE(ID);
ALTER TABLE TEXT_SETUP_MAPPING ADD CONSTRAINT FK_TXTSU_MAP_ED FOREIGN KEY(ENDING) REFERENCES TYPE(ID);

INSERT INTO type_type (id, description, identifier, userright, creationdate, lastmodified, updateuser, createuser) VALUES (80,3088,'TEXT_SETUP_MAPPING_CONFIGURATION', 100005, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3088,2,3088,'Textkonfiguration-Mapping-Konfiguration', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3588,1,3088,'Text configuration mapping configuration', sysdate, sysdate, 1, 1);

INSERT INTO type_type (id, description, identifier, userright, creationdate, lastmodified, updateuser, createuser) VALUES (81,3089,'TEXT_SETUP_MAPPING_ENDING', 100005, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3089,2,3089,'Textkonfiguration-Mapping-Endung', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (3589,1,3089,'Text configuration mapping ending', sysdate, sysdate, 1, 1);

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (210, 6453,80,'NAME',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6453,2,6453,'Name', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6953,1,6453,'Name', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (211, 6454,80,'VALUE',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6454,2,6454,'Wert', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6954,1,6454,'Value', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (212, 6455,80,'NAME_VALUE',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6455,2,6455,'Name und Wert', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6955,1,6455,'Name and value', sysdate, sysdate, 1, 1);

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (213, 6456,81,'NEW_LINE',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6456,2,6456,'Neue Zeile', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6956,1,6456,'New line', sysdate, sysdate, 1, 1);
INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (214, 6457,81,'SPACE',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6457,2,6457,'Leerzeichen', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6957,1,6457,'Space', sysdate, sysdate, 1, 1);

-- PIMCORE-2719
-- If adding a constraint fails, you might have invalid data. Delete it with for example
-- delete from ATTRIBUTE_AV where id in (select av1.id from ATTRIBUTE_AV av1, ATTRIBUTE_AV av2 where av1.id <> av2.id and av1.SOURCEATTRIBUTE = av2.SOURCEATTRIBUTE and av1.ATTRIBUTE = av2.ATTRIBUTE)
ALTER TABLE ARTICLE_CSG_REL_AV ADD CONSTRAINT UC_ART_CSG_REL_AV UNIQUE (RELATION, ATTRIBUTE);
ALTER TABLE ARTICLE_ASSET_REL_AV ADD CONSTRAINT UC_ART_ASSET_REL_AV UNIQUE (RELATION, ATTRIBUTE);
ALTER TABLE ARTICLE_PRICE_AV ADD CONSTRAINT UC_ART_PRICE_AV UNIQUE (PRICE, ATTRIBUTE);
ALTER TABLE ARTICLE_RATING_AV ADD CONSTRAINT UC_ART_RAT_AV UNIQUE (RATING, ATTRIBUTE);
ALTER TABLE ASSET_AV ADD CONSTRAINT UC_ASSET_AV UNIQUE (ASSET, ATTRIBUTE, CLIENT);
ALTER TABLE ATTRIBUTE_AV ADD CONSTRAINT UC_AT_AV UNIQUE (SOURCEATTRIBUTE, ATTRIBUTE);
ALTER TABLE ATTRIBUTE_VALUE_AV ADD CONSTRAINT UC_AV_AV UNIQUE (SOURCEAV, ATTRIBUTE);
ALTER TABLE TYPE_AV ADD CONSTRAINT UC_TYPE_AV UNIQUE (TYPE, ATTRIBUTE);
ALTER TABLE USER_AV ADD CONSTRAINT UC_USER_AV UNIQUE (USERID, ATTRIBUTE, CLIENT);
ALTER TABLE PARTNER_AV ADD CONSTRAINT UC_PARTNER_AV UNIQUE (PARTNER, ATTRIBUTE, CLIENT);
ALTER TABLE NODE_ARTICLE_REL_AV ADD CONSTRAINT UC_NARAV UNIQUE (RELATION, ATTRIBUTE);

-- PIMCORE-2730
-- Just execute the migration statement if the project will be set to "global folders for worklists = true"
-- Replace Client = 1 by the relevant default client (WsDescriptiveEntity.CLIENT_DEFAULT_ID) if customized to another value
-- UPDATE LIST_FOLDER SET CLIENT = 1 WHERE TYPE = 189;

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.07', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
