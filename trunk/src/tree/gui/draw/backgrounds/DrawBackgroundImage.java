package tree.gui.draw.backgrounds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import tree.gui.draw.DrawImage;

public class DrawBackgroundImage extends Background{

	
	private DrawImage westNorth;
	private DrawImage eastNorth;
	private DrawImage eastSouth;
	private DrawImage westSouth;
	
	private int imageHeight;
	
	private int imageWidth;
	
	private int width;
	
	private int height;
	
	private int mode;
	
	private int xPos;
	
	private int yPos;
	
	private double scaling;
	
	static final public int MODE_MIRRORED=0;
	
	static final public int MODE_SCALED=1;
	
	static final public int MODE_FIT=2;
	
	public DrawBackgroundImage(DrawImage basic){
		this.mode = MODE_MIRRORED;
		
		this.westNorth = (DrawImage) basic.clone();
		this.eastNorth = (DrawImage) basic.clone();
		this.eastSouth = (DrawImage) basic.clone();
		this.westSouth = (DrawImage) basic.clone();
		
		setImageHeight(2*this.westNorth.getImageHeight());
		setImageWidth(2 * this.westNorth.getImageWidth());
		
		this.westNorth.setOrientation(DrawImage.TOP_LEFT_SIDE);
		this.eastNorth.setOrientation(DrawImage.TOP_RIGHT_SIDE);
		this.eastSouth.setOrientation(DrawImage.BOTTOM_RIGHT_SIDE);
		this.westSouth.setOrientation(DrawImage.BOTTOM_LEFT_SIDE);
		
		xPos = 0;
		yPos = 0;
		
	}
	
	 /**
	   * 
	   * @param g
	   * @param x upper left x-coordinate
	   * @param y upper left y-coordinate
	   */
	  public void paintPicture(Graphics g,int x,int y){
		  setImageHeight(2*this.westNorth.getImageHeight());
			setImageWidth(2 * this.westNorth.getImageWidth());
		  
		  this.westNorth.paintPicture(g, x, y);
		  this.eastNorth.paintPicture(g, x+this.westNorth.getImageWidth(), y);
		  this.westSouth.paintPicture(g, x, y+this.westNorth.getImageHeight());
		  this.eastSouth.paintPicture(g, x+this.westNorth.getImageWidth(), y+this.westNorth.getImageHeight());
	  }
	  
	  @Override
	  public void paintBackground(Graphics2D g){
		  	this.paintBackground(g, this.xPos, this.yPos);
	  }
	  
	  public void paintBackground(Graphics2D g, int xPos, int yPos){
		  switch (mode) {
			case MODE_MIRRORED:
				for (int x = 0; x < this.width * (1 / scaling); x = x
						+ this.getImageWidth()) {
					for (int y = 0; y < this.height * (1 / scaling); y = y
							+ this.getImageHeight()) {
						this.paintPicture(g, x, y);
					}
				}
				break;
			case MODE_SCALED:
				Image image = this.westNorth.getImage();
				int imWidth = image.getWidth(null);
				int imHeight = image.getHeight(null);
				double scale = this.width / (double) imWidth;
				if (scale * imHeight > this.height) {
					scale = this.height / (double) imHeight;
				}
				g.drawImage(image, (int) (xPos*scale),(int) (yPos*scale), (int) (scale * imWidth),
						(int) (scale * imHeight), null);
				break;
			case MODE_FIT:
				image = this.westNorth.getImage();
				g.drawImage(image, 0, 0, this.width, this.height, null);
				break;
			default: //do nothing
			}
	  }
	  
	  

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public void setDoubleHeight(int height){
		
	}
	
	public void setSize(int width, int height, double scaling){
		this.width = width;
		this.height = height;
		this.scaling = scaling;
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	@Override
	public BufferedImage exportImage(){
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graph = image.createGraphics();
		graph.setBackground(Color.WHITE);
		graph.clearRect(0, 0, width, height);
		graph.setColor(Color.BLACK);
		paintBackground(graph,0,0);
		
		return image;
	}
	
	public void setXPosition(int x){
		this.xPos = x;
	}
	
	public void setYPosition(int y){
		this.yPos = y;
	}
	
	public int getXPosition(){
		return this.xPos;
	}
	
	public int getYPosition(){
		return this.yPos;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}

	
	
}
