package tree.gui.draw;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import tree.model.Note;

public class DrawNote extends AbstractDraw {

	private Note note;

	private int maxwidth;

	private int stringHeight;

	public DrawNote(Note note) {
		this.note = note;
	}

	/**
	 * 
	 * @param g
	 * @param scaling
	 * @param widthUnit
	 * @param heightUnit
	 * @param drawX
	 */
	@Override
	public void draw(Graphics2D g, int widthUnit, int heightUnit,
			double scaling, boolean drawX) {
		if (!note.isVisible()) {
			return;
		}
		this.startDraw(g);
		// save org Font
		Font orgFont = g.getFont();

		g.setFont(new Font(orgFont.getFontName(), orgFont.getStyle(), this.note
				.getFontSize()));

		this.calculateBounds(widthUnit, heightUnit, scaling, g);

		int x = (int) (this.note.getHalfX() * widthUnit * 1.1)
				+ (int) Math.ceil(this.note.getSmoothX() * widthUnit);
		int y = (int) (this.note.getY() * heightUnit * 1.3)
				+ (int) Math.ceil(this.note.getSmoothY() * heightUnit);

		this.setRow(y);

		for (String str : note.getComments()) {
			g.drawString(str, x, this.nextRow());

		}
		// restores org font
		g.setFont(orgFont);

		this.finishDraw(g);
	}

	protected void calculateBounds(int widthUnit, int heightUnit,
			double scaling, Graphics2D g) {
		// dont know if the size of the graphics changed
		FontMetrics met = g.getFontMetrics();
		maxwidth = 0;
		stringHeight = met.getHeight();
		for (String str : note.getComments()) {
			if (maxwidth < met.stringWidth(str)) {
				maxwidth = met.stringWidth(str);
			}
		}
		maxwidth = (int) (maxwidth * scaling);
		this.calculateBounds(widthUnit, heightUnit, scaling);

	}

	@Override
	public void calculateBounds(int widthUnit, int heightUnit, double scaling) {
		int x = (int) (this.note.getHalfX() * scaling * widthUnit * 1.1)
				+ (int) Math.ceil(this.note.getSmoothX() * widthUnit * scaling);
		int y = (int) (this.note.getY() * scaling * heightUnit * 1.3)
				+ (int) Math
						.ceil(this.note.getSmoothY() * heightUnit * scaling)
				- (int) (scaling * stringHeight);

		this.setMinX(x);
		this.setMinY(y);
		this.setMaxY(y
				+ (int) Math.ceil(this.note.getComments().size() * stringHeight
						* scaling));
		this.setMaxX(x + maxwidth);

	}

	public Note getNote() {
		return this.note;
	}

	@Override
	public void setNewContentXCoordinate(int x) {
		this.getNote().setX(x);
	}

	@Override
	public void addDistanceToXCoordinate(int dist) {
		this.getNote().setX(this.getNote().getX() + dist);

	}

	@Override
	public int getRelativeXCoordinate() {

		return this.getNote().getX();
	}

	@Override
	public int getRelativeYCoordinate() {

		return this.getNote().getY();
	}

	@Override
	public void setRelativeYCoordinate(int y) {
		this.getNote().setY(y);
	}

	@Override
	public void setVisible(boolean visible) {
		this.getNote().setVisible(visible);
	}

}
