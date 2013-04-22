package tree.gui.window;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Main;
import translator.Translator;
import tree.gui.field.EntryField;
import tree.gui.field.ModifiedCheckBox;
import tree.gui.field.TextAreaField;
import tree.gui.util.GUIUtils;
import tree.model.Note;

public class EditNoteDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3585799795490081086L;
	
	private Note editableNote;
	
	private ModifiedCheckBox visible;
	
	private TextAreaField textArea;
	
	private EntryField xCoord;
	
	private EntryField yCoord;
	
	private EntryField fontSize;
	
	public EditNoteDialog(Note editableNote){
		this.editableNote = editableNote;
		if(editableNote==null){
			this.editableNote = new Note();
		}
		GUIUtils.assignIcon(this);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(700,800);
		JPanel panel = new JPanel();
		
		
		
		
		textArea = new TextAreaField(Main.getTranslator().getTranslation("note", Translator.EDIT_NOTE_JDIALOG) ,
				TextAreaField.DEFAULT_LABEL_WIDTH, 
				TextAreaField.EMPTY_TEXT_FIELD_WIDTH);
		
		xCoord = new EntryField(Main.getTranslator().getTranslation("xCoord", Translator.EDIT_NOTE_JDIALOG),
				EntryField.DEFAULT_LABEL_WIDTH);
		yCoord = new EntryField(Main.getTranslator().getTranslation("yCoord", Translator.EDIT_NOTE_JDIALOG), 
				EntryField.DEFAULT_LABEL_WIDTH);
		fontSize = new EntryField(Main.getTranslator().getTranslation("fontSize", Translator.EDIT_NOTE_JDIALOG)
				, EntryField.DEFAULT_LABEL_WIDTH);
		
		
		visible = new ModifiedCheckBox(Main.getTranslator().getTranslation("visible", Translator.EDIT_PERSON_JDIALOG),
				EntryField.DEFAULT_LABEL_WIDTH);
		visible.setSelected(this.editableNote.isVisible());
		
		JSlider smoothX = new JSlider();
		smoothX.setToolTipText(Main.getTranslator().getTranslation("smoothX_tool", Translator.EDIT_NOTE_JDIALOG));
		
		smoothX.setMaximum(100);
		smoothX.setValue((int) Math.ceil(this.editableNote.getSmoothX()*smoothX.getMaximum()));
		
		
		final EditNoteDialog self = this;
		
		smoothX.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(arg0.getSource() instanceof JSlider){
					JSlider slide = (JSlider) arg0.getSource();
					if(!slide.getValueIsAdjusting()){
						
						int val = slide.getValue();
						double percent = ((double)val / 100.0);
						self.editableNote.setSmoothX(percent);
					}
				}
			}
			
		});
		
		JSlider smoothY = new JSlider();
		smoothY.setToolTipText(Main.getTranslator().getTranslation("smoothY_tool", Translator.EDIT_NOTE_JDIALOG));
		smoothY.setMaximum(100);
		smoothY.setValue((int) Math.ceil(this.editableNote.getSmoothY()*smoothY.getMaximum()));
		
		smoothY.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(arg0.getSource() instanceof JSlider){
					JSlider slide = (JSlider) arg0.getSource();
					if(!slide.getValueIsAdjusting()){
						
						int val = slide.getValue();
						double percent = ((double)val / 100.0);
						self.editableNote.setSmoothY(percent);
					}
				}
			}
			
		});
		
		JButton accept = new JButton(Main.getTranslator().getTranslation("saveNote", Translator.EDIT_NOTE_JDIALOG));
		
		accept.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				self.editableNote.setComments(self.textArea.getContent());
				int x;
				int y;
				int fSize;
				try{
					x = Integer.parseInt(xCoord.getContent());
					y = Integer.parseInt(yCoord.getContent());
					fSize = Integer.parseInt(fontSize.getContent());
				}catch(NumberFormatException e){
					x=1;
					y=1;
					fSize=11;
				}
				self.editableNote.setX(x);
				self.editableNote.setY(y);
				self.editableNote.setVisible(visible.isSelected());
				self.editableNote.setFontSize(fSize);
				Main.getMainNode().addNote(self.editableNote);
				Main.getMainFrame().revalidateTree();
				self.dispose();
			}
			
		});
		
		JButton delete = new JButton(Main.getTranslator().getTranslation("removeNote", Translator.EDIT_NOTE_JDIALOG));
		
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.getMainNode().removeNote(self.editableNote);
				Main.getMainFrame().revalidateTree();
				self.dispose();
			}
			
		});
		
		panel.add(visible);
		panel.add(Box.createVerticalStrut(10));
		panel.add(xCoord);
		panel.add(Box.createVerticalStrut(5));
		panel.add(smoothX);
		panel.add(Box.createVerticalStrut(10));
		panel.add(yCoord);
		panel.add(Box.createVerticalStrut(5));
		panel.add(smoothY);
		panel.add(Box.createVerticalStrut(10));
		panel.add(fontSize);
		panel.add(Box.createVerticalStrut(10));
		panel.add(textArea);
		panel.add(Box.createVerticalStrut(10));
		panel.add(accept);
		panel.add(Box.createVerticalStrut(10));
		panel.add(delete);
		
		
		BoxLayout box = new BoxLayout(panel,BoxLayout.Y_AXIS);
		panel.setLayout(box);
		
		this.setLayout(new FlowLayout());
		this.add(panel);
		
		//load stuff:
		
		xCoord.setContent(this.editableNote.getX());
		yCoord.setContent(this.editableNote.getY());
		fontSize.setContent(this.editableNote.getFontSize());
		textArea.setContent(this.editableNote.getComments());
		
		this.setVisible(true);
	}
	
	

}
