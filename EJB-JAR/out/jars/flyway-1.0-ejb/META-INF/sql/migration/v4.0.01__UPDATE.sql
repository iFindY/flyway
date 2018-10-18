-- PIMCORE-2419
ALTER TABLE ARTICLE_AV ADD PARTNER NUMBER(19);
ALTER TABLE ARTICLE_AV ADD CONSTRAINT FK_AAV_PARTNER FOREIGN KEY(PARTNER) REFERENCES PARTNER(ID);

-- Migration needed if count is > 0, see release notes
select count(av.id) from article_av av, attribute attr where av.attribute = attr.id and attr.scopename = 'SUPPLIER';

-- PIMCORE-2414
UPDATE locale_lookuptext set text = 'ohne VK-Validierung' where id = 6209;
UPDATE locale_lookuptext set text = 'no sales category validation' where id = 6709;

INSERT INTO type (id, description, type, identifier, sequenceno, creationdate, lastmodified, updateuser, createuser) VALUES (209,6450,3,'SC_VALIDATE',0, sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6450,2,6450,'mit VK-Validierung', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (6950,1,6450,'Allows sales category validation', sysdate, sysdate, 1, 1);

-- Migration update statement for PIMCORE-2414
-- *** FIRST check if the project has purchase categories with sales channel validation and set them first to "allows sales validation")
update version set type = 209 where id in (XXXX);
-- Then reset all traffic light states of other categories/versions
update NODE_ARTICLE_REL set status = 4 where id in (select rel.id from NODE_ARTICLE_REL rel
left outer join CATEGORY_NODE node on node.id = rel.node
left outer join CATEGORY_NODE root_node on root_node.id = node.rootnode
left outer join VERSION ver on ver.rootnode = root_node.id
where ver.TYPE = 67);

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.01', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
