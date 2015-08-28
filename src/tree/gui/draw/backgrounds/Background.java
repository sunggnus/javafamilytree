package tree.gui.draw.backgrounds;

import java.awt.Graphics2D;

import tree.gui.ExportImage;

public abstract class Background implements ExportImage {

	abstract public void paintBackground(Graphics2D g);

	/**
	 * 
	 * @param width
	 * @param height
	 * @param scaling
	 */
	abstract public void setSize(int width, int height, double scaling);

}
