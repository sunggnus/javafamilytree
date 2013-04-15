package tree.gui.search.line.factory;

import tree.gui.search.AbstractOverview;
import tree.gui.search.line.AbstractLine;
import tree.gui.search.line.NoteEditLine;
import tree.gui.search.line.PersonEditLine;
import tree.model.Note;
import tree.model.Person;

public class EditLineFactory extends AbstractOverviewLineFactory{

	public EditLineFactory(){
		super();
	}
	
	@Override
	public AbstractLine createOverviewLine(Person person, AbstractOverview view) {
		return new PersonEditLine(person,view);
	}

	@Override
	public AbstractLine createOverviewLine(Note note, AbstractOverview view) {
		// TODO Auto-generated method stub
		return new NoteEditLine(note, view);
	}

}
