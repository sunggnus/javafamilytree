package tree.gui.field;


import javax.swing.JButton;


public class ModifiedJButton extends AbstractField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4349584867205369481L;
	

	public ModifiedJButton(String name, int labelWidth, int fieldWidth) {
		super("", labelWidth, new JButton(name),fieldWidth);
		
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
	
	}
	
	public JButton getJButton(){
		return (JButton)super.getFieldComponent();
	}

}
