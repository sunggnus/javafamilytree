package tree.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import main.Config;
import main.OptionList;

public class MainNode implements Serializable {

	/**
	 * unique class id for serialization
	 */
	private static final long serialVersionUID = -6013204678912067257L;

	private Person mainPerson;

	/**
	 * contains every person within the tree
	 */
	private LinkedList<Person> createdPersons;

	/**
	 * contains every note within the tree
	 */
	private LinkedList<Note> notes;

	public MainNode(Person person) {
		mainPerson = person;
		createdPersons = new LinkedList<Person>();
		notes = new LinkedList<Note>();
	}

	public void addPerson(Person person) {
		createdPersons.add(person);
	}

	public List<Note> getNotes() {
		LinkedList<Note> res = new LinkedList<Note>();
		res.addAll(this.notes);
		return res;
	}

	public List<Note> getNotesReference() {
		return this.notes;
	}

	public List<Person> getPersons() {
		LinkedList<Person> res = new LinkedList<Person>();
		res.addAll(this.createdPersons);
		return res;
	}

	/**
	 * warning this should be used only for reading not writing in the list
	 * because it is a reference and not a clone
	 * 
	 * @return
	 */
	public List<Person> getPersonsReference() {
		return this.createdPersons;
	}

	public boolean removePerson(Person person) {
		return this.createdPersons.remove(person);
	}

	public void setPerson(Person person) {
		this.mainPerson = person;
	}

	public Person getPerson() {
		return this.mainPerson;
	}

	public void refreshGeneration() {
		if (this.getPerson() != null && this.getPerson().getGeneration() >= 0) {
			int smallestGeneration = this.getPerson().getGeneration();
			for (Person person : this.getPersonsReference()) {
				if (person.getGeneration() < smallestGeneration) {
					smallestGeneration = person.getGeneration();
				}
			}
			this.getPerson().setGeneration(
					this.getPerson().getGeneration() - smallestGeneration + 1);
		}
	}

	public void addNote(Note note) {
		if (!this.notes.contains(note)) {
			this.notes.add(note);
		}
	}

	public boolean removeNote(Note note) {
		return this.notes.remove(note);
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		// this.refreshGeneration();
		if (Config.Y_POSITIONING_MODE == OptionList.Y_AUTO_POSITIONING
				&& this.getPerson() != null)
			Utils.determineTreeGenerations(this.getPerson());

		for (Person person : this.createdPersons) {
			if (person.getGeneration() < 1) {
				person.setGeneration(1);
			}
		}

		if (this.notes == null) {
			this.notes = new LinkedList<Note>();
		}
	}

	public void setTreeVisibility(boolean visible) {
		for (Person person : this.createdPersons) {
			person.setVisible(visible);
		}
	}

	public void addAll(List<Person> persons) {
		createdPersons.addAll(persons);
	}
	
	public void normalizeIDs(){
		long id = 0;
		for(Person s :this.createdPersons){
			s.setID(id);
			id++;
		}
		Person.NEXT_ID = id;
	}

}
