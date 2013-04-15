package tree.gui.draw.backgrounds;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class SummerTree extends Background{
	
	static public int OFFSET=5;
	
	private Graphics2D g;

	private int width;
	
	private int height;
	
	private int minLength;
	
	private double scaling;
	
	private double lengthX;
	/**
	 * ratio of width to height (width / height)
	 */
	private double ratio;
	
	private BufferedImage tree;
	
	private boolean random;
	
	public SummerTree(int width, int height, int minLength, boolean random){
		this.random = random;
		this.minLength = minLength;
		//scaling is not used here
		this.setSize(width, height,0);
		
		
	}
	
	public void bufferTree(){
		tree = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tree.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		this.paintTree(g);
		System.out.println("new tree painted width "+ this.getWidth() + " height " + this.getHeight());
	}
	
	public void paintBufferedTree(Graphics2D g){
		
		
		
		AffineTransform trans = g.getTransform();
		g.scale(scaling, scaling);
		while(!g.drawImage(tree, 0, 0, null)){
			System.out.println("painting");
		}
		g.setTransform(trans);
	}
	
	public void init() {
	//	this.getContentPane().setBackground(new Color(103, 46, 103));
	}

	protected Color nextLeftColor(Color oldColor) {
		return new Color(
				(int) (0.9 * oldColor.getRed()   + 0.1 * Color.GREEN.getRed()),
				(int) (0.9 * oldColor.getGreen() + 0.1 * Color.GREEN.getGreen()),
				(int) (0.9 * oldColor.getBlue()  + 0.1 * Color.GREEN.getBlue()));
	}

	protected Color nextRightColor(Color oldColor) {
		return new Color(
				(int) (0.9 * oldColor.getRed()   + 0.1 * Color.YELLOW.getRed()),
				(int) (0.9 * oldColor.getGreen() + 0.1 * Color.YELLOW.getGreen()),
				(int) (0.9 * oldColor.getBlue()  + 0.1 * Color.YELLOW.getBlue()));
	}
	/**
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @param thickness
	 * @param color
	 */
	protected void drawBranch(double xStart, double yStart, double xEnd,
			double yEnd, double thickness,
			Color color) {
	

		g.setStroke(new BasicStroke((float) thickness, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(color);
		g.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
	}

	protected void drawTree() {
		
			lengthX = this.getWidth();
		if(random){
		drawTreeRandom(this.getWidth() / 2, this.getHeight()+this.getHeight()/10,
				-Math.PI / 2.0, lengthX/5.1, this.getHeight()/4, lengthX/32.0, new Color(155, 100, 60));
		}
		else{
			drawTreeRec(this.getWidth() / 2, this.getHeight()+this.getHeight()/11,
					-Math.PI / 2.0, lengthX/5.1, this.getHeight()/4, lengthX/32.0, new Color(155, 100, 60));
		}
	//	drawTreeRec(this.getWidth() / 2, this.getHeight(),
	//			-Math.PI / 2.0, lengthX/5, this.getHeight()/4, lengthX/32.0, new Color(155, 100, 60));
		
	//	drawTreeRec(this.getWidth() / 2, this.getHeight()+this.getHeight()/4,
	//			-Math.PI / 2.0, lengthX/5, this.getHeight()/4, lengthX/32.0, new Color(155, 100, 60));
	}

	
	public void paintTree(Graphics g) {
		
		this.g = (Graphics2D) g;
		this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		Stroke orgStroke = this.g.getStroke();
		Color orgColor = this.g.getColor();
		
		drawTree();
		
		this.g.setColor(orgColor);
		this.g.setStroke(orgStroke);
	}

		protected void drawTreeRec(double x, double y, double alpha,
			double lengthX, double lengthY, double thickness, Color color){
			
			if(lengthX < this.lengthX/400.0){
				return;
			}
			double distanceX = lengthX * Math.cos(alpha);
			double distanceY = lengthY * Math.sin(alpha);
						
			drawBranch(x, y, x + distanceX, y + distanceY,  thickness, color);

			drawTreeRec(x+distanceX, y + distanceY, alpha-0.5, lengthX*9/12, lengthY*27/35, thickness*9/10, nextLeftColor(color));
			drawTreeRec(x+distanceX, y + distanceY, alpha+0.6, lengthX*9/12, lengthY*27/35, thickness*9/10, nextRightColor(color));
			}
		
		protected void drawTreeRandom(double x, double y, double alpha,
				double lengthX, double lengthY, double thickness, Color color){
			if(lengthX < this.lengthX/400.0){
				return;
			}
			double rnd = Math.random();
			double rndB =- (1-rnd*rnd);
			double distanceX = lengthX * rnd;
			double distanceY = lengthY * rndB;
			if(lengthX>this.lengthX/5.5){
			distanceX = lengthX * Math.cos(alpha);
			distanceY = lengthY * Math.sin(alpha);
			}
			drawBranch(x, y, x + distanceX, y + distanceY,  thickness, color);
			double rnd2 = Math.random();
			double rnd3 = Math.random();
			drawTreeRec(x+distanceX, y + distanceY, alpha-0.5, lengthX*rnd2, lengthY*rnd2, thickness*9/10, nextLeftColor(color));
			drawTreeRec(x+distanceX, y + distanceY, alpha+0.6, lengthX*rnd3, lengthY*rnd3, thickness*9/10, nextRightColor(color));
			
		}


	

	public int getWidth() {
		return width;
	}
	
	
	public void setWidth(int width) {
		if(width/scaling==this.width){
			return;
		}
		if(width < scaling){
			width = (int) scaling;
		}
		this.width = (int ) (width/scaling);
		this.bufferTree();
	}

	public int getHeight() {
		return height;
	}
	
	
	public void setHeight(int height) {
		if(height/scaling==this.height){
			return;
		}
		if(height<scaling){
			height = (int) scaling;
		}
		this.height =(int) (height/scaling);
		this.bufferTree();
	}

	@Override
	public void paintBackground(Graphics2D g) {
		this.paintBufferedTree(g);
		
	}
	
	@Override
	public void setSize(int width, int height, double scaling){
		//scaling is not necessary here so it is not used
		double ratio = width / (double) height;
		this.scaling = width / (double) this.width;
		if(!equalsWithError(this.ratio,ratio,0.005)){
			this.ratio = ratio;
		
		if(ratio>1){
			this.width =(int) (this.minLength * ratio); 
			this.height = this.minLength;
		}
		else{
			this.width =(int) (this.minLength ); 
			this.height = (int)(this.minLength / ratio);
		}
			
			this.bufferTree();
			
		}
	}
	
	static public boolean equalsWithError(double a, double b, double err){
		return (Math.abs(a-b)<err);
	}
	
	@Override
	public BufferedImage exportImage(){
		return tree;
	}



}
