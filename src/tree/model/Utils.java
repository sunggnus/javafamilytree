package tree.model;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class Utils {
	
	static public final int TREE_UP = 1;
	
	static public final int TREE_DOWN = 2;
	
	static public final int TREE_SEARCH = 3;
	
	
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

	static public BufferedImage convertByteToImage(byte[] bytearray)
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
	
	/**
	 * generates a sub tree of the whole family tree
	 * @param caller the person which is the connection point between the rest of the tree and the generated sub tree
	 * @param direction the search direction
	 * @return
	 */
	static public List<Person> generateSubTree(Person caller, int direction){
		List<Person> subTree = new LinkedList<Person>();
		if(direction == TREE_UP){
			subTree =  generateSubTree(caller,null,direction);
		}else if(direction == TREE_DOWN){
			subTree.add(caller);
			for(Person partner : caller.getPartners()){
				subTree.add(partner);
			}
			subTree = generateSubTree(caller,subTree, direction);
		}
		
		subTree.remove(caller);
		for(Person partner : caller.getPartners()){
			if(subTree.contains(partner)){
				subTree.remove(partner);
			}
		}
		return subTree;
	}
	
	
	/**
	 * generates a sub tree of the whole family tree
	 * @param caller the person which is the connection point between the rest of the tree and the generated sub tree
	 * @param subTree the generated subTree 
	 * @param direction the search direction
	 * @return
	 */
	static private List<Person> generateSubTree(Person caller, List<Person> subTree, int direction){
		if(subTree==null){
			subTree = new LinkedList<Person>();
			subTree.add(caller);
		}
		switch(direction){
		case TREE_UP:
			searchParentGeneration(caller, subTree);
			break;
		case TREE_DOWN:
			searchChildGeneration(caller, subTree);
			break;
		case TREE_SEARCH:
			searchParentGeneration(caller, subTree);
			searchChildGeneration(caller, subTree);
			break;
		default: //do nothing
		}
		
		return subTree;
	}
	
	static private void searchParentGeneration(Person caller, List<Person> subTree){
		if(caller.getMother()!=null&&!subTree.contains(caller.getMother())){
			subTree.add(caller.getMother());
			generateSubTree(caller.getMother(),subTree,TREE_SEARCH);
		}
		if(caller.getFather()!=null&&!subTree.contains(caller.getFather())){
			subTree.add(caller.getFather());
			generateSubTree(caller.getFather(),subTree,TREE_SEARCH);
		}
	}
	
	static private void searchChildGeneration(Person caller, List<Person> subTree){
		for(Person child : caller.getChildren()){
			if(!subTree.contains(child)){
				subTree.add(child);
				generateSubTree(child,subTree,TREE_SEARCH);
			}
		}
		for(Person partner : caller.getPartners()){
			if(!subTree.contains(partner)){
				subTree.add(partner);
				generateSubTree(partner,subTree,TREE_SEARCH);
			}
		}
	}

}
