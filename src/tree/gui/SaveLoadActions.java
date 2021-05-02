package tree.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Config;
import main.Main;
import translator.Translator.LanguageFile;
import tree.gui.draw.DrawImage;
import tree.gui.draw.backgrounds.BackgroundFactory;
import tree.gui.draw.backgrounds.DrawBackgroundImage;
import tree.gui.draw.backgrounds.InvalidDrawImageException;
import tree.gui.util.ImageLoaderDialog;
import tree.model.AgeException;
import tree.model.LineageException;
import tree.model.MainNode;
import tree.model.Person;
import tree.model.Utils;
import tree.model.io.TreeIO;

public class SaveLoadActions {

	
	private static String addFileEnding(String orgString, String ending){
		int lastDot = orgString.lastIndexOf(".");
		String res = "";
		if(! ending.startsWith(".")){
			ending = "." + ending;
		}
		
		if(lastDot != -1){
			res = orgString.substring(0, lastDot) + ending; 
		}
		else{
			res = orgString + ending;
		}
		return res;
		
	}
	
	/**
	 * 
	 * @return the path to the file which should be saved
	 */
	private static String saveFileDialog(boolean addFilter) {
		JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
		FileFilter sbt = new FileNameExtensionFilter("sbt", 
	            "sbt"); 
			FileFilter xml = new FileNameExtensionFilter("xml", 
	            "xml"); 
		if(addFilter){
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.addChoosableFileFilter(sbt);
			chooser.addChoosableFileFilter(xml);
		}
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int val = chooser.showSaveDialog(chooser);
		if (val == JFileChooser.APPROVE_OPTION) {
			String filePath = chooser.getSelectedFile().getAbsolutePath();
			Config.LAST_PATH = chooser.getSelectedFile().getParent();

			if(chooser.getFileFilter() == sbt && !filePath.endsWith(".sbt")){
				filePath = addFileEnding(filePath, "sbt");
			}
			if(chooser.getFileFilter() == xml && !filePath.endsWith(".xml")){
				filePath = addFileEnding(filePath, "xml");
			}
			

			return filePath;
		}
		return null;
	}

	public static void loadBackgroundPicture(ActionEvent e) {
		DrawImage draw = new DrawImage(1024, 800);
		ImageLoaderDialog dialog = new ImageLoaderDialog(draw);
		dialog.actionPerformed(e);
		draw = dialog.getDraw();
		try {
			DrawBackgroundImage drawBackground = new DrawBackgroundImage(draw);
			Main.getMainFrame().getCanvas().setBackgroundImage(drawBackground);
			Main.getMainFrame().getCanvas().repaint();
		} catch (InvalidDrawImageException exception) {
			// do nothing no new image is loaded
		}
	}

	public static void exportBackgroundPicture(ActionEvent e) {
		String mode = e.getActionCommand();
		String filePath = saveFileDialog(false);
		if (filePath != null) {
			TreeIO saver = new TreeIO();
			try {
				if (!saver.writeImageAs(filePath, mode,
						BackgroundFactory.getBufferedBackground(), false)) {
					int override = javax.swing.JOptionPane.showConfirmDialog(
							null,
							Main.getTranslator().getTranslation("fileExists",
									LanguageFile.MAIN_FRAME));
					if (override == javax.swing.JOptionPane.YES_OPTION) {
						saver.writeImageAs(filePath, mode,
								BackgroundFactory.getBufferedBackground(), true);
					}
				}
			} catch (IOException a) {
				javax.swing.JOptionPane.showMessageDialog(null, a.getMessage());
			}
		}
	}

	public static void saveTree() {
		String filePath = saveFileDialog(true);
		if (filePath != null) {

			TreeIO saver = new TreeIO();
			try {
				if (!saver.writeTree(Main.getMainNode(), filePath)) {
					int override = javax.swing.JOptionPane.showConfirmDialog(
							null,
							Main.getTranslator().getTranslation("fileExists",
									LanguageFile.MAIN_FRAME));
					if (override == javax.swing.JOptionPane.YES_OPTION) {
						saver.writeTree(Main.getMainNode(), filePath, true);
					}
				}
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	public static void loadTree() {
		JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
		FileFilter sbt = new FileNameExtensionFilter("sbt", "sbt"); 
		FileFilter xml = new FileNameExtensionFilter("xml", "xml"); 
		chooser.addChoosableFileFilter(sbt);
		chooser.addChoosableFileFilter(xml);
		int val = chooser.showOpenDialog(chooser);
		if (val == JFileChooser.APPROVE_OPTION) {
			try {
				String filePath = chooser.getSelectedFile().getAbsolutePath();
				Config.LAST_PATH = chooser.getSelectedFile().getParent();
				
				TreeIO loader = new TreeIO();
				MainNode node = loader.loadTree(filePath);
				Main.setMainNode(node);
				Main.getMainFrame().revalidateTree();
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	public static void importTreeFromGedcom() {
		JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
		int val = chooser.showOpenDialog(chooser);
		if (val == JFileChooser.APPROVE_OPTION) {
			try {
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
			} catch (IOException | AgeException | LineageException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	public static void exportAsImage(ActionEvent arg0) {
		String mode = arg0.getActionCommand();
		String filePath = saveFileDialog(false);
		if (filePath != null) {
			TreeIO saver = new TreeIO();
			try {
				if (!saver.writeImageAs(filePath, mode, Main.getMainFrame()
						.getCanvas(), false)) {
					int override = javax.swing.JOptionPane.showConfirmDialog(
							null,
							Main.getTranslator().getTranslation("fileExists",
									LanguageFile.MAIN_FRAME));
					if (override == javax.swing.JOptionPane.YES_OPTION) {
						saver.writeImageAs(filePath, mode, Main.getMainFrame()
								.getCanvas(), true);
					}
				}
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

}
