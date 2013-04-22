package tree.gui.field;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tree.gui.util.GUIUtils;

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
		this.label = new JLabel();
		
		this.label.setText(name);
		
		this.field.setMinimumSize(new Dimension(fieldWidth,
				(int)this.field.getPreferredSize().getHeight()));
		this.label.setMinimumSize(new Dimension(labelWidth,
				(int)this.label.getPreferredSize().getHeight()));

		
		GUIUtils.normalizeSize(this.field);
	    GUIUtils.normalizeSize(this.label);
		
		
		Dimension filler = new Dimension(5,5);
		
		
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(new Box.Filler(filler , filler , filler ));
		this.add(this.label);
		this.add(this.field);
		this.add(new Box.Filler(filler , filler , filler ));
		
		Dimension minDim = new Dimension(100,10);
		minDim.setSize(fieldWidth + labelWidth + 10, ( this.field.getPreferredSize().getHeight()> this.label.getPreferredSize().getHeight())?
				this.field.getPreferredSize().getHeight():this.label.getPreferredSize().getHeight());
		
		this.setMinimumSize(minDim);
		GUIUtils.normalizeSize(this);
		
		
		this.setMaximumSize(new Dimension(1000,1000));
		
		
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
