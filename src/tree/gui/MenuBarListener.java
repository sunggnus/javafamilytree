package tree.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import javax.swing.AbstractAction;

import main.Main;
import main.OptionList;
import tree.gui.draw.backgrounds.BackgroundFactory;
import tree.gui.search.DefaultFilter;
import tree.gui.search.NoteOverview;
import tree.gui.search.PersonOverview;
import tree.gui.search.line.factory.EditLineFactory;
import tree.gui.window.EditNoteDialog;
import tree.gui.window.EditPersonDialog;
import tree.gui.window.Help;
import tree.gui.window.OptionDialog;
import tree.model.ComponentPrinter;
import tree.model.ForceXPosition;
import tree.model.Utils;

public enum MenuBarListener implements ActionListener{
	/**
	 * opens a window with the help to this program
	 */
	SHOW_HELP(){
		@Override
		public void actionPerformed(ActionEvent e){
			Help help = new Help();
			help.showHelp(true);
		}
	},
	/**
	 * opens a window with a message about the program authors and licenses 
	 */
	SHOW_IMPRESSUM(){
		@Override
		public void actionPerformed(ActionEvent e){
			Help help = new Help();
			help.showHelp(false);
		}
	},
	LANG_GERMAN(){
		@Override
		public void actionPerformed(ActionEvent e){
			loadLanguage("de", "DE");
		}
	},
	LANG_ENGLISH(){
		@Override
		public void actionPerformed(ActionEvent e){
			loadLanguage("en", "US");
		}
	},
	LANG_RUSSIAN(){
		@Override
		public void actionPerformed(ActionEvent e){
			loadLanguage("ru", "RU");
		}
	},
	REDRAW_BACKGROUND(){
		@Override
		public void actionPerformed(ActionEvent e){
			BackgroundFactory.deleteSavedBackground();
		}
	},
	/**
	 * prints the whole tree on multiple pages
	 */
	PRINT_TREE(){
		@Override
		public void actionPerformed(ActionEvent e){
			try {
				ComponentPrinter.doPrint(Main.getMainFrame().getCanvas());
			} catch (PrinterException e1) {
				javax.swing.JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	},
	/**
	 * loads an image and mirrors it on the x and y axis then 
	 * draw it on the background of the tree multiplying it 
	 * so it fills out the whole area
	 */
	LOAD_BACKGROUND_PICTURE(){
		@Override
		public void actionPerformed(ActionEvent e){
			SaveLoadActions.loadBackgroundPicture(e);
		}
	},
	/**
	 * save the background as a jpeg file
	 */
	EXPORT_BACKGROUND_AS_JPEG(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			EXPORT_BACKGROUND_PICTURE.actionPerformed(new ActionEvent(arg0.getSource(), 
					arg0.getID(), "jpeg"));
			
		}
	},
	/**
	 * save the background as a png file
	 */
	EXPORT_BACKGROUND_AS_PNG(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			EXPORT_BACKGROUND_PICTURE.actionPerformed(new ActionEvent(arg0.getSource(), 
					arg0.getID(), "png"));
			
		}
	},
	/**
	 * exports the background picture
	 */
	EXPORT_BACKGROUND_PICTURE(){
		@Override
		public  void actionPerformed(ActionEvent e){
			SaveLoadActions.exportBackgroundPicture(e);
		}
	},
	/**
	 * opens a window to add a new person to the tree
	 */
	ADD_PERSON(){
		@Override
		public void actionPerformed(ActionEvent e){
			new EditPersonDialog(true);
		}
	},
	/**
	 * opens a window to add a new note to the tree
	 */
	ADD_NOTE(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new EditNoteDialog(null);
			
		}
	},
	/**
	 * opens a window to change the configuration settings
	 */
	OPEN_OPTIONS(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
		new OptionDialog();
		}
	},
	/**
	 * opens a Dialog to save the tree to a file 
	 */
	SAVE_TREE(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveLoadActions.saveTree();
			
		}
	},
	/**
	 * opens a Dialog to load a tree from a file
	 */
	LOAD_TREE(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveLoadActions.loadTree();
		}
	},
	/**
	 * opens a Dialog to import a tree from a GEDCOM5 file
	 */
	IMPORT_TREE_FROM_GEDCOM5(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveLoadActions.importTreeFromGedcom();
		}
	},
	/**
	 * save the tree as an image (png or jpeg)
	 */
	EXPORT_AS_IMAGE(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveLoadActions.exportAsImage(arg0);
		}
	},
	/**
	 * save the tree as a jpeg file
	 */
	EXPORT_AS_JPEG(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			EXPORT_AS_IMAGE.actionPerformed(new ActionEvent(arg0.getSource(), 
					arg0.getID(), "jpeg"));
			
		}
	},
	/**
	 * save the tree as a png file
	 */
	EXPORT_AS_PNG(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			EXPORT_AS_IMAGE.actionPerformed(new ActionEvent(arg0.getSource(), 
					arg0.getID(), "png"));
			
		}
	},
	/**
	 * opens an overview window with every note
	 * it is possible to search for certain notes there, to open an edit frame for each note
	 * and to delete them
	 */
	NOTE_OVERVIEW(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new NoteOverview(Main.getMainNode().getNotesReference(), new DefaultFilter(),
			new EditLineFactory());
		}
	},
	/**
	 * opens an overview window with every person
	 * it is possible to search for certain persons there, to open an edit frame for each person
	 * and to delete them
	 */
	PERSON_OVERVIEW(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new PersonOverview(Main.getMainNode().getPersonsReference(), new DefaultFilter(),
			new EditLineFactory(),true);
		}
	},
	/**
	 * change the draw x-y-position option if true the grid net x and y position of each person
	 * is written within the person frame on the tree so it is easier to edit the tree
	 */
	DRAW_X_Y_POSITION(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Main.getMainFrame().getCanvas().changeDrawXYPositon();
			boolean value = Main.getMainFrame().getCanvas().isDrawXYPosition();
			if(Main.getMainFrame().getJMenuBar() instanceof MenuBar){
				MenuBar bar = (MenuBar) Main.getMainFrame().getJMenuBar();
				bar.getDrawX().setSelected(value);
			}
			Main.getMainFrame().getCanvas().repaint();
		}
		
	},
	CALCULATE_Y_POSITION(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Utils.determineTreeGenerations(Main.getMainNode().getPerson());				
			Main.getMainFrame().getCanvas().repaint();
		}
	},
	CALCULATE_X_POSITION(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ForceXPosition.forceXPositioning();		
			Main.getMainFrame().getCanvas().repaint();
		}
	}
	,
	INVERT_ORDERING(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Utils.changeOrderingMode(Main.getMainNode().getPerson());				
			Main.getMainFrame().getCanvas().repaint();
		}
	}
	;
	
	static private void loadLanguage(String lang, String country){
		Main.getTranslator().loadLocale(lang, country);
		Main.getMainFrame().changeLanguage();
		OptionList.translate();
	}
	
	public AbstractAction getAction(){
		return new ActionProvider(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {						
	}
	
}
