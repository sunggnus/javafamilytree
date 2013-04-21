package tree.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import main.Main;
import tree.gui.draw.AbstractDraw;
import tree.gui.draw.DrawNote;
import tree.gui.draw.DrawPerson;
import tree.gui.draw.backgrounds.Background;
import tree.gui.draw.backgrounds.BackgroundFactory;
import tree.gui.draw.backgrounds.DrawBackgroundImage;
import tree.model.Note;
import tree.model.Person;
/**
 * The family tree is drawn onto / with this TreeCanvas
 * @author Stefan
 *
 */
public class TreeCanvas extends JPanel implements ExportImage{
	
	
	
	/**
	 * contains every thing else which should be drawn on the canvas
	 */
	private List<AbstractDraw> toDraw;

	/**
	 * contains the normal height of a person node
	 */
	private int heightUnit;

	/**
	 * contains the normal width of a person node
	 */
	private int widthUnit;
	/**
	 * contains the preferred most outer x-coordinate of this panel
	 */
	private int prefX;
	
	/**
	 * contains the preferred most outer y-coordinate of this panel
	 */
	private int prefY;
	/**
	 * the scaling
	 */
	private double scaling;
	
	/**
	 * if this boolean is true the grid coordinates of the persons are written within a person node
	 */
	private boolean drawXYPositon;
	
	
	
	/**
	 * a unique id used for serialization of the canvas
	 */
	private static final long serialVersionUID = -8331400744843621677L;
	
	private Background background;

	/**
	 * constructs a new TreeCanvas
	 * @param heightUnit the initial height of a person node
	 * @param widthUnit the initial width of a person node
	 * @param scaling a scaling factor used for drawing 1.0 should be used as default
	 */
	public TreeCanvas(int heightUnit,  int widthUnit, double scaling) {
		this.heightUnit = heightUnit;
		this.widthUnit = widthUnit;
		this.scaling = scaling;
		this.generateDrawStuff();
		this.drawXYPositon = false;
		TreeCanvasMouseListener canvasMouseListener = new TreeCanvasMouseListener(this);
		this.addMouseMotionListener(canvasMouseListener);
		this.addMouseListener(canvasMouseListener);
		this.setBackground(Color.WHITE);
		
		
	}
	
	/**
	 * loads the data from the model which should be represented in this tree
	 * and calculates the preferred size of this canvas
	 */
	public void generateDrawStuff() {
		List<Person> persons = Main.getMainNode().getPersons();
		toDraw = new LinkedList<AbstractDraw>();
		
		for (Person person : persons) {
			toDraw.add(new DrawPerson(5, 15, person));

		}
		for (Note note : Main.getMainNode().getNotes()) {
			toDraw.add(new DrawNote(note));
		}

		this.refreshBounds();
	}
	
	public void refreshBounds(){
		this.prefX = 10;
		this.prefY = 10;
		for (AbstractDraw person : toDraw) {
			person.calculateBounds(widthUnit, heightUnit, scaling);
			this.increasePrefX(person.getMaxX());
			this.increasePrefY(person.getMaxY());
		}

		this.setPreferredSize(new Dimension(this.prefX, this.prefY));
		if (this.isVisible()) {
			this.repaint();
		}
	}
	
	
	public void generateBackground(){
		background = BackgroundFactory.generateBackground(prefX, prefY);
	}

	
	/**
	 * returns the {@link #heightUnit } of the TreeCanvas
	 * @return
	 */
	protected int getHeightUnit() {
		return this.heightUnit;
	}
	
	/**
	 * returns the {@link #widthUnit} of the TreeCanvas
	 * @return
	 */
	protected int getWidthUnit() {
		return this.widthUnit;
	}
	
