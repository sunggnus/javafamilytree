package tree.gui.search;

import tree.model.Note;
import tree.model.Person;
import tree.model.PersonUtil;

public class DefaultFilter implements Filter {

	@Override
	public boolean filter(Object obj, String spec) {
		if (spec.isEmpty()) {
			return true;
		}

		spec = spec.toLowerCase();
		String[] specs = spec.split(" ");

		String toSearch = "";

		if (obj instanceof Person) {
			Person person = (Person) obj;

			String birthday = "";
			String deathday = "";
			if (person.getBirthdate() != null) {
				birthday = PersonUtil.calendarToSimpleString(person
						.getBirthdate());
			}
			if (person.getDeathdate() != null) {
				deathday = PersonUtil.calendarToSimpleString(person
						.getDeathdate());
			}
			toSearch = person.getGivenName() + " " + person.getFamilyName()
					+ " " + person.getBirthName() + " " + birthday + " "
					+ deathday;

		}

		if (obj instanceof Note) {
			Note note = (Note) obj;
			for (String str : note.getComments()) {
				toSearch += str;
			}

		}

		boolean accept = true;
		for (String speci : specs) {
			accept &= toSearch.toLowerCase().contains(speci);

		}

		return toSearch.toLowerCase().contains(spec) || accept;
	}

}
