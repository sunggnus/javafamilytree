package tree.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Note implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2377237475665023276L;

	private List<String> comments;

	private int x;

	private int y;

	private int fontSize;

	private boolean visible;

	/**
	 * moves the text within the note rectangle horizontally should be between 0
	 * and 1
	 */
	private double smoothY;
	/**
	 * moves the text within the note rectangle vertically should be between 0
	 * and 1
	 */
	private double smoothX;

	public Note() {
		super();
		comments = new LinkedList<String>();
		this.setSmoothX(1.0);
		this.setSmoothY(1.0);
		this.setFontSize(11);
	}

	public void addLine(String str) {
		comments.add(str);
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public boolean removeLine(String str) {
		return comments.remove(str);
	}

	public void removeLineNum(int i) {
		comments.remove(i);
	}

	public List<String> getComments() {
		return this.comments;
	}

	public int getX() {
		return x;
	}

	public double getHalfX() {
		return x / 2.0;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getSmoothY() {
		return smoothY;
	}

	public void setSmoothY(double smoothY) {
		this.smoothY = smoothY;
	}

	public double getSmoothX() {
		return smoothX;
	}

	public void setSmoothX(double smoothX) {
		this.smoothX = smoothX;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
