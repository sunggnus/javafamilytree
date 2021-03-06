package tree.gui.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.Main;

import translator.Translator;
import tree.gui.search.line.AbstractLine;
import tree.gui.search.line.factory.AbstractOverviewLineFactory;
import tree.gui.window.EditNoteDialog;
import tree.model.Note;

public class NoteOverview extends AbstractOverview {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280416841272668562L;

	private List<Note> notes;

	public NoteOverview(List<Note> notes, Filter filter,
			AbstractOverviewLineFactory factory) {
		super(factory, filter);
		this.notes = notes;

		Comparator<Note> comp = new Comparator<Note>() {

			@Override
			public int compare(Note noteOne, Note noteTwo) {

				List<String> firstNotes = noteOne.getComments();
				List<String> secondNotes = noteTwo.getComments();

				if (firstNotes.size() > secondNotes.size()) {
					firstNotes = noteTwo.getComments();
					secondNotes = noteOne.getComments();
				}

				Iterator<String> itTwo = secondNotes.iterator();
				int key = 0;
				for (Iterator<String> itOne = firstNotes.iterator(); itOne
						.hasNext();) {
					key = itOne.next().toLowerCase()
							.compareTo(itTwo.next().toLowerCase());
					if (key != 0) {
						break;
					}
				}

				return key;
			}

		};

		Collections.sort(this.notes, comp);

		setCenter(new JPanel());

		this.filterView();

		this.constructOverview(true);

		this.getAddButton().setText(
				Main.getTranslator().getTranslation("generateNote",
						Translator.LanguageFile.OVERVIEW_DIALOG));

		this.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditNoteDialog(null);
				filterView();
			}

		});

		this.setSize(700, 300);
		this.setVisible(true);

	}

	@Override
	public void filterView() {
		getCenter().removeAll();
		getCenter().setLayout(new BoxLayout(getCenter(), BoxLayout.Y_AXIS));
		for (Note note : this.notes) {
			if (getFilter().filter(note, this.getFilterText().getText())) {
				AbstractLine edit = getFactory().createOverviewLine(note, this);
				if (edit == null) {
					continue;
				}
				edit.setMainSize(120);
				getCenter().add(edit);
				setpSize((int) edit.getPreferredSize().getHeight());
			}
		}
		if (this.isVisible()) {
			this.actualizeSize();
		}

	}

}
