package tree.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MainNode implements Serializable{
	
	/**
	 * unique class id for serialization
	 */
	private static final long serialVersionUID = -6013204678912067257L;

	private Person mainPerson;
	
	private LinkedList<Person> createdPersons;
	
	private LinkedList<Note> notes;
	
	public MainNode(Person person){
		mainPerson = person;
		createdPersons = new LinkedList<Person>();
		notes = new LinkedList<Note>();
	}
	
	public void addPerson(Person person){
		createdPersons.add(person);
	}
	

	public List<Note> getNotes(){
		LinkedList<Note> res = new LinkedList<Note>();
		res.addAll(this.notes);
		return res;
	}
	
	public List<Note> getNotesReference(){
		return this.notes;
	}
	
	
	public List<Person> getPersons(){
		LinkedList<Person> res = new LinkedList<Person>();
		res.addAll(this.createdPersons);
		return res;
	}
	/**
	 * warning this should be used only for reading not writing in the list
	 * because it is a reference and not a clone
	 * @return
	 */
	public List<Person> getPersonsReference(){
		return this.createdPersons;
	}
	
	public boolean removePerson(Person person){
		return this.createdPersons.remove(person);
	}
	
	public void setPerson(Person person){
		this.mainPerson = person;
	}
	
	public Person getPerson(){
		return this.mainPerson;
	}
	
	public void refreshGeneration(){
		if(this.getPerson()!=null&&this.getPerson().getGeneration()>=0){
		int smallestGeneration = this.getPerson().getGeneration();
		for(Person person : this.getPersonsReference()){
			if(person.getGeneration()<smallestGeneration){
				smallestGeneration = person.getGeneration();
			}
		}
		this.getPerson().setGeneration(this.getPerson().getGeneration()-smallestGeneration+1);
		}
	}
	
	public void addNote(Note note){
		if(!this.notes.contains(note)){
			this.notes.add(note);
		}
	}
	
	public boolean removeNote(Note note){
		return this.notes.remove(note);
	}
	
	private void readObject( ObjectInputStream s ) throws IOException, 
    ClassNotFoundException {
		s.defaultReadObject();
		this.refreshGeneration();
		
		if(this.notes==null){
			this.notes =new LinkedList<Note>();
		}
	}

}
