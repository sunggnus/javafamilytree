package tree.gui.util;

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import tree.gui.field.AbstractField;

public class GroupSize {
	
	List<AbstractField> group;
	
	private int width;
	
	private int defaultWidth;
	
	public GroupSize(){
		this(0);
	}
	
	public GroupSize(int defaultWidth){
		group = new LinkedList<AbstractField>();
		width = 0;
		this.defaultWidth = defaultWidth + 10;
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
	
	public void addPanel(JPanel panel){
		for(Component comp : panel.getComponents()){
			if(comp instanceof AbstractField){
				AbstractField abs  = (AbstractField) comp;
				this.add(abs);
			}
		}
		this.processEvent();
	}
	
	public void processEvent(){
		for(AbstractField field : group){
			field.increaseLabelWidth(width);
		}
	}
	
	public void refreshSize(){
		this.width = this.defaultWidth;
		for(AbstractField field : group){
		if(field.getLabel() != null && width < field.getLabel().getPreferredSize().getWidth()){
			width = (int) field.getLabel().getPreferredSize().getWidth();
		}
		
		}
		processEvent();
	}
	
	public void unlockPrefSize(){
		for(AbstractField field : group){
			if(field.getLabel()!=null){
			field.getLabel().setPreferredSize(null);
			field.getLabel().invalidate();
			}
		}
	}

}
