package translator;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Translator {
	
	
	static public final int MAIN_FRAME=1;
	static public final int EDIT_NOTE_JDIALOG=2;
	static public final int EDIT_PERSON_JDIALOG=3;
	static public final int OPTION_JDIALOG=4;
	static public final int OVERVIEW_JDIALOG=5;
	static public final int HELP=6;
	
	ResourceBundle resourceMainFrame;
	

	ResourceBundle resourceEditNoteJDialog;
	ResourceBundle resourceEditPersonJDialog;
	ResourceBundle resourceOptionJDialog;
	ResourceBundle resourceOverviewJDialog;
	
	ResourceBundle resourceHelp;
	


	public Translator(){
		this.loadLocale(Locale.getDefault());
	}  
	
	public void loadLocale(Locale loc){
		this.loadLocale(loc.getLanguage(),loc.getCountry());
	}
	
	public void loadLocale(String lang, String country){		
		Locale loc = new Locale(lang, country);
		Locale.setDefault(loc);
		String path = this.getResourceLocation();
		this.loadLocale(loc, path);
	}
	
	public String getResourceLocation(){
		Locale loc = Locale.getDefault();
		String path = "lang." + loc.getLanguage() + "_"  + loc.getCountry() + ".";
		return path;
	}
	
	public void loadLocale(Locale loc, String path){
		 try{
		Locale.setDefault(loc);
		ResourceBundleControl control = new ResourceBundleControl();
		resourceMainFrame = ResourceBundle.getBundle(path + "MainFrame",loc,control);
		resourceEditNoteJDialog = ResourceBundle.getBundle(path + "EditNoteJDialog",loc,control);
		resourceEditPersonJDialog = ResourceBundle.getBundle(path + "EditPersonJDialog", loc,control);
		resourceOptionJDialog = ResourceBundle.getBundle(path + "OptionJDialog",loc,control);
		resourceOverviewJDialog = ResourceBundle.getBundle(path + "OverviewJDialog", loc,control);
		resourceHelp = ResourceBundle.getBundle(path + "Help", loc,control);
		
		 }catch(MissingResourceException e){
			 System.out.println("Resource not found");
			 System.out.println("Path: " + path);
			 System.out.println("Switch to default Locale");
			 this.loadLocale("en","US");
		 }
	}
	
	public String getHelpPath(){
		Locale loc = Locale.getDefault();
		String path = this.getResourceLocation()+ "Help_" + loc.getLanguage() + "_" + loc.getCountry();
		return resolveName(path) +".html";
	}
	
	public String getAboutPath(){
		Locale loc = Locale.getDefault();
		String path = this.getResourceLocation()+ "About_" + loc.getLanguage() + "_" + loc.getCountry() ;
		return resolveName(path)+".html";
	}
	
	private String resolveName(String path){
		return path.replaceAll("\\.", "/");
	}
	
	public ResourceBundle getResourceMainFrame() {
		return resourceMainFrame;
	}

	public ResourceBundle getResourceEditNoteJDialog() {
		return resourceEditNoteJDialog;
	}

	public ResourceBundle getResourceEditPersonJDialog() {
		return resourceEditPersonJDialog;
	}

	public ResourceBundle getResourceOptionJDialog() {
		return resourceOptionJDialog;
	}

	public ResourceBundle getResourceOverviewJDialog() {
		return resourceOverviewJDialog;
	}
	
	public ResourceBundle getResourceHelp() {
		return resourceHelp;
	}

	public void setResourceHelp(ResourceBundle resourceHelp) {
		this.resourceHelp = resourceHelp;
	}
	
	public String getTranslation(String key, int source){
		String path = ""; //for error handling
		try{
			
			switch(source){
			case MAIN_FRAME:
				path = "MAIN_FRAME";
				return this.getResourceMainFrame().getString(key);
			case EDIT_NOTE_JDIALOG:
				path = "EDIT_NOTE_JDIALOG";
				return this.getResourceEditNoteJDialog().getString(key);
			case EDIT_PERSON_JDIALOG:
				path = "EDIT_PERSON_JDIALOG";
				return this.getResourceEditPersonJDialog().getString(key);
			case OPTION_JDIALOG:
				path = "OPTION_JDIALOG";
				return this.getResourceOptionJDialog().getString(key);
			case OVERVIEW_JDIALOG:
				path = "OVERVIEW_JDIALOG";
				return this.getResourceOverviewJDialog().getString(key);
			case HELP:
				path = "HELP";
				return this.getResourceHelp().getString(key);
			default:
				return "err: missing String";
			}
		}catch(MissingResourceException e){
			System.out.println("Konnte die Resource " + key + " in " + path +" nicht finden!");
			return "err: missing String";
		}
	}
	
	

}
