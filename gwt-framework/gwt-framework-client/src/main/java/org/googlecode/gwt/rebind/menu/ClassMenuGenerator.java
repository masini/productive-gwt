package org.googlecode.gwt.rebind.menu;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import org.googlecode.gwt.client.ui.menu.SMenu;
import org.googlecode.gwt.client.ui.menu.SMenuItem;
import org.googlecode.gwt.client.ui.menu.model.MenuModel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassMenuGenerator {

	private MyMenuModel menu = new MyMenuModel();;
	private TreeLogger logger;
	private GeneratorContext context;
	private SourceWriter sw;
	private static Class<?> requestedClass = null;
	private static JClassType requestedJClassType = null;
	private TypeOracle typeOracle;
//
	private static final String SUPER_INTERFACE = SMenu.class.getName();
	private static final String GENERIC_MESSAGE = "Errore durante la generazione della classe di navigazione.";

	private static final String STARTCOMMAND = "public "+Command.class.getName()+" getCommand() {";
	private static final String NULLCOMMAND = "return null;";
	private static final String ENDCOMMAND = "}";
	private static final int MAX_SHORTCUT_LENGHT = 4;

	private static final String META_PREFIX_GWT = "gwt.";
	private static final String META_USER_ROLE = META_PREFIX_GWT + "user_role";
	private static final String META_ITEM_POSITION = META_PREFIX_GWT + "item_position";
	private static final String META_ITEM_LABEL = META_PREFIX_GWT + "item_label";
	private static final String META_ITEM_ICON = META_PREFIX_GWT + "item_icon";
	private static final String META_ITEM_SHORTCUT = META_PREFIX_GWT + "item_shortcut";
	
	private static final String[] ALL_META = { META_USER_ROLE,  META_ITEM_POSITION, META_ITEM_LABEL, META_ITEM_ICON ,META_ITEM_SHORTCUT};
	
	private Map<String,String> roles = new HashMap<String, String>();
	private Map<String, MyContext> contextCommand = new HashMap<String, MyContext>();
	private Map<String, String> nameMethod = new HashMap<String, String>();
	
	private Map<String, String> shortcutItem = new HashMap<String, String>();

	/*
	 * classe di utility interna 
	 */
	private class MyContext{
		String[] role;
		String name;
		public MyContext(String[] role, String name) {
			this.role = role;
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String[] getRole() {
			return role;
		}
	}
//
	private class MyMenuModel{
		private final String menuModel = MenuModel.class.getName();
		private String name = null;
		private String label = null;
		private String icon = null;
		private String[] role = null;
		private List<MyMenuModel> childs = new ArrayList<MyMenuModel>();
		private String shortcut;
		public MyMenuModel() {
		}
		
		public MyMenuModel(String name, String label, String icon, String[] role,String shortcut) {
			super();
			this.name = name;
			this.label = label;
			this.icon = icon;
			this.role = role;
			this.shortcut = shortcut;
		}
		public void addChild(int position, MyMenuModel child) {
			while(childs.size()<position){
				childs.add(null);
			}
			childs.set((position-1), child);
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
			writeRows(getNomeItem(),"Role", role,ret);
			writeRow(getNomeItem(),"Shortcut", shortcut,ret);
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

		private void writeRows(final String nomeItem,final String property,String[] value, StringBuffer a) {
			a.append(nomeItem+".set"+property+"(");
			if(value!=null){
				a.append("new String[]{");
				for (int i = 0; i < value.length; i++) {
					if (i > 0) a.append(", ");
					a.append("\""+value[i]+"\"");
				}
				a.append("}");
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
	private void elabClass(Class<?> inClass,boolean firstLevel,MyMenuModel parent) throws MenuGeneratorException, NotFoundException, ClassNotFoundException {
		
		JClassType inJClass = typeOracle.getType(inClass.getCanonicalName());

		//i metodi sono gli item del menù
		elabMethod(inJClass,firstLevel,parent);
			
		//le classi sono i menu
		Class<?>[] interfaces = inClass.getDeclaredClasses();
		for (int i = 0; i < interfaces.length; i++) {
			Class<?> clazz = interfaces[i];
			JClassType inJClazz = typeOracle.getType(clazz.getCanonicalName());
			
			String[] metas = inJClazz.getMetaDataTags();
			rightMetasItem(metas);
			String iconMenu = getValueMeta(META_ITEM_ICON, inJClazz);
			String labelMenu = getValueMeta(META_ITEM_LABEL, inJClazz);
			int positionMenu = new Integer(getValueMeta(META_ITEM_POSITION, inJClazz)).intValue();
			
			MyMenuModel menuModel = new MyMenuModel(clazz.getSimpleName(),labelMenu,iconMenu,null,null);
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
				String msg = "I metodi dei metodi devono avere un nome univoco all'interno del descrittore.";
				MenuGeneratorException e = new MenuGeneratorException(msg);
				throw e;
			}

			nameMethod.put(metodo.getName(), metodo.getName());
			
			String[] metas = metodo.getMetaDataTags();
			
			rightMetasItem(metas); 
			
			JType returnType = metodo.getReturnType();
			Class<?> clazz = Class.forName(getCanonicalName(returnType));

			String[] role = getValueMeta(META_USER_ROLE,metodo).split(" ");
			
			//carico l'appoggio per il contesto
			if(instanceOf(clazz, Command.class)){
				contextCommand.put(metodo.getName(),new MyContext(role,clazz.getCanonicalName()));
			}
			else{
				String msg = "I metodi possono tornare classi solo di tipo "+Command.class.getName();
				MenuGeneratorException e = new MenuGeneratorException(msg);
				throw e;
			}
			
//			carico l'appoggio per il menuModel
			String iconMenu = getValueMeta(META_ITEM_ICON, metodo);
			String labelMenu = getValueMeta(META_ITEM_LABEL, metodo);
			String shortcutMenu = getValueMeta(META_ITEM_SHORTCUT, metodo);
			
			if(shortcutMenu!=null){
				if(shortcutItem.containsKey(shortcutMenu)){
					String msg = "I shortcut devono avere un nome univoco all'interno del descrittore.";
					MenuGeneratorException e = new MenuGeneratorException(msg);
					throw e;
				}
				shortcutItem.put(shortcutMenu, shortcutMenu);
			}
			
			int positionMenu = new Integer(getValueMeta(META_ITEM_POSITION, metodo)).intValue();
				
			MyMenuModel myMenuModel = new MyMenuModel(metodo.getName(),labelMenu,iconMenu,role,shortcutMenu);
			menuModel.addChild(positionMenu, myMenuModel);
			
		}
	}

	private static String getCanonicalName(JType jType) {
		String canonicalName = jType.getQualifiedSourceName();
		if (jType instanceof JClassType) {
			for (JClassType currentJClassType = (JClassType) jType ; currentJClassType.isMemberType() ; currentJClassType = currentJClassType.getEnclosingType()) {
				int index = canonicalName.lastIndexOf('.');
				canonicalName = canonicalName.substring(0, index) + "$" + canonicalName.substring(index + 1);
			}
		}
		return canonicalName;
	}
	
	/*
	 * creo i metodi che l'interfaccia mi richiede
	 */
	private void createMethod_createMenuModel() throws MenuGeneratorException {
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
	private void writeMethod_MenuModel(MyMenuModel myMenuModel) throws MenuGeneratorException {
		List<MyMenuModel> childs = myMenuModel.getChilds();
		for (int i = 0; i < childs.size(); i++) {
			MyMenuModel model = childs.get(i);
			
			if(model==null){
				String msg = "Non sono stati valorizzati nel modo corretto i valori della gwt annotation @" + META_ITEM_POSITION + ". Il numero della posizione non può avere buchi.";
				throw new MenuGeneratorException(msg);
			}
		
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
			throw e;
		}
	}
//	
	
	private String getValueMeta(String key,SMenuItem menuItem) throws MenuGeneratorException {

		if(META_USER_ROLE.equals(key)){
			StringBuffer ruoli = new StringBuffer();
			for (int i = 0; i < menuItem.userRoles().length; i++) {
				String ruolo = menuItem.userRoles()[i];
				if(!roles.containsKey(ruolo)){
					roles.put(ruolo, ruolo);
				}
				if (i > 0) ruoli.append(" ");
				ruoli.append(ruolo);
			}
			return ruoli.toString();
		}
		if(META_ITEM_ICON.equals(key)){
			
			return menuItem.icon();
		}
		if(META_ITEM_SHORTCUT.equals(key)){
			
			return "".equals(menuItem.shortcut())?null:menuItem.shortcut();
		}
		if(META_ITEM_LABEL.equals(key)){
			
			return menuItem.label();
		}
		if(META_ITEM_POSITION.equals(key)){
			return Integer.toString(menuItem.position());
		}
		
		return null;
	}
	
	private <T extends HasAnnotations & HasMetaData> String getValueMeta(String key,T classMetaData) throws MenuGeneratorException {
		
		String retVal = null;
		
		if(classMetaData.isAnnotationPresent(SMenuItem.class)) {
			
			SMenuItem annotation = classMetaData.getAnnotation(SMenuItem.class);

			retVal = getValueMeta(key, annotation);
			
		} else {
			
			HasMetaData metaData = classMetaData;
			
			retVal = getValueMeta(key, metaData.getMetaData(key));
		}
		
		return retVal;
	}
		
	private String getValueMeta(String key,String[][] meta) throws MenuGeneratorException {
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
				throw e;
			}
			else{
				StringBuffer ruoli = new StringBuffer();
				for (int i = 0; i < meta[0].length; i++) {
					String ruolo = meta[0][i];
					if(!roles.containsKey(ruolo)){
						roles.put(ruolo, ruolo);
					}
					if (i > 0) ruoli.append(" ");
					ruoli.append(ruolo);
				}
				return ruoli.toString();
			}
		}
		if(META_ITEM_ICON.equals(key)){
			if (meta.length == 0) {
				return null;
			}
			else if( meta.length > 1){
				String msg = "Non è possibile specificare più di una gwt annotation @" + META_ITEM_ICON + " per item.";
				MenuGeneratorException e = new MenuGeneratorException(msg);
				throw e;
			}
			else{
				return meta[0][0];
			}
		}
		if(META_ITEM_SHORTCUT.equals(key)){
			if (meta.length == 0) {
				return null;
			}
			else if( meta.length > 1){
				String msg = "Non è possibile specificare più di una gwt annotation @" + META_ITEM_SHORTCUT + " per item.";
				MenuGeneratorException e = new MenuGeneratorException(msg);
				throw e;
			}
			else{
				String text = meta[0][0];
				if(text.trim().length() > MAX_SHORTCUT_LENGHT){
					String msg = "Non è possibile specificare un @" + META_ITEM_SHORTCUT + " con un testo maggiore di "+MAX_SHORTCUT_LENGHT+". Traovo il testo <"+text+">";
					MenuGeneratorException e = new MenuGeneratorException(msg);
					throw e;
				}
				return text;
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
	private boolean instanceOf(Class<?> myClass,Class<?> superInterfaces){
		try{
			myClass.asSubclass(superInterfaces);
		}
		catch (Exception e) {
			return false;
		}
		
		return true;
	}

}