	/**
	 * returns the {@link #scaling} factor of the TreeCanvas
	 * @return
	 */
	protected double getScaling() {
		return this.scaling;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.paintTree(g);
		
		for(MouseListener listener : this.getMouseListeners()){
			if(listener instanceof TreeCanvasMouseListener){
				TreeCanvasMouseListener mouseListener = (TreeCanvasMouseListener) listener;
				mouseListener.paintBorder(g);
			}
		}
		
	}
	/**
	 * paint the tree on the given graphics object
	 * @param g the given graphics object
	 */
	private void paintTree(Graphics g) {
		
		
		
		
		
		g.setFont(new Font("Dialog.plain", Font.PLAIN, 11));
		
		
		
		
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform trans = g2.getTransform();
		
		background = BackgroundFactory.generateBackground(prefX, prefY);
		if(background != null){
			
			background.setSize(this.prefX,this.prefY,this.getScaling());
			background.paintBackground(g2); 
	}
		
		
		
		
		// scales the tree:
		g2.scale(this.getScaling(), this.getScaling());
		// set rendering hints
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		
		if (this.toDraw != null) {

			for (AbstractDraw drawObj : this.toDraw) {
				drawObj.draw(g2, this.getWidthUnit(), this.getHeightUnit(),
						this.scaling, this.drawXYPositon);
				this.increasePrefX(drawObj.getMaxX());
				this.increasePrefY(drawObj.getMaxY());

			}

			this.setPreferredSize(new Dimension(this.prefX, this.prefY));
			this.setMaximumSize(this.getPreferredSize());
		}
		
		g2.setTransform(trans);
		
	}
	/**
	 * paints the tree on a new BufferedImage
	 * @return a BufferedImage which contains the tree
	 */
	@Override
	public BufferedImage exportImage() {
		int width = (int) this.getPreferredSize().getWidth();
		int height = (int) this.getPreferredSize().getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graph = image.createGraphics();
		graph.setBackground(Color.WHITE);
		graph.clearRect(0, 0, width, height);
		graph.setColor(Color.BLACK);

		this.paintTree(graph);

		return image;
	}

	/**
	 * sets a new prefX value
	 * @param prefX {@link #prefX}
	 */
	private void setPrefX(int prefX) {
		this.prefX = prefX;
	}
	/**
	 * increases the {@link #prefX} value by x
	 * @param x the value by which {@link #prefX} will be increased
	 */
	private void increasePrefX(int x) {
		if (x > this.prefX) {
			this.setPrefX(x);
		}
	}

	/**
	 * increases the {@link #prefY} value by y
	 * @param y the value by which {@link #prefY} will be increased
	 */
	private void increasePrefY(int y) {
		if (y > this.prefY) {
			this.setPrefY(y);
		}
	}
	/**
	 * sets a new prefY value
	 * @param prefY {@link #prefY}
	 */
	private void setPrefY(int prefY) {
		this.prefY = prefY;
	}
	
	
	/**
	 * switches the value of the {@link #drawXYPositon}
	 * 
	 */
	public void changeDrawXYPositon() {
		this.drawXYPositon = !this.drawXYPositon;
	}
	/**
	 * returns the value of {@link #drawXYPositon}
	 * @return value of {@link #drawXYPositon}
	 */
	public boolean isDrawXYPosition(){
		return this.drawXYPositon;
	}
	
	/**
	 * sets a new {@link #scaling} value
	 * @param scaling the new {@link #scaling} value
	 */
	public void setScaling(double scaling) {
		this.scaling = scaling;
	}
	
	/**
	 * sets a new {@link #heightUnit} value
	 * @param heightUnit the new {@link #heightUnit} value
	 */
	public void setHeightUnit(int heightUnit) {
		this.heightUnit = heightUnit;
	}
	/**
	 * sets a new {@link #widthUnit} value
	 * @param widthUnit the new {@link #widthUnit} value
	 */
	public void setWidthUnit(int widthUnit) {
		this.widthUnit = widthUnit;
	}
	
	/**
	 * return the list with everything which should be drawn on the canvas
	 * @return the toDraw list
	 */
	protected List<AbstractDraw> getToDraw(){
		return this.toDraw;
	}
	
	
	/**
	 * sets a new {@link #backgroundImage}
	 * @param backgroundImage the new image
	 */
	protected void setBackgroundImage(DrawBackgroundImage backgroundImage) {
		BackgroundFactory.setDrawBackground(backgroundImage);
	}

}
