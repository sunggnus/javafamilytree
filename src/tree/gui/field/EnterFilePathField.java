package tree.gui.field;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import translator.Translator;
import tree.gui.util.GUIUtils;

import main.Config;
import main.Main;

public class EnterFilePathField extends AbstractField{

	
	private JButton edit;
	private JButton search;
	private JLabel defaultSearch;
	private JTextField text;
	/**
	 * 
	 */
	private static final long serialVersionUID = -145113757419382199L;
	
	public EnterFilePathField(){
		
		defaultSearch = new JLabel(Main.getTranslator().getTranslation("defaultSearch", Translator.OPTION_JDIALOG));
		
		text = new JTextField();
		text.setText(Config.DEFAULT_PATH);
		text.setMinimumSize(new Dimension(AbstractField.EMPTY_TEXT_FIELD_WIDTH,(int)text.getPreferredSize().getHeight()));
		GUIUtils.normalizeSize(text);
		text.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				Config.DEFAULT_PATH = text.getText();
				Config.LAST_PATH = Config.DEFAULT_PATH;
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				this.changedUpdate(arg0);			
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				this.changedUpdate(arg0);
			}
			
		});
		text.setEditable(false);
		
		edit = new JButton(Main.getTranslator().getTranslation("edit", Translator.OPTION_JDIALOG));
		edit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				text.setEditable(!text.isEditable());
				if(text.isEditable()){
					edit.setText(Main.getTranslator().getTranslation("save", Translator.OPTION_JDIALOG));
				}
				else{
					edit.setText(Main.getTranslator().getTranslation("edit", Translator.OPTION_JDIALOG));
				}
			}
			
		});
		
		search = new JButton(Main.getTranslator().getTranslation("search", Translator.OPTION_JDIALOG));
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(Config.LAST_PATH);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int val = chooser.showSaveDialog(chooser);
				if(val == JFileChooser.APPROVE_OPTION){
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					Config.LAST_PATH = filePath;			
					Config.DEFAULT_PATH=filePath;
					text.setText(Config.DEFAULT_PATH);
				}
				
			}
			
		});
	
		
		this.increaseLabelWidth(AbstractField.DEFAULT_LABEL_WIDTH + 100);
		
	}
	
	@Override
	public void increaseLabelWidth(int width){
		int Y_OFFSET=30;
		JPanel firstLine = new JPanel();
		
		firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
		JPanel secondLine = new JPanel();
		secondLine.setLayout(new BoxLayout(secondLine, BoxLayout.X_AXIS));
		int pos = 5;
		firstLine.add(Box.createHorizontalStrut(pos));
		firstLine.add(defaultSearch);
		firstLine.add(Box.createGlue());
		
		JButton toDetermineMinEdit = new JButton(Main.getTranslator().getTranslation("save", Translator.OPTION_JDIALOG));
		if(toDetermineMinEdit.getMinimumSize().getWidth()>edit.getMinimumSize().getWidth()){
			edit.setMinimumSize(toDetermineMinEdit.getMinimumSize());
		}
		
		GUIUtils.normalizeSize(edit);
		GUIUtils.normalizeSize(search);
		
		JPanel subLine = new JPanel();
		subLine.setLayout(new BoxLayout(subLine, BoxLayout.X_AXIS));
		subLine.add(Box.createHorizontalStrut(pos));
		subLine.add(search);
		subLine.add(Box.createHorizontalStrut(5));
		subLine.add(edit);
		
		int neccessaryMin = 5+width - pos  - (int) ( edit.getMinimumSize().getWidth() + search.getMinimumSize().getWidth() );
		
		
		
		subLine.add(Box.createHorizontalStrut(neccessaryMin));
		subLine.add(Box.createGlue());
		subLine.setMaximumSize(new Dimension(width,100));
		secondLine.add(subLine);
		secondLine.add(text);
		secondLine.add(Box.createHorizontalStrut(5));
		text.setMaximumSize(new Dimension(Short.MAX_VALUE,Y_OFFSET));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(firstLine);
		this.add(secondLine);
	}
	
	

}
