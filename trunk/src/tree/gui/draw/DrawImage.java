package tree.gui.draw;



import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;


public class DrawImage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2128719894096228434L;

	
	
	//first the 0throw information than the 0th column information
	static public final int TOP_LEFT_SIDE=1;
	static public final int TOP_RIGHT_SIDE=2;
	static public final int BOTTOM_RIGHT_SIDE=3;
	static public final int BOTTOM_LEFT_SIDE=4;
	static public final int LEFT_SIDE_TOP=5;
	static public final int RIGHT_SIDE_TOP=6;
	static public final int RIGHT_SIDE_BOTTOM=7;
	static public final int LEFT_SIDE_BOTTOM=8;
	
	private BufferedImage image;
	
	
	private int height;
	private int width;
	
	private int orientation;
	
	private int maxWidth;
	private int maxHeight;

	
	public DrawImage(){
		super();
		this.image = null;
		this.orientation=1;
		this.maxWidth=150;
		this.maxHeight=200;
	}
	
	public DrawImage(int maxWidth, int maxHeight){
		this();
		this.maxHeight=maxHeight;
		this.maxWidth=maxWidth;
	}
	
	
	
	public void setImage(BufferedImage image){
		this.image = image;
		if(this.image==null){
			return;
		}
		this.init();
	}
	
	private void init(){
		this.height = image.getHeight(null);
		this.width = image.getWidth(null);
	
		scale();
		this.setPreferredSize(new Dimension(width,height));
		this.setMinimumSize(this.getPreferredSize());
		if(this.getParent()!=null){
			
			int width = (int) this.getParent().getPreferredSize().getWidth();
			int height = 0;
			if(this.getParent()!=null){
				for(Component comp : this.getParent().getComponents()){
					height += (int)comp.getPreferredSize().getHeight();
				}
			}
			
			this.getParent().setPreferredSize(new Dimension(width,height));
			this.getParent().revalidate();
		}
	}
	
	public void scale(){
		if(height>maxHeight){
			double ratio = maxHeight/((double)height);
			this.height = (int) (this.height * ratio);
			this.width = (int) (this.width * ratio);
		}
		if(width>maxWidth){
			double ratio = maxWidth/((double)width);
			this.height = (int) (this.height * ratio);
			this.width = (int) (this.width * ratio);
		}
		
		
	
	}
	/**
	 * reorientates the image
	 */
	protected void transformImage(){
		if(this.orientation==TOP_LEFT_SIDE||this.image==null){
			return;
		}
		
		
	    
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		if(this.orientation==LEFT_SIDE_TOP||
				this.orientation==RIGHT_SIDE_TOP||
				this.orientation==RIGHT_SIDE_BOTTOM||
				this.orientation==LEFT_SIDE_BOTTOM){
			//swaps width and height
			newImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());			
		}
		
		
		Graphics2D graphics = newImage.createGraphics();
		graphics.transform(this.orientateImage(image,newImage));
		boolean drawed = false;
		while(!drawed){
				drawed=graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		
		}
		this.setImage(newImage);
		this.orientation=TOP_LEFT_SIDE;
	}
	
	/**
	 * copies the current BufferedImage
	 * @return
	 */
	private BufferedImage cloneImage(){
		return this.cloneImage(this.getImage());
	}
	
	protected BufferedImage cloneImage(BufferedImage image){
		if (image != null) {
			BufferedImage newImage = new BufferedImage(image.getWidth(),
					image.getHeight(), image.getType());
			Graphics2D graphics = newImage.createGraphics();
			boolean drawed = false;
			while (!drawed) {
				drawed = graphics.drawImage(image, 0, 0, image.getWidth(),
						image.getHeight(), null);

			}

			return newImage;
		}
		return null;
	}
	/**
	 * orientate the image corresponding to it exif information
	 * @param oldImage
	 * @param newImage
	 * @return
	 */
	private AffineTransform orientateImage(BufferedImage oldImage, BufferedImage newImage){
		AffineTransform trans = new AffineTransform();
		
		switch(this.orientation){
		case TOP_LEFT_SIDE:
			//do nothing
			break;
		case TOP_RIGHT_SIDE:
			//flip horizontal
			trans.translate(image.getWidth(), 0);
			trans.scale(-1.0, 1.0);
			break;
		case BOTTOM_RIGHT_SIDE:
			trans.translate(image.getWidth(), image.getHeight());
			trans.rotate(Math.toRadians(180));
			break;
		case BOTTOM_LEFT_SIDE:
			//flip vertical
			trans.translate(0, image.getHeight());
			trans.scale(1.0, -1.0);
			break;
		case LEFT_SIDE_TOP:
			//flip horizontal
			trans.translate(image.getWidth(), 0);
			trans.scale(-1.0, 1.0);
			//this should be equals to transpose
			trans.translate(image.getWidth(), 0);
			trans.rotate(Math.toRadians(90));
			
			break;
		case RIGHT_SIDE_TOP:
			trans.translate(oldImage.getHeight(),0);
			trans.rotate(Math.toRadians(90));
		
			break;
		case RIGHT_SIDE_BOTTOM:
			//flip vertical
			trans.translate(0, oldImage.getWidth());
			trans.scale(1.0, -1.0);
			//this should be equals to transverse
			trans.translate(oldImage.getHeight(),0);
			trans.rotate(Math.toRadians(90));
			
			break;
		case LEFT_SIDE_BOTTOM:
			trans.translate(0,oldImage.getWidth());
			trans.rotate(Math.toRadians(270));
			
			break;
		default:
			//do nothing
			break;
		}
		return trans;
	}
	
	
	
	public void setImage(String absolutePath) throws IOException{
		this.setImage(new File(absolutePath));
	}
	
	 public void setImage(File file ) throws IOException
	  {
	    this.setImage(ImageIO.read(file ));
	    this.readMetaData(file);
	    if ( image != null ){
	    	if(this.getParent()!=null){
	    		this.getParent().repaint();
	    	}
	    	else{
	    		this.repaint();
	    	}
	      
	    }
	  }
	 
	 public void deleteImage(){
		 this.image = null;
		 
		 if(this.getParent()!=null){
			 this.getParent().repaint();
		 }
	 }
	 
	
	  @Override
	  protected void paintComponent(Graphics g )
	  {
		 this.paintPicture(g,0,0);
	    
	  }
	  /**
	   * 
	   * @param g
	   * @param x upper left x-coordinate
	   * @param y upper left y-coordinate
	   */
	  public void paintPicture(Graphics g,int x,int y){
		  if(height==-1||width==-1){
			  this.init();
			  
		  }
		  else{
			  this.transformImage();
		  }
		  
	    if ( image != null ){     
	      g.drawImage( image, x, y,width,height, this );
	  }
	  }
	  
	  
	public void readMetaData(File file){
		try{
		Metadata metadata = JpegMetadataReader.readMetadata(file);
        Directory exifDirectory = metadata.getDirectory(ExifIFD0Directory.class);
        this.orientation = exifDirectory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        
		}catch(IOException | JpegProcessingException | MetadataException e){
			//do nothing
		}catch(NullPointerException f){
			javax.swing.JOptionPane.showMessageDialog(this,"keine Bildorientierungsinformationen gefunden");
			
		}
		
		
	}

	public BufferedImage getImage() {		
		return this.image;
	}
	
	public void setOrientation(int orient){
		this.orientation = orient;
	}
	/**
	 * clones the DrawImage object
	 */
	@Override
	public Object clone(){
		DrawImage clone = new DrawImage();
		clone.height = this.height;
		clone.width = this.width;
		clone.maxHeight = this.maxHeight;
		clone.maxWidth = this.maxWidth;
		clone.orientation = this.orientation;
		clone.image = this.cloneImage();
		return clone;
	}
	
	public int getImageHeight(){
		return this.height;
	}
	
	public int getImageWidth(){
		return this.width;
	}
	
}
