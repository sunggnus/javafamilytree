package tree.gui.draw;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class AbstractDraw {

	static public final int STRING_HEIGHT = 15;

	static public final int STRING_MARGING = 4;

	static public final int DEFAULT_MARGING = 5;

	private double scaling;

	protected double getScaling() {
		return scaling;
	}

	private int row;

	private int maxX;

	private int maxY;

	private int minX;

	private int minY;

	private boolean marked;

	protected AbstractDraw() {

		this.setScaling(1.0);
		this.setMarked(false);
	}

	protected int nextRow() {
		int row = this.row;
		this.row += STRING_HEIGHT;
		return row;
	}

	public int getRow() {
		return this.row;
	}

	protected void setScaling(double scaling) {
		this.scaling = scaling;
	}

	protected void setRow(int row) {
		this.row = row;
	}

	/**
	 * should be executed by subclasses before executing the draw body
	 * 
	 * @param g
	 */
	public void startDraw(Graphics2D g) {
		if (this.isMarked()) {
			g.setColor(Color.BLUE);
		}
	}

	/**
	 * should be executed by subclasses before after the draw body has been
	 * executed
	 * 
	 * @param g
	 */
	public void finishDraw(Graphics2D g) {
		g.setColor(Color.BLACK);
	}

	public abstract void calculateBounds(int widthUnit, int heightUnit,
			double scaling);

	public abstract void draw(Graphics2D g, int widthUnit, int heightUnit,
			double scaling, boolean drawX);

	public int getMaxX() {
		return this.maxX;
	}

	public int getMaxY() {
		return this.maxY;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public boolean containsCoordinates(int x, int y) {

		if (this instanceof DrawPerson) {
			if (!((DrawPerson) this).isVisible()) {
				return false; // so mouse only reacts if the person is visible
			}

		}

		return (this.getMinX() < x && x < this.getMaxX() && this.getMinY() < y && y < this
				.getMaxY());
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * sets the content visibility
	 */
	abstract public void setVisible(boolean visible);

	abstract public void setNewContentXCoordinate(int x);

	abstract public void addDistanceToXCoordinate(int dist);

	abstract public int getRelativeXCoordinate();

	abstract public int getRelativeYCoordinate();

	abstract public void setRelativeYCoordinate(int y);

}
