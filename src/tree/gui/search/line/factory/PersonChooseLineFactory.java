package tree.gui.search.line.factory;

import tree.gui.search.AbstractOverview;
import tree.gui.search.line.AbstractPersonLine;
import tree.gui.search.line.PersonChooseLine;
import tree.model.Note;
import tree.model.Person;

public class PersonChooseLineFactory extends AbstractOverviewLineFactory {

	private Person caller;
	private int mode;

	public PersonChooseLineFactory(Person caller, int mode) {
		this.caller = caller;
		this.mode = mode;
	}

	@Override
	public AbstractPersonLine createOverviewLine(Person person,
			AbstractOverview view) {
		if ((mode == PersonChooseLine.MODE_FATHER && person.isFemale())
				|| (mode == PersonChooseLine.MODE_MOTHER && person.isMale())
				|| caller == person) {
			// since this does not correspond with the chosen mode or the person
			// should not be able to choose himself
			return null;
		}
		return new PersonChooseLine(person, caller, mode, view);
	}

	@Override
	public AbstractPersonLine createOverviewLine(Note note,
			AbstractOverview view) {
		// returns null since this is only a PersonChooseLineFactory
		return null;
	}

}
