package tree.gui.search.line.factory;

import tree.gui.search.AbstractOverview;
import tree.gui.search.line.AbstractLine;
import tree.model.Note;
import tree.model.Person;

public abstract class AbstractOverviewLineFactory {
	
	abstract public AbstractLine createOverviewLine(Person person, AbstractOverview view);
	
	abstract public AbstractLine createOverviewLine(Note note, AbstractOverview view);
	

}
