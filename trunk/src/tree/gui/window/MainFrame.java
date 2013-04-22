package tree.gui.window;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicArrowButton;

import main.Config;
import main.Main;
import translator.Translator;
import tree.gui.CustomSliderUI;
import tree.gui.MainFrameKeyInputMap;
import tree.gui.MenuBar;
import tree.gui.TreeCanvas;
import tree.gui.field.AbstractField;
import tree.gui.field.EntryField;
import tree.gui.util.GUIUtils;
import tree.model.Person;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4722546962603518024L;
	
	private TreeCanvas canvas;
	
	private JScrollPane pane;
	
	private JPanel options;
	
	private JSlider slider;
	
	private final EntryField widthUnit;
	
	private final EntryField heightUnit;
	
	private final MainFrameKeyInputMap map;
	
	public MainFrame(){
		
		GUIUtils.loadLookAndFeel();
		
		GUIUtils.assignIcon(this);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Config.writeConfigIni();
				System.exit(0);
			}
		});
	
		
		
		this.setTitle(Main.getTranslator().getTranslation("title",Translator.MAIN_FRAME));
		this.setJMenuBar(new MenuBar());
		canvas = new TreeCanvas(Config.PERSON_HEIGHT, Config.PERSON_WIDTH, Config.SCALING);
		pane = new JScrollPane();
		
		pane.setViewportView(canvas);
		
		//create option panel
		options = new JPanel();
		
		BasicArrowButton moveLeft = new BasicArrowButton(BasicArrowButton.WEST);
		JButton moveRight = new BasicArrowButton(BasicArrowButton.EAST);
		
		int width = 75;

		final EntryField xCoordinate = new EntryField("X: ",AbstractField.DEFAULT_LABEL_WIDTH,width);
		xCoordinate.setContent("-1");
		final EntryField yCoordinate = new EntryField("Y: ",AbstractField.DEFAULT_LABEL_WIDTH,width);
		yCoordinate.setContent("-1");
		
		
		
		widthUnit = new EntryField(Main.getTranslator().getTranslation("defaultWidth",Translator.MAIN_FRAME),
				AbstractField.DEFAULT_LABEL_WIDTH,width);
		widthUnit.setContent(String.valueOf(Config.PERSON_WIDTH));
		
		heightUnit = new EntryField(Main.getTranslator().getTranslation("defaultHeight",Translator.MAIN_FRAME),
				AbstractField.DEFAULT_LABEL_WIDTH,width);
		heightUnit.setContent(String.valueOf(Config.PERSON_HEIGHT));
		
		heightUnit.addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				int height;
				try{
					height = Integer.parseInt(heightUnit.getContent());
				
				}catch(NumberFormatException f){
					height = Config.PERSON_HEIGHT;
				}
				canvas.setHeightUnit(height);
				canvas.repaint();
				revalidateTree();
				
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
		
		widthUnit.addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				int width;
				try{
					width = Integer.parseInt(widthUnit.getContent());
				
				}catch(NumberFormatException ff){
					width = Config.PERSON_WIDTH;
				}
				canvas.setWidthUnit(width);
				canvas.repaint();
				revalidateTree();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				this.changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				this.changedUpdate(e);
			}
			
		});
		
		
		
		moveLeft.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int x=-1;
				int y=-1;
				try{
					x=Integer.parseInt(xCoordinate.getContent());
					y=Integer.parseInt(yCoordinate.getContent());
				}catch(NumberFormatException e){
					//do nothing
				}
				
				for(Person person : Main.getMainNode().getPersons()){
					if((x==-1||y==-1)||(person.getGeneration()>=y&&person.getXPosition()>x)){
					person.setXPosition(person.getXPosition()-1, false);
					
					}
					
				}
				canvas.generateDrawStuff();
				pane.getViewport().revalidate();
				
			}
			
		});
		
		moveRight.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int x=-1;
				int y=-1;
				try{
					x=Integer.parseInt(xCoordinate.getContent());
					y=Integer.parseInt(yCoordinate.getContent());
				}catch(NumberFormatException e){
					//do nothing
				}
				
				for(Person person : Main.getMainNode().getPersons()){
					if((x==-1||y==-1)||(person.getGeneration()>=y&&person.getXPosition()>x)){
					person.setXPosition(person.getXPosition()+1, false);
					}
					
				}
				canvas.generateDrawStuff();
				pane.getViewport().revalidate();
				
			}
			
		});
		
		options.setLayout(new GridLayout(3,2,1,5));
		options.add(moveLeft);
		options.add(moveRight);
		
		options.add(xCoordinate);
		options.add(yCoordinate);
		
		options.add(widthUnit);
		options.add(heightUnit);
		JLabel zoom = new JLabel("  Zoom:");
		slider = new JSlider();
		slider.setUI(new CustomSliderUI(slider));
		slider.setOrientation(JSlider.VERTICAL);
		slider.setValue(100); //this corresponds to 100%
		
		slider.setMinimum(10); //10 %
		slider.setMaximum(300); //400 %
		slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    Hashtable<Integer, JLabel> sliderTable = new Hashtable<Integer,JLabel>();
	    sliderTable.put(10, new JLabel("10%"));
	    for(int i=50; i<=300; i=i+50){
	    	sliderTable.put(i,new JLabel(i + "%"));
	    }
	    
	    slider.setLabelTable(sliderTable);
	    slider.setName(String.valueOf(slider.getValue()));
		
		slider.addChangeListener(new ChangeListener(){
			private int oldValue;
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(arg0.getSource() instanceof JSlider){
					JSlider slider = (JSlider) arg0.getSource();
					int val = slider.getValue();
					if(oldValue != val){
					
					oldValue = val;
					double percent = ((double)( val) /( 100.0));
					canvas.setScaling(percent);
					canvas.repaint();

					canvas.refreshBounds();
					pane.getViewport().revalidate();
					}
				}
				
			}
			
		});
		
		
		
		///////////////////////////////////////////////
		
		
		this.add(pane);
		this.add(options,BorderLayout.NORTH);
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BorderLayout());
		sliderPanel.add(zoom,BorderLayout.NORTH);
		sliderPanel.add(slider);
		this.add(sliderPanel,BorderLayout.WEST);
		this.pack();
		this.setSize(600, 800);
		slider.requestFocus();
		
		
		map = new MainFrameKeyInputMap((JComponent)this.getContentPane());
		map.deactivateKeyBindings();
		map.setDefaultKeyBindings();
		
		this.setVisible(true);
	}
	
	public void revalidateTree(){
		this.canvas.generateDrawStuff();
		this.pane.getViewport().revalidate();
		
	}
	
	public TreeCanvas getCanvas(){
		return this.canvas;
	}
	
	public void changeLanguage(){
		this.setJMenuBar(new MenuBar());
		this.setTitle(Main.getTranslator().getTranslation("title",Translator.MAIN_FRAME));
		this.heightUnit.setLabelText(Main.getTranslator().getTranslation("defaultHeight",Translator.MAIN_FRAME));
		this.widthUnit.setLabelText(Main.getTranslator().getTranslation("defaultWidth",Translator.MAIN_FRAME));
		this.revalidate();
		this.canvas.repaint();
		this.pane.getViewport().revalidate();
	}
	
	public MainFrameKeyInputMap getKeyInputMap(){
		return this.map;
	}
	
	public void refreshSlider(){
		slider.setUI(new CustomSliderUI(slider));
	}

}
