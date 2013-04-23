package tree.gui.util;

import java.util.LinkedList;
import java.util.List;

import tree.gui.field.AbstractField;

public class GroupSize {
	
	List<AbstractField> group;
	
	private int width;
	
	public GroupSize(){
		group = new LinkedList<AbstractField>();
		width = 0;
	}
	
	public void add(AbstractField field){
		group.add(field);
		if(field.getLabel() != null && width < field.getLabel().getPreferredSize().getWidth()){
			width = (int) field.getLabel().getPreferredSize().getWidth() + 10;
		}
	}
	
	public boolean remove(AbstractField field){
		return group.remove(field);
	}
	
	
	public void processEvent(int width){
		if(width>this.width){
			this.width = width;
			this.processEvent();
		}
	}
	
	public void processEvent(){
		for(AbstractField field : group){
			field.increaseLabelWidth(width);
		}
	}

}
