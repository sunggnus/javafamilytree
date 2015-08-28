package tree.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {

	private int newHeight;

	private JSlider slider;

	public CustomSliderUI(JSlider arg0) {
		super(arg0);
		slider = arg0;
		newHeight = 20;
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g, c);
	}

	@Override
	public void paintThumb(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (thumbRect.height != newHeight) {
			int oldHeight = thumbRect.height;
			thumbRect.height = newHeight;
			thumbRect.y = thumbRect.y + (oldHeight - thumbRect.height) / 2;
		}
		thumbRect.width = 27;

		super.paintThumb(g2d);
		Color save = g2d.getColor();
		g2d.setColor(Color.BLACK);
		int x = thumbRect.x + 2;
		int y = thumbRect.y + 13;

		g2d.drawString(String.valueOf(slider.getValue()), x, y);
		g2d.setColor(save);
	}

}
