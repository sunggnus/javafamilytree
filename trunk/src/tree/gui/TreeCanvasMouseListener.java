package tree.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import tree.gui.draw.AbstractDraw;
import tree.gui.draw.DrawNote;
import tree.gui.draw.DrawPerson;
import tree.gui.window.EditNoteDialog;
import tree.gui.window.EditPersonDialog;

/**
 * TreeCanvasMouseListener is a MouseListener and a MouseMotionListener
 * which listens on MouseEvents happening within the TreeCanvas.
 * This TreeCanvasMouseListener makes it possible to drag and drop person nodes within the canvas
 * @author Stefan
 *
 */
public class TreeCanvasMouseListener implements MouseListener,MouseMotionListener{
	
	/**
	 * the TreeCanvas by which this mouse listener was added
	 * 
	 */
	private TreeCanvas canvas;
	
	/**
	 * constructs a new {@link TreeCanvasMouseListener} a {@link TreeCanvasMouseListener} should be added 
	 * only by one {@link TreeCanvas}!
	 * @param canvas the TreeCanvas by which this MouseListener is added
	 */
	public TreeCanvasMouseListener(final TreeCanvas canvas){
		this.canvas = canvas;
	}
	
	/**
	 * if move is true the listener listens on new MousePositions and move marked persons correspondingly
	 */
	private boolean move= false;
	
	/**
	 * if mark is true a border can be created an  everything within will be marked
	 */
	private boolean mark= false;
	/**
	 * the last important x-position where a mouseButton was pressed
	 */
	private int pressedX;
	/**
	 * the last important y-position where a mouseButton was pressed
	 */
	private int pressedY;
	
	/**
	 * last known x-position of the mouse
	 */
	private int lastX;
	/**
	 * last known y-position of the mouse
	 */
	private int lastY;
	/**
	 * the mouse button1 this is saved here so it is possible to swap them
	 */
	static private int mouseButton1 = MouseEvent.BUTTON1;
	/**
	 * the mouse button3 this is saved here so it is possible to swap them
	 */
	static private int mouseButton3 = MouseEvent.BUTTON3;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
			int x = arg0.getX();
			int y = arg0.getY();
			for (AbstractDraw draw : canvas.getToDraw()) {
				if (draw.containsCoordinates(x, y)) {
					if(arg0.getButton() == mouseButton1){
					if (draw instanceof DrawPerson) {
						new EditPersonDialog(((DrawPerson) draw)
								.getDrawPerson());
						break;
					}
					if (draw instanceof DrawNote) {
						new EditNoteDialog(((DrawNote) draw).getNote());
						break;
					}
					}
					else if(arg0.getButton() == mouseButton3){
						draw.setMarked(!draw.isMarked());
						canvas.repaint();
					}
				}
			}
		

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		pressedX = arg0.getX();
		pressedY = arg0.getY();
		lastX=pressedX;
		lastY=pressedY;
		
		if (arg0.getButton() == mouseButton3) {
			mark = true;
		}
		if (arg0.getButton() == mouseButton1) {
			for (AbstractDraw draw : canvas.getToDraw()) {
				if (draw.containsCoordinates(pressedX, pressedY)) {

					move = true;
				}
			}

		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.mouseMoved(arg0);
		move=false;
		//only happens when a real border has been drawn
		if(mark&&(Math.abs(pressedX-lastX)+Math.abs(pressedY-lastY)>30)){
			int x = arg0.getX();
			int y = arg0.getY();
			int upperX = pressedX;
			int lowerX = x;
			int upperY = pressedY;
			int lowerY = y;
			if(pressedX<x){
				upperX = x;
				lowerX = pressedX;
			}
			if(pressedY<y){
				upperY=y;
				lowerY=pressedY;
			}
			
			for(AbstractDraw draw : canvas.getToDraw()){
				int drawXMax = draw.getMaxX();
				int drawYMax = draw.getMaxY();
				int drawXMin = draw.getMinX();
				int drawYMin = draw.getMinY();
				if(drawXMin<upperX&&drawXMax>lowerX&&
						drawYMin<upperY&&drawYMax>lowerY){
					draw.setMarked(true);
				}
				else{
					if(!arg0.isControlDown()){
					draw.setMarked(false);
					}
				}
			}
			
		}
		canvas.repaint();
		mark=false;
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		this.mouseMoved(arg0);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		
		if(mark && (lastX!=x||lastY!=y)){
			lastX=x;
			lastY=y;
			canvas.repaint();
		}
		if(move){
			
			
			
			//TODO mouse and object should be overlapping the whole time!
			int difX = (int)(2*(x-pressedX)/((double)canvas.getWidthUnit()*canvas.getScaling()*0.91));
			int difY = (int)((y-pressedY)/((double)canvas.getHeightUnit()*canvas.getScaling()*1.15));
			boolean changedX = false;
			boolean changedY = false;
			for (AbstractDraw draw : canvas.getToDraw()) {
				if(draw.isMarked()){
				int relativX = draw.getRelativeXCoordinate();
				int relativY = draw.getRelativeYCoordinate();
				
				draw.setRelativeYCoordinate(draw.getRelativeYCoordinate()+difY);
				
				draw.addDistanceToXCoordinate(difX);
				if(relativX!=draw.getRelativeXCoordinate()){
					changedX = true;
				}
				if(relativY!=draw.getRelativeYCoordinate()){
					changedY = true;
				}
				}
			}
			if(changedX){
				pressedX = x;
			}
			if(changedY){
				pressedY = y;
			}
			canvas.repaint();
		}
		
	}
	
	protected void paintBorder(Graphics g){
		if(mark){
			
			int upperX = pressedX;
			int lowerX = lastX;
			int upperY = pressedY;
			int lowerY = lastY;
			if(pressedX<lastX){
				upperX = lastX;
				lowerX = pressedX;
			}
			if(pressedY<lastY){
				upperY=lastY;
				lowerY=pressedY;
			}
			
			Color orgColor = g.getColor();
			g.setColor(new Color(0, 255, 255, 100));
			g.fillRect(lowerX, lowerY, upperX-lowerX, upperY-lowerY);
			g.setColor(orgColor);
			
		}
	}
	
	static public void swapMouseButtons(){
		int save = mouseButton1;
		mouseButton1 = mouseButton3;
		mouseButton3 = save;
	}
	
	static public void defaultConfig(){
		mouseButton1 = MouseEvent.BUTTON1;
		mouseButton3  =MouseEvent.BUTTON3;
	}
	
	static public void swapedConfig(){
		mouseButton1 = MouseEvent.BUTTON3;
		mouseButton3 = MouseEvent.BUTTON1;
	}

}
