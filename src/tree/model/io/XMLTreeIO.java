package tree.model.io;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import tree.model.Note;
import tree.model.Person;
import tree.model.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class XMLTreeIO {


	
	
static private void addCompleteSubNode(XMLEventWriter eventWriter, String name,
        String value, int indention) throws XMLStreamException{
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    XMLEvent tab = eventFactory.createDTD("\t");
    // create Start node
    StartElement sElement = eventFactory.createStartElement("", "", name);
    for(int i = 0; i < indention; i++){
    	eventWriter.add(tab);
    }
    eventWriter.add(sElement);
    // create Content
    if(value != null){
    	Characters characters = eventFactory.createCharacters(value);
    	eventWriter.add(characters);
    }
    // create End node
    EndElement eElement = eventFactory.createEndElement("", "", name);
    eventWriter.add(eElement);
    eventWriter.add(end);
}

static private void addCompleteSubNode(XMLEventWriter eventWriter, String name,
        long value, int indention) throws XMLStreamException{
	
	addCompleteSubNode(eventWriter, name, String.valueOf(value), indention);
}

static private void addCompleteSubNode(XMLEventWriter eventWriter, String name,
        double value, int indention) throws XMLStreamException{
	
	addCompleteSubNode(eventWriter, name, String.valueOf(value), indention);
}

static private void addCompleteSubNode(XMLEventWriter eventWriter, String name,
        boolean value, int indention) throws XMLStreamException{
	
	addCompleteSubNode(eventWriter, name, String.valueOf(value), indention);
}

static private void addCompleteSubNode(XMLEventWriter eventWriter, String name,
GregorianCalendar value, int indention) throws XMLStreamException{
	addCompleteSubNode(eventWriter, name, Utils.calendarToSimpleString(value), indention);
}

static private void addCompleteSubNode(XMLEventWriter eventWriter, String name,
Person value, int indention) throws XMLStreamException{
	if(value == null){
		addCompleteSubNode(eventWriter, name, "", indention);
	}
	else{
		addCompleteSubNode(eventWriter, name, value.getID(), indention);	
	}
	
	
}
	
static private void writeXMLPerson(XMLEventWriter eventWriter, Person person) throws XMLStreamException{
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    XMLEvent tab = eventFactory.createDTD("\t");
    // create Start node
    StartElement sPerson = eventFactory.createStartElement("", "", "person");
    eventWriter.add(end);
    eventWriter.add(tab);
    eventWriter.add(sPerson);
    eventWriter.add(end);
    int indention = 2;
    addCompleteSubNode(eventWriter, "ID", person.getID(), indention);
    addCompleteSubNode(eventWriter, "givenName", person.getGivenName(), indention);
    addCompleteSubNode(eventWriter, "familyName", person.getFamilyName(), indention);
    addCompleteSubNode(eventWriter, "birthName", person.getBirthName(), indention);
    addCompleteSubNode(eventWriter, "alive", person.isAlive(), indention);
    addCompleteSubNode(eventWriter, "sex", person.getSex().name(), indention);
    addCompleteSubNode(eventWriter, "birthDate", person.getBirthdate(), indention);
    addCompleteSubNode(eventWriter, "deathDate", person.getDeathdate(), indention);
    addCompleteSubNode(eventWriter, "father", person.getFather(), indention);
    addCompleteSubNode(eventWriter, "mother", person.getMother(), indention);
    for(Person p : person.getPartners()){
    	addCompleteSubNode(eventWriter, "partner", p, indention);
    }
    for(Person p : person.getChildren()){
    	addCompleteSubNode(eventWriter, "child", p, indention);
    }
    addCompleteSubNode(eventWriter, "location", person.getLocation(), indention);
    addCompleteSubNode(eventWriter, "trade", person.getTrade(), indention);
    addCompleteSubNode(eventWriter, "visible", person.isVisible(), indention);
    addCompleteSubNode(eventWriter, "xPosition", person.getXPosition(), indention);
    addCompleteSubNode(eventWriter, "generation", person.getGeneration(), indention);
    addCompleteSubNode(eventWriter, "commentOne", person.getCommentOne(), indention);
    
    // create End node
    EndElement eElement = eventFactory.createEndElement("", "", "person");
    eventWriter.add(tab);
    eventWriter.add(eElement);
    eventWriter.add(end);
}

static private void writeXMLNote(XMLEventWriter eventWriter, Note note) throws XMLStreamException{
	XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    XMLEvent tab = eventFactory.createDTD("\t");
    // create Start node
    StartElement sNote = eventFactory.createStartElement("", "", "note");
    eventWriter.add(end);
    eventWriter.add(tab);

    eventWriter.add(sNote);
    eventWriter.add(end);
    int indention = 2;
    for(String s : note.getComments()){
    	addCompleteSubNode(eventWriter, "comment", s, indention);
    }
    addCompleteSubNode(eventWriter, "fontSize", note.getFontSize(), indention);
    addCompleteSubNode(eventWriter, "visible", note.isVisible(), indention);
    addCompleteSubNode(eventWriter, "x", note.getX(), indention);
    addCompleteSubNode(eventWriter, "y", note.getY(), indention);
    addCompleteSubNode(eventWriter, "smoothX", note.getSmoothX(), indention);
    addCompleteSubNode(eventWriter, "smoothY", note.getSmoothY(), indention);
    
    
 // create End node
    EndElement eElement = eventFactory.createEndElement("", "", "note");
    eventWriter.add(tab);
    eventWriter.add(eElement);
    eventWriter.add(tab);
    eventWriter.add(end);
}

static public void writeXMLTree(String path, List<Person> createdPersons, List<Note> notes) throws FileNotFoundException, XMLStreamException{
	// create an XMLOutputFactory
    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    // create XMLEventWriter
    XMLEventWriter eventWriter = outputFactory
            .createXMLEventWriter(new FileOutputStream(path));
    // create an EventFactory
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    // create and write Start Tag
    StartDocument startDocument = eventFactory.createStartDocument();
    eventWriter.add(startDocument);
    
    // create config open tag
    StartElement configStartElement = eventFactory.createStartElement("",
            "", "tree");
    eventWriter.add(configStartElement);
    eventWriter.add(end);
    for(Person p : createdPersons){
    	writeXMLPerson(eventWriter, p);
    }
    for(Note n : notes){
    	writeXMLNote(eventWriter, n);
    }
    eventWriter.add(eventFactory.createEndElement("", "", "tree"));
    eventWriter.add(end);
    eventWriter.add(eventFactory.createEndDocument());
    eventWriter.close();
}

}