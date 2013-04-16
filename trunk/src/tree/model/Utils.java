package tree.model;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

public class Utils {
	
	static public String calendarToString(GregorianCalendar time) {

		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("EEE, dd MMMM yyyy");
		
		return calendarToString(time,terminlayout);

		
	}
	
	static public String calendarToSimpleString(GregorianCalendar time){
		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("dd.MM.yyyy");
		
		return calendarToString(time,terminlayout);
	}
	
	static public String calendarToString(GregorianCalendar time, SimpleDateFormat layout){
		if(time==null){
			return "";
		}
		
		return layout.format(time.getTime());

	}

	static BufferedImage convertByteToImage(byte[] bytearray)
	{
	Image image=Toolkit.getDefaultToolkit().createImage(bytearray);
	JFrame frm=new JFrame();
	MediaTracker mt=new MediaTracker(frm);
	mt.addImage(image,0);
	try
	    {
	    mt.waitForAll();
	    }
	catch(InterruptedException ex)
	    {
	    
	    }
	int width=image.getWidth(null);
	int height=image.getHeight(null);
	BufferedImage bi=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	while(!bi.createGraphics().drawImage(image,0,0,null)){
		
	}
	return bi;
	}

}
