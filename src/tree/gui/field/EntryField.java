package tree.gui.field;


import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.event.DocumentListener;


public class EntryField extends AbstractField{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9145105242712604077L;
	
	/**
	 * 
	 * @param name
	 * @param labelWidth
	 */
	public EntryField(String name, int labelWidth){
		
		super(name, labelWidth, new JTextField());
		
		
	}
	/**
	 * 
	 * @param name
	 * @param labelWidth
	 * @param fieldWidth
	 */
	public EntryField(String name, int labelWidth, int fieldWidth){
		super(name, labelWidth, new JTextField(), fieldWidth);
	}
	
	
	
	private JTextField getField(){
		return (JTextField) super.getFieldComponent();
	}
	
	public String getContent(){
		JTextField field = this.getField();
		return field.getText();
	}
	
	public void setContent(String content){
		this.getField().setText(content);
	}
	
	public void setContent(int content){
		this.getField().setText(String.valueOf(content));
	}
	
	public void setLabelText(String text){
		this.getLabel().setText(text);
	}
	
	public void setEntryFieldSize(int width, int height){
		
		this.setPreferredSize(new Dimension(width,height));
		this.setMaximumSize(this.getField().getPreferredSize());
	}
	
	public void setEntryFieldTextFieldSize(int width, int height){
		this.getField().setSize(width, height);
		this.getField().setPreferredSize(new Dimension(width,height));
		this.getField().setMaximumSize(this.getField().getPreferredSize());
	}
	
	public void setLabelSize(int width, int height){
		this.getLabel().setSize(width, height);
		this.getLabel().setPreferredSize(new Dimension(width,height));
		this.getLabel().setMaximumSize(this.getLabel().getPreferredSize());
	}
	
	public void addDocumentListener(DocumentListener docLis){
		this.getField().getDocument().addDocumentListener(docLis);
	}
	
	
	public void clear(){
		
		this.getLabel().setText("");
	}
	
}
