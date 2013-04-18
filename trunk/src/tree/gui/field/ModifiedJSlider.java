package tree.gui.field;

import java.awt.Dimension;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class ModifiedJSlider extends AbstractField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6187568633787660247L;
	
	private ChangeListener addedChangeListener;

	public ModifiedJSlider(String name, int labelWidth){
		super(name, labelWidth, new JSlider());
		this.addedChangeListener = null;
		
		this.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(),
				(int) this.getJSlider().getPreferredSize().getHeight()+10));
	}
	
	public JSlider getJSlider(){
		JSlider slider  = (JSlider) this.getFieldComponent();
		
		return slider;
	}
	
	public void addChangeListener(ChangeListener listener){
		if(this.addedChangeListener != null){
			this.getJSlider().removeChangeListener(this.addedChangeListener);
		}
		this.addedChangeListener = listener;
		this.getJSlider().addChangeListener(this.addedChangeListener);
	}
	
	
}
