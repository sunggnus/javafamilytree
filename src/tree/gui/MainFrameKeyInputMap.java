package tree.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import tree.gui.MenuBar;
import tree.gui.draw.AbstractDraw;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import main.Main;



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
			
		}),
		INVISIBLE_MARKED(new AbstractAction(){
			private static final long serialVersionUID = -8602680793815299702L;

			@Override
			public void actionPerformed(ActionEvent e) {
				TreeCanvas canvas = INVISIBLE_MARKED.getCanvas();
				for(AbstractDraw draw : canvas.getToDraw()){
					if(draw.isMarked()){
						draw.setVisible(false);
					}
				}
				canvas.repaint();
			}
			
		}),
		VISIBLE_MARKED(new AbstractAction(){
			private static final long serialVersionUID = -7602680793815299702L;

			@Override
			public void actionPerformed(ActionEvent e) {
				TreeCanvas canvas = VISIBLE_MARKED.getCanvas();
				for(AbstractDraw draw : canvas.getToDraw()){
					if(draw.isMarked()){
						draw.setVisible(true);
					}
				}
				canvas.repaint();
			}
			
		}),
		VISIBLE_ALL(new AbstractAction(){
			private static final long serialVersionUID = -8602680713815299702L;

			@Override
			public void actionPerformed(ActionEvent e) {
				TreeCanvas canvas = VISIBLE_ALL.getCanvas();
				for(AbstractDraw draw : canvas.getToDraw()){
						draw.setVisible(true);				
				}
				canvas.repaint();
			}
			
		}),
		INVISIBLE_ALL(new AbstractAction(){
			private static final long serialVersionUID = -8602680793815399702L;

			@Override
			public void actionPerformed(ActionEvent e) {
				TreeCanvas canvas = INVISIBLE_ALL.getCanvas();
				for(AbstractDraw draw : canvas.getToDraw()){
						draw.setVisible(false);			
				}
				canvas.repaint();
			}
			
		})	;
		
		
		private AbstractAction action;
		
		private KeyActions(AbstractAction action){
			this.action = action;
		}
		
		private TreeCanvas getCanvas(){
			return Main.getMainFrame().getCanvas();
		}
		
		public AbstractAction getAction(){
			return this.action;
		}
		
	}
	
	private JComponent component;
	
	private long bindings;
	
	static private int NO_MODIFIERS = 0;
	
	public MainFrameKeyInputMap(JComponent component){
		this.component = component;
		bindings = 0;
	}
	
	public void deactivateKeyBindings(){
		this.component.getInputMap(
				JComponent.WHEN_IN_FOCUSED_WINDOW).clear();
		this.bindings=0;
	}
	
	public void setDefaultKeyBindings(){
		this.deactivateKeyBindings();
		//redraw on r
		this.addKeyAction(KeyEvent.VK_R,NO_MODIFIERS,KeyActions.REDRAW);
		//help on f1
		this.addKeyAction(KeyEvent.VK_F1,NO_MODIFIERS,KeyActions.HELP);
		//impressum on f2
		this.addKeyAction(KeyEvent.VK_F2,NO_MODIFIERS,MenuBar.MenuBarListener.SHOW_IMPRESSUM);
		//visible marked on v
		this.addKeyAction(KeyEvent.VK_V,NO_MODIFIERS,KeyActions.VISIBLE_MARKED);
		//invisible marked on i
		this.addKeyAction(KeyEvent.VK_I,NO_MODIFIERS,KeyActions.INVISIBLE_MARKED);
		//visible all on ctrl-v
		this.addKeyAction(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK, KeyActions.VISIBLE_ALL);
		//invisible all on ctrl-i
		this.addKeyAction(KeyEvent.VK_I,KeyEvent.CTRL_DOWN_MASK, KeyActions.INVISIBLE_ALL);
		//save dialog ctrl-s
		this.addKeyAction(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK, MenuBar.MenuBarListener.SAVE_TREE);
		//load dialog ctrl-l
		this.addKeyAction(KeyEvent.VK_L,KeyEvent.CTRL_DOWN_MASK, MenuBar.MenuBarListener.LOAD_TREE);
		//open person overview o
		this.addKeyAction(KeyEvent.VK_O, NO_MODIFIERS, MenuBar.MenuBarListener.PERSON_OVERVIEW);
		//open note overview ctrl-o
		this.addKeyAction(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK, MenuBar.MenuBarListener.NOTE_OVERVIEW);
		//show / hide coordinates c
		this.addKeyAction(KeyEvent.VK_C,NO_MODIFIERS,MenuBar.MenuBarListener.DRAW_X_Y_POSITION);
		//print strg-p
		this.addKeyAction(KeyEvent.VK_P,KeyEvent.CTRL_DOWN_MASK, MenuBar.MenuBarListener.PRINT_TREE);
	}
	
	private void addKeyAction(int eventKey, int modifiers, KeyActions action){
		this.addKeyAction(eventKey, modifiers, action.getAction());
	}
	private void addKeyAction(int eventKey, int modifiers, MenuBar.MenuBarListener listener){
		this.addKeyAction(eventKey, modifiers, listener.getAction());
	}
	private void addKeyAction(int eventKey, int modifiers, AbstractAction action){
		InputMap inputMap = this.component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.component.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(eventKey,modifiers), Long.valueOf(this.bindings));
		actionMap.put(Long.valueOf(this.bindings),action);
		this.bindings++;
	}

}
