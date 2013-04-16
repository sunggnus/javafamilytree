package tree.gui.window;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.Config;
import main.Main;
import main.OptionList;
import translator.Translator;
import tree.gui.draw.backgrounds.BackgroundFactory;
import tree.gui.field.AbstractField;
import tree.gui.field.DropDownField;
import tree.gui.field.EnterFilePathField;
import tree.gui.field.EntryField;
import tree.gui.util.IconUtil;

public class OptionDialog extends JDialog{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6409084117710520555L;
	
	public static final int OPTION_WIDTH = 600;
	
	public OptionDialog(){
		IconUtil.assignIcon(this);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int width = AbstractField.DEFAULT_LABEL_WIDTH+50;
		JPanel panel = new JPanel();
		DropDownField<OptionList> mode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("displayMode", Translator.OPTION_JDIALOG),
						width);
		
		DropDownField<OptionList> lineDrawMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("lineMode", Translator.OPTION_JDIALOG), 
						width);
		
		DropDownField<OptionList> backgroundMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("backgroundMode", Translator.OPTION_JDIALOG), 
						width);
		
		final JSlider xBackgroundPosition = new JSlider();
		final JSlider yBackgroundPosition = new JSlider();
		xBackgroundPosition.setVisible(false);
		yBackgroundPosition.setVisible(false);
		
		
		DropDownField<OptionList> lineBreakMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("lineBreakMode", Translator.OPTION_JDIALOG), 
						width);
		//text ist still lineBreakMode
		DropDownField<OptionList> dataPositioningMode = 
				new DropDownField<OptionList>("", 
						width);
		
		
		DropDownField<OptionList> mouseMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("mouseMode", Translator.OPTION_JDIALOG), 
						width);
		
		DropDownField<OptionList> keyboardMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("keyboardMode", Translator.OPTION_JDIALOG), 
						width);
		
		for(OptionList dispMode : OptionList.values()){
			switch(dispMode.getID()){
				case OptionList.TYPE_BACKGROUND_MODE:
					backgroundMode.add(dispMode);
					break;
				case OptionList.TYPE_CONNECTION_MODE:
					lineDrawMode.add(dispMode);
					break;
				case OptionList.TYPE_TEXT_LAYOUT:
					mode.add(dispMode);
					break;
				case OptionList.TYPE_LINE_BREAK_MODE:
					lineBreakMode.add(dispMode);
					break;
				case OptionList.TYPE_PERSON_POSITION_DATA_MODE:
					dataPositioningMode.add(dispMode);
					break;
				case OptionList.TYPE_MOUSE_MODE:
					mouseMode.add(dispMode);
					break;
				case OptionList.TYPE_KEYBOARD_MODE:
					keyboardMode.add(dispMode);
					break;
				default: //do nothing
			}
			

		}
		
		
		
		mode.setSelectedItem(Config.ORIENTATION_MODE);
		
		
		mode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					Config.ORIENTATION_MODE = item;
					Main.getMainFrame().getCanvas().repaint();
				}
				
			}
			
		});
		
		
		
		
	
		
		
		lineDrawMode.setSelectedItem(Config.CONNECTION_MODE);
		
		lineDrawMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					Config.CONNECTION_MODE = item;
					Main.getMainFrame().getCanvas().repaint();
				}
				
			}
			
		});
		
		
		
		
		
		backgroundMode.setSelectedItem(Config.BACKGROUND_MODE);
		
		backgroundMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					Config.BACKGROUND_MODE = item;
					BackgroundFactory.deleteSavedBackground();
					if(item.equals(OptionList.DRAW_LOADED_BACKGROUND)){
						xBackgroundPosition.setVisible(true);
						yBackgroundPosition.setVisible(true);
					}else{
						xBackgroundPosition.setVisible(false);
						yBackgroundPosition.setVisible(false);
					}
					Main.getMainFrame().getCanvas().repaint();
				}
				
			}
			
		});
		
		lineBreakMode.setSelectedItem(Config.LINE_BREAK_MODE);
		lineBreakMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					Config.LINE_BREAK_MODE = item;
					Main.getMainFrame().getCanvas().repaint();
				}
				
			}
			
		});
		
		dataPositioningMode.setSelectedItem(Config.DATA_POSITIONING_MODE);
		dataPositioningMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					Config.DATA_POSITIONING_MODE = item;
					Main.getMainFrame().getCanvas().repaint();
				}
				
			}
			
		});
		
		mouseMode.setSelectedItem(Config.MOUSE_MODE);
		mouseMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==ItemEvent.SELECTED && arg0.getItem() instanceof OptionList){
					OptionList item = (OptionList) arg0.getItem();
					Config.setMouseMode(item);
				}
			}
			
		});
		
		keyboardMode.setSelectedItem(Config.KEYBOARD_MODE);
		keyboardMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==ItemEvent.SELECTED && arg0.getItem() instanceof OptionList){
					OptionList item = (OptionList) arg0.getItem();
					Config.setKeyboardMode(item);
				}
			}
			
		});
		
		final EntryField unitWidth = new EntryField(Main.getTranslator().getTranslation("defaultWidth", Translator.OPTION_JDIALOG),
				width);
		final EntryField unitHeight = new EntryField("defaultHeight", width);
		
		unitWidth.addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				try{
					Config.PERSON_WIDTH = Integer.parseInt(unitWidth.getContent());
				}catch(NumberFormatException e){
					Config.PERSON_WIDTH = Config.DEFAULT_PERSON_WIDTH;
				}
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				this.changedUpdate(arg0);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				this.changedUpdate(arg0);
				
			}
			
		});
		
		unitHeight.addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				try{
					Config.PERSON_HEIGHT = Integer.parseInt(unitHeight.getContent());
				}catch(NumberFormatException e){
					Config.PERSON_HEIGHT = Config.DEFAULT_PERSON_HEIGHT;
				}
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				this.changedUpdate(arg0);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				this.changedUpdate(arg0);
				
			}
			
		});
		
		EnterFilePathField filePath = new EnterFilePathField();
		
		panel.add(mode);
		panel.add(lineDrawMode);
		panel.add(backgroundMode);
		panel.add(xBackgroundPosition);
		panel.add(yBackgroundPosition);
		panel.add(lineBreakMode);
		panel.add(dataPositioningMode);
		panel.add(mouseMode);
		panel.add(keyboardMode);
		panel.add(filePath);
		panel.add(unitWidth);
		panel.add(unitHeight);
		
		this.add(panel);
		this.setSize(OPTION_WIDTH,500);
		this.setResizable(false);
		this.setVisible(true);
	}

}
