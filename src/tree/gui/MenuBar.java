package tree.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.Config;
import main.Main;
import main.OptionList;
import translator.Translator;
import translator.Translator.LanguageFile;
import tree.gui.draw.DrawImage;
import tree.gui.draw.backgrounds.BackgroundFactory;
import tree.gui.draw.backgrounds.DrawBackgroundImage;
import tree.gui.draw.backgrounds.InvalidDrawImageException;
import tree.gui.search.DefaultFilter;
import tree.gui.search.NoteOverview;
import tree.gui.search.PersonOverview;
import tree.gui.search.line.factory.EditLineFactory;
import tree.gui.util.ImageLoaderDialog;
import tree.gui.window.EditNoteDialog;
import tree.gui.window.EditPersonDialog;
import tree.gui.window.Help;
import tree.gui.window.OptionDialog;
import tree.model.AgeException;
import tree.model.ComponentPrinter;
import tree.model.ForceXPosition;
import tree.model.LineageException;
import tree.model.MainNode;
import tree.model.Person;
import tree.model.TreeIO;
import tree.model.Utils;

public class MenuBar extends JMenuBar{
	
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
				DrawImage draw = new DrawImage(1024,800);
				ImageLoaderDialog dialog = new ImageLoaderDialog(draw);
				dialog.actionPerformed(e);
				draw = dialog.getDraw();
				try{
				DrawBackgroundImage drawBackground = new DrawBackgroundImage(draw);
				Main.getMainFrame().getCanvas().setBackgroundImage(drawBackground);
				Main.getMainFrame().getCanvas().repaint();
				}catch(InvalidDrawImageException exception){
					//do nothing no new image is loaded
				}
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
				String mode = e.getActionCommand();
				String filePath = saveFileDialog();
				if(filePath!=null){
					TreeIO saver = new TreeIO();
					try {
						if(!saver.writeImageAs(filePath,mode, BackgroundFactory.getBufferedBackground(), false)){
							int override = javax.swing.JOptionPane.
									showConfirmDialog(null, Main.getTranslator().getTranslation("fileExists", LanguageFile.MAIN_FRAME));
							if(override == javax.swing.JOptionPane.YES_OPTION){
								saver.writeImageAs(filePath,mode, BackgroundFactory.getBufferedBackground(), true);
							}
						}
					} catch (IOException a) {
						javax.swing.JOptionPane.
						showMessageDialog(null,a.getMessage());
					}
				}
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
				String filePath = saveFileDialog();
				if(filePath!=null){
					if(!filePath.endsWith(".sbt")){
							String[] split = filePath.split("\\.");
							if(split.length>1){
								filePath = split[0] + ".sbt";
							}
							else{
								filePath += ".sbt";
							}
					}
					TreeIO saver = new TreeIO();
					try {
						if(!saver.writeTree(Main.getMainNode(), filePath)){
							int override = javax.swing.JOptionPane.
									showConfirmDialog(null, Main.getTranslator().getTranslation("fileExists", LanguageFile.MAIN_FRAME));
							if(override == javax.swing.JOptionPane.YES_OPTION){
								saver.writeTree(Main.getMainNode(), filePath,true);
							}
						}
					} catch (IOException e) {
						javax.swing.JOptionPane.
						showMessageDialog(null,e.getMessage());
					}
				}
				
			}
		},
		/**
		 * opens a Dialog to load a tree from a file
		 */
		LOAD_TREE(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
				int val = chooser.showOpenDialog(chooser);
				if(val == JFileChooser.APPROVE_OPTION){
					try{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					Config.LAST_PATH = chooser.getSelectedFile().getParent();
					TreeIO loader = new TreeIO();
					MainNode node = loader.loadTree(filePath);
					Main.setMainNode(node);
					Main.getMainFrame().revalidateTree();
					}catch(IOException e){
						javax.swing.JOptionPane.
						showMessageDialog(null,e.getMessage());
					}
				}
			}
		},
		/**
		 * opens a Dialog to import a tree from a GEDCOM5 file
		 */
		IMPORT_TREE_FROM_GEDCOM5(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
				int val = chooser.showOpenDialog(chooser);
				if(val == JFileChooser.APPROVE_OPTION){
					try{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					Config.LAST_PATH = chooser.getSelectedFile().getParent();
					TreeIO loader = new TreeIO();
					List<Person> persons = loader.loadGEDCOM5(filePath);
				
					MainNode node = new MainNode(persons.get(0));
					node.addAll(persons);
					Utils.determineTreeGenerations(persons.get(0));
					Utils.basicXPositioning(persons.get(0));
					Main.setMainNode(node);
					Main.getMainFrame().revalidateTree();
					}catch(IOException | AgeException | LineageException e){
						javax.swing.JOptionPane.
						showMessageDialog(null,e.getMessage());
					}
				}
			}
		},
		/**
		 * save the tree as an image (png or jpeg)
		 */
		EXPORT_AS_IMAGE(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String mode = arg0.getActionCommand();
				String filePath = saveFileDialog();
				if(filePath!=null){
					TreeIO saver = new TreeIO();
					try {
						if(!saver.writeImageAs(filePath,mode, Main.getMainFrame().getCanvas(), false)){
							int override = javax.swing.JOptionPane.
									showConfirmDialog(null, Main.getTranslator().getTranslation("fileExists", LanguageFile.MAIN_FRAME));
							if(override == javax.swing.JOptionPane.YES_OPTION){
								saver.writeImageAs(filePath,mode, Main.getMainFrame().getCanvas(), true);
							}
						}
					} catch (IOException e) {
						javax.swing.JOptionPane.
						showMessageDialog(null,e.getMessage());
					}
				}
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
		/**
		 * 
		 * @return the path to the file which should be saved
		 */
		static private String saveFileDialog(){
			JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int val = chooser.showSaveDialog(chooser);
			if(val == JFileChooser.APPROVE_OPTION){
				String filePath = chooser.getSelectedFile().getAbsolutePath();
				Config.LAST_PATH = chooser.getSelectedFile().getParent();
				
				return filePath;
			}
			return null;
		}
		
	}

	/**
	 * this unique id is used for serialization of the MenuBar
	 */
	private static final long serialVersionUID = 5029513380745844369L;
	/**
	 * this JCheckBox is a class variable since it is needed by the DRAW_X_Y_POSITION to actualize its selection state
	 */
	private JCheckBox drawX;
	
	
	
	/**
	 * generates the MenuBar for this program
	 */
	public MenuBar(){
		JMenu file = new JMenu(Main.getTranslator().getTranslation("file",Translator.LanguageFile.MAIN_FRAME));
		JMenu help = new JMenu(Main.getTranslator().getTranslation("help",Translator.LanguageFile.MAIN_FRAME));
		JMenu language = new JMenu(Main.getTranslator().getTranslation("language",Translator.LanguageFile.MAIN_FRAME));
		JMenu extras = new JMenu(Main.getTranslator().getTranslation("extra", Translator.LanguageFile.MAIN_FRAME));
		//create new Person
		JMenu neu = new JMenu(Main.getTranslator().getTranslation("person",Translator.LanguageFile.MAIN_FRAME));
		
		JMenuItem newPerson = new JMenuItem(Main.getTranslator().getTranslation("newPerson",Translator.LanguageFile.MAIN_FRAME));
		
		neu.add(newPerson);
		this.add(file);
		this.add(neu);
		this.add(language);
		this.add(extras);
		this.add(help);
		
		newPerson.addActionListener(MenuBarListener.ADD_PERSON);
		
		//add Note
		JMenuItem addNote = new JMenuItem(Main.getTranslator().getTranslation("newNote",Translator.LanguageFile.MAIN_FRAME));
		addNote.addActionListener(MenuBarListener.ADD_NOTE);
		neu.add(addNote);
		
		
		
		//save Tree
		JMenuItem saveTree = new JMenuItem(Main.getTranslator().getTranslation("saveTree",Translator.LanguageFile.MAIN_FRAME));
		saveTree.addActionListener(MenuBarListener.SAVE_TREE);
		file.add(saveTree);
		
		//load Tree
		JMenuItem loadTree = new JMenuItem(Main.getTranslator().getTranslation("loadTree",Translator.LanguageFile.MAIN_FRAME));
		loadTree.addActionListener(MenuBarListener.LOAD_TREE);
		file.add(loadTree);
		
		//import Tree from GEDCOM5
		JMenuItem importTreeGEDCOM = new JMenuItem(Main.getTranslator().getTranslation("importTreeGedcom", Translator.LanguageFile.MAIN_FRAME));
		importTreeGEDCOM.addActionListener(MenuBarListener.IMPORT_TREE_FROM_GEDCOM5);
		file.add(importTreeGEDCOM);
		
		//jpeg exportieren
		JMenu exportAs = new JMenu(Main.getTranslator().getTranslation("exportAs",Translator.LanguageFile.MAIN_FRAME));
		JMenuItem exportAsJPEG = new JMenuItem("jpg");
		exportAsJPEG.addActionListener(MenuBarListener.EXPORT_AS_JPEG);
		exportAs.add(exportAsJPEG);
		
		JMenuItem exportAsPNG = new JMenuItem("png");
		exportAsPNG.addActionListener(MenuBarListener.EXPORT_AS_PNG);
		exportAs.add(exportAsPNG);
		
		file.add(exportAs);
	
		
		JMenuItem overview = new JMenuItem(Main.getTranslator().getTranslation("personOverview",Translator.LanguageFile.MAIN_FRAME));
		overview.addActionListener(MenuBarListener.PERSON_OVERVIEW);
		neu.add(overview);
		
		
		//background picture loading
		
		JMenuItem loadBackground = new JMenuItem(Main.getTranslator().getTranslation("loadBackground",Translator.LanguageFile.MAIN_FRAME));
		loadBackground.addActionListener(MenuBarListener.LOAD_BACKGROUND_PICTURE);
		file.add(loadBackground);
		
		//background picture export
		
		JMenu exportBackground = new JMenu(Main.getTranslator().getTranslation("exportBackground", Translator.LanguageFile.MAIN_FRAME));
		JMenuItem exportBackgroundAsJPEG = new JMenuItem("jpg");
		exportBackgroundAsJPEG.addActionListener(MenuBarListener.EXPORT_BACKGROUND_AS_JPEG);
		exportBackground.add(exportBackgroundAsJPEG);
		
		JMenuItem exportBackgroundAsPNG = new JMenuItem("png");
		exportBackgroundAsPNG.addActionListener(MenuBarListener.EXPORT_BACKGROUND_AS_PNG);
		exportBackground.add(exportBackgroundAsPNG);
		
		BackgroundFactory.registerMenuItem(exportBackground);
		
		file.add(exportBackground);
		
		//print
		
		JMenuItem print = new JMenuItem(Main.getTranslator().getTranslation("print",Translator.LanguageFile.MAIN_FRAME));
		print.addActionListener(MenuBarListener.PRINT_TREE);
		file.add(print);
		
		
		
		//note overview
		JMenuItem noteOverview = new JMenuItem(Main.getTranslator().getTranslation("noteOverview",Translator.LanguageFile.MAIN_FRAME));
		noteOverview.addActionListener(MenuBarListener.NOTE_OVERVIEW);
		neu.add(noteOverview);
		
		drawX = new JCheckBox(Main.getTranslator().getTranslation("coords",Translator.LanguageFile.MAIN_FRAME));
		drawX.addActionListener(MenuBarListener.DRAW_X_Y_POSITION);
		neu.add(drawX);
		
		JMenuItem helpEntry = new JMenuItem(Main.getTranslator().getTranslation("help",Translator.LanguageFile.MAIN_FRAME));
		helpEntry.addActionListener(MenuBarListener.SHOW_HELP);
		help.add(helpEntry);
		
		
		JMenuItem impressum = new JMenuItem(Main.getTranslator().getTranslation("about",Translator.LanguageFile.MAIN_FRAME));
		impressum.addActionListener(MenuBarListener.SHOW_IMPRESSUM);
		help.add(impressum);
		
		//languages
		
		JMenuItem german = new JMenuItem(Main.getTranslator().getTranslation("german",Translator.LanguageFile.MAIN_FRAME));
		german.addActionListener(MenuBarListener.LANG_GERMAN);
		language.add(german);
		
		JMenuItem english = new JMenuItem(Main.getTranslator().getTranslation("english",Translator.LanguageFile.MAIN_FRAME));
		english.addActionListener(MenuBarListener.LANG_ENGLISH);
		language.add(english);
		
		JMenuItem russian = new JMenuItem(Main.getTranslator().getTranslation("russian",Translator.LanguageFile.MAIN_FRAME));
		russian.addActionListener(MenuBarListener.LANG_RUSSIAN);
		language.add(russian);
		
		//extras
		//options
		
		JMenuItem options = new JMenuItem(Main.getTranslator().getTranslation("options",Translator.LanguageFile.MAIN_FRAME));
		options.addActionListener(MenuBarListener.OPEN_OPTIONS);
		extras.add(options);
		
		//refresh background
		JMenuItem redrawBackground = new JMenuItem(Main.getTranslator().getTranslation("redraw", Translator.LanguageFile.MAIN_FRAME));
		redrawBackground.addActionListener(MenuBarListener.REDRAW_BACKGROUND);
		
		extras.add(redrawBackground);
		
		//recalculate y position
		JMenuItem recalculateY = new JMenuItem(Main.getTranslator().getTranslation("calculatey",Translator.LanguageFile.MAIN_FRAME));
		recalculateY.addActionListener(MenuBarListener.CALCULATE_Y_POSITION);
		extras.add(recalculateY);
		
		//calculate x position
		JMenuItem calculateX = new JMenuItem(Main.getTranslator().getTranslation("calculatex",Translator.LanguageFile.MAIN_FRAME));
		calculateX.addActionListener(MenuBarListener.CALCULATE_X_POSITION);
		extras.add(calculateX);
		
		//invert ordering
		JMenuItem invertOrdering = new JMenuItem(Main.getTranslator().getTranslation("invertOrdering", Translator.LanguageFile.MAIN_FRAME));
		invertOrdering.addActionListener(MenuBarListener.INVERT_ORDERING);
		extras.add(invertOrdering);
		
		
		
	}
	
	protected JCheckBox getDrawX(){
		return this.drawX;
	}

}
