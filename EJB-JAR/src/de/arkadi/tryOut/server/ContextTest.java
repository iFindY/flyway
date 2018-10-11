package de.arkadi.tryOut.server;


import de.arkadi.tryOut.model.Arkadi;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.naming.*;
import java.util.Properties;

@Singleton
@Startup
public class ContextTest {

    //@EJB(name = "neinBean")
    @Inject // geht auch
    //@Named(value = "beanName")
    private HelloUserBean helloUserBean;

    @Inject
    private SomeService someService;


    @Inject
    private ConnectionService connectionService;

    public ContextTest() throws NamingException {

        // on mir gesetzt, wenn nicht gesetz einfach der klassen name
        Context context = new InitialContext();
        SomeService service = (SomeService) context.lookup("java:module/myService");
        service.shout();

    }

    @PostConstruct
    @Inject
    public void test(Arkadi arkadi) throws NamingException {


        connectionService.printDatasource();
        helloUserBean.sayHello("Arkadi Inject");
        helloUserBean.printCreator();
        someService.shout();
        arkadi.setCreator("postConstruct");
        arkadi.getCreator();
        someService.shout();
        //String name = "/Users/arkadi/Downloads/SBOnlineZahltraeger.pdf";
        //"/home/max/questions.txt";
        //String name = "/home/max/";

        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        props.put(Context.PROVIDER_URL, "file:///Users/arkadi/");

        Context initialContext = new InitialContext(props);
        //File obj = (File) initialContext.lookup(name);
        //System.out.println(obj.getName());
        //if (name.equals(""))
        //   System.out.println("Looked up the initial context");
        // else
        //   System.out.println(name + " is bound to: " + obj);

        NamingEnumeration bindings = initialContext.listBindings("/Downloads/");

        while (bindings.hasMore()) {
            Binding bd = (Binding) bindings.next();
            System.out.println("Arkadi:: " + bd.getName() + ": " + bd.getObject());
        }
    }
}
