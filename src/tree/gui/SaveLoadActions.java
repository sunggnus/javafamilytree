package tree.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

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
import tree.model.TreeIO;
import tree.model.Utils;

public class SaveLoadActions {
	
	/**
	 * 
	 * @return the path to the file which should be saved
	 */
	private static String saveFileDialog(){
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

	public static void loadBackgroundPicture(ActionEvent e){
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
	
	public static void exportBackgroundPicture(ActionEvent e){
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
	
	public static void saveTree(){
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
	
	public static void loadTree(){
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
	
	public static void importTreeFromGedcom(){
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
	
	public static void exportAsImage(ActionEvent arg0){
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
	
}
