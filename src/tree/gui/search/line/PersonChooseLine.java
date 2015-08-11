package tree.gui.search.line;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Main;

import translator.Translator;
import tree.gui.search.AbstractOverview;
import tree.model.AgeException;
import tree.model.InvalidSexException;
import tree.model.LineageException;
import tree.model.Person;

public class PersonChooseLine extends AbstractPersonLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4136144460332598376L;
	
	static public final int MODE_FATHER = 1;
	static public final int MODE_MOTHER = 2;
	static public final int MODE_CHILD = 3;
	static public final int MODE_PARTNER = 4;
	
	private JButton choose;
	/**
	 * 
	 * @param person
	 * @param caller
	 * @param mode (mother, father, child, partner)
	 * @param view
	 */
	public PersonChooseLine(final Person person,final Person caller,final int mode, final AbstractOverview view) {
		super(person, view);
		this.setMulti(this.getMulti()+1);
		
		choose = new JButton(Main.getTranslator().getTranslation("choose", Translator.LanguageFile.OVERVIEW_DIALOG));
		
		choose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
				switch(mode){
				case MODE_FATHER:
					caller.setFather(person);
					break;
				case MODE_MOTHER:
					caller.setMother(person);
					break;
				case MODE_CHILD:
					caller.addChild(person);
					break;
				case MODE_PARTNER:
					caller.addPartner(person);
					break;
				default:
					//do nothing
				}
				view.dispose();
				
				}catch(InvalidSexException e){
					if(mode==MODE_FATHER){
					javax.swing.JOptionPane.showMessageDialog(null, 
							Main.getTranslator().getTranslation("InvalidSexExceptionMale", Translator.LanguageFile.OVERVIEW_DIALOG));
					}
					else{
						javax.swing.JOptionPane.showMessageDialog(null, 
								Main.getTranslator().getTranslation("InvalidSexExceptionFemale", Translator.LanguageFile.OVERVIEW_DIALOG));
					
					}
				}catch(LineageException e){
					javax.swing.JOptionPane.showMessageDialog(null, 
							Main.getTranslator().getTranslation("LineageException", Translator.LanguageFile.OVERVIEW_DIALOG));
				}catch(AgeException e){
					javax.swing.JOptionPane.showMessageDialog(null, 
							Main.getTranslator().getTranslation("AgeException", Translator.LanguageFile.OVERVIEW_DIALOG));
				}
				
			}
			
		});
		

		this.add(choose);
		
		
		
	}
	
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.choose.getPreferredSize().getHeight());
	}

}
