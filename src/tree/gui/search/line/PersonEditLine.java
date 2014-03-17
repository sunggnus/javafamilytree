package tree.gui.search.line;



import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import main.Config;
import main.Main;
import main.OptionList;
import translator.Translator;
import tree.gui.TreeCanvas;
import tree.gui.draw.DrawPerson;
import tree.gui.search.AbstractOverview;
import tree.gui.window.EditPersonDialog;
import tree.model.Person;
import tree.model.Utils;

public class PersonEditLine extends AbstractPersonLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7784309137470222352L;
	
	
	
	private JButton delete;
	
	private JButton edit;
	
	private JButton visible;
	
	private JButton focus;
	
	public PersonEditLine(final Person person, final AbstractOverview view){
		super(person,view);
		this.setMulti(this.getMulti()+4);
		delete = new JButton(Main.getTranslator().getTranslation("remove", Translator.OVERVIEW_JDIALOG));
		edit = new JButton(Main.getTranslator().getTranslation("edit", Translator.OVERVIEW_JDIALOG));
		visible =  new JButton(Main.getTranslator().getTranslation("makeVisible", Translator.OVERVIEW_JDIALOG));
		focus = new JButton(Main.getTranslator().getTranslation("focus", Translator.OVERVIEW_JDIALOG));
		
		if(person.isVisible()){
			visible.setText(Main.getTranslator().getTranslation("makeInvisible", Translator.OVERVIEW_JDIALOG));
		}
		
		final PersonEditLine thisLine = this;
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			
					person.disconnect();
					Main.getMainNode().removePerson(person);
					if(Main.getMainNode().getPerson()==person){
						if(Main.getMainNode().getPersons().size()>0){
							Main.getMainNode().setPerson(Main.getMainNode().getPersons().get(0));
						}
						else{
							Main.getMainNode().setPerson(null);
						}
					}
				//	Main.getMainNode().refreshGeneration();
					if(Config.Y_POSITIONING_MODE== OptionList.Y_AUTO_POSITIONING)
						Utils.determineTreeGenerations(person);
					Main.getMainFrame().getCanvas().generateDrawStuff();
					view.getCenter().remove(thisLine);
					
					view.actualizeSize();
					
				
				
			}
			
		});
		
		edit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new EditPersonDialog(person);
				getGivenName().setText(person.getGivenName());
				getFamilyName().setText(person.getFamilyName());
				getBirthday().setText("");
				getDeathday().setText("");
				if(person.getBirthdate()!=null){
					getBirthday().setText(Utils.calendarToSimpleString(person.getBirthdate()));
					}
					if(person.getDeathdate()!=null){
					getDeathday().setText(Utils.calendarToSimpleString(person.getDeathdate()));
					}
				
			}
			
		});
		
		visible.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				person.setVisible(!person.isVisible());
				
				if(person.isVisible()){
					visible.setText(Main.getTranslator().getTranslation("makeInvisible", Translator.OVERVIEW_JDIALOG));
				}else{
					visible.setText(Main.getTranslator().getTranslation("makeVisible", Translator.OVERVIEW_JDIALOG));
				}
				
				Main.getMainFrame().getCanvas().repaint();
				
			}
			
		});
		
		focus.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TreeCanvas canvas = Main.getMainFrame().getCanvas();
				double scaling = canvas.getScaling();
				int widthUnit = canvas.getWidthUnit();
				int heightUnit = canvas.getHeightUnit();
				
				int x = (int) (person.getHalfXPosition() * scaling * widthUnit * 1.1);
				int y = (int) (person.getGeneration() * scaling * heightUnit * 1.3 + DrawPerson.DEFAULT_MARGING);
				
				JScrollPane pane = Main.getMainFrame().getTreeScrollPane();
				
				pane.getViewport().setViewPosition(new Point(x,y));
				
			}
			
		});
				
				this.add(edit);
				this.add(delete);
				this.add(visible);
				this.add(focus);

		
	}
	@Override
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.edit.getPreferredSize().getHeight());
	}

}
