package tree.gui.field;

import java.awt.Dimension;

import javax.swing.JButton;


public class ModifiedJButton extends AbstractField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4349584867205369481L;
	
	private static int DEFAULT_HEIGHT=30;

	public ModifiedJButton(String name, int labelWidth, int fieldWidth) {
		super("", labelWidth, new JButton(name),fieldWidth);
		
		this.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(),DEFAULT_HEIGHT));
		this.setMinimumSize(this.getPreferredSize());
	}
	/**
	 * 
	 * @param name
	 * @param buttonName
	 * @param labelWidth
	 * @param fieldWidth
	 */
	protected ModifiedJButton(String name, String buttonName, int labelWidth, int fieldWidth){
		super(name, labelWidth, new JButton(buttonName),fieldWidth);
		this.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(),DEFAULT_HEIGHT));
		this.setMinimumSize(this.getPreferredSize());
	}
	
	public JButton getJButton(){
		return (JButton)super.getFieldComponent();
	}

}
