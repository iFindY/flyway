package de.arkadi.tryOut.server;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

@Stateless
public class ConnectionService {


    @Resource(mappedName = "java:jboss/datasources/OracleDS")
    DataSource mappedName;

    @Resource(lookup = "java:jboss/datasources/OracleDS")
    private DataSource ds;

    //wenn man den Namen nicht angibt wird die die ExampleDS angebunden
    //stimmt nicht ganz, es hängt von den subsystem domain.ee ab, da steht die default bindings,
    //richtig gesetzte DS kommt hier das richtige raus
    @Resource
    private DataSource noName;

    // hier liegt auch eine exampleDS drine. wenn 2 gleiche ds unter unterschiedlichen namen leigen wird nur eine variabel angezeigt
    @Resource(name = "OracleDS")
    private DataSource named;

    // funktioniert nicht
    //@Resource(name = "java:jboss/datasources/OracleDS")
    //DataSource name;
    //@Resource(lookup = "java:global/jboss/datasources/OracleDS")
    //private DataSource global;



    public void printDatasource() {

        System.out.println("here is a container defined Datasource: " + ds.toString());
        System.out.println("here is a mappedName example"+ mappedName.toString());
        System.out.println("here is a noName example"+ noName.toString());
        System.out.println("here is a named example"+ named.toString());
        //System.out.println("here is a named example"+ name.toString());
        //System.out.println("here is a named example"+ global.toString());



    }


    public void jndiDatasource() throws NamingException, SQLException {
        Context context = new InitialContext();
        DataSource service = (DataSource) context.lookup("java:jboss/datasources/OracleDS");
        System.out.println(service.toString());

    }


}
