package translator;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * the Translator is responsible for the localization of the ui
 * 
 * @author sunggnus
 *
 */
public final class Translator {

	/**
	 * the used {@link ResourceBundle.Control} it reads UTF-8 formated property
	 * files see {@link ResourceBundleControl}
	 */
	static private final ResourceBundleControl CONTROL = new ResourceBundleControl();

	public enum LanguageFile {
		MAIN_FRAME("MainFrame"), EDIT_NOTE_DIALOG("EditNoteJDialog"), EDIT_PERSON_DIALOG(
				"EditPersonJDialog"), OPTION_DIALOG("OptionJDialog"), OVERVIEW_DIALOG(
				"OverviewJDialog"), HELP("Help");

		/**
		 * the file name of the language file without any language information
		 */
		private String fileName;
		/**
		 * the {@link ResourceBundle} of the LanguageFile
		 */
		private ResourceBundle resourceBundle;

		/**
		 * creates a new LanguageFile
		 * 
		 * @param {@link #fileName} of the LanguageFile
		 */
		private LanguageFile(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * returns the translation to a given key
		 * 
		 * @param key
		 *            the key which should be translated
		 * @return
		 */
		public String getTranslation(String key) {
			try {
				return this.resourceBundle.getString(key);
			} catch (MissingResourceException e) {
				System.out.println("Resource not found " + key + " in "
						+ this.name());
				return "err: missing String: " + key;
			}
		}

		/**
		 * loads new localization data like e.g. language data
		 * 
		 * @param loc
		 *            the {@link Locale} which will be loaded
		 * @param path
		 *            the path to the language data directory
		 * @throws MissingResourceException
		 *             if no localization file was found
		 */
		public void loadLocale(Locale loc, String path)
				throws MissingResourceException {
			resourceBundle = ResourceBundle.getBundle(path + fileName, loc,
					CONTROL);
		}

	}

	public Translator() {
		this.loadLocale(Locale.getDefault());
	}

	/**
	 * loads new localization data like e.g. language data
	 * 
	 * @param loc
	 *            the {@link Locale} which will be loaded
	 */
	public void loadLocale(Locale loc) {
		this.loadLocale(loc.getLanguage(), loc.getCountry());
	}

	/**
	 * loads new localization data like e.g. language data
	 * 
	 * @param lang
	 *            the language String of the {@link Locale} e.g. de
	 * @param country
	 *            the country String of the {@link Locale} e.g. DE
	 */
	public void loadLocale(String lang, String country) {
		Locale loc = new Locale(lang, country);
		Locale.setDefault(loc);
		String path = this.getResourceLocation();
		this.loadLocale(loc, path);
	}

	/**
	 * constructs the path to the directory of the current used {@link Locale}
	 * 
	 * @return the directory path
	 */
	public String getResourceLocation() {
		Locale loc = Locale.getDefault();
		String path = "lang." + loc.getLanguage() + "_" + loc.getCountry()
				+ ".";
		return path;
	}

	/**
	 * loads new localization data like e.g. language data this method is
	 * usually only called internally
	 * 
	 * @param loc
	 *            the {@link Locale} which will be loaded
	 * @param path
	 *            the path to the directory where it will be searched for
	 *            localization data
	 */
	public void loadLocale(Locale loc, String path) {
		try {
			Locale.setDefault(loc);
			for (LanguageFile file : LanguageFile.values()) {
				file.loadLocale(loc, path);
			}

		} catch (MissingResourceException e) {
			System.out.println("Resource not found");
			System.out.println("Path: " + path);
			System.out.println("Switch to default Locale");
			javax.swing.JOptionPane.showMessageDialog(null,
					"Resource not found, switch to English!");
			this.loadLocale("en", "US");
		}
	}

	/**
	 * retrieves the path to the help file
	 * 
	 * @return the help file path
	 */
	public String getHelpPath() {
		Locale loc = Locale.getDefault();
		String path = this.getResourceLocation() + "Help_" + loc.getLanguage()
				+ "_" + loc.getCountry();
		return resolveName(path) + ".html";
	}

	/**
	 * retrieves the path to the help file
	 * 
	 * @return the about file path
	 */
	public String getAboutPath() {
		Locale loc = Locale.getDefault();
		String path = this.getResourceLocation() + "About_" + loc.getLanguage()
				+ "_" + loc.getCountry();
		return resolveName(path) + ".html";
	}

	/**
	 * replaces dots with slashes
	 * 
	 * @param path
	 *            the string where the symbols should be replaced
	 * @return the resolved path
	 */
	private String resolveName(String path) {
		return path.replaceAll("\\.", "/");
	}

	/**
	 * retrieves the translation to a given key and a given {@link LanguageFile}
	 * 
	 * @param key
	 *            the key for the translation
	 * @param source
	 *            the corresponding {@link LanguageFile}
	 * @return
	 */
	public String getTranslation(String key, LanguageFile source) {
		return source.getTranslation(key);
	}

}
