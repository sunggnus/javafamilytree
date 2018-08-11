package tree.model.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import tree.model.Note;
import tree.model.Person;
import tree.model.Utils;

public class XmlBuilder {
	
	private boolean lineStart;

	private int indention;
	
	private Stack<String> openNodes;
	
	private class ImageStruct{
		
		public long id;
		public BufferedImage image;
		
	}
	
	private LinkedList<ImageStruct> images;
	
	XMLEventWriter eventWriter;
	
	public XmlBuilder(String path) throws FileNotFoundException, XMLStreamException{
		this.indention = 0;
		this.lineStart = true;
		this.openNodes = new Stack<String>();
		this.images = new LinkedList<XmlBuilder.ImageStruct>();
		
		// create an XMLOutputFactory
	    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	    // create XMLEventWriter
	    this.eventWriter = outputFactory
	            .createXMLEventWriter(new FileOutputStream(path));

	}
	
	private void increaseIndention(){
		this.indention++;
	}
	
	private void decreaseIndention(){
		this.indention--;
		if(this.indention < 0){
			this.indention = 0;
		}
	}
	
	private void indention() throws XMLStreamException{
		if(!this.lineStart){
			return; //only at line start indentions are valid
		}
		this.lineStart = false;
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent tab = eventFactory.createDTD("\t");
		for(int i = 0; i < indention; i++){
			eventWriter.add(tab);
		}
	}
	
	private void lineBreak() throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    XMLEvent end = eventFactory.createDTD("\n");
	    eventWriter.add(end);
	    this.lineStart = true;
	}
	
	private void openNode(String name) throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    // create Start node
	    StartElement sElement = eventFactory.createStartElement("", "", name);
	    lineBreak();
	    indention();
	    eventWriter.add(sElement);
	    this.openNodes.push(name);
	    this.increaseIndention();
	    
	}
	
	private void closeNode() throws XMLStreamException{
		String n = this.openNodes.pop();
		
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    EndElement eElement = eventFactory.createEndElement("", "", n);
	    this.decreaseIndention();
	    this.indention();
	    eventWriter.add(eElement);
	    
	}
	
	private void addCompleteSubNode(String name, String value) throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    
		openNode(name);
    if(value != null){
    	Characters characters = eventFactory.createCharacters(value);
    	eventWriter.add(characters);
    }
	    closeNode();
	}

	private void addCompleteSubNode(String name, long value) throws XMLStreamException{
	
		addCompleteSubNode(name, String.valueOf(value));
	}

	private void addCompleteSubNode(String name, double value) throws XMLStreamException{
	
		addCompleteSubNode(name, String.valueOf(value));
	}

	private void addCompleteSubNode(String name, boolean value) throws XMLStreamException{
		addCompleteSubNode( name, String.valueOf(value));
	}

	private void addCompleteSubNode(String name, GregorianCalendar value) throws XMLStreamException{
		addCompleteSubNode(name, Utils.calendarToSimpleString(value));
	}

	private void addCompleteSubNode( String name, Person value) throws XMLStreamException{
	if(value == null){
		addCompleteSubNode(name, "");
	}
	else{
		addCompleteSubNode( name, value.getID());	
	}
	
	
	
}
	private void addCompleteSubNode(String name, BufferedImage image) throws XMLStreamException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (image != null) {
			try {
				ImageIO.write(image, "JPEG", baos);
				addCompleteSubNode(name, Utils.bytesToHex(baos.toByteArray()));
			} catch (IOException e) {
				// should never happen
				e.printStackTrace();
			}
		}
	}
	
	
	private void writeXMLImages() throws XMLStreamException{
		for(ImageStruct s : images){
			openNode("image");
			addCompleteSubNode( "ID", s.id);
			addCompleteSubNode("data", s.image);
			lineBreak();
			closeNode();
		}
		images.clear();
	}
	
	private void writeXMLPerson(Person person) throws XMLStreamException{
	    
		openNode("person");
	    addCompleteSubNode( "ID", person.getID());
	    addCompleteSubNode( "givenName", person.getGivenName());
	    addCompleteSubNode( "familyName", person.getFamilyName());
	    addCompleteSubNode( "birthName", person.getBirthName());
	    addCompleteSubNode( "alive", person.isAlive());
	    addCompleteSubNode( "sex", person.getSex().name());
	    addCompleteSubNode( "birthDate", person.getBirthdate());
	    addCompleteSubNode( "deathDate", person.getDeathdate());
	    addCompleteSubNode( "father", person.getFather());
	    addCompleteSubNode( "mother", person.getMother());
	    for(Person p : person.getPartners()){
	    	addCompleteSubNode( "partner", p);
	    }
	    for(Person p : person.getChildren()){
	    	addCompleteSubNode( "child", p);
	    }
	    addCompleteSubNode( "location", person.getLocation());
	    addCompleteSubNode( "trade", person.getTrade());
	    addCompleteSubNode( "visible", person.isVisible());
	    addCompleteSubNode( "xPosition", person.getXPosition());
	    addCompleteSubNode( "generation", person.getGeneration());
	    addCompleteSubNode( "commentOne", person.getCommentOne());
	    
	    BufferedImage image = person.getPicture();
	    if(image != null){
	    	ImageStruct is = new ImageStruct();
	    	is.id = person.getID();
	    	is.image = image;
	    	images.add(is);
	    }
	    
	    //addCompleteSubNode( "image", person.getPicture());
	    
	    // create End node
	    lineBreak();
	   closeNode();
	}

	private void writeXMLNote( Note note) throws XMLStreamException{
		openNode("note");
	    for(String s : note.getComments()){
	    	addCompleteSubNode( "comment", s);
	    }
	    addCompleteSubNode( "fontSize", note.getFontSize());
	    addCompleteSubNode( "visible", note.isVisible());
	    addCompleteSubNode( "x", note.getX());
	    addCompleteSubNode( "y", note.getY());
	    addCompleteSubNode( "smoothX", note.getSmoothX());
	    addCompleteSubNode( "smoothY", note.getSmoothY());
	    lineBreak();
	    closeNode();
	}
	
	private void startDocument() throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		StartDocument startDocument = eventFactory.createStartDocument();
	    eventWriter.add(startDocument);
	    lineBreak();
	    openNode("tree");
	}
	
	private void endDocument() throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		lineBreak();
	    closeNode();
	    
	    eventWriter.add(eventFactory.createEndDocument());
	    eventWriter.close();
	}
	
	
	public static void writeXMLTree(String path, List<Person> createdPersons, List<Note> notes) throws FileNotFoundException, XMLStreamException{
		
		XmlBuilder builder = new XmlBuilder(path);
		
	    builder.startDocument();
	    
	    for(Person p : createdPersons){
	    	builder.writeXMLPerson( p);
	    }
	    for(Note n : notes){
	    	builder.writeXMLNote( n);
	    }
	    builder.writeXMLImages();
	    
	    builder.endDocument();
	}
	
}
