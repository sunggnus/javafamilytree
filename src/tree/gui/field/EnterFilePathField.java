package tree.gui.field;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import translator.Translator;
import tree.gui.window.OptionDialog;

import main.Config;
import main.Main;

public class EnterFilePathField extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -145113757419382199L;
	
	public EnterFilePathField(){
		
		JLabel defaultSearch = new JLabel(Main.getTranslator().getTranlation("defaultSearch", Translator.OPTION_JDIALOG));
		
		final JTextField text = new JTextField();
		text.setText(Config.DEFAULT_PATH);
		text.setPreferredSize(new Dimension(AbstractField.EMPTY_TEXT_FIELD_WIDTH,(int)text.getPreferredSize().getHeight()));
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
		
		final JButton edit = new JButton(Main.getTranslator().getTranlation("edit", Translator.OPTION_JDIALOG));
		edit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				text.setEditable(!text.isEditable());
				if(text.isEditable()){
					edit.setText(Main.getTranslator().getTranlation("save", Translator.OPTION_JDIALOG));
				}
				else{
					edit.setText(Main.getTranslator().getTranlation("edit", Translator.OPTION_JDIALOG));
				}
			}
			
		});
		
		final JButton search = new JButton(Main.getTranslator().getTranlation("search", Translator.OPTION_JDIALOG));
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
		int OFFSET = 35;
		int Y_OFFSET=30;
		SpringLayout spring = new SpringLayout();
		
		spring.putConstraint(SpringLayout.WEST, defaultSearch, 10 + OFFSET, SpringLayout.WEST, this);
		spring.putConstraint(SpringLayout.NORTH, defaultSearch, 5, SpringLayout.NORTH, this);
		
		spring.putConstraint(SpringLayout.WEST, search,5+OFFSET, SpringLayout.WEST, this);
		spring.putConstraint(SpringLayout.NORTH, search, 5+Y_OFFSET, SpringLayout.NORTH, this);
		
		spring.putConstraint(SpringLayout.WEST, edit, 10, SpringLayout.EAST, search);
		spring.putConstraint(SpringLayout.NORTH, edit, 5+Y_OFFSET, SpringLayout.NORTH, this);
		
		spring.putConstraint(SpringLayout.WEST, text, AbstractField.DEFAULT_LABEL_WIDTH+OFFSET+65, SpringLayout.WEST, this);
		spring.putConstraint(SpringLayout.NORTH, text, 6+Y_OFFSET, SpringLayout.NORTH, this);
		this.setPreferredSize(new Dimension(OptionDialog.OPTION_WIDTH,(int) (search.getPreferredSize().getHeight()+5+Y_OFFSET)));
		this.setLayout(spring);
		this.add(defaultSearch);
		this.add(search);
		this.add(edit);		
		this.add(text);
	}

}
