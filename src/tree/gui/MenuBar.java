package tree.gui;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.Main;
import translator.Translator;
import tree.gui.draw.backgrounds.BackgroundFactory;

public class MenuBar extends JMenuBar {

	/**
	 * this unique id is used for serialization of the MenuBar
	 */
	private static final long serialVersionUID = 5029513380745844369L;
	/**
	 * this JCheckBox is a class variable since it is needed by the
	 * DRAW_X_Y_POSITION to actualize its selection state
	 */
	private JCheckBox drawX;

	/**
	 * generates the MenuBar for this program
	 */
	public MenuBar() {
		JMenu file = new JMenu(Main.getTranslator().getTranslation("file",
				Translator.LanguageFile.MAIN_FRAME));
		JMenu help = new JMenu(Main.getTranslator().getTranslation("help",
				Translator.LanguageFile.MAIN_FRAME));
		JMenu language = new JMenu(Main.getTranslator().getTranslation(
				"language", Translator.LanguageFile.MAIN_FRAME));
		JMenu extras = new JMenu(Main.getTranslator().getTranslation("extra",
				Translator.LanguageFile.MAIN_FRAME));
		// create new Person
		JMenu neu = new JMenu(Main.getTranslator().getTranslation("person",
				Translator.LanguageFile.MAIN_FRAME));

		JMenuItem newPerson = new JMenuItem(
				Main.getTranslator().getTranslation("newPerson",
						Translator.LanguageFile.MAIN_FRAME));

		neu.add(newPerson);
		this.add(file);
		this.add(neu);
		this.add(language);
		this.add(extras);
		this.add(help);

		newPerson.addActionListener(MenuBarListener.ADD_PERSON);

		// add Note
		JMenuItem addNote = new JMenuItem(Main.getTranslator().getTranslation(
				"newNote", Translator.LanguageFile.MAIN_FRAME));
		addNote.addActionListener(MenuBarListener.ADD_NOTE);
		neu.add(addNote);

		// save Tree
		JMenuItem saveTree = new JMenuItem(Main.getTranslator().getTranslation(
				"saveTree", Translator.LanguageFile.MAIN_FRAME));
		saveTree.addActionListener(MenuBarListener.SAVE_TREE);
		file.add(saveTree);

		// load Tree
		JMenuItem loadTree = new JMenuItem(Main.getTranslator().getTranslation(
				"loadTree", Translator.LanguageFile.MAIN_FRAME));
		loadTree.addActionListener(MenuBarListener.LOAD_TREE);
		file.add(loadTree);

		// import Tree from GEDCOM5
		JMenuItem importTreeGEDCOM = new JMenuItem(Main.getTranslator()
				.getTranslation("importTreeGedcom",
						Translator.LanguageFile.MAIN_FRAME));
		importTreeGEDCOM
				.addActionListener(MenuBarListener.IMPORT_TREE_FROM_GEDCOM5);
		file.add(importTreeGEDCOM);

		// jpeg exportieren
		JMenu exportAs = new JMenu(Main.getTranslator().getTranslation(
				"exportAs", Translator.LanguageFile.MAIN_FRAME));
		JMenuItem exportAsJPEG = new JMenuItem("jpg");
		exportAsJPEG.addActionListener(MenuBarListener.EXPORT_AS_JPEG);
		exportAs.add(exportAsJPEG);

		JMenuItem exportAsPNG = new JMenuItem("png");
		exportAsPNG.addActionListener(MenuBarListener.EXPORT_AS_PNG);
		exportAs.add(exportAsPNG);

		file.add(exportAs);

		JMenuItem overview = new JMenuItem(Main.getTranslator().getTranslation(
				"personOverview", Translator.LanguageFile.MAIN_FRAME));
		overview.addActionListener(MenuBarListener.PERSON_OVERVIEW);
		neu.add(overview);

		// background picture loading

		JMenuItem loadBackground = new JMenuItem(Main.getTranslator()
				.getTranslation("loadBackground",
						Translator.LanguageFile.MAIN_FRAME));
		loadBackground
				.addActionListener(MenuBarListener.LOAD_BACKGROUND_PICTURE);
		file.add(loadBackground);

		// background picture export

		JMenu exportBackground = new JMenu(Main.getTranslator().getTranslation(
				"exportBackground", Translator.LanguageFile.MAIN_FRAME));
		JMenuItem exportBackgroundAsJPEG = new JMenuItem("jpg");
		exportBackgroundAsJPEG
				.addActionListener(MenuBarListener.EXPORT_BACKGROUND_AS_JPEG);
		exportBackground.add(exportBackgroundAsJPEG);

		JMenuItem exportBackgroundAsPNG = new JMenuItem("png");
		exportBackgroundAsPNG
				.addActionListener(MenuBarListener.EXPORT_BACKGROUND_AS_PNG);
		exportBackground.add(exportBackgroundAsPNG);

		BackgroundFactory.registerMenuItem(exportBackground);

		file.add(exportBackground);

		// print

		JMenuItem print = new JMenuItem(Main.getTranslator().getTranslation(
				"print", Translator.LanguageFile.MAIN_FRAME));
		print.addActionListener(MenuBarListener.PRINT_TREE);
		file.add(print);

		// note overview
		JMenuItem noteOverview = new JMenuItem(Main.getTranslator()
				.getTranslation("noteOverview",
						Translator.LanguageFile.MAIN_FRAME));
		noteOverview.addActionListener(MenuBarListener.NOTE_OVERVIEW);
		neu.add(noteOverview);

		drawX = new JCheckBox(Main.getTranslator().getTranslation("coords",
				Translator.LanguageFile.MAIN_FRAME));
		drawX.addActionListener(MenuBarListener.DRAW_X_Y_POSITION);
		neu.add(drawX);

		JMenuItem helpEntry = new JMenuItem(Main.getTranslator()
				.getTranslation("help", Translator.LanguageFile.MAIN_FRAME));
		helpEntry.addActionListener(MenuBarListener.SHOW_HELP);
		help.add(helpEntry);

		JMenuItem impressum = new JMenuItem(Main.getTranslator()
				.getTranslation("about", Translator.LanguageFile.MAIN_FRAME));
		impressum.addActionListener(MenuBarListener.SHOW_IMPRESSUM);
		help.add(impressum);

		// languages

		JMenuItem german = new JMenuItem(Main.getTranslator().getTranslation(
				"german", Translator.LanguageFile.MAIN_FRAME));
		german.addActionListener(MenuBarListener.LANG_GERMAN);
		language.add(german);

		JMenuItem english = new JMenuItem(Main.getTranslator().getTranslation(
				"english", Translator.LanguageFile.MAIN_FRAME));
		english.addActionListener(MenuBarListener.LANG_ENGLISH);
		language.add(english);

		JMenuItem russian = new JMenuItem(Main.getTranslator().getTranslation(
				"russian", Translator.LanguageFile.MAIN_FRAME));
		russian.addActionListener(MenuBarListener.LANG_RUSSIAN);
		language.add(russian);

		// extras
		// options

		JMenuItem options = new JMenuItem(Main.getTranslator().getTranslation(
				"options", Translator.LanguageFile.MAIN_FRAME));
		options.addActionListener(MenuBarListener.OPEN_OPTIONS);
		extras.add(options);

		// refresh background
		JMenuItem redrawBackground = new JMenuItem(Main.getTranslator()
				.getTranslation("redraw", Translator.LanguageFile.MAIN_FRAME));
		redrawBackground.addActionListener(MenuBarListener.REDRAW_BACKGROUND);

		extras.add(redrawBackground);

		// recalculate y position
		JMenuItem recalculateY = new JMenuItem(Main.getTranslator()
				.getTranslation("calculatey",
						Translator.LanguageFile.MAIN_FRAME));
		recalculateY.addActionListener(MenuBarListener.CALCULATE_Y_POSITION);
		extras.add(recalculateY);

		// calculate x position
		JMenuItem calculateX = new JMenuItem(Main.getTranslator()
				.getTranslation("calculatex",
						Translator.LanguageFile.MAIN_FRAME));
		calculateX.addActionListener(MenuBarListener.CALCULATE_X_POSITION);
		extras.add(calculateX);

		// invert ordering
		JMenuItem invertOrdering = new JMenuItem(Main.getTranslator()
				.getTranslation("invertOrdering",
						Translator.LanguageFile.MAIN_FRAME));
		invertOrdering.addActionListener(MenuBarListener.INVERT_ORDERING);
		extras.add(invertOrdering);

	}

	protected JCheckBox getDrawX() {
		return this.drawX;
	}

}
