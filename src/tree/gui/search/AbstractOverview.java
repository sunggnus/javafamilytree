package tree.gui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.Main;

import translator.Translator;
import tree.gui.search.line.factory.AbstractOverviewLineFactory;
import tree.gui.util.IconUtil;



public abstract class AbstractOverview extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1080814803414028806L;
	
	
	private AbstractOverviewLineFactory factory;
	
	public AbstractOverview(AbstractOverviewLineFactory factory, Filter filter){
		IconUtil.assignIcon(this);
		this.setFactory(factory);
		this.setFilter(filter);
		
		setFilterText(new JTextField());
		getFilterText().setPreferredSize(new Dimension(100,25));
		
		getFilterText().getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				filterView();		
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				filterView();	
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				filterView();	
			}
			
		});
		
		
		
	}


	private JPanel center;


	private JScrollPane pane;


	private int pSize;


	private JTextField filterText;


	private Filter filter;


	private JPanel north;


	private JButton addButton;

	protected AbstractOverviewLineFactory getFactory() {
		return factory;
	}

	protected void setFactory(AbstractOverviewLineFactory factory) {
		this.factory = factory;
	}

	public JPanel getCenter() {
		return this.center;
	}

	protected void setCenter(JPanel center) {
		this.center = center;
	}

	protected JScrollPane getPane() {
		return pane;
	}

	protected void setPane(JScrollPane pane) {
		this.pane = pane;
	}

	protected int getpSize() {
		return pSize;
	}

	protected void setpSize(int pSize) {
		this.pSize = pSize;
	}

	public void actualizeSize() {
		int pCounter = this.getCenter().getComponentCount();
		getCenter().setLayout(new GridLayout(pCounter,1));
		getCenter().setPreferredSize(new Dimension(300,pCounter*(getpSize()+5)));
		getCenter().setMinimumSize(getCenter().getPreferredSize());
		getCenter().repaint();
		getPane().getViewport().revalidate();
		
	}
	
	abstract public void filterView();

	protected JTextField getFilterText() {
		return filterText;
	}

	protected void setFilterText(JTextField filterText) {
		this.filterText = filterText;
	}

	protected Filter getFilter() {
		return filter;
	}

	protected void setFilter(Filter filter) {
		this.filter = filter;
	}
	/**
	 * should be only called by constructors of AbstractOverview subclasses
	 */
	protected void constructOverview(boolean showAddButton){
		int pCounter = getCenter().getComponentCount();
		getCenter().setLayout(new GridLayout(pCounter,1));
		getCenter().setPreferredSize(new Dimension(300,pCounter*(getpSize()+5)));
		
		setPane(new JScrollPane());
		getPane().setViewportView(getCenter());
		
		this.add(getPane());
		
		setNorth(new JPanel());
		getNorth().setLayout(new FlowLayout());
		
		
		
		this.setAddButton(new JButton());
		
		if(showAddButton){
			getNorth().add(this.getAddButton());
		}
			getNorth().add(new JLabel(Main.getTranslator().getTranslation("search", Translator.OVERVIEW_JDIALOG)));
			getNorth().add(getFilterText());
			
			this.add(getNorth(),BorderLayout.NORTH);
			
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			this.pack();
			//the request focus does not work before the dialog is visible without calling pack first
			this.getFilterText().requestFocus();
			this.setSize(700, 500);
		
	}

	protected JPanel getNorth() {
		return north;
	}

	protected void setNorth(JPanel north) {
		this.north = north;
	}

	protected JButton getAddButton() {
		return addButton;
	}

	protected void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

}
