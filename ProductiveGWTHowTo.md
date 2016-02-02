## Introduction ##
Productive GWT consists of a set of modules to be used as a basis for Web applications:
_basic_, _templates_, _menu_, _bootstrap_, _header_, _footer_.

## Dependencies for the Web application ##
In the pom.xml file of the Web project include the following dependencies.
```
<dependency>
    <groupId>com.google.gwt</groupId>
    <artifactId>gwt-user</artifactId>
    <version>${gwtVersion}</version>
    <type>jar</type>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>com.google.gwt</groupId>
    <artifactId>gwt-servlet</artifactId>
    <version>${gwtVersion}</version>
    <type>jar</type>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>productive-gwt</groupId>
    <artifactId>base</artifactId>
    <version>2.0.0</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>productive-gwt</groupId>
    <artifactId>template</artifactId>
    <version>2.0.0</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>productive-gwt</groupId>
    <artifactId>bootstrap</artifactId>
    <version>2.0.0</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>productive-gwt</groupId>
    <artifactId>menu</artifactId>
    <version>2.0.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>productive-gwt</groupId>
    <artifactId>header</artifactId>
    <version>2.0.0</version>
    <type>jar</type>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>productive-gwt</groupId>
    <artifactId>footer</artifactId>
    <version>2.0.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.1.1</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>
```

## GWT module definition ##
When you create the GWT module make sure it inherits the elements of Productive GWT
in the file _ModuleName.gwt.xml_.
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.0.3//EN"
"http://google-web-toolkit.googlecode.com/svn/tags/2.0.3/distro-source/core/src/gwt-module.dtd">
<module>

    <inherits name="org.googlecode.gwt.base.Base"/>
    <inherits name="org.googlecode.gwt.bootstrap.Bootstrap"/>
    <inherits name='org.googlecode.gwt.template.Template'/>
    <inherits name='org.googlecode.gwt.menu.Menu'/>
    <inherits name='org.googlecode.gwt.header.Header'/>
    <inherits name='org.googlecode.gwt.footer.Footer'/>

    <entry-point class="it.davideling.rubrica.client.RubricaEntryPoint" />

    <set-property name="user.agent" value="ie6"/>
</module>
```

### Menu ###
The menu is created by an interface in the module _client_ package (or in a _client_ subpackage). This interface presents a method signature for each menu item.
```
public interface RubricaMenu extends SMenu {

    @SMenuItem(position=1,
        label="Home",
        icon="images/action_go.gif",
        userRoles="Amministratore"
    )
    HomeCommand homeCommand();

    @SMenuItem(position=2,
        label="Contatti",
        icon="images/action_go.gif",
        userRoles="Amministratore"
    )
    ListCommand listCommand();

    @SMenuItem(position=3,
        label="Nuovo contatto",
        icon="images/action_go.gif",
        userRoles="Amministratore"
    )
    InsertCommand insertCommand();

}
```
In particular, the interface must extend _SMenu_ and the menu should use the annotation
_@SMenuItem_._@SMenuItem_ has attributes that define the position of the menu entry, the label, the icon to use and allowed user roles.
```
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SMenuItem {
        int position();
        String label();
        String icon();
        String shortcut() default "";
        String[] userRoles() default {""};
}
```

### Command ###
The menu interface methods return objects that implement Command. These objects must then implement the _execute()_ method.
```
public class HomeCommand implements Command {

        public void execute() {
            HomePanel homePanel = new HomePanel();
            TemplateManager.setApplicationContent(homePanel);
        }

}
```
In the example, _execute()_ method uses _TemplateManager_ to change the application's main panel. In this case the user back to Home.
### Entry Point ###
```
public class RubricaEntryPoint implements EntryPoint {

    public void onModuleLoad() {
            // Titolo dell'applicazione
        TemplateManager.setApplicationTitle(new Label("RUBRICA"));

            // Creazione del menu con deferred binding
            SMenu menu = GWT.create(RubricaMenu.class);
         TemplateManager.setMenu(menu);

            // Come default imposto la Home Page
        TemplateManager.setApplicationContent(new HomePanel());
    }

}
```
In the example above are set: the application title, the menu and the initial content.

### web.xml ###
In web.xml must be defined and configured the bootstrap servlet. The bootstrap servlet task is to retrieve the application bootstrap data (ie. user info, application name ecc.).
```
<servlet>
    <servlet-name>GWTBootstrapServiceServlet</servlet-name>
    <servlet-class>org.googlecode.gwt.bootstrap.server.GWTBootstrapServiceServlet</servlet-class>
    <!-- Here we can put any servlet parameter useful for bootstrap data resolution -->
</servlet>
<servlet-mapping>
    <servlet-name>GWTBootstrapServiceServlet</servlet-name>
    <url-pattern>COMPLETE_GWT_MODULE_NAME/bootstrap</url-pattern>
</servlet-mapping>
```

## Bootstrap Data Resolver ##
The GWTBootstrapServiceServlet uses an external service to do the job, so we have to develop it.
We can do it creating a simple project that exposes a _org.googlecode.gwt.bootstrap.server.BootstrapDataResolverFactory_ service inside the META-INF folder.
So in the file _/META-INF/services/org.googlecode.gwt.bootstrap.server.BootstrapDataResolverFactory_ there is the following content
```
org.googlecode.gwt.example.rubrica.server.MyBootstrapDataResolverFactory
```

We have to implement the _MyBootstrapDataResolverFactory_.
```
/**
 * Implementation of <b>BootstrapDataResolverFactory</b>.
 * It must be registered as service in:
 * <i>/META-INF/services/org.googlecode.gwt.bootstrap.server.BootstrapDataResolverFactory</i>
 * 
 * The <b>BootstrapDataResolverFactory</b> must implements the method <b>createUserInfoResolver()</b>
 * that return an implementation of <b>BootstrapDataResolver</b>.
 * 
 */
public class MyBootstrapDataResolverFactory extends
		AbstractBootstrapDataResolverFactory {

	public MyBootstrapDataResolverFactory(ServletConfig servletConfig) {
		super(servletConfig);
	}

	public BootstrapDataResolver createUserInfoResolver(Map<String, String> params) {
		return new MyBootstrapDataResolver();
	}

}
```
The class _MyBootstrapDataResolver_ in this example is very easy, it call a service
returning us the data we need.
```
/**
 * The <b>BootstrapDataResolver</b> has the task to retrieve the
 * data necessary to the bootstrap of the application.
 * The application developer can implements the service as she wants/needs.
 */
public class MyBootstrapDataResolver implements BootstrapDataResolver {

	public ApplicationContextData getAppContextData(HttpServletRequest request,
			String appContextName) {
		ApplicationContextData applicationContextData = new ApplicationContextData();
		return applicationContextData;
	}

	public BootstrapData getBootstrapData(HttpServletRequest request) {
		
		// Here we should call a real service

		// We can use the ServletConfig or the ServletRequest
		// to pass actual parameters to a real repository
		SomeBootstrapDataServiceParameters parameters = new SomeBootstrapDataServiceParameters();

                //... fill parameters as required by the service

		BootstrapData bootstrapData = SomeBootstrapDataService.getBootstrapData(parameters);
		
		return bootstrapData;
	}

}
```
Then we have to add the dependency in the webapp POM (here we assume the project is productive-gwt-example:my-bootstrap-validation).
```
		<dependency>
			<groupId>productive-gwt-example</groupId>
			<artifactId>my-bootstrap-validation</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<scope>compile</scope>
		</dependency>
```