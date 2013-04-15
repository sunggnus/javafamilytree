package tree.gui.search.line;

import javax.swing.JPanel;

public abstract class AbstractLine extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1635683172882827743L;
	
	abstract public void setMainSize(int width);
	
	abstract public void setMainSize(int width, int height);

}
