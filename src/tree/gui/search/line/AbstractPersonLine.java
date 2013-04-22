package tree.gui.search.line;


import javax.swing.JLabel;

import tree.gui.search.AbstractOverview;
import tree.model.Person;
import tree.model.Utils;

public abstract class AbstractPersonLine extends AbstractLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3628996808953937374L;
	
	
	
	
	

	private JLabel givenName;
	
	private JLabel familyName;
	private JLabel birthName;
	private JLabel birthday;
	private JLabel deathday;
	
	

	
	protected AbstractPersonLine(final Person person, final AbstractOverview view){
		super();
		this.setMulti(5);
		
		givenName = new JLabel(person.getGivenName());
		familyName = new JLabel(person.getFamilyName());
		birthName = new JLabel(person.getBirthName());
		birthday = new JLabel("");
		deathday = new JLabel("");
		if(person.getBirthdate()!=null){
		birthday.setText(Utils.calendarToSimpleString(person.getBirthdate()));
		}
		if(person.getDeathdate()!=null){
		deathday.setText(Utils.calendarToSimpleString(person.getDeathdate()));
		}
		
		
		
		
		
		this.add(givenName);
		this.add(familyName);
		this.add(birthName);
		this.add(birthday);
		this.add(deathday);
		
		
		
		
	}
	
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.familyName.getPreferredSize().getHeight());
	}
	
	
	
	protected JLabel getGivenName() {
		return givenName;
	}

	protected void setGivenName(JLabel givenName) {
		this.givenName = givenName;
	}

	protected JLabel getFamilyName() {
		return familyName;
	}

	protected void setFamilyName(JLabel familyName) {
		this.familyName = familyName;
	}

	protected JLabel getBirthName() {
		return birthName;
	}

	protected void setBirthName(JLabel birthName) {
		this.birthName = birthName;
	}

	protected JLabel getBirthday() {
		return birthday;
	}

	protected void setBirthday(JLabel birthday) {
		this.birthday = birthday;
	}

	protected JLabel getDeathday() {
		return deathday;
	}

	protected void setDeathday(JLabel deathday) {
		this.deathday = deathday;
	}
	
	

}
