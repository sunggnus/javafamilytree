package tree.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Window;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.Config;
import main.Main;

public class GUIUtils {

	static public void assignIcon(Window frame) {
		Image img = frame.getToolkit().getImage(
				System.class.getResource("/graphics/treeIcon.png"));
		MediaTracker mt = new MediaTracker(frame);

		mt.addImage(img, 0);
		frame.setIconImage(img);
	}

	static public void normalizeSize(Component comp) {
		Dimension pref = comp.getPreferredSize();
		Dimension min = comp.getMinimumSize();
		Dimension max = comp.getMaximumSize();
		normalizeSize(min, pref);
		normalizeSize(pref, max);
		comp.setMaximumSize(max);
		comp.setPreferredSize(pref);
		comp.setMinimumSize(min);
	}

	static private void normalizeSize(Dimension min, Dimension pref) {
		if (min.getHeight() > pref.getHeight()) {
			pref.setSize(pref.getWidth(), min.getHeight());
		}
		if (min.getWidth() > pref.getWidth()) {
			pref.setSize(min.getWidth(), pref.getHeight());
		}
		if (pref.getWidth() > Short.MAX_VALUE) {
			pref.setSize(Short.MAX_VALUE, pref.getHeight());
		}
		if (min.getWidth() > Short.MAX_VALUE) {
			min.setSize(Short.MAX_VALUE, min.getHeight());
		}
	}

	static public void loadLookAndFeel() {
		switch (Config.LOOK_AND_FEEL_MODE) {
		case NATIVE_LOOK_AND_FEEL:
			loadLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			break;
		case JAVA_LOOK_AND_FEEL:
			loadLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			break;
		case ADDITIONAL_LOOK_AND_FEEL:
			loadLookAndFeel(Config.ADDITIONAL_LOOK_AND_FEEL);
			break;
		default:
			// do nothing
		}
	}

	static private void loadLookAndFeel(String name) {
		Exception except = null;
		try {
			UIManager.setLookAndFeel(name);

		} catch (ClassNotFoundException e1) {
			except = e1;
		} catch (InstantiationException e1) {
			except = e1;
		} catch (IllegalAccessException e1) {
			except = e1;
		} catch (UnsupportedLookAndFeelException e1) {
			except = e1;
		}

		if (except != null) {
			javax.swing.JOptionPane
					.showMessageDialog(null, except.getMessage());
		} else if (Main.getMainFrame() != null) {
			SwingUtilities.updateComponentTreeUI(Main.getMainFrame());
			Main.getMainFrame().refreshSlider();
		}

	}

}
