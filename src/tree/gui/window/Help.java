package tree.gui.window;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import tree.gui.util.GUIUtils;



public class Help extends JDialog{


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
	private void assignIcon()
	{
		GUIUtils.assignIcon(this);
	}
	
	/**
	 * reads the html-help or the about us file and show it in the help window.
	 * @param boolean helpOrImp decides whether to show the help file or the about file true=help false=aboutUs
	 */
	public void showHelp(final boolean helpOrImp) {
		JScrollPane helpContentPane = new JScrollPane();
		setTitle(helpOrImp?"Hilfe":"Impressum");
		String path = helpOrImp?"./data/help/hilfe.html":"./data/help/impressum.html";
		
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
									javax.swing.JOptionPane.showMessageDialog(null,"Browser konnte nicht gefunden werden.");
								}
							}
						} else {
							theHelpText.setPage(e.getURL());

						}
					} catch (IOException e1) {
						javax.swing.JOptionPane.showMessageDialog(null,"Die " + (helpOrImp?"Hilfedatei":"Impressumsdatei") + " konnte nicht gefunden werden.");
					}
				}
			}
		});

		try {
			File helpHTML = new File(path);
			theHelpText.setPage(helpHTML.toURI().toURL());
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(null,"Die " + (helpOrImp?"Hilfedatei":"Impressumsdatei") + " konnte nicht gefunden werden.");
		}

		GridLayout grid = new GridLayout(1, 1);
		undergroundPanel.setLayout(grid);

		helpContentPane.setViewportView(theHelpText);
		undergroundPanel.add(helpContentPane);
		add(undergroundPanel);

		setVisible(true);
		
	}

}
