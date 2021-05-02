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
import javax.xml.namespace.QName;
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
	
	private Stack<QName> openNodes;
	
	private class ImageStruct{
		
		public long id;
		public BufferedImage image;
		
	}
	
	private LinkedList<ImageStruct> images;
	
	XMLEventWriter eventWriter;
	
	public XmlBuilder(String path) throws FileNotFoundException, XMLStreamException{
		this.indention = 0;
		this.lineStart = true;
		this.openNodes = new Stack<QName>();
		this.images = new LinkedList<XmlBuilder.ImageStruct>();
		
		// create an XMLOutputFactory
	    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	    outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
	    // create XMLEventWriter
	    this.eventWriter = outputFactory
	            .createXMLEventWriter(new FileOutputStream(path), "UTF-8");

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
	
	private void openNode(XmlNodes name) throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    // create Start node
	    StartElement sElement = eventFactory.createStartElement(name.getQName(), null, null);
	    lineBreak();
	    indention();
	    eventWriter.add(sElement);
	    this.openNodes.push(name.getQName());
	    this.increaseIndention();
	    
	}
	
	private void closeNode() throws XMLStreamException{
		QName n = this.openNodes.pop();
		
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	    EndElement eElement = eventFactory.createEndElement(n, null);
	    this.decreaseIndention();
	    this.indention();
	    eventWriter.add(eElement);
	    
	}
	
	private void addCompleteSubNode(XmlNodes name, String value) throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    
		openNode(name);
    if(value != null){
    	Characters characters = eventFactory.createCharacters(value);
    	eventWriter.add(characters);
    }
	    closeNode();
	}

	private void addCompleteSubNode(XmlNodes name, long value) throws XMLStreamException{
	
		addCompleteSubNode(name, String.valueOf(value));
	}

	private void addCompleteSubNode(XmlNodes name, double value) throws XMLStreamException{
	
		addCompleteSubNode(name, String.valueOf(value));
	}

	private void addCompleteSubNode(XmlNodes name, boolean value) throws XMLStreamException{
		addCompleteSubNode( name, String.valueOf(value));
	}

	private void addCompleteSubNode(XmlNodes name, GregorianCalendar value) throws XMLStreamException{
		addCompleteSubNode(name, Utils.calendarToXmlStorageString(value));
	}

	private void addCompleteSubNode( XmlNodes name, Person value) throws XMLStreamException{
	if(value == null){
		addCompleteSubNode(name, "");
	}
	else{
		addCompleteSubNode( name, value.getID());	
	}
	
	
	
}
	private void addCompleteSubNode(XmlNodes name, BufferedImage image) throws XMLStreamException{
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
			openNode(XmlNodes.IMAGE);
			addCompleteSubNode( XmlNodes.ID, s.id);
			addCompleteSubNode( XmlNodes.DATA, s.image);
			lineBreak();
			closeNode();
		}
		images.clear();
	}
	
	private void writeXMLPerson(Person person) throws XMLStreamException{
	    
		openNode(XmlNodes.PERSON);
	    addCompleteSubNode( XmlNodes.ID, person.getID());
	    addCompleteSubNode( XmlNodes.GENERATION, person.getGeneration());
	    addCompleteSubNode( XmlNodes.GIVEN_NAME, person.getGivenName());
	    addCompleteSubNode( XmlNodes.FAMILY_NAME, person.getFamilyName());
	    addCompleteSubNode( XmlNodes.BIRTH_NAME, person.getBirthName());
	    addCompleteSubNode( XmlNodes.ALIVE, person.isAlive());
	    addCompleteSubNode( XmlNodes.SEX, person.getSex().name());
	    addCompleteSubNode( XmlNodes.BIRTH_DATE, person.getBirthdate());
	    addCompleteSubNode( XmlNodes.DEATH_DATE, person.getDeathdate());
	    addCompleteSubNode( XmlNodes.FATHER, person.getFather());
	    addCompleteSubNode( XmlNodes.MOTHER, person.getMother());
	    for(Person p : person.getPartners()){
	    	addCompleteSubNode( XmlNodes.PARTNER, p);
	    }
	    for(Person p : person.getChildren()){
	    	addCompleteSubNode( XmlNodes.CHILD, p);
	    }
	    addCompleteSubNode( XmlNodes.LOCATION, person.getLocation());
	    addCompleteSubNode( XmlNodes.TRADE, person.getTrade());
	    addCompleteSubNode( XmlNodes.VISIBLE, person.isVisible());
	    addCompleteSubNode( XmlNodes.X_POSITION, person.getXPosition());
	    addCompleteSubNode( XmlNodes.COMMENT_ONE, person.getCommentOne());
	    
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
		openNode(XmlNodes.NOTE);
	    for(String s : note.getComments()){
	    	addCompleteSubNode( XmlNodes.COMMENT, s);
	    }
	    addCompleteSubNode( XmlNodes.FONT_SIZE, note.getFontSize());
	    addCompleteSubNode( XmlNodes.VISIBLE, note.isVisible());
	    addCompleteSubNode( XmlNodes.X_COORDINATE, note.getX());
	    addCompleteSubNode( XmlNodes.Y_COORDINATE, note.getY());
	    addCompleteSubNode( XmlNodes.SMOOTH_X, note.getSmoothX());
	    addCompleteSubNode( XmlNodes.SMOOTH_Y, note.getSmoothY());
	    lineBreak();
	    closeNode();
	}
	
	private void startDocument() throws XMLStreamException{
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		
		StartDocument startDocument = eventFactory.createStartDocument("UTF-8");
	    eventWriter.add(startDocument);
	    eventWriter.setDefaultNamespace(XmlNodes.TREE.getNamespace());
	    lineBreak();
	    openNode(XmlNodes.TREE);
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
