package tree.gui.search.line;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SpringLayout;

import tree.gui.search.AbstractOverview;
import tree.model.Person;
import tree.model.PersonUtil;

public abstract class AbstractPersonLine extends AbstractLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3628996808953937374L;
	
	
	private int multi;
	
	

	private JLabel givenName;
	
	private JLabel familyName;
	private JLabel birthName;
	private JLabel birthday;
	private JLabel deathday;
	
	

	
	protected AbstractPersonLine(final Person person, final AbstractOverview view){
		multi = 5;
		
		givenName = new JLabel(person.getGivenName());
		familyName = new JLabel(person.getFamilyName());
		birthName = new JLabel(person.getBirthName());
		birthday = new JLabel("");
		deathday = new JLabel("");
		if(person.getBirthdate()!=null){
		birthday.setText(PersonUtil.calendarToSimpleString(person.getBirthdate()));
		}
		if(person.getDeathdate()!=null){
		deathday.setText(PersonUtil.calendarToSimpleString(person.getDeathdate()));
		}
		
		
		
		//layout stuff
		SpringLayout layout = new SpringLayout();
		//givenName
		layout.putConstraint(SpringLayout.WEST, this.givenName,
				5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, this.givenName, 
				5, SpringLayout.NORTH, this);
		//familyName
		layout.putConstraint(SpringLayout.WEST, this.familyName,
				5, SpringLayout.EAST, this.givenName);
		
		layout.putConstraint(SpringLayout.NORTH, this.familyName,
				5, SpringLayout.NORTH, this);
		//birthName
		layout.putConstraint(SpringLayout.WEST, this.birthName,
				5, SpringLayout.EAST, this.familyName);
		
		layout.putConstraint(SpringLayout.NORTH, this.birthName,
				5, SpringLayout.NORTH, this);
		//birthday
		layout.putConstraint(SpringLayout.WEST, this.birthday,
				5, SpringLayout.EAST, this.birthName);
		
		layout.putConstraint(SpringLayout.NORTH, this.birthday,
				5, SpringLayout.NORTH, this);
		//deathday
		layout.putConstraint(SpringLayout.WEST, this.deathday,
				5, SpringLayout.EAST, this.birthday);
		
		layout.putConstraint(SpringLayout.NORTH, this.deathday,
				5, SpringLayout.NORTH, this);
		
		
		
		
		this.add(givenName);
		this.add(familyName);
		this.add(birthName);
		this.add(birthday);
		this.add(deathday);
		
		
		this.setLayout(layout);
		
	}
	
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.familyName.getPreferredSize().getHeight());
	}
	
	public void setMainSize(int width,int height){
		Dimension pref = new Dimension(width,height);
		for(Component comp : this.getComponents()){
			comp.setPreferredSize(pref);
		}
		
		this.setPreferredSize(new Dimension(width*multi,height));
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
	
	protected int getMulti() {
		return multi;
	}

	protected void setMulti(int multi) {
		this.multi = multi;
	}


}
