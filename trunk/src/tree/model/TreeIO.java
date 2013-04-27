package tree.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import tree.gui.ExportImage;


public class TreeIO {

	/**
	 * loads a tree
	 * @param path to the source file
	 * @return
	 * @throws IOException
	 */
	public MainNode loadTree(String path)throws IOException{
		return loadTree(new File(path));
	}
	
	/**
	 * loads a tree
	 * @param file the source file
	 * @return the loaded tree
	 * @throws IOException
	 */
	public MainNode loadTree(File file) throws IOException{
		FileInputStream input = null;
		ObjectInputStream objIn = null;
		try{
			input = new FileInputStream(file);
			objIn = new ObjectInputStream(input);
			Object obj = objIn.readObject();
			if(!(obj instanceof MainNode)){
				throw new IOException("Ung�ltiger Datensatz");
			}
			MainNode node = (MainNode) obj;
			
			return node;
		}catch(IOException e){
			throw e;
		} catch (ClassNotFoundException e) {
			throw new IOException("Ung�ltiger Datensatz");
		}finally{
			if(objIn != null){
				objIn.close();
			}
			if(input!=null){
				input.close();
			}
		}
		
	}
	
	/**
	 * this method will write the tree to a file 
	 * with the given path if the file does not exist
	 * @param node
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public boolean writeTree(MainNode node, String path) 
			throws IOException{
		return writeTree(node, new File(path), false);
	}
	
	/**
	 * this method will write the tree to a file 
	 * @param node
	 * @param path
	 * @param override
	 * @return
	 * @throws IOException
	 */
	public boolean writeTree(MainNode node, String path, boolean override) 
			throws IOException{
		return writeTree(node, new File(path), override);
	}
	
	/**
	 * this method will write the tree to a file if the file does not exist
	 * @param node
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public boolean writeTree(MainNode node, File file) throws IOException{
		return writeTree(node,file,false);
	}
	
	
	public boolean writeImageAs(String path, String mode, ExportImage canvas,boolean override)throws IOException{
		switch(mode){
		case "jpeg":
			return this.writeImageAsJPEG(path, canvas, override);
		case "png":
			return this.writeImageAsPNG(path, canvas, override);
		default:
			return false;
		}
	}
	
	/**
	 * 
	 * @param node
	 * @param file
	 * @param override
	 * @return false if the file exists and the override option is not set
	 * @throws IOException
	 */
	public boolean writeTree(MainNode node, File file, boolean override) throws IOException{
		FileOutputStream stream=null;
		ObjectOutputStream objOut=null;
		if(file.exists()&&!override){
			return false;
		}
		if(file.exists()){
			file.delete();
		}
		try{
			stream = new FileOutputStream(file);	
			objOut = new ObjectOutputStream(stream);
			objOut.writeObject(node);
			
		}catch(IOException e){		
			throw e;
		}finally{
			if(objOut!=null){
				objOut.flush();
				objOut.close();
			}
			if(stream!=null){
			stream.flush();
			stream.close();
			}
		}
		
		return true;
	}
	
	public boolean writeImageAsJPEG(String path, ExportImage canvas, boolean override) throws IOException{
		if(!path.endsWith(".jpg")){
			String[] split = path.split("\\.");
			if(split.length>1){
				path = split[0] + ".jpg";
			}
			else{
				path += ".jpg";
			}
			
		}
		return this.writeImageAsJPEG(new File(path), canvas, override);
	}
	
	public boolean writeImageAsPNG(String path, ExportImage canvas, boolean override) throws IOException{
		if(!path.endsWith(".png")){
			String[] split = path.split("\\.");
			if(split.length>1){
				path = split[0] + ".png";
			}
			else{
				path += ".png";
			}
			
		}
		return this.writeImageAsPNG(new File(path), canvas, override);
	}
	
	public boolean writeImageAsJPEG(File file, ExportImage canvas, boolean override) throws IOException{
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");		 
		return this.writeImage(file, iter, canvas,override);
	}
	
	public boolean writeImageAsPNG(File file, ExportImage canvas, boolean override) throws IOException{
					
		 Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("png");
		 return this.writeImage(file, iter, canvas,override);
	}
	
	public boolean writeImage(File file, Iterator<ImageWriter> iter, 
			ExportImage canvas, boolean override)throws IOException{
		if((file.exists()&&!override)||canvas==null){
			return false;
		}
		FileImageOutputStream output = new FileImageOutputStream(file);
		try{
		
		ImageWriter writer = (ImageWriter)iter.next(); 
		 ImageWriteParam iwp = writer.getDefaultWriteParam();
		 try{
	     iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	     iwp.setCompressionQuality(1.0f); 
		 }catch(UnsupportedOperationException e){
			// iwp.setCompressionMode(ImageWriteParam.MODE_DEFAULT);
			 System.out.println("Compression not supported.");
		 }
	     writer.setOutput(output);
	     BufferedImage export = canvas.exportImage();
	     if(export!=null){
	    	 writer.write(export);
	     }else{
	    	 System.out.println("err export failed");
	     }
		return true;
		}catch(IOException e){
			throw e;
		}finally{
			output.close();
		
		}
	}
	
	public MainNode loadGEDCOM5(File file){
		return null;
	}
	
}
