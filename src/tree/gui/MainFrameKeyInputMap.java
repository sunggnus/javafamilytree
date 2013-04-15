package tree.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import tree.gui.MenuBar;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;



public class MainFrameKeyInputMap {
	public enum KeyActions{
		REDRAW( new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -6083015051623385205L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MenuBar.MenuBarListener.REDRAW_BACKGROUND.actionPerformed(arg0);
				
			}
			
		}),
		HELP(new AbstractAction(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1143533572747003953L;

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuBar.MenuBarListener.SHOW_HELP.actionPerformed(e);
			}
			
		})
		;
		
		private AbstractAction action;
		
		private KeyActions(AbstractAction action){
			this.action = action;
		}
		
		public AbstractAction getAction(){
			return this.action;
		}
		
	}
	
	private JComponent component;
	
	static private int NO_MODIFIERS = 0;
	
	public MainFrameKeyInputMap(JComponent component){
		this.component = component;
	}
	
	public void deactivateKeyBindings(){
		this.component.getInputMap(
				JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
	}
	
	public void setDefaultKeyBindings(){
		this.deactivateKeyBindings();
		InputMap inputMap = this.component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.component.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R,NO_MODIFIERS), "redraw");
		actionMap.put("redraw",KeyActions.REDRAW.getAction());
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1,NO_MODIFIERS), "help");
		actionMap.put("help", KeyActions.HELP.getAction());
	}

}
