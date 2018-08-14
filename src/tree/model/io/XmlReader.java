package tree.model.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import main.Config;
import main.OptionList;
import tree.model.AgeException;
import tree.model.InvalidSexException;
import tree.model.LineageException;
import tree.model.MainNode;
import tree.model.Note;
import tree.model.Person;
import tree.model.Person.Sex;
import tree.model.Utils;

public class XmlReader {


	static final long NO_REFERENCE_ID = -1;
	
	private LinkedList<XmlNodes> openNodes;
	LinkedList<Person> persons;

	LinkedList<Note> notes;
	Person activePerson;

	Note activeNote;
	
	long nextImageID;
	BufferedImage activeImage;
	
	public XmlReader(){
		this.openNodes = new LinkedList<XmlNodes>();
		
		persons = new LinkedList<Person>();

		notes = new LinkedList<Note>();
	}
	
	private Person getPersonByID(long id){
		for(Person p : this.persons){
			if(p.getID() == id){
				return p;
			}
		}
		return null;
	}
	
	private void consumeNode(XmlNodes node, XMLEventReader reader) throws XMLStreamException{
		this.openNodes.push(node);
		String content = "";
		boolean contentAsBool = false;
		long contentAsInt = -1;
		double contentAsDouble = 0.0;
		if(reader.hasNext() && 
				reader.peek().getEventType() == XMLStreamConstants.CHARACTERS){
		      Characters characters = reader.peek().asCharacters();
		      if ( ! characters.isWhiteSpace() ){
		    	  content = characters.getData();
		      }
		        
		}
		contentAsBool = content.equals("true");
		try{
			contentAsInt = Long.parseLong(content);
		} catch (NumberFormatException e){
			
		}
		try{
			contentAsDouble = Double.parseDouble(content);
		} catch (NumberFormatException e){
			
		}
		
		try {
		if(this.openNodes.contains(XmlNodes.PERSON)){
			switch(node){
			case PERSON:
				activePerson = new Person("", "", Sex.FEMALE);
				break;
			case GIVEN_NAME:
				activePerson.setGivenName(content);
				break;
			case FAMILY_NAME:
				activePerson.setFamilyName(content);
			case SEX:
				if(content.equals("MALE")){
					activePerson.setSex(Sex.MALE);
				}
				else{
					activePerson.setSex(Sex.FEMALE);
				}
				break;
			case BIRTH_NAME:
				activePerson.setBirthName(content);
				break;
			case ALIVE:
				activePerson.setAlive(contentAsBool);
				break;
			case BIRTH_DATE:
				activePerson.setBirthdate(Utils.stringToCalendar(content));
				break;
			case DEATH_DATE:
				activePerson.setDeathdate(Utils.stringToCalendar(content));
				break;
			case ID:
				activePerson.setID(contentAsInt);
				break;
			case FATHER:
				{
				Person f = getPersonByID(contentAsInt);
				if(f != null){
						activePerson.setFather(f);
				}
				}
				break;
			case MOTHER:
			{
				Person f = getPersonByID(contentAsInt);
				if(f != null){
						activePerson.setMother(f);
				}
			}
				break;
			case PARTNER:
			{
				Person f = getPersonByID(contentAsInt);
				if(f != null){
						activePerson.addPartner(f);
				}
			}
				break;
			case CHILD:
				{
				Person f = getPersonByID(contentAsInt);
				if(f != null){
						activePerson.addChild(f);
				}
				}
				break; 
			case LOCATION:
				activePerson.setLocation(content);
				break;
			case TRADE:
				activePerson.setTrade(content);
				break;
			case VISIBLE:
				activePerson.setVisible(contentAsBool);
				break;
			case X_POSITION:
				activePerson.setXPosition((int)contentAsInt, false);
				break;
			case GENERATION:
				activePerson.setGeneration((int) contentAsInt);
			case COMMENT_ONE:
				activePerson.setCommentOne(content);
				break;
				default:
					break;
			}
		}
		else if(this.openNodes.contains(XmlNodes.NOTE)){
			switch(node){
			case NOTE:
				activeNote = new Note();
				break;
			case FONT_SIZE:
				activeNote.setFontSize((int) contentAsInt);
				break;
			case X_COORDINATE:
				activeNote.setX( (int) contentAsInt); 
				break;
			case Y_COORDINATE:
				activeNote.setY((int) contentAsInt);
				break;
			case SMOOTH_X:
				activeNote.setSmoothX(contentAsDouble);
				break;
			case SMOOTH_Y:
				activeNote.setSmoothY(contentAsDouble);
				break;
			case COMMENT:
				activeNote.addLine(content);
				break;
			case VISIBLE:
				activeNote.setVisible(contentAsBool);
				break;
			default:
				break;
			}
		}
		else if(this.openNodes.contains(XmlNodes.IMAGE)){
			switch(node){
			case IMAGE:
				//do nothing
				break;
			case ID:
				nextImageID = contentAsInt;
				break;
			case DATA:
				ByteArrayInputStream bais = new ByteArrayInputStream(Utils.hexStringToByteArray(content));
				try {
					activeImage = ImageIO.read( bais );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			default:
				break;
			}
		
		}
		} catch (InvalidSexException | AgeException
				| LineageException e) {

		}
		
	}
	private void closeNode(XmlNodes node){
		switch(node){
		case PERSON:
			persons.add(activePerson);
			activePerson = null;
			break;
		case NOTE:
			if(activeNote != null){
				notes.add(activeNote);
			}
			activeNote = null;
			break;
		case IMAGE:
			Person p = getPersonByID(nextImageID);
			p.setPicture(activeImage);
			activeImage = null; 
			break;
		default:
			//do nothing
		}
		
		this.openNodes.pop();
	}
	
	public MainNode readXmlTree(String path) throws FileNotFoundException {
		OptionList ypMode = Config.Y_POSITIONING_MODE;
		try {
		
		Config.Y_POSITIONING_MODE =	OptionList.Y_MANUAL_POSITIONING;

		InputStream in = new FileInputStream(path);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		XMLEventReader parser;
		
			parser = factory.createXMLEventReader(in);
		
		

		while (parser.hasNext()) {

			XMLEvent event = parser.nextEvent();

			switch (event.getEventType()) {
			case XMLStreamConstants.START_DOCUMENT:
				break;
			case XMLStreamConstants.END_DOCUMENT:
				parser.close();
				break;
			case XMLStreamConstants.NAMESPACE:
				break;
			case XMLStreamConstants.START_ELEMENT:
				{
				StartElement element = event.asStartElement();
				QName eName = element.getName();
				XmlNodes node = XmlNodes.fromQName(eName);
				if(node != null){
					consumeNode(node, parser);
				}
				else{
					throw new XMLStreamException("unknown node");
				}

				break;
				}
			case XMLStreamConstants.CHARACTERS:
				break;
			case XMLStreamConstants.END_ELEMENT:
					EndElement element = event.asEndElement();
					QName eName = element.getName();
					XmlNodes node = XmlNodes.fromQName(eName);
					if(node != null && this.openNodes.peek() == node){
						closeNode(node);
					}
					else{
						
						throw new XMLStreamException("unknown node");
					}
				break;
			default:
				break;
			}

		}
		if(persons.size() > 0){
			MainNode node = new MainNode(persons.getFirst());
			node.addAll(persons);
			for(Note note : notes){
				node.addNote(note);
			}
			Config.Y_POSITIONING_MODE = ypMode;
			return node;
		}
		
		} catch (XMLStreamException e) {
			
		}
		Config.Y_POSITIONING_MODE = ypMode;
		return null;

	}
}
