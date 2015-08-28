package tree.gui.window;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.Main;

import translator.Translator;
import tree.gui.util.GUIUtils;

public class Help extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6698465628901768206L;

	/**
	 * constructor for help window
	 */
	public Help() {
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(640, 500);
		this.assignIcon();

	}

	/**
	 * loads the icon
	 */
	private void assignIcon() {
		GUIUtils.assignIcon(this);
	}

	/**
	 * reads the html-help or the about us file and show it in the help window.
	 * 
	 * @param boolean helpOrImp decides whether to show the help file or the
	 *        about file true=help false=aboutUs
	 */
	public void showHelp(final boolean helpOrImp) {

		JScrollPane helpContentPane = new JScrollPane();
		setTitle(helpOrImp ? this.getTranslation("help") : this
				.getTranslation("about"));
		String path = helpOrImp ? Main.getTranslator().getHelpPath() : Main
				.getTranslator().getAboutPath();

		JPanel undergroundPanel = new JPanel();

		helpContentPane.setBackground(new Color(0, 100, 100));
		helpContentPane.setAutoscrolls(false);
		helpContentPane.setWheelScrollingEnabled(true);
		final JEditorPane theHelpText = new JEditorPane();
		theHelpText.setEditable(false);

		theHelpText.setPreferredSize(new Dimension(350, 550));
		theHelpText.setBackground(undergroundPanel.getBackground());

		theHelpText.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						// if hyperlink is an external link, open local browser
						if (e.getURL().toString().startsWith("http")) {
							if (Desktop.isDesktopSupported()) {
								try {
									Desktop.getDesktop().browse(
											e.getURL().toURI());
								} catch (URISyntaxException e1) {
									javax.swing.JOptionPane.showMessageDialog(
											null, getTranslation("noBrowser"));
								}
							}
						} else {
							theHelpText.setPage(e.getURL());

						}
					} catch (IOException e1) {
						javax.swing.JOptionPane.showMessageDialog(null,
								(helpOrImp ? getTranslation("noHelp")
										: getTranslation("noAbout")));
					}
				}
			}
		});

		try {
			System.out.println(path);
			URL location = ClassLoader.getSystemResource(path);
			System.out.println(location.toExternalForm());
			theHelpText.setPage(location);
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(null,
					(helpOrImp ? getTranslation("noHelp")
							: getTranslation("noAbout")));
		}

		GridLayout grid = new GridLayout(1, 1);
		undergroundPanel.setLayout(grid);

		helpContentPane.setViewportView(theHelpText);
		undergroundPanel.add(helpContentPane);
		add(undergroundPanel);

		setVisible(true);

	}

	private String getTranslation(String key) {
		return Main.getTranslator().getTranslation(key,
				Translator.LanguageFile.HELP);
	}

}
