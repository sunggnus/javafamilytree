package tree.gui.search.line;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Main;
import translator.Translator;
import tree.gui.search.AbstractOverview;
import tree.gui.window.EditPersonDialog;
import tree.model.AgeException;
import tree.model.InvalidSexException;
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
	
	public PersonEditLine(final Person person, final AbstractOverview view){
		super(person,view);
		this.setMulti(this.getMulti()+3);
		delete = new JButton(Main.getTranslator().getTranslation("remove", Translator.OVERVIEW_JDIALOG));
		edit = new JButton(Main.getTranslator().getTranslation("edit", Translator.OVERVIEW_JDIALOG));
		visible =  new JButton(Main.getTranslator().getTranslation("makeVisible", Translator.OVERVIEW_JDIALOG));
		
		if(person.isVisible()){
			visible.setText(Main.getTranslator().getTranslation("makeInvisible", Translator.OVERVIEW_JDIALOG));
		}
		
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
				
				this.add(edit);
				this.add(delete);
				this.add(visible);

		
	}
	@Override
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.edit.getPreferredSize().getHeight());
	}

}
