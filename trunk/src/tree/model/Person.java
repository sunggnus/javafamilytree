package tree.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class Person implements Serializable{
	
	/**
	 * unique class id for serialization
	 */
	private static final long serialVersionUID = 6986513043854273482L;
	/**
	 * contains the id for the next person that will be created
	 */
	public static long NEXT_ID=0;

	static public final Person NULL = new Person("", "", false);
	
	static public final boolean FEMALE = true;
	
	static public final boolean MALE = !FEMALE;
	
	static public final int IS_YOUNGER_THAN=-1;
	
	static public final int UNDECIDABLE = 0;
	
	static public final int IS_OLDER_THAN=1;
	
	static public final int NO_GENERATION = -256;
	
	static public final int NO_VALID_POSITION = -256;
	
	static public final int SIBLING_DISTANCE = 2;
	
	static public final int PARTNER_DISTANCE = 1;
	

	
	

	/**
	 * contains the family name of the person
	 */
	private String familyName;
	/**
	 * contains the given Name of the person
	 */
	private String givenName;
	/**
	 * contains the birth name of the person
	 */
	private String birthName;
	
	/**
	 * contains the location where this person is born
	 */
	private String location;
	
	/**
	 * contains the trade of the person
	 */
	private String trade;
	/**
	 * contains comments
	 */
	private String commentOne;
	/**
	 * this boolean is true if the person is still alive
	 */
	private boolean alive;
	/**
	 * true if the person is female
	 */
	private boolean sex;
	
	/**
	 * if the father is null, he is unknown
	 * the father must be male
	 */
	private Person father;
	/**
	 * if the mother is null, she is unknown
	 * the mother must be female
	 */
	private Person mother;
	
	/**
	 * the partners of a person
	 * the last entry within the list is the actual partner
	 */
	private List<Person> partners;
	
	/**
	 * the children of a person
	 */
	private List<Person> children;
	
	/**
	 * the birth date of a person
	 */
	
	private GregorianCalendar birthdate;
	
	/**
	 * the death date of a person
	 * it is null if the person is still alive or it is not known
	 */
	
	private GregorianCalendar deathdate;
	
	/**
	 * contains the generation in which this person was born
	 * the oldest generation has the number 0
	 * than 1 and so on
	 */
	private int generation;
	
	/**
	 *contains the relative x position of the person within the 
	 *tree this is important for positioning the person when drawing the tree 
	 *the positioning starts with zero
	 * 
	 */
	private int xPosition;
	
	
	/**
	 * this boolean is true if the person should be visible within the family tree else false
	 */
	private boolean visible;
	
	/**
	 * contains a picture of the person
	 */
	private transient BufferedImage picture;
	

	
	
	private long ID;
	
	
	private LinkedList<ConnectionListener> listeners = new LinkedList<ConnectionListener>();
	
	
	/**
	 * 
	 * @param givenName the given name of the person
	 * @param familyName the family name of the person
	 * @param sex is true if the Person is female use the public constants FEMALE and MALE
	 */
	public Person(String givenName, String familyName, boolean sex){
		this.givenName = givenName;
		this.familyName = familyName;
		this.sex = sex;
		
		this.additonalConstructorStuff();
		
	}
	
	
	/**
	 * constructs a new person
	 * @param givenName the given name of the person
	 * @param familyName the family name of the person
	 * @param alive is true if the Person is still alive
	 * @param sex is true if the Person is female use the public constants FEMALE and MALE
	 * @param father contains the father of the person
	 * @param mother contains the mother of the person
	 * @param birthdate as a {@link GregorianCalendar}
	 * @throws InvalidSexException 
	 * @throws AgeException 
	 * @throws LineageException 
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			GregorianCalendar birthdate) throws InvalidSexException, AgeException, LineageException{
		this.givenName = givenName;
		this.familyName = familyName;
		this.alive = alive;
		this.sex = sex;
		this.setFather(father);
		this.setMother(mother);
		this.birthdate = birthdate;
		
		this.additonalConstructorStuff();
		
	}
	/**
	 * 
	 * constructs a new person
	 * @param givenName the given name of the person
	 * @param familyName the family name of the person
	 * @param alive is true if the Person is still alive
	 * @param sex is true if the Person is female use the public constants FEMALE and MALE
	 * @param father contains the father of the person
	 * @param mother contains the mother of the person
	 * @param birthdate as a {@link GregorianCalendar}
	 * @param deathdate as a {@link GregorianCalendar}
	 * @throws InvalidSexException 
	 * @throws AgeException 
	 * @throws LineageException 
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			GregorianCalendar birthdate, GregorianCalendar deathdate) throws InvalidSexException, AgeException, LineageException{
		this.givenName = givenName;
		this.familyName = familyName;
		this.alive = alive;
		this.sex = sex;
		this.setFather(father);
		this.setMother(mother);
		this.birthdate = birthdate;
		this.deathdate = deathdate;
		
		this.additonalConstructorStuff();
		
	}
	/**
	 * 
	 * constructs a new person
	 * @param givenName the given name of the person
	 * @param familyName the family name of the person
	 * @param alive is true if the Person is still alive
	 * @param sex is true if the Person is female use the public constants FEMALE and MALE
	 * @param father contains the father of the person
	 * @param mother contains the mother of the person
	 * @param birthday the day of birth
	 * @param birthmonth the month of birth
	 * @param birthyear the year of birth
	 * @throws InvalidSexException 
	 * @throws AgeException 
	 * @throws LineageException 
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			int birthday, int birthmonth, int birthyear) throws InvalidSexException, AgeException, LineageException{
		this.givenName = givenName;
		this.familyName = familyName;
		this.alive = alive;
		this.sex = sex;
		this.setFather(father);
		this.setMother(mother);
		this.setBirthdate(birthyear,birthmonth,birthday);
		
		this.additonalConstructorStuff();
		
	}
	/**
	 * 
	  * constructs a new person
	 * @param givenName the given name of the person
	 * @param familyName the family name of the person
	 * @param alive is true if the Person is still alive
	 * @param sex is true if the Person is female use the public constants FEMALE and MALE
	 * @param father contains the father of the person
	 * @param mother contains the mother of the person
	 * @param birthday the day of birth
	 * @param birthmonth the month of birth
	 * @param birthyear the year of birth
	 * @param deathday the day of death
	 * @param deathmonth the month of death
	 * @param deathyear the year of death
	 * @throws InvalidSexException 
	 * @throws AgeException 
	 * @throws LineageException 
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			int birthday, int birthmonth, int birthyear,
			int deathday, int deathmonth, int deathyear) throws InvalidSexException, AgeException, LineageException{
		this.givenName = givenName;
		this.familyName = familyName;
		this.alive = alive;
		this.sex = sex;
		this.setFather(father);
		this.setMother(mother);
		this.setBirthdate(birthyear,birthmonth,birthday);
		this.setDeathdate(deathyear,deathmonth,deathday);
		
		this.additonalConstructorStuff();
		
	}
	
	private void additonalConstructorStuff(){
		this.setID(NEXT_ID);
		NEXT_ID++;
		this.generation = NO_GENERATION;
		this.children = new LinkedList<Person>();
		this.partners = new LinkedList<Person>();
		this.setPicture(null);
		this.listeners.add(new RefreshTreeLayoutListener());
	}
	
	/**
	 * this sets the number of generation, if this person is 
	 * connected with other persons their positions will be 
	 * reseted correspondent
	 * @param gen the new number of generation the oldest generation get the number 
	 * one
	 */
	public void setGeneration(int gen){
		//TODO implement
		this.generation = gen;
	}
	/**
	 * returns the generation of this person
	 * generation 0 is equals to the oldest saved generation
	 * @return the generation of this person
	 */
	public int getGeneration(){
		return this.generation;
	}
	/**
	 * the auto generation of the xPosition is in beta stage 
	 * and does not work proper
	 * @param xPosition
	 * @param auto
	 */
	public void setXPosition(int xPosition,boolean auto){
		if(this.xPosition==xPosition){
			return;
		}
		this.xPosition = xPosition;
		if(auto){
			this.setXPositionAuto();
		}
	}
	
	protected void setXPositionAuto(){
		int numberOfChildren = this.getChildren().size();
		
		int firstNumber = this.xPosition-(numberOfChildren/2)*SIBLING_DISTANCE;
	//	int lastNumber = this.xPosition + (numberOfChildren/2)*SIBLING_DISTANCE;
		int counter = firstNumber;
		for(Person child : this.getChildren()){
			child.setXPosition(counter, true);
			counter += SIBLING_DISTANCE;
		}
		
	}
	/**
	 * true if the person has parents
	 * @return
	 */
	public boolean isChild(){
		return !(this.getMother()==null&&this.getFather()==null);
	}
	
	public double getHalfXPosition(){
		return this.xPosition/2.0;
	}
	
	public int getXPosition(){
		return this.xPosition;
	}
	
	public void setGivenName(String givenName){
		this.givenName = givenName;
	}
	
	public void setFamilyName(String familyName){
		this.familyName = familyName;
	}
	
	public String getGivenName(){
		return this.givenName;
	}
	
	public String getFamilyName(){
		return this.familyName;
	}
	/**
	 * set a new father and renews the generation positioning
	 * @param father
	 * @throws InvalidSexException
	 * @throws AgeException
	 * @throws LineageException 
	 */
	public void setFather (Person father)throws InvalidSexException, AgeException, LineageException {
		if(this.father==father){
			return;
		}
		if(this.compareAge(father)==IS_OLDER_THAN){
			throw new AgeException("Parents cannot be younger than there children!");
		}
		
		
		
		if(father==null||father.isMale()){
			if(this.father!=null){
				this.father.removeChild(this);
				if(this.mother==null){

				}
			}
			this.father = father;
			if(this.father!=null){
				this.setParent(this.father);
			}
		}
		else{
			throw new InvalidSexException("Fathers should be male!");
		}
	}
	
	private void setParent(Person person) throws LineageException, InvalidSexException, AgeException{
		person.addChild(this);
		
		if((this.isACircleStructure())){
			person.removeChild(this);
			if(person.isFemale()){
				this.setMother(null);
			}
			else{
				this.setFather(null);
			}
			throw new LineageException("Something is wrong with the lineage!");
		}
	
		if(this.hasTwoParents()){
			this.getMother().addPartner(this.getFather());
		}
		
	}
	
	public  void setMother(Person mother) throws InvalidSexException, AgeException, LineageException{
		if(this.mother==mother){
			return;
		}
		if(this.compareAge(mother)==IS_OLDER_THAN){
			throw new AgeException("Parents cannot be younger than there children!");
		}
		
		
		if(mother==null||mother.isFemale()){
			if(this.mother!=null){
				this.mother.removeChild(this);
				if(this.father==null){

				}
			}
			this.mother = mother;
			
			if(this.mother!=null){
				this.setParent(this.mother);		
			}
		}
		else{
			throw new InvalidSexException("Mothers should be female!");
		}
	}
	
	public void setBirthdate(int day, int month, int year){
		month--;
		this.birthdate = new GregorianCalendar(year,month,day);
	}
	
	public void setBirthdate(GregorianCalendar birth){
		this.birthdate = birth;
	}
	
	public void setDeathdate(int day, int month, int year){
		month--;
		this.deathdate = new GregorianCalendar(year,month,day);
	}
	
	public void setDeathdate(GregorianCalendar death){
		this.deathdate = death;
	}
	
	public GregorianCalendar getBirthdate(){
		return this.birthdate;
	}
	
	public GregorianCalendar getDeathdate(){
		return this.deathdate;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	
	public void setSex(boolean sex){
		this.sex = sex;
	}
	
	public boolean isFemale(){
		return this.sex == FEMALE;
	}
	
	public boolean isMale(){
		return this.sex == MALE;
	}
	public boolean getSex(){
		return this.sex;
	}
	
	/**
	 * the Age of the Person
	 * @return the current age of the person floored to years, returns the life time if the person is dead
	 * @throws AgeException 
	 */
	public int getAge() throws AgeException{
		return this.getAge(false);
	}
	/**
	 * the Age of the Person
	 * @param dcAliveStatus true if the current age should be returned whether or not the person is still alive, if false the life span 
	 * will be returned
	 * @return the current age of the person floored to years, returns the life time if the person is dead and dcAliveStatus false 
	 * else it returns the age the person would have today
	 */
	public int getAge(boolean dcAliveStatus) throws AgeException{
		long currTime = System.currentTimeMillis();
		GregorianCalendar today = new GregorianCalendar();
		today.setTimeInMillis(currTime);
		int currYear = today.get(Calendar.YEAR);
		int currMonth = today.get(Calendar.MONTH);
		int currday = today.get(Calendar.DAY_OF_MONTH);
		if (this.getBirthdate() == null) {
			throw new AgeException("The Birthdate is not known.");
		}
		int birthyear = this.getBirthdate().get(Calendar.YEAR);
		int birthMonth = this.getBirthdate().get(Calendar.MONTH);
		int birthDay = this.getBirthdate().get(Calendar.DAY_OF_MONTH);
		
		if (!this.isAlive() && !dcAliveStatus) {
			if (this.getDeathdate() == null) {
				throw new AgeException("The Deathdate is not known.");
			}
			currYear = this.getDeathdate().get(Calendar.YEAR);
			currMonth = this.getDeathdate().get(Calendar.MONTH);
			currday = this.getDeathdate().get(Calendar.DAY_OF_MONTH);
		}
		int offset = 0;
		if(currMonth < birthMonth || 
				(currMonth == birthMonth && currday < birthDay)){
			offset = 1;
		}
		
		int age = currYear - birthyear - offset;
		return age;
	}
	/**
	 * adds a child to the children list
	 * @param child
	 * @return true if the adding was successful
	 * @throws AgeException 
	 * @throws LineageException 
	 */
	public boolean addChild(Person child) throws AgeException, LineageException{
		boolean result = addPersons(this.children,child);
		if(result){
			try{
			if(this.isFemale()){
			child.setMother(this);
			}
			else if(this.isMale()){
			child.setFather(this);
			}
			}catch(InvalidSexException e){
				
					System.out.println("invalid sex while adding child!");
				
					//Do nothing because this cannot happen
				}
			this.notifyChangeListener();
			}
			
		
		return result;
	}
	
	/**
	 * adds a partner to the partners list
	 * @param partner
	 * @return true if the adding was successful
	 */
	public boolean addPartner(Person partner){
		if(!this.partners.contains(partner)){
		addPersons(this.partners,partner);
		partner.addPartner(this);
		this.notifyChangeListener();
		return true;
		}
		return false;
	}
	
	/**
	 * adds a person to a personslist
	 * @param persons
	 * @param partner
	 * @return
	 */
	static protected boolean addPersons(List<Person> persons, Person partner){
		if(!persons.contains(partner)){
			persons.add(partner);
			return true;
		}
		return false;
	}
	
	public List<Person> getChildren(){
		List<Person> result = new LinkedList<Person>();
		result.addAll(this.children);
		return result;
	}
	
	public List<Person> getPartners(){
		List<Person> result = new LinkedList<Person>();
		result.addAll(this.partners);
		return result;
	}
	
	private boolean removeChild(Person child){
		return this.children.remove(child);
	}
	
	public boolean disconnectChild(Person child){
		try{
		if(this.isMale()&&child.getFather()!=null&&child.getFather()==this){
			child.setFather(null);
			return true;
		}
		if(this.isFemale()&&child.getMother()!=null&&child.getMother()==this){
			child.setMother(null);
			return true;
		}
		}catch(InvalidSexException | AgeException | LineageException e){
			//should never happen
		}
		return false;
	}
	
	public boolean removePartner(Person partner){
		boolean result = this.partners.remove(partner);
		if(partner.getPartners().contains(this)){
			partner.removePartner(this);
		}
		return result;
	}
	
	
	public Person getFather(){
		return this.father;
	}
	
	public Person getMother(){
		return this.mother;
	}
	/**
	 * constructs a list with the siblings of a person
	 * @param halfBroSis if true the lists will contain half brothers and sisters as well
	 * @return
	 */
	protected List<Person> getSiblings(boolean halfBroSis){
		List<Person> siblings = new LinkedList<Person>();
		if(this.getFather()!=null){
			siblings.addAll(this.getFather().getChildren());
		}
		if(this.getMother()!= null){
			for(Person sisBro : this.getMother().getChildren()){
				addPersons(siblings,sisBro);
			}
		}
		
		if(!halfBroSis){
			for(Person person : siblings){
				if(person.getFather()!=this.getFather()||person.getMother()!=this.getMother()){
					siblings.remove(person);
				}
			}
		}
		
		return siblings;
		
	}
	
	
	
	/**
	 * decides if this person is older than the given person
	 * @param person to compare to
	 * @return UNDECIDABLE if it is undecidable IS_OLDER_THAN if this person is older 
	 * and IS_YOUNGER_THAN if this person is younger
	 */
	public int compareAge(Person person){
		
		int result = UNDECIDABLE;
		if(person == null){
			return result;
		}
		if(this.getBirthdate()!=null&&person.getBirthdate()!=null){
			boolean isOlder = this.getBirthdate().before(person.getBirthdate());
			if(isOlder){
				result = IS_OLDER_THAN;
			}
			else{
				result = IS_YOUNGER_THAN;
			}
		}
		return result;
	}
	
	@Override
	public String toString(){
		return this.getGivenName() + " " + 
				this.getFamilyName() + " *" + 
				((this.getBirthdate()!=null)?Utils.calendarToString(this.getBirthdate()):"");
				
	}


	public BufferedImage getPicture() {
		return picture;
	}


	public void setPicture(BufferedImage picture) {
		this.picture = picture;
	}
	
	public boolean hasChildren(){
		return !this.children.isEmpty();
	}
	
	public boolean hasParents(){
		return (this.getFather()!=null||this.getMother()!=null);
	}
	
	public boolean hasTwoParents(){
		return (this.getFather()!=null&&this.getMother()!=null);
	}
	
	public boolean hasOrHadPartner(){
		if(!this.getPartners().isEmpty()){
			return true;
		}
		for(Person child : this.children){
			if(child.hasTwoParents()){
				return true;
			}
		}
		return false;
	}
	


	
	/**
	 * disconnects this person from any other persons via family structures
	 * this should be called before deleting a person
	 * @throws InvalidSexException
	 * @throws AgeException
	 * @throws LineageException 
	 */
	public void disconnect(){
		try{
		if(this.getFather()!=null){
			this.getFather().removeChild(this);
		}
		if(this.getMother()!=null){
			this.getMother().removeChild(this);
		}
		for(Person child : this.getChildren()){
			if(child.getMother()==this){
				child.setMother(null);
			}
			if(child.getFather()==this){
				child.setFather(null);
			}
		}
		
		for(Person partner : this.partners){
			partner.removePartner(this);
		}
		
		this.children = new LinkedList<Person>();
		this.partners = new LinkedList<Person>();
		this.setFather(null);
		this.setMother(null);
		}catch(InvalidSexException | AgeException | LineageException e){
			//do nothing should never happen
		}
		
	}
	

	/**
	 * check that someone is not his own parent
	 * @return
	 */
	
	public boolean isACircleStructure(){
		boolean result = false;
		if(this.getFather()!=null){
			result = this.getFather().isACircleStructure(this);
		}
		if(this.getMother()!=null){
			result = result || this.getMother().isACircleStructure(this);
		}
		return result;
	}
	
	
	/**
	 * check that someone is not his own parent
	 * @return
	 */
	private boolean isACircleStructure(Person person){
		if(person==null){
			return false;
		}
		if(this==person){
			return true;
		}
		if(this.getMother()!=null&&this.getMother().isACircleStructure(person)){
			return true;
		}
		if(this.getFather()!=null&&this.getFather().isACircleStructure(person)){
			return true;
		}
		return false;
	}
	

	
	/**
	 * for writing the image
	 * @param oos
	 * @throws IOException 
	 */
	private void writeObject(ObjectOutputStream oos ) throws IOException{
		oos.defaultWriteObject();
		 ByteArrayOutputStream baos=new ByteArrayOutputStream();
		 if(this.getPicture()!=null){
		 ImageIO.write(this.getPicture(), "JPEG", baos);
		 oos.write(baos.toByteArray());
		 
		 }		    			        
	}
	/**
	 * this is needed so pictures can be restructured
	 * @param s
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject( ObjectInputStream s ) throws IOException, 
    ClassNotFoundException {
		s.defaultReadObject();
		ArrayList<Byte> byteImage = new ArrayList<Byte>(2000);
		while(s.available()>0){
		byteImage.add(s.readByte());
		}
		// System.out.println("Size: " + byteImage.size());
		byte[] primByte = new byte[byteImage.size()];
		int i=0;
		for(Byte by : byteImage){

			primByte[i] = by;
			i++;
		}
		
		if(primByte.length>50){
			this.setPicture(Utils.convertByteToImage(primByte));
		}
		else{
			this.setPicture(null);
		}
	}
	
	
	public String getBirthName() {
		return birthName;
	}


	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}


	public String getCommentOne() {
		return commentOne;
	}


	public void setCommentOne(String commentOne) {
		this.commentOne = commentOne;
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * sets recursively the ancestor visibility to the value of visible
	 * @param visible
	 */
	public void setAncestorVisibility(boolean visible){
		List<Person> subTree = Utils.generateSubTree(this, Utils.TREE_UP);
		for(Person person : subTree){
			person.setVisible(visible);
		}
	}
	
	/**
	 * sets recursively the descendant visibility to the value of visible
	 * @param visible
	 */
	public void setDescendantVisibility(boolean visible){
		List<Person> subTree = Utils.generateSubTree(this, Utils.TREE_DOWN);
		for(Person person : subTree){
			person.setVisible(visible);
		}
	}
	
	/**
	 * sets recursively the parent visibility to the value of visible
	 * @param visible
	 */
	public void setParentVisibility(boolean visible){
		if(this.getMother() != null){
			this.getMother().setVisible(visible);
			this.getMother().setParentVisibility(visible);
		}
		if(this.getFather() != null){
			this.getFather().setVisible(visible);
			this.getFather().setParentVisibility(visible);
		}
	}
	/**
	 * set recursively the children visibility to the value of visible
	 * @param visible
	 */
	public void setChildrenVisibility(boolean visible){
		for(Person child : this.getChildren()){
			child.setVisible(visible);
			child.setChildrenVisibility(visible);
		}
	}


	public long getID() {
		return ID;
	}


	public void setID(long iD) {
		ID = iD;
	}
	
	private void notifyChangeListener(){
		if(listeners == null){
			listeners = new LinkedList<>();
			listeners.add(new RefreshTreeLayoutListener());
		}
		
		for(ConnectionListener listener : listeners){
			listener.connectionChanged(this);
		}
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getTrade() {
		return trade;
	}


	public void setTrade(String trade) {
		this.trade = trade;
	}





}

