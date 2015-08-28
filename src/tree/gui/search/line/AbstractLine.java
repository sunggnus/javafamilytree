package tree.gui.search.line;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tree.gui.util.GUIUtils;

public abstract class AbstractLine extends JPanel {

	private int multi;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1635683172882827743L;

	public AbstractLine() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	abstract public void setMainSize(int width);

	public void setMainSize(int width, int height) {
		Dimension pref = new Dimension(width, height);
		int fillerCounter = 0;
		for (Component comp : this.getComponents()) {
			if (!(comp instanceof Box.Filler)) {
				comp.setMinimumSize(pref);
				GUIUtils.normalizeSize(comp);
				comp.setMaximumSize(new Dimension(500, (int) comp
						.getPreferredSize().getHeight()));
			} else {
				fillerCounter++;
			}
		}

		this.setMinimumSize(new Dimension(width * multi + fillerCounter * 5,
				height));
		GUIUtils.normalizeSize(this);
		this.setMaximumSize(new Dimension((int) this.getMaximumSize()
				.getWidth(), (int) this.getPreferredSize().getHeight() + 5));

	}

	@Override
	public Component add(Component comp) {
		super.add(new Box.Filler(new Dimension(5, 5), new Dimension(5, 5),
				new Dimension(5, 5)));
		super.add(comp);

		return comp;
	}

	public Component normalAdd(Component comp) {
		return super.add(comp);
	}

	protected int getMulti() {
		return multi;
	}

	protected void setMulti(int multi) {
		this.multi = multi;
	}

}
