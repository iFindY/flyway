--FOR MSSQL: 1. Replace sysdate with getdate(), 2. Replace <FREETEXT,> with <"FREETEXT",>, 3. Replace <.freetext> with <."freetext">
--FOR DB2: 1. Replace sysdate with CURRENT TIMESTAMP, 2. Setting right schema with: set schema ipim_core;
--FOR TOAD: set scan off; /* ignore ampersand */
INSERT INTO role(role_id, role_name)
VALUES(10, 'peter');

--Server properties
-- INSERT INTO SERVER_PROPERTY VALUES (1, 'server.id', 'v4.0.10', sysdate, sysdate, 1, 1);
-- INSERT INTO SERVER_PROPERTY VALUES (2, 'general.cache.changedate', '01.01.2000 00:00:00', sysdate, sysdate, 1, 1);
