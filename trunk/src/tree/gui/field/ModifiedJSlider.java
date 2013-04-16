package tree.gui.field;

import java.awt.Dimension;

import javax.swing.JSlider;

public class ModifiedJSlider extends AbstractField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6187568633787660247L;

	public ModifiedJSlider(String name, int labelWidth){
		super(name, labelWidth, new JSlider());
		
		this.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(),
				(int) this.getJSlider().getPreferredSize().getHeight()+10));
	}
	
	public JSlider getJSlider(){
		JSlider slider  = (JSlider) this.getFieldComponent();
		
		return slider;
	}

}
