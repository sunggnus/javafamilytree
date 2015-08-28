package tree.gui.field;

import javax.swing.JCheckBox;

public class ModifiedCheckBox extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8079477510706548527L;

	public ModifiedCheckBox(String name, int labelWidth) {

		super(name, labelWidth, new JCheckBox());

	}

	private JCheckBox getCheckBox() {
		JCheckBox box = (JCheckBox) super.getFieldComponent();
		return box;
	}

	public boolean isSelected() {
		return this.getCheckBox().isSelected();
	}

	public void setSelected(boolean selected) {
		this.getCheckBox().setSelected(selected);
	}

}
