package tree.gui.field;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

public class ComboToolTipRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 833141323591342838L;

	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JComponent comp = (JComponent) super.getListCellRendererComponent(list,
				value, index, isSelected, cellHasFocus);

		if (value != null) {
			comp.setToolTipText(String.valueOf(value));
		} else {
			comp.setToolTipText(null);
		}
		return comp;
	}

}
