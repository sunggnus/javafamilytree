package tree.gui.field;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tree.gui.util.GUIUtils;
import tree.gui.util.GroupSize;

public abstract class AbstractField extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2334844379688056517L;
	
	private JComponent field;
	private JLabel label;
	
	
	private GroupSize size;

	final static public int EMPTY_TEXT_FIELD_WIDTH=400;
	
	final static public int DEFAULT_LABEL_WIDTH=150;
	
	final static public int DEFAULT_WIDTH = EMPTY_TEXT_FIELD_WIDTH + DEFAULT_LABEL_WIDTH;
	
	final static public int OFFSET_SIZE = 20;
	
	final static public int Y_OFFSET_SIZE = 5;
	
	/**
	 * use this constructor only for
	 * the gaining of the GroupSize feature and override the increaseLabelWidth
	 * in the sub class
	 */
	protected AbstractField(){
		
	}
	
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
		this.add(Box.createHorizontalStrut(5));
		this.add(this.field);
		this.add(new Box.Filler(filler , filler , filler ));
		
		this.refreshMinDim(fieldWidth, labelWidth);
		
		
		
	}
	
	private void refreshMinDim(double fieldWidth, double labelWidth){
		Dimension minDim = new Dimension(100,10);
		minDim.setSize(fieldWidth + labelWidth + 10, ( this.field.getPreferredSize().getHeight()> this.label.getPreferredSize().getHeight())?
				this.field.getPreferredSize().getHeight():this.label.getPreferredSize().getHeight());
		
		this.setMinimumSize(minDim);
		GUIUtils.normalizeSize(this);
	}
	

	
	public void add(GroupSize size){
		this.size = size;
		size.add(this);
	}
	
	public void increaseLabelWidth(int width){
			Dimension newDim = new Dimension(width, 
					(int) this.getLabel().getPreferredSize().getHeight());
			this.getLabel().setMinimumSize(newDim);

			
			GUIUtils.normalizeSize(this.getLabel());
	
			this.getLabel().setMaximumSize(newDim);
			this.refreshMinDim(this.getFieldComponent().getMinimumSize().getWidth(),
					this.getLabel().getMinimumSize().getWidth());
		
	}
	
	public boolean remove(GroupSize size){
		boolean result = size.remove(this);
		this.size = null;
		return result;
	}
	
	public void triggerResizeEvent(){
		if(this.size != null){
		this.size.processEvent((int)this.getLabel().getPreferredSize().getWidth());
		}
	}
	
	
	public JLabel getLabel(){
		return this.label;
	}
	
	protected JComponent getFieldComponent(){
		return this.field;
	}
	

	
	public void setToolTip(String toolTip){
		this.getLabel().setToolTipText(toolTip);
	}

}
