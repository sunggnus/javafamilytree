package tree.gui.search.line;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SpringLayout;

import main.Main;
import translator.Translator;
import tree.gui.search.AbstractOverview;
import tree.gui.window.EditPersonDialog;
import tree.model.AgeException;
import tree.model.InvalidSexException;
import tree.model.Person;
import tree.model.PersonUtil;

public class PersonEditLine extends AbstractPersonLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7784309137470222352L;
	
	
	
	private JButton delete;
	
	private JButton edit;
	
	public PersonEditLine(final Person person, final AbstractOverview view){
		super(person,view);
		this.setMulti(this.getMulti()+2);
		delete = new JButton(Main.getTranslator().getTranlation("remove", Translator.OVERVIEW_JDIALOG));
		edit = new JButton(Main.getTranslator().getTranlation("edit", Translator.OVERVIEW_JDIALOG));
		final PersonEditLine thisLine = this;
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
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
					Main.getMainNode().refreshGeneration();
					Main.getMainFrame().getCanvas().generateDrawStuff();
					view.getCenter().remove(thisLine);
					
					view.actualizeSize();
					
				} catch (InvalidSexException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AgeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
					getBirthday().setText(PersonUtil.calendarToSimpleString(person.getBirthdate()));
					}
					if(person.getDeathdate()!=null){
					getDeathday().setText(PersonUtil.calendarToSimpleString(person.getDeathdate()));
					}
				
			}
			
		});
				//layout stuff
				SpringLayout layout = (SpringLayout) this.getLayout();
				
		
				//edit
				layout.putConstraint(SpringLayout.WEST, this.edit,
						5, SpringLayout.EAST, this.getDeathday());
				
				layout.putConstraint(SpringLayout.NORTH, this.edit,
						5, SpringLayout.NORTH, this);
				
				//delete
				layout.putConstraint(SpringLayout.WEST, this.delete,
						5, SpringLayout.EAST, this.edit);
				
				layout.putConstraint(SpringLayout.NORTH, this.delete,
						5, SpringLayout.NORTH, this);
				
				this.add(edit);
				this.add(delete);
				

				//this.setLayout(layout);
		
	}
	@Override
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.edit.getPreferredSize().getHeight());
	}

}
