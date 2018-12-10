-- PIMCORE-3163
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (308, 2, 308, 'Schwarz', sysdate, sysdate, 1, 1);
INSERT INTO locale_lookuptext (id, locale, textid, text, creationdate, lastmodified, updateuser, createuser) VALUES (808, 1, 308, 'Black', sysdate, sysdate, 1, 1);

-- PIMCORE-3140
update attribute set inheritable = 0 where inheritable = 1 and onlyitemlevel = 1 and id > 9999;

-- PIMCORE-3137
update job set REPORTPARAMETERS = '<?xml version="1.0" encoding="UTF-8"?>
<java version="1.8.0" class="java.beans.XMLDecoder">
 <object class="com.novomind.ipim.common.pojo.WsReportParameter">
  <void property="parameter">
   <object class="java.util.ArrayList">
    <void method="add">
     <object class="com.novomind.ipim.common.pojo.WsReportParameterTemplate">
      <void property="parameterSequence">
       <int>1</int>
      </void>
      <void property="parameterValue">
       <string>true</string>
      </void>
     </object>
    </void>
   </object>
  </void>
 </object>
</java>
' where identifier = 'Elastic_Search_Product_Incremental_ExportJob' and exists (select 1 FROM PLUGIN WHERE IDENTIFIER = 'WebSearch' AND ENABLED = 1);

-- ID
UPDATE SERVER_PROPERTY SET PROPERTYVALUE='v4.0.13', LASTMODIFIED=sysdate WHERE PROPERTYKEY='server.id';
