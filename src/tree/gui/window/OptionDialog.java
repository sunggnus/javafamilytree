package tree.gui.window;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;


import main.Config;
import main.Main;
import main.OptionList;
import translator.Translator;
import tree.gui.draw.DrawPerson;
import tree.gui.draw.DrawPerson.Ordering;
import tree.gui.draw.backgrounds.BackgroundFactory;
import tree.gui.draw.backgrounds.DrawBackgroundImage;
import tree.gui.field.AbstractField;
import tree.gui.field.DropDownField;
import tree.gui.field.EnterFilePathField;
import tree.gui.field.EntryField;
import tree.gui.field.ModifiedJSlider;
import tree.gui.util.GUIUtils;
import tree.gui.util.GroupSize;


public class OptionDialog extends JDialog{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6409084117710520555L;
	
	public static final int OPTION_WIDTH = 600;
	
	/**
	 * contains the textual data ordering
	 */
	private JList<DrawPerson.Ordering> ordering;
	
	
	public OptionDialog(){
		this.constructOptions();
		}
	
	
	
	private void constructOptions(){

		GUIUtils.assignIcon(this);
		final OptionDialog self = this;
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int width = AbstractField.DEFAULT_LABEL_WIDTH+100;
		final JPanel panel = new JPanel(){
			private static final long serialVersionUID = 3996847380408971485L;
			@Override
			public Component add(Component comp){
				super.add(Box.createVerticalStrut(5));
				super.add(comp);
				
				return comp;
			}
			
		};
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		DropDownField<OptionList> mode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("displayMode", Translator.LanguageFile.OPTION_DIALOG),
						width);
		
