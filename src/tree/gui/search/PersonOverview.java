package tree.gui.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

import translator.Translator;
import tree.gui.search.line.AbstractLine;
import tree.gui.search.line.factory.AbstractOverviewLineFactory;
import tree.gui.window.EditPersonDialog;
import tree.model.Person;

public class PersonOverview extends AbstractOverview {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3193305371569566100L;

	private List<Person> persons;

	private JLabel hits;

	/**
	 * 
	 * @param persons
	 *            list of persons which should be showed
	 * @param filter
	 *            a filter for searching this list
	 * @param factory
	 *            a factory which constructs a line with entries about a person
	 *            of the list and buttons for actions
	 * @param showAddPerson
	 *            if true the addPersonButton will be displayed else not
	 */
	public PersonOverview(List<Person> persons, Filter filter,
			AbstractOverviewLineFactory factory, boolean showAddPerson) {

		super(factory, filter);
		hits = new JLabel();
		this.persons = persons;

		Comparator<Person> comp = new Comparator<Person>() {

			@Override
			public int compare(Person personOne, Person personTwo) {
				int keyOne = personOne.getFamilyName().toLowerCase()
						.compareTo(personTwo.getFamilyName().toLowerCase());
				if (keyOne != 0) {
					return keyOne;
				}
				int keyTwo = personOne.getGivenName().toLowerCase()
						.compareTo(personTwo.getGivenName().toLowerCase());
				if (keyTwo != 0) {
					return keyTwo;
				}
				// TODO other compare stuff
				return 0;
			}

		};

		Collections.sort(persons, comp);

		setCenter(new JPanel());

		this.filterView();

		// all visible / invisible button
		JButton allVisible = new JButton(Main.getTranslator().getTranslation(
				"allVisible", Translator.LanguageFile.OVERVIEW_DIALOG));
		JButton allInvisible = new JButton(Main.getTranslator().getTranslation(
				"allInvisible", Translator.LanguageFile.OVERVIEW_DIALOG));

		allVisible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getMainNode().setTreeVisibility(true);
				filterView();
				Main.getMainFrame().getCanvas().repaint();
			}
		});

		allInvisible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getMainNode().setTreeVisibility(false);
				filterView();
				Main.getMainFrame().getCanvas().repaint();
			}
		});

		this.constructOverview(showAddPerson);
		if (showAddPerson) {
			this.getNorth().add(allVisible);
			this.getNorth().add(allInvisible);
		}
		this.getNorth().add(hits);
		// north panel

		this.getAddButton().setText(
				Main.getTranslator().getTranslation("generatePerson",
						Translator.LanguageFile.OVERVIEW_DIALOG));

		this.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EditPersonDialog(true);
				filterView();
			}

		});

		this.setSize(900, 300);
		this.setVisible(true);

	}

	@Override
	public void filterView() {
		getCenter().removeAll();
		getCenter().setLayout(new BoxLayout(getCenter(), BoxLayout.Y_AXIS));
		int hit = 0;
		for (Person person : this.persons) {
			if (getFilter().filter(person, this.getFilterText().getText())) {
				AbstractLine edit = getFactory().createOverviewLine(person,
						this);
				if (edit == null) {
					continue;
				}
				edit.setMainSize(90);
				getCenter().add(edit);
				setpSize((int) edit.getPreferredSize().getHeight());
				hit++;

			}
		}
		hits.setText(String.valueOf(hit));
		if (this.isVisible()) {
			this.actualizeSize();
		}
	}

}
