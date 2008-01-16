package org.googlecode.gwt.menu.generator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.menu.client.model.MenuModel;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class ClassMenuGenerator {

	private MyMenuModel menu = new MyMenuModel();;
	private TreeLogger logger;
	private GeneratorContext context;
	private SourceWriter sw;
	private static Class requestedClass = null;
	private static JClassType requestedJClassType = null;
	private TypeOracle typeOracle;
//
	private static final String SUPER_INTERFACE = SMenu.class.getName();
	private static final Log log = LogFactory.getLog(ClassMenuGenerator.class);
	private static final String GENERIC_MESSAGE = "Errore durante la generazione della classe di navigazione.";

	private static final String STARTCOMMAND = "public "+Command.class.getName()+" getCommand() {";
	private static final String NULLCOMMAND = "return null;";
	private static final String ENDCOMMAND = "}";

	private static final String META_PREFIX_GWT = "gwt.";
	private static final String META_USER_ROLE = META_PREFIX_GWT + "user_role";
	private static final String META_ITEM_POSITION = META_PREFIX_GWT + "item_position";
	private static final String META_ITEM_LABEL = META_PREFIX_GWT + "item_label";
	private static final String META_ITEM_ICON = META_PREFIX_GWT + "item_icon";
	private static final String[] ALL_META = { META_USER_ROLE,  META_ITEM_POSITION, META_ITEM_LABEL, META_ITEM_ICON };
	
	private Map<String,String> roles = new HashMap<String, String>();
	private Map<String, MyContext> contextCommand = new HashMap<String, MyContext>();
	private Map<String, String> nameMethod = new HashMap<String, String>();

	/*
	 * classe di utility interna 
	 */
	private class MyContext{
		String role;
		String name;
		public MyContext(String role, String name) {
			this.role = role;
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String getRole() {
			return role;
		}
	}
//
	private class MyMenuModel{
		private final String menuModel = MenuModel.class.getName();
		private String name = null;
		private String label = null;
		private String icon = null;
		private String role = null;
		private List<MyMenuModel> childs = new ArrayList<MyMenuModel>();
		public MyMenuModel() {
		}
		
		public MyMenuModel(String name, String label, String icon, String role) {
			super();
			this.name = name;
			this.label = label;
			this.icon = icon;
			this.role = role;
		}
		public void addChild(int position, MyMenuModel child) {
			if(childs.size()<position){
				childs.add(child);
			}
			else{
				childs.add(position - 1, child);
			}
		}
		
		public String getNomeItem(){
			if(name==null){
				return "root";
			}
			return name+"Model";
		}
		public String getMenu(){
			StringBuffer  ret = createInstance(false);
			writeRow(getNomeItem(),"Label",label, ret);
			writeRow(getNomeItem(),"Icon",icon, ret);
			return ret.toString();
		}
		public String getItem(){
			StringBuffer ret = createInstance(true);
			writeRow(getNomeItem(),"Label",label, ret);
			writeRow(getNomeItem(),"Icon", icon,ret);
			writeRow(getNomeItem(),"Role", role,ret);
			
			return ret.toString();
		}
		
		private StringBuffer createInstance(boolean isItem){
			StringBuffer ret = new StringBuffer(menuModel+" "+getNomeItem() + " = new "+menuModel+"(){");
			
			MyContext context = contextCommand.get(name);
			if(isItem){
				ret.append(context.getName() + " item = new " + context.getName() +"();");
			}
			
			ret.append(STARTCOMMAND);
			
			if(isItem){
				ret.append("return item;");
			}
			else{
				ret.append(NULLCOMMAND);
			}
			ret.append(ENDCOMMAND);
			
			ret.append("};");
			return ret;
		}

		private void writeRow(final String nomeItem,final String property,String value, StringBuffer a) {
			a.append(nomeItem+".set"+property+"(");
			if(value!=null){
				a.append("\""+value+"\"");
			}
			else{
				a.append("null");
			}
			
			a.append(");");
			
		}
		public List<MyMenuModel> getChilds() {
			return childs;
		}
		
		public boolean isMenu(){
			return !childs.isEmpty();
		}
	}
	
	public ClassMenuGenerator(TreeLogger logger, GeneratorContext context) {
		this.logger = logger;
		this.context = context;
	}
	
	public String create(String typeName) throws NotFoundException, ClassNotFoundException, MenuGeneratorException, IllegalArgumentException, IllegalAccessException {

		typeOracle = context.getTypeOracle();

		// recupero la classe principale nei formati java e gwt
		requestedJClassType = typeOracle.getType(typeName);
		requestedClass = Class.forName(requestedJClassType.getQualifiedSourceName());

		String proxyClassName = requestedJClassType.getSimpleSourceName() + "ImplGenerate";

		// creo la struttura della classe generata
		sw = getSourceWriter(context, requestedJClassType.getPackage().getName(), proxyClassName);

		// se è nulla è perchè esiste già una classe con quel nome....
		if (sw == null) {
			return requestedJClassType.getPackage().getName() + "." + proxyClassName;
		}

	
		//popolo le variabili locali su cui mi baserò per scrivere i metodi reali
		elabClass(requestedClass,true,menu);
		
		//creo i metodi reali
		createMethod_createMenuModel();

		sw.commit(logger);
		return requestedJClassType.getPackage().getName() + "." + proxyClassName;
	}

	private SourceWriter getSourceWriter(GeneratorContext context, String packageName, String className) {
		PrintWriter printWriter = context.tryCreate(logger, requestedJClassType.getPackage().getName(), className);
		if (printWriter == null)
			return null;
		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(requestedJClassType.getPackage().getName(), className);
		composerFactory.addImplementedInterface(SUPER_INTERFACE);

		return composerFactory.createSourceWriter(context, printWriter);
	}

//	
	private void elabClass(Class inClass,boolean firstLevel,MyMenuModel parent) throws MenuGeneratorException, NotFoundException, ClassNotFoundException {
		
		JClassType inJClass = typeOracle.getType(inClass.getCanonicalName());

		//i metodi sono gli item del menù
		elabMethod(inJClass,firstLevel,parent);
			
		//le classi sono i menu
		Class[] interfaces = inClass.getDeclaredClasses();
		for (int i = 0; i < interfaces.length; i++) {
			Class clazz = interfaces[i];
			JClassType inJClazz = typeOracle.getType(clazz.getCanonicalName());
			
			String[] metas = inJClazz.getMetaDataTags();
			rightMetasItem(metas);
			String iconMenu = getValueMeta(META_ITEM_ICON, inJClazz.getMetaData(META_ITEM_ICON));
			String labelMenu = getValueMeta(META_ITEM_LABEL, inJClazz.getMetaData(META_ITEM_LABEL));
			int positionMenu = new Integer(getValueMeta(META_ITEM_POSITION, inJClazz.getMetaData(META_ITEM_POSITION))).intValue();
			
			MyMenuModel menuModel = new MyMenuModel(clazz.getSimpleName(),labelMenu,iconMenu,null);
			parent.addChild(positionMenu, menuModel);
			
			elabClass(clazz,false,menuModel);
		}
	}
//
	private void elabMethod(JClassType inJClassType,boolean firstLevel,MyMenuModel menuModel) throws MenuGeneratorException, ClassNotFoundException {
		JMethod[] methods = inJClassType.getMethods();
		for (int i = 0; i < methods.length; i++) {
			JMethod metodo = methods[i];
			if (nameMethod.containsKey(metodo.getName())) {
				String msg = "I metodi dei metoid devono avere un nome univoco all'interno del descrittore.";
				MenuGeneratorException e = new MenuGeneratorException(msg);
				log.error(GENERIC_MESSAGE, e);
				throw e;
			}

			nameMethod.put(metodo.getName(), metodo.getName());
			
			String[] metas = metodo.getMetaDataTags();
			
			rightMetasItem(metas); 
			
			JType returnType = metodo.getReturnType();
			String typetype = returnType.getQualifiedSourceName();
			Class clazz = Class.forName(typetype);
			
			String role = getValueMeta(META_USER_ROLE,metodo.getMetaData(META_USER_ROLE));
			
			//carico l'appoggio per il contesto
			if(instanceOf(clazz, Command.class)){
				String name = clazz.getName();
				contextCommand.put(metodo.getName(),new MyContext(role,name));
			}
			else{
				String msg = "I metodi possono tornare classi solo di tipo "+Command.class.getName();
				MenuGeneratorException e = new MenuGeneratorException(msg);
				log.error(GENERIC_MESSAGE,e);
				throw e;
			}
			
//			carico l'appoggio per il menuModel
			String iconMenu = getValueMeta(META_ITEM_ICON, metodo.getMetaData(META_ITEM_ICON));
			String labelMenu = getValueMeta(META_ITEM_LABEL, metodo.getMetaData(META_ITEM_LABEL));
			int positionMenu = new Integer(getValueMeta(META_ITEM_POSITION, metodo.getMetaData(META_ITEM_POSITION))).intValue();
				
			MyMenuModel myMenuModel = new MyMenuModel(metodo.getName(),labelMenu,iconMenu,role);
			menuModel.addChild(positionMenu, myMenuModel);
			
		}
	}
	
	/*
	 * creo i metodi che l'interfaccia mi richiede
	 */
	private void createMethod_createMenuModel() {
		sw.println("public " + MenuModel.class.getName() + " getMenuModel(){");
		sw.indent();
		{
			sw.println(MenuModel.class.getName()+" root = new "+MenuModel.class.getName()+"(){" +
			STARTCOMMAND +
			NULLCOMMAND +
			ENDCOMMAND +
			"};");
			writeMethod_MenuModel(menu);
			
			sw.println("return root;");
		}
		sw.outdent();
		sw.println("}");

	}
//
	private void writeMethod_MenuModel(MyMenuModel myMenuModel){
		List<MyMenuModel> childs = myMenuModel.getChilds();
		for (int i = 0; i < childs.size(); i++) {
			MyMenuModel model = childs.get(i);
		
			if(model.isMenu()){
				sw.println(model.getMenu());
				sw.println(myMenuModel.getNomeItem()+".addChild("+i+", "+model.getNomeItem()+");");
				writeMethod_MenuModel(model);
			}
			else{
				sw.println(model.getItem());
				sw.println(myMenuModel.getNomeItem()+".addChild("+i+", "+model.getNomeItem()+");");
				
			}
		}
	}

	/*
	 * controlla che le annotation del metodo abbiano senso
	 * 
	 */
	private void rightMetasItem(String[] metas) throws MenuGeneratorException {
		for (int j = 0; j < metas.length; j++) {
			String meta = metas[j];
			checkInMetas(meta,ALL_META);
		}
	}
//
	private void checkInMetas(String meta,String[] ranges) throws MenuGeneratorException {
		boolean find = false;
		
		for (int x = 0; x < ranges.length; x++) {
			String myMeta = ranges[x];
			if(meta.equals(myMeta)){
				find = true;
				break;
			}
		}
		if(!find){
			String msg = "Trovata una gwt annotation non gestita: @"+meta;
			MenuGeneratorException e = new MenuGeneratorException(msg);
			log.error(GENERIC_MESSAGE, e);
			throw e;
		}
	}
//	
	private String getValueMeta(String key,String[][] meta) throws MenuGeneratorException{
		if(META_USER_ROLE.equals(key)){
			if (meta.length == 0 || meta.length > 1) {
				String msg ;
				if(meta.length == 0){
					msg = "Non trovata la gwt annotation @" + META_USER_ROLE + ". " +
					"Per ogni item deve essere specificato quale ruolo è abilitato per eseguirlo.";
				}
				else{
					msg = "Non è possibile specificare più di una gwt annotation @" + META_USER_ROLE + " per item.";
				}
				MenuGeneratorException e = new MenuGeneratorException(msg);
				log.error(GENERIC_MESSAGE,e);
				throw e;
			}
			else{
				String ruolo = meta[0][0];
				if(!roles.containsKey(ruolo)){
					roles.put(ruolo, ruolo);
				}
				return ruolo;
			}
		}
		if(META_ITEM_ICON.equals(key)){
			if (meta.length == 0) {
				return null;
			}
			else if( meta.length > 1){
				String msg = "Non Ã¨ possibile specificare più di una gwt annotation @" + META_ITEM_ICON + " per item.";
				MenuGeneratorException e = new MenuGeneratorException(msg);
				log.error(GENERIC_MESSAGE,e);
				throw e;
			}
			else{
				return meta[0][0];
			}
		}
		if(META_ITEM_LABEL.equals(key)){
			if (meta.length == 0 || meta.length > 1) {
				String msg;
				if(meta.length == 0){
					msg = "Non trovata la gwt annotation @" + META_ITEM_LABEL + ". " +
						"Per ogni item e menu deve essere specificata la label.";
					
				}
				else{
					msg = "Non è possibile specificare più di una gwt annotation @" + META_ITEM_LABEL + " per item.";
				}
				MenuGeneratorException e = new MenuGeneratorException(msg);
				log.error(GENERIC_MESSAGE,e);
				throw e;
			}
			else{
				String label="";
				for (int i = 0; i < meta[0].length; i++) {
					label = label.concat(" "+meta[0][i]);
				}
				
				return label;
			}
			
		}
		if(META_ITEM_POSITION.equals(key)){
			if (meta.length == 0 || meta.length > 1) {
				String msg;
				if(meta.length == 0){
					msg = "Non trovata la gwt annotation @" + META_ITEM_POSITION + ". " +
						"Per ogni item e menu deve essere specificata la posizione all'interno del menù.";
				}
				else{
					msg = "Non è possibile specificare più di una gwt annotation @" + META_ITEM_POSITION + " per item.";
				}
				MenuGeneratorException e = new MenuGeneratorException(msg);
				log.error(GENERIC_MESSAGE,e);
				throw e;
			}
			else{
				return  meta[0][0];
			}
		}
		
		return null;
	}
//	
	/*
	 * controllo se la classe passata è del tipo che mi aspetto
	 */
	private boolean instanceOf(Class myClass,Class superInterfaces){
		try{
			myClass.asSubclass(superInterfaces);
		}
		catch (Exception e) {
			return false;
		}
		
		return true;
	}

}
