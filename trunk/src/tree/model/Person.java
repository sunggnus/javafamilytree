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
	
	
	static private int smallestGeneration=0;
	

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
	 * contains a picture of the person
	 */
	private transient BufferedImage picture;
	/**
	 * this boolean contains necessary information for the oedipus algorithm
	 */
	private boolean visited;
	private boolean trulyVisited;
	private Person noCall;
	
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
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			GregorianCalendar birthdate) throws InvalidSexException, AgeException{
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
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			GregorianCalendar birthdate, GregorianCalendar deathdate) throws InvalidSexException, AgeException{
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
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			int birthday, int birthmonth, int birthyear) throws InvalidSexException, AgeException{
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
	 */
	public Person(String givenName, String familyName,
			boolean alive, boolean sex, Person father, Person mother,
			int birthday, int birthmonth, int birthyear,
			int deathday, int deathmonth, int deathyear) throws InvalidSexException, AgeException{
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
		this.generation = NO_GENERATION;
		this.children = new LinkedList<Person>();
		this.partners = new LinkedList<Person>();
		this.setPicture(null);
	}
	
	/**
	 * this sets the number of generation, if this person is 
	 * connected with other persons their positions will be 
	 * reseted correspondent
	 * @param gen the new number of generation the oldest generation get the number 
	 * one
	 */
	public void setGeneration(int gen){
		if(this.generation != gen){
		this.generation = gen;
		if(this.getMother()!=null){
			this.getMother().setGeneration(this.generation -1);
		}
		if(this.getFather()!= null){
			this.getFather().setGeneration(this.generation -1);
		}
		//if there is no father or mother check that the generation starts
		//with one
		if(this.getMother()==null&&this.getFather()==null&&this.generation <1){
			this.generation=1;
		}
		for(Person partner : this.partners){
				partner.setGeneration(this.generation);
		}
		
		for(Person child : this.getChildren()){
			child.setGeneration(this.generation+1);
		}
		
		}
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
	 */
	public void setFather (Person father)throws InvalidSexException, AgeException {
		if(this.father==father){
			return;
		}
		if(this.compareAge(father)==IS_OLDER_THAN){
			throw new AgeException("Eltern dürfen nicht jünger als ihre Kinder sein.");
		}
		
		
		
		if(father==null||father.isMale()){
			if(this.father!=null){
				this.father.removeChild(this);
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
	
	private void setParent(Person person) throws AgeException, InvalidSexException{
		person.addChild(this);
		
		if(this.isACircleStructure()||this.isOedipusStructure()){
			person.removeChild(this);
			if(person.isFemale()){
				this.setMother(null);
			}
			else{
				this.setFather(null);
			}
			throw new AgeException("Etwas in der Erblinie stimmt nicht, Änderung wurde nicht akzeptiert!");
		}
		
		int gen = person.getGeneration();
		
		if(gen+1>this.getGeneration()){
			this.setGeneration(gen+1);
		}
		else{
			person.setGeneration(this.getGeneration()-1);
		}
		for(Person child : this.getChildren()){
			child.setGeneration(this.getGeneration()+1);
		}
		//to calculate smallestGeneration
		this.isOedipusStructure();
		this.setGeneration(this.getGeneration()-smallestGeneration+1);
		
		if(this.hasTwoParents()){
			this.getMother().addPartner(this.getFather());
		}
		
	}
	
	public  void setMother(Person mother) throws InvalidSexException, AgeException{
		if(this.mother==mother){
			return;
		}
		if(this.compareAge(mother)==IS_OLDER_THAN){
			throw new AgeException("Eltern dürfen nicht jünger als ihre Kinder sein.");
		}
		
		
		if(mother==null||mother.isFemale()){
			if(this.mother!=null){
				this.mother.removeChild(this);
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
	
	public void setDeathdate(int day, int month, int year){
		month--;
		this.deathdate = new GregorianCalendar(year,month,day);
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
	 */
	public boolean addChild(Person child) throws AgeException{
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
		partner.setGeneration(this.generation);
		addPersons(this.partners,partner);
		partner.addPartner(this);
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
		}catch(InvalidSexException | AgeException e){
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
	
	
	private boolean isOedipusStructure(){
		smallestGeneration = this.getGeneration();
		//the visit status change during every run
		this.visited = !this.visited;
		for(Person child : this.children){
			
			if(child.isOedipusStructure(this,this,this.visited)){
				
				this.erazeNoCall();
				return true;
			}
		}
		
		List<Person> siblings = this.getSiblings(true);
		for(Person sib : siblings){
			sib.noCall = this.getFather();
		}
		
		if(this.getMother()!=null&&
				this.getMother().isOedipusStructure(this, this, this.visited)){
			this.erazeNoCall();
			return true;
		}
		
		for(Person sib : siblings){
			sib.noCall = this.getMother();
		}
		
		if(this.getFather()!=null&&
				this.getFather().isOedipusStructure(this, this, this.visited)){
			this.erazeNoCall();
			return true;
		}
		
		this.erazeNoCall();
		return false;
	}
	private void erazeNoCall(){
		List<Person> siblings = this.getSiblings(true);
		for(Person sib : siblings){
			sib.noCall = null;
		}
	}
	
	/**
	 * disconnects this person from any other persons via family structurs
	 * this should be called before deleting a person
	 * @throws InvalidSexException
	 * @throws AgeException
	 */
	public void disconnect() throws InvalidSexException, AgeException{
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
	}
	
	/**
	 * decides whether this is a oedipusstructure or not
	 * and recalculates the oldest generation
	 * @param sex
	 * @param person
	 * @param caller
	 * @param smallestGeneration
	 * @return
	 */
	private boolean isOedipusStructure(Person person, Person caller, boolean visitStatus){
		if(this.getGeneration()<smallestGeneration){
			smallestGeneration = this.getGeneration();
		}	
		if(person==this){
			return true;
		}
		if(visitStatus==this.visited){
			return false;
		}
		//TODO it seems to work now but code is a little bit complicate maybe it is possible
		//to simplify it
		this.visited=visitStatus;
		List<Person> siblings = this.getSiblings(true);
		for(Person sib : siblings){
			sib.setTrulyVisited(sib.getVisited());
		}
		if(caller==this.getMother()||caller==this.getFather()){
			for(Person sib : siblings){
				sib.setVisited(visitStatus);
			}
		}
		for(Person child : this.children){
			if(child == caller){
				continue;
			}
			if(child.isOedipusStructure(person, this, visitStatus)){
				return true;
			}
			
		}
		
			if(this.getFather()!=null&&
					this.getFather()!=this.noCall&&
					this.getFather()!=caller&&
					this.getFather().isOedipusStructure(person,this,visitStatus)){
				return true;
			}
		
			if(this.getMother()!=null&&
					this.getMother()!=this.noCall&&
					this.getMother()!=caller&&
					this.getMother().isOedipusStructure( person,this,visitStatus)){
				return true;
			}
		
			for(Person sib : siblings){
				sib.setVisited(sib.getTrulyVisited()); // to reopen second search way
			}
		
		return false;
	}
	
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
	
	private void setVisited(boolean visited){
		this.visited = visited;
	}
	
	private boolean getVisited(){
		return this.visited;
	}
	
	private boolean getTrulyVisited(){
		return this.trulyVisited;
	}
	
	private void setTrulyVisited(boolean trulyVisited){
		this.trulyVisited = trulyVisited;
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


}
