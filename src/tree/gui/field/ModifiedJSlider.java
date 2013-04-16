package tree.gui.field;

import javax.swing.JSlider;

public class ModifiedJSlider extends AbstractField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6187568633787660247L;

	public ModifiedJSlider(String name, int labelWidth, int fieldWidth){
		super(name, labelWidth, new JSlider());
	}

}
