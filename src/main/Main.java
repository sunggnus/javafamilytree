package main;





import translator.Translator;
import tree.gui.window.MainFrame;
import tree.model.MainNode;

public class Main {
	
	/**
	 * the head of the model part of this program
	 */
	static private MainNode node;
	/**
	 * the head of the graphical user interface of this program
	 */
	static private MainFrame frame;
	/**
	 * is responsible for different translations
	 */
	static private Translator translator;
	/**
	 * initiate the program
	 * @param args not used should be empty
	 */
	static public void main(String[] args){
		Config.readConfigIni();
		translator = new Translator();
		OptionList.translate();
		node = new MainNode(null);
		frame = new MainFrame();
		Config.initiateConfig();
		
		
		
	}
	
	static public MainNode getMainNode(){
		return node;
	}
	
	static public MainFrame getMainFrame(){
		return frame;
	}
	
	
	
	static public Translator getTranslator(){
		return translator;
	}
	
	static public void setMainNode(MainNode node){
		Main.node = node;
	}

}
