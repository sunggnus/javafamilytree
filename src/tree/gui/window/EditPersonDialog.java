package tree.gui.window;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.Config;
import main.Main;
import translator.Translator;
import tree.gui.InvalidInputException;
import tree.gui.draw.DrawImage;
import tree.gui.field.AbstractField;
import tree.gui.field.DropDownField;
import tree.gui.field.EntryField;
import tree.gui.field.ModifiedCheckBox;
import tree.gui.field.ModifiedJButton;
import tree.gui.field.PersonEditField;
import tree.gui.util.GUIUtils;
import tree.gui.util.ImageLoaderDialog;
import tree.model.Person;
import tree.model.Utils;

public class EditPersonDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7819630660872075922L;
	
	private Person editablePerson;
	
	private EntryField givenName;
	
	private EntryField familyName;
	
	private EntryField birthName;
	
	private EntryField commentOne;
	
	/**
	 * this field is used for the birth village
	 */
	private EntryField location;
	
	/**
	 * this field is used for trade of the person
	 */
	private EntryField trade;
	
	private EntryField xPosition;
	
	private EntryField dateOfBirth;
	
	private EntryField dateOfDeath;
	
	private DropDownField<String> sex; 
	
	private ModifiedCheckBox alive;
	
	private ModifiedCheckBox visible;
	
	private PersonEditField mother;
	
	private PersonEditField father;
	
	
	
	private JButton accept;
	
	
	
	private final DrawImage pictureContent;
	
	private boolean isNewPerson;
	
	
	public EditPersonDialog(Person person){
		this.editablePerson = person;
		pictureContent = new DrawImage();
		
		this.construct(true);
		
	}
	
	
	public EditPersonDialog(boolean addPerson){
		pictureContent = new DrawImage();
		this.construct(addPerson);		
	}
	
	private void construct(boolean addPerson){
		this.isNewPerson = false;
		if(!addPerson){
			this.editablePerson = (Person) javax.swing.JOptionPane.showInputDialog(this
					, Main.getTranslator().getTranslation("choosePerson", Translator.LanguageFile.EDIT_PERSON_DIALOG)
					, Main.getTranslator().getTranslation("editPerson", Translator.LanguageFile.EDIT_PERSON_DIALOG), 
					javax.swing.JOptionPane.NO_OPTION, 
					null, Main.getMainNode().getPersons().toArray()
					, new JComboBox<Person>());
		}
		GUIUtils.assignIcon(this);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel(){
			private static final long serialVersionUID = 3304353361624648625L;

			@Override
			public Component add(Component comp){
				super.add(comp);
				super.add(Box.createVerticalStrut(5));
				return comp;
			}
		};
		
		
		givenName = new EntryField(Main.getTranslator().getTranslation("givenName", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		familyName = new EntryField(Main.getTranslator().getTranslation("familyName", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		birthName = new EntryField(Main.getTranslator().getTranslation("birthName", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				, AbstractField.DEFAULT_LABEL_WIDTH);
		location = new EntryField(Main.getTranslator().getTranslation("location", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				, AbstractField.DEFAULT_LABEL_WIDTH);
		trade = new EntryField(Main.getTranslator().getTranslation("trade", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				, AbstractField.DEFAULT_LABEL_WIDTH);
		
		commentOne = new EntryField(Main.getTranslator().getTranslation("comment", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				, AbstractField.DEFAULT_LABEL_WIDTH);
		xPosition = new EntryField(Main.getTranslator().getTranslation("relX", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				, AbstractField.DEFAULT_LABEL_WIDTH);
		xPosition.setToolTip(Main.getTranslator().getTranslation("relX_tool", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		dateOfBirth = new EntryField(Main.getTranslator().getTranslation("dateOfBirth", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		dateOfDeath = new EntryField(Main.getTranslator().getTranslation("dateOfDeath", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		sex = new DropDownField<String>(Main.getTranslator().getTranslation("sex", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		sex.add(Main.getTranslator().getTranslation("male", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		sex.add(Main.getTranslator().getTranslation("female", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		sex.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED && e.getItem() instanceof String){
					editablePerson.setSex(((String) sex.getSelectedItem()).
							equals(Main.getTranslator().getTranslation("female", Translator.LanguageFile.EDIT_PERSON_DIALOG))?Person.FEMALE:Person.MALE);
			
				}
				
			}
			
		});
		
		
		alive = new ModifiedCheckBox(Main.getTranslator().getTranslation("alive", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		
		visible = new ModifiedCheckBox(Main.getTranslator().getTranslation("visible", Translator.LanguageFile.EDIT_PERSON_DIALOG)
				,AbstractField.DEFAULT_LABEL_WIDTH);
		
		mother = new PersonEditField(editablePerson, PersonEditField.MODE_MOTHER);
		father = new PersonEditField(editablePerson,PersonEditField.MODE_FATHER);
		accept = new JButton(Main.getTranslator().getTranslation("generatePerson", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		
		
		
		accept.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveData();		
				Main.getMainFrame().revalidateTree();
			}
			
		});
		
		accept.setMinimumSize(new Dimension(AbstractField.DEFAULT_WIDTH,(int)accept.getMinimumSize().getHeight()));
		GUIUtils.normalizeSize(accept);
		
		
		
		panel.add(visible);
		panel.add(givenName);
		panel.add(familyName);
		panel.add(birthName);
		panel.add(location);
		panel.add(trade);
		panel.add(commentOne);
		panel.add(xPosition);
		panel.add(sex);
		panel.add(alive);
		panel.add(dateOfBirth);
		panel.add(dateOfDeath);
		panel.add(mother);
		panel.add(father);
		
		int maxwidth = 0;
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	
		
		
		
		JPanel orderPanel = new JPanel();
		JPanel secOrder = new JPanel();
		secOrder.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(secOrder,BorderLayout.NORTH);
		secOrder.add(orderPanel,BorderLayout.CENTER);
		orderPanel.setLayout(new BoxLayout(orderPanel,BoxLayout.Y_AXIS));
		orderPanel.add(panel);
		
		//image stuff
		JPanel picturePanel = new JPanel();
		
	
		ModifiedJButton loadImage = new ModifiedJButton(Main.getTranslator().getTranslation("loadImage", Translator.LanguageFile.EDIT_PERSON_DIALOG),
				-10, AbstractField.DEFAULT_LABEL_WIDTH);
		ModifiedJButton deleteImage = new ModifiedJButton(Main.getTranslator().getTranslation("removeImage", Translator.LanguageFile.EDIT_PERSON_DIALOG),
				-10, AbstractField.DEFAULT_LABEL_WIDTH);
		
		
		secOrder.add(picturePanel,BorderLayout.EAST);
		picturePanel.setLayout(new BoxLayout(picturePanel,BoxLayout.Y_AXIS));
		loadImage.getJButton().addActionListener(new ImageLoaderDialog(pictureContent));
		deleteImage.getJButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {			
				pictureContent.deleteImage();			
			}
			
		});
		
		pictureContent.setPreferredSize(new Dimension(50,50));
		
		picturePanel.add(pictureContent);
		
		picturePanel.add(loadImage);
		picturePanel.add(deleteImage);
		
		//visibility stuff added to picture stuff
		ModifiedJButton visibleParents = new ModifiedJButton(Main.getTranslator().
				getTranslation("visibleParents", Translator.LanguageFile.EDIT_PERSON_DIALOG),
				-10, AbstractField.DEFAULT_LABEL_WIDTH);
		
		visibleParents.getJButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editablePerson.setAncestorVisibility(true);
				Main.getMainFrame().getCanvas().repaint();
			}	
		});
		
		ModifiedJButton invisibleParents = new ModifiedJButton(Main.getTranslator().
				getTranslation("invisibleParents", Translator.LanguageFile.EDIT_PERSON_DIALOG),
				-10, AbstractField.DEFAULT_LABEL_WIDTH);
		
		invisibleParents.getJButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editablePerson.setAncestorVisibility(false);
				Main.getMainFrame().getCanvas().repaint();
			}	
		});
		
		ModifiedJButton visibleChildren = new ModifiedJButton(Main.getTranslator().
				getTranslation("visibleChildren", Translator.LanguageFile.EDIT_PERSON_DIALOG),
				-10, AbstractField.DEFAULT_LABEL_WIDTH);
		
		visibleChildren.getJButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editablePerson.setDescendantVisibility(true);
				Main.getMainFrame().getCanvas().repaint();
			}	
		});
		
		ModifiedJButton invisibleChildren = new ModifiedJButton(Main.getTranslator().
				getTranslation("invisibleChildren", Translator.LanguageFile.EDIT_PERSON_DIALOG),
				-10, AbstractField.DEFAULT_LABEL_WIDTH);
		
		invisibleChildren.getJButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				editablePerson.setDescendantVisibility(false);
				Main.getMainFrame().getCanvas().repaint();
			}	
		});
		
		picturePanel.add(Box.createVerticalStrut(15));
		picturePanel.add(visibleParents);
		picturePanel.add(invisibleParents);
		picturePanel.add(visibleChildren);
		picturePanel.add(invisibleChildren);
		
		maxwidth = 0;
		for(Component comp : picturePanel.getComponents()){
			if(comp.getPreferredSize().getWidth()>maxwidth){
				maxwidth = (int) comp.getPreferredSize().getWidth();
			}
		}
		picturePanel.setPreferredSize(new Dimension(maxwidth+10,(int)picturePanel.getPreferredSize().getHeight()));
		for(Component comp : picturePanel.getComponents()){
			if(comp instanceof ModifiedJButton){
				ModifiedJButton but = (ModifiedJButton) comp;
				but.getJButton().setPreferredSize(new Dimension(maxwidth,(int)comp.getPreferredSize().getHeight()));
				but.getJButton().setMaximumSize(new Dimension(maxwidth,(int)comp.getPreferredSize().getHeight()));
			}
			comp.setMinimumSize(new Dimension(maxwidth,(int)comp.getPreferredSize().getHeight()));
			
		}
		
		//south stuff
		
		
		
		
		
		PersonEditField partners = new PersonEditField(editablePerson, 
				PersonEditField.MODE_PARTNER);
		partners.setBorder(new  LineBorder(Color.BLUE, 2, false));
		PersonEditField children = new PersonEditField(editablePerson,
				PersonEditField.MODE_CHILD);
		children.setBorder(new  LineBorder(Color.BLUE, 2, false));
		
		
		
		
		
		orderPanel.add(partners);
		orderPanel.add(children);
		JPanel acceptPanel = new JPanel();
		acceptPanel.add(accept);
		orderPanel.add(acceptPanel);
		
		
		
		this.loadData();
		this.ini();
		
		mother.setOwner(this.editablePerson);
		father.setOwner(this.editablePerson);
		partners.setOwner(this.editablePerson);
		children.setOwner(this.editablePerson);
		
		
		this.pack();
		this.setVisible(true);
	}
	
	
	
	
	
	private void ini(){
		if(this.editablePerson == null){
			this.isNewPerson = true;
			this.editablePerson =new Person(givenName.getContent(), 
					familyName.getContent(), ((String) sex.getSelectedItem()).
					equals(Main.getTranslator().getTranslation("female", Translator.LanguageFile.EDIT_PERSON_DIALOG))?Person.FEMALE:Person.MALE
					);
		}
		
		if(Main.getMainNode().getPerson()==null){
			Main.getMainNode().setPerson(this.editablePerson);
			this.editablePerson.setGeneration(0);
		}
		if(!Main.getMainNode().getPersons().contains(this.editablePerson)){
			Main.getMainNode().addPerson(this.editablePerson);
		}
	}
	
	public void setEditablePerson(Person person){
		this.editablePerson = person;
	}
	
	private int[] readDate(String txt) throws InvalidInputException{
		if(txt.length() == 0){
			return null;
		}
		
		String[] birth = txt.split("\\.");
		InvalidInputException exp = new InvalidInputException(
				Main.getTranslator().getTranslation("invalidInput", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		
		if(birth.length!=3){
			throw exp;
		}
		try{
		int day = Integer.parseInt(birth[0]);
		int month = Integer.parseInt(birth[1]);
		int year = Integer.parseInt(birth[2]);
		int[] result = new int[]{day,month,year};
		return result;
		}catch(NumberFormatException e){
			throw exp;
		}
	}
	
	
	public void saveData(){
		try{
		int[] birth = this.readDate(dateOfBirth.getContent());
		int[] death = this.readDate(dateOfDeath.getContent());
		
		
		
		
		editablePerson.setGivenName(givenName.getContent());
		editablePerson.setFamilyName(familyName.getContent());
		editablePerson.setBirthName(birthName.getContent());
		editablePerson.setTrade(trade.getContent());
		editablePerson.setLocation(location.getContent());
		editablePerson.setCommentOne(commentOne.getContent());
		editablePerson.setSex(((String) sex.getSelectedItem()).
					equals(Main.getTranslator().getTranslation("female", Translator.LanguageFile.EDIT_PERSON_DIALOG))?Person.FEMALE:Person.MALE);
		if(!Config.XAUTO_POSITIONING){
			int xpos = Integer.parseInt(xPosition.getContent());
			editablePerson.setXPosition(xpos, false);
		}
		
		
		
		
		
		boolean alive = this.alive.isSelected();
		alive = alive && (death == null);
		editablePerson.setAlive(alive);
		editablePerson.setVisible(this.visible.isSelected());
		if(birth != null){
			this.editablePerson.setBirthdate(birth[0], birth[1], birth[2]);
		}
		
		if(death != null){
			this.editablePerson.setDeathdate(death[0],death[1],death[2]);
		}
		this.editablePerson.setPicture(this.pictureContent.getImage());
		//if the saving was successful the window can be closed
		this.isNewPerson = false;
		this.dispose();
		
		
		
		
		}catch(InvalidInputException e){
			javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
		}catch(NumberFormatException k){
			javax.swing.JOptionPane.showMessageDialog(this,
					Main.getTranslator().getTranslation("validX", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		}
	}
	
	public void loadData(){
		if(this.editablePerson==null){
			return;
		}
		this.givenName.setContent(this.editablePerson.getGivenName());
		this.familyName.setContent(this.editablePerson.getFamilyName());
		this.birthName.setContent(this.editablePerson.getBirthName());
		this.trade.setContent(this.editablePerson.getTrade());
		this.location.setContent(this.editablePerson.getLocation());
		this.commentOne.setContent(this.editablePerson.getCommentOne());
		this.dateOfBirth.setContent(Utils.calendarToSimpleString(
				this.editablePerson.getBirthdate()));
		this.dateOfDeath.setContent(Utils.calendarToSimpleString(
				this.editablePerson.getDeathdate()));
		this.alive.setSelected(this.editablePerson.isAlive());
		this.visible.setSelected(this.editablePerson.isVisible());
		this.sex.setSelectedItem(this.editablePerson.isFemale()?
				Main.getTranslator().getTranslation("female", Translator.LanguageFile.EDIT_PERSON_DIALOG):Main.getTranslator().getTranslation("male", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		this.father.setOwner(this.editablePerson);
		this.mother.setOwner(this.editablePerson);
		this.xPosition.setContent(String.valueOf(this.editablePerson.getXPosition()));
		this.pictureContent.setImage(this.editablePerson.getPicture());
	}
	
	@Override
	public void dispose(){
		if(this.isNewPerson){
			Main.getMainNode().removePerson(this.editablePerson);
		}
		super.dispose();
		Main.getMainFrame().getCanvas().repaint();
	}

}
