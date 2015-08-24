package tree.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

public class ActionProvider extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8755398481106063476L;
	ActionListener listener;
	
	public ActionProvider(ActionListener listener){
		super();
		this.listener = listener;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		this.listener.actionPerformed(arg0);
	}

}
