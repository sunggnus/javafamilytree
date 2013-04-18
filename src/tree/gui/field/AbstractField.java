package tree.gui.field;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public abstract class AbstractField extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2334844379688056517L;
	
	private JComponent field;
	private JLabel label;

	final static public int EMPTY_TEXT_FIELD_WIDTH=300;
	
	final static public int DEFAULT_LABEL_WIDTH=150;
	
	final static public int DEFAULT_WIDTH = EMPTY_TEXT_FIELD_WIDTH + DEFAULT_LABEL_WIDTH;
	
	final static public int OFFSET_SIZE = 20;
	
	final static public int Y_OFFSET_SIZE = 5;
	
	protected AbstractField(String name, int labelWidth, JComponent field){
		this(name,labelWidth,field,EMPTY_TEXT_FIELD_WIDTH);
	}
	
	/**
	 * 
	 * @param name
	 * @param labelWidth
	 * @param field
	 * @param fieldWidth
	 */
	protected AbstractField(String name, int labelWidth, JComponent field,int fieldWidth){
		this.field = field;
		this.label = new JLabel(name);
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.WEST, this.label,
				5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, this.label, 
				5, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, this.field,
				5, SpringLayout.EAST, this.label);
		
		layout.putConstraint(SpringLayout.NORTH, this.field,
				5, SpringLayout.NORTH, this);
		
		
		this.field.setPreferredSize(new Dimension(fieldWidth,
				(int)this.field.getPreferredSize().getHeight()));
		this.label.setPreferredSize(new Dimension(labelWidth,
				(int)this.label.getPreferredSize().getHeight()));
		
		
		this.add(this.label);
		this.add(this.field);
		
		this.setLayout(layout);
		this.setPreferredSize(new Dimension((int)this.field.getPreferredSize().getWidth() +
				(int)this.label.getPreferredSize().getWidth() + OFFSET_SIZE  ,
				(int)this.field.getPreferredSize().getHeight()+ Y_OFFSET_SIZE));
		
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setSize(this.getPreferredSize());
		
	}
	
	
	protected JLabel getLabel(){
		return this.label;
	}
	
	protected JComponent getFieldComponent(){
		return this.field;
	}
	

	
	public void setToolTip(String toolTip){
		this.getLabel().setToolTipText(toolTip);
	}

}