		DropDownField<OptionList> lineDrawMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("lineMode", Translator.LanguageFile.OPTION_DIALOG), 
						width);
		
		DropDownField<OptionList> backgroundMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("backgroundMode", Translator.LanguageFile.OPTION_DIALOG), 
						width);
		
		final ModifiedJSlider xBackgroundPosition = 
				new ModifiedJSlider(Main.getTranslator().getTranslation("xSlider", Translator.LanguageFile.OPTION_DIALOG),width);
		final ModifiedJSlider yBackgroundPosition = 
				new ModifiedJSlider(Main.getTranslator().getTranslation("ySlider", Translator.LanguageFile.OPTION_DIALOG),width);
		initSliders(xBackgroundPosition, yBackgroundPosition);
		
		DropDownField<OptionList> lineBreakMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("lineBreakMode", Translator.LanguageFile.OPTION_DIALOG), 
						width);
		//text ist still lineBreakMode
		DropDownField<OptionList> dataPositioningMode = 
				new DropDownField<OptionList>("", 
						width);
		
		
		DropDownField<OptionList> mouseMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("mouseMode", Translator.LanguageFile.OPTION_DIALOG), 
						width);
		
		DropDownField<OptionList> keyboardMode = 
				new DropDownField<OptionList>(Main.getTranslator().getTranslation("keyboardMode", Translator.LanguageFile.OPTION_DIALOG), 
						width);
		DropDownField<OptionList> lookAndFeelMode = new DropDownField<OptionList>(Main.getTranslator().getTranslation("lookAndFeelMode", Translator.LanguageFile.OPTION_DIALOG), 
				width);
		
		DropDownField<OptionList> yPositioningMode = new DropDownField<OptionList>(Main.getTranslator().getTranslation("ypositioning", Translator.LanguageFile.OPTION_DIALOG), 
				width);
		
		DropDownField<OptionList> treeOrderingMode = new DropDownField<OptionList>(Main.getTranslator().getTranslation("treeOrderingMode", Translator.LanguageFile.OPTION_DIALOG), 
				width);
		
		
		final DropDownField<LookAndFeelInfo> additionalLookAndFeel = new DropDownField<LookAndFeelInfo>("", width);
		
		for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
			additionalLookAndFeel.add(info);
			if(info.getClassName().equals(Config.ADDITIONAL_LOOK_AND_FEEL)){
				additionalLookAndFeel.setSelectedItem(info);
			}
		}
		
		additionalLookAndFeel.setVisible(Config.LOOK_AND_FEEL_MODE.equals(OptionList.ADDITIONAL_LOOK_AND_FEEL));
		
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
				case OptionList.TYPE_LOOK_AND_FEEL:
					lookAndFeelMode.add(dispMode);
					break;
				case OptionList.TYPE_Y_POSITIONING_MODE:
					yPositioningMode.add(dispMode);
					break;
				case OptionList.TYPE_TREE_ORDERING_MODE:
					treeOrderingMode.add(dispMode);
					break;
				default: //do nothing
			}
			

		}
		
		treeOrderingMode.setSelectedItem(Config.TREE_ORDERING_MODE);
		
		treeOrderingMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					if(Config.TREE_ORDERING_MODE != item){
					Config.TREE_ORDERING_MODE = item;
		
					Main.getMainFrame().getCanvas().repaint();
					}
				}
				
			}
			
		});
		
		yPositioningMode.setSelectedItem(Config.Y_POSITIONING_MODE);
		
		yPositioningMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof OptionList){
					OptionList item = (OptionList) e.getItem();
					Config.Y_POSITIONING_MODE = item;
				}
				
			}
			
		});
		
		
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
					Main.getMainFrame().getCanvas().generateBackground();
					initSliders(xBackgroundPosition, yBackgroundPosition);
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
		
		lookAndFeelMode.setSelectedItem(Config.LOOK_AND_FEEL_MODE);
		lookAndFeelMode.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==ItemEvent.SELECTED && arg0.getItem() instanceof OptionList){
					OptionList item = (OptionList) arg0.getItem();
					Config.setLookAndFeel(item);
					SwingUtilities.updateComponentTreeUI(self);
					additionalLookAndFeel.setVisible(item.equals(OptionList.ADDITIONAL_LOOK_AND_FEEL));
					remove(panel);
					constructOptions();
				}
			}
			
		});
		
		additionalLookAndFeel.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==ItemEvent.SELECTED && arg0.getItem() instanceof LookAndFeelInfo){
					LookAndFeelInfo info = (LookAndFeelInfo) arg0.getItem();
					Config.setAdditionalLookAndFeel(info.getClassName());
					SwingUtilities.updateComponentTreeUI(self);
					remove(panel);
					constructOptions();
				}
			}
			
		});
		
		final EntryField unitWidth = new EntryField(Main.getTranslator().getTranslation("defaultWidth", Translator.LanguageFile.OPTION_DIALOG),
				width);
		unitWidth.setContent(Config.PERSON_WIDTH);
		final EntryField unitHeight = new EntryField(Main.getTranslator().getTranslation("defaultHeight", Translator.LanguageFile.OPTION_DIALOG), width);
		unitHeight.setContent(Config.PERSON_HEIGHT);
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
		
		final DefaultListModel<DrawPerson.Ordering> lmodel = new DefaultListModel<>();
		ordering = new JList<DrawPerson.Ordering>(lmodel);
		for(int i=0; i < Config.ORDERING.length; i++){
			lmodel.addElement(Config.ORDERING[i]);
		}
	
		 ordering.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        
	    MouseInputAdapter mouseHandler = new MouseInputAdapter() {
	        private Ordering draggedObject;
	        private int fromIndex;
	        public void mousePressed(final MouseEvent evt) {
	            draggedObject = ordering.getSelectedValue();
	            fromIndex = ordering.getSelectedIndex();
	        
	        }
	        public void mouseDragged(final MouseEvent evt) {
	        	
	            int toIndex = ordering.locationToIndex(evt.getPoint());
	            if (toIndex != fromIndex) {
	            
	            	lmodel.removeElementAt(fromIndex);
	            	lmodel.insertElementAt(draggedObject, toIndex);
	            	
	                fromIndex = toIndex;
	            }
	           
	        }
	    };
	    ordering.addMouseListener(mouseHandler);
	    ordering.addMouseMotionListener(mouseHandler);
	    
	    JPanel ord = new JPanel();
	    ord.setLayout(new FlowLayout(FlowLayout.LEADING));
	    ord.add(new JLabel(Main.getTranslator().getTranslation("orderingHead", Translator.LanguageFile.OPTION_DIALOG)), BorderLayout.NORTH);
	    ord.add(ordering);
		panel.add(Box.createVerticalStrut(20));
		GroupSize size = new GroupSize();
		panel.add(mode);
		panel.add(lineDrawMode);
		panel.add(backgroundMode);
		panel.add(xBackgroundPosition);
		panel.add(yBackgroundPosition);
		panel.add(lineBreakMode);
		panel.add(dataPositioningMode);
		panel.add(mouseMode);
		panel.add(keyboardMode);
		panel.add(yPositioningMode);
		panel.add(treeOrderingMode);
		panel.add(lookAndFeelMode);
		panel.add(additionalLookAndFeel);
		panel.add(filePath);
		panel.add(unitWidth);
		panel.add(unitHeight);
		panel.add(ord);
		panel.add(Box.createGlue());
		
		
		size.addPanel(panel);
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	
	static private void initSliders(ModifiedJSlider xMSlider, ModifiedJSlider yMSlider){
		
		if(BackgroundFactory.getBufferedBackground() != null && 
				BackgroundFactory.getBufferedBackground() instanceof DrawBackgroundImage &&
				Config.BACKGROUND_MODE.equals(OptionList.DRAW_LOADED_BACKGROUND)){
			DrawBackgroundImage background = (DrawBackgroundImage) BackgroundFactory.getBufferedBackground();
			JSlider xSlider = xMSlider.getJSlider();
			JSlider ySlider = yMSlider.getJSlider();
			xSlider.setMaximum(background.getWidth());
			ySlider.setMaximum(background.getHeight());
			
			xSlider.setMinimum(-background.getWidth());
			ySlider.setMinimum(-background.getHeight());
			
			xSlider.setValue(background.getXPosition());
			ySlider.setValue(background.getYPosition());
			
			
			
			xMSlider.addChangeListener(createChangeListener('x', background));
			yMSlider.addChangeListener(createChangeListener('y', background));
			
			xMSlider.setVisible(true);
			yMSlider.setVisible(true);
			
		}else{
			xMSlider.setVisible(false);
			yMSlider.setVisible(false);
		}
	}
	
	static private ChangeListener createChangeListener(final char a, final DrawBackgroundImage background){
		ChangeListener result = new ChangeListener() {
			private int oldValue;
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(e.getSource() instanceof JSlider){
					JSlider source = (JSlider) e.getSource();
					int val = source.getValue();
					if(oldValue != val){
						oldValue = val;
						switch(a){
						case 'x':
							background.setXPosition(val);
							break;
						case 'y':
							background.setYPosition(val);
							break;
						default:
							//do nothing
						}
						
					}
					Main.getMainFrame().getCanvas().repaint();
				}
				
			}
		};
		
		return result;
	}
	
	
	@Override
	public void dispose(){
		
		for(int i=0; i < Config.ORDERING.length; i++){
			Object val = ordering.getModel().getElementAt(i);
			if(val instanceof DrawPerson.Ordering){
				Config.ORDERING[i] = (DrawPerson.Ordering)val;
			}
		}
		Main.getMainFrame().getCanvas().repaint();
		super.dispose();
	}
	

}
