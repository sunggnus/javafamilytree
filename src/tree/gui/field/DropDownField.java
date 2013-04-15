package tree.gui.field;



import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.JComboBox;





public class DropDownField<T> extends AbstractField{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1036335360396433375L;
	
	private LinkedList<T> addedItems;
	
	public DropDownField(String name, int labelWidth){
		
		super(name,labelWidth, new JComboBox<T>());
		JComboBox<T> box = this.getField();
		box.setRenderer(new ComboToolTipRenderer());
		addedItems = new LinkedList<T>();
	}
	
	@SuppressWarnings("unchecked")
	private JComboBox<T> getField(){
		return (JComboBox<T>) super.getFieldComponent();
	}
	
	public void add(T toAdd){
		this.getField().addItem(toAdd);
		this.addedItems.add(toAdd);
		
		
	}
	
	public void addItemListener(ItemListener l){
		this.getField().addItemListener(l);
	}
	
	public Object getSelectedItem(){
		return this.getField().getSelectedItem();
	}
	
	public void setSelectedItem(Object selected){
		if(this.addedItems.contains(selected)){
		this.getField().setSelectedItem(selected);
		}
	}
	
	
	public void setLabelSize(int width, int height){
		this.getLabel().setSize(width, height);
	}
	
	
}
