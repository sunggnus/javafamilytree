package tree.gui.field;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;
import translator.Translator;
import tree.gui.search.DefaultFilter;
import tree.gui.search.PersonOverview;
import tree.gui.search.line.PersonChooseLine;
import tree.gui.search.line.factory.PersonChooseLineFactory;
import tree.model.AgeException;
import tree.model.InvalidSexException;
import tree.model.LineageException;
import tree.model.Person;

public class PersonEditField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4114184176996598355L;

	static public final int MODE_FATHER = PersonChooseLine.MODE_FATHER;
	static public final int MODE_MOTHER = PersonChooseLine.MODE_MOTHER;
	static public final int MODE_CHILD = PersonChooseLine.MODE_CHILD;
	static public final int MODE_PARTNER = PersonChooseLine.MODE_PARTNER;

	static final private int buttonSize = 100;

	private Person owner;
	private int mode;

	/**
	 * 
	 * @param owner
	 * @param mode
	 * @param width
	 */
	public PersonEditField(Person owner, int mode) {

		this.mode = mode;

		this.setOwner(owner);

	}

	public void setOwner(Person owner) {

		this.owner = owner;
		this.removeAll();
		switch (mode) {
		case MODE_FATHER:
			if (owner != null) {
				this.constructSingle(owner.getFather());
			} else {
				this.constructSingle(null);
			}
			break;
		case MODE_MOTHER:
			if (owner != null) {
				this.constructSingle(owner.getMother());
			} else {
				this.constructSingle(null);
			}
			break;
		case MODE_CHILD:
			this.constructMulti(Main.getTranslator().getTranslation("children",
					Translator.LanguageFile.EDIT_PERSON_DIALOG));
			break;
		case MODE_PARTNER:
			this.constructMulti(Main.getTranslator().getTranslation("partner",
					Translator.LanguageFile.EDIT_PERSON_DIALOG));
			break;
		default:
			// do nothing field stays empty
		}
	}

	protected Person getOwner() {
		return this.owner;
	}

	private void constructMulti(final String header) {
		int defaultSize = AbstractField.DEFAULT_WIDTH - buttonSize;
		ModifiedJButton add = new ModifiedJButton(header, Main.getTranslator()
				.getTranslation("add",
						Translator.LanguageFile.EDIT_PERSON_DIALOG),
				defaultSize, buttonSize);

		LinkedList<Person> theList;
		if (this.getOwner() != null) {
			switch (mode) {
			case MODE_PARTNER:
				theList = (LinkedList<Person>) this.getOwner().getPartners();
				break;
			case MODE_CHILD:
				theList = (LinkedList<Person>) this.getOwner().getChildren();
				break;
			default:
				theList = (LinkedList<Person>) this.getOwner().getPartners();
			}

		} else {
			theList = new LinkedList<Person>();
		}
		int num = theList.size();

		this.setLayout(new GridLayout(num + 1, 1));
		this.add(add);
		final PersonEditField thisField = this;
		for (final Person person : theList) {
			String labelContent = person.getGivenName() + " "
					+ person.getFamilyName();
			if (person.getBirthName() != null
					&& !person.getBirthName().isEmpty()) {
				labelContent += " "
						+ Main.getTranslator().getTranslation("birth",
								Translator.LanguageFile.EDIT_PERSON_DIALOG)
						+ person.getBirthName();
			}
			ModifiedJButton button = new ModifiedJButton(labelContent, Main
					.getTranslator().getTranslation("remove",
							Translator.LanguageFile.EDIT_PERSON_DIALOG),
					defaultSize, buttonSize);
			button.getJButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					switch (mode) {
					case MODE_CHILD:
						getOwner().disconnectChild(person);
						break;
					case MODE_PARTNER:
						getOwner().removePartner(person);
						break;
					default: // do nothing
					}
					thisField.removeAll();
					thisField.constructMulti(header);

					thisField.getTopLevelAncestor().revalidate();
				}

			});
			this.add(button);

		}

		this.setPreferredSize(new Dimension((int) this.getPreferredSize()
				.getWidth(), (theList.size() + 1) * 35));

		add.getJButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PersonOverview(Main.getMainNode().getPersonsReference(),
						new DefaultFilter(), new PersonChooseLineFactory(owner,
								mode), false);
				thisField.removeAll();
				thisField.constructMulti(header);

				thisField.getTopLevelAncestor().revalidate();
				if (thisField.getTopLevelAncestor() instanceof Window) {
					((Window) thisField.getTopLevelAncestor()).pack();
				}

			}

		});

	}

	private void constructSingle(Person person) {

		final JButton delete = new JButton(Main.getTranslator().getTranslation(
				"remove", Translator.LanguageFile.EDIT_PERSON_DIALOG));
		JButton change = new JButton("ersetzen");
		String labelContent = (mode == MODE_MOTHER) ? Main.getTranslator()
				.getTranslation("mum",
						Translator.LanguageFile.EDIT_PERSON_DIALOG) : Main
				.getTranslator().getTranslation("dad",
						Translator.LanguageFile.EDIT_PERSON_DIALOG);
		if (person != null) {
			labelContent += person.getGivenName() + " "
					+ person.getFamilyName();
			if (person.getBirthName() != null
					&& !person.getBirthName().isEmpty()) {
				labelContent += " "
						+ Main.getTranslator().getTranslation("birth",
								Translator.LanguageFile.EDIT_PERSON_DIALOG)
						+ person.getBirthName();
			}
		}
		final JLabel name = new JLabel(labelContent);
		name.setHorizontalAlignment(JLabel.LEFT);
		name.setHorizontalTextPosition(JLabel.LEFT);

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					switch (mode) {
					case MODE_FATHER:
						owner.setFather(null);
						break;
					case MODE_MOTHER:
						owner.setMother(null);
						break;
					default:
						// do nothing
					}
					name.setText("");
					delete.setEnabled(false);
				} catch (InvalidSexException e) {
					if (mode == MODE_FATHER) {
						javax.swing.JOptionPane
								.showMessageDialog(
										null,
										Main.getTranslator()
												.getTranslation(
														"InvalidSexExceptionMale",
														Translator.LanguageFile.OVERVIEW_DIALOG));
					} else {
						javax.swing.JOptionPane
								.showMessageDialog(
										null,
										Main.getTranslator()
												.getTranslation(
														"InvalidSexExceptionFemale",
														Translator.LanguageFile.OVERVIEW_DIALOG));

					}
				} catch (LineageException e) {
					javax.swing.JOptionPane.showMessageDialog(
							null,
							Main.getTranslator().getTranslation(
									"LineageException",
									Translator.LanguageFile.OVERVIEW_DIALOG));
				} catch (AgeException e) {
					javax.swing.JOptionPane.showMessageDialog(
							null,
							Main.getTranslator().getTranslation("AgeException",
									Translator.LanguageFile.OVERVIEW_DIALOG));
				}

			}

		});

		change.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PersonOverview(Main.getMainNode().getPersonsReference(),
						new DefaultFilter(), new PersonChooseLineFactory(owner,
								mode), false);
				Person person;
				String labelContent = "";
				switch (mode) {
				case MODE_FATHER:
					person = owner.getFather();
					labelContent = Main.getTranslator().getTranslation("dad",
							Translator.LanguageFile.EDIT_PERSON_DIALOG);
					break;
				case MODE_MOTHER:
					person = owner.getMother();
					labelContent = Main.getTranslator().getTranslation("mum",
							Translator.LanguageFile.EDIT_PERSON_DIALOG);
					break;
				default:
					person = owner.getFather();
					// do nothing
				}

				if (person != null) {
					delete.setEnabled(true);
					labelContent += person.getGivenName() + " "
							+ person.getFamilyName();
					if (person.getBirthName() != null
							&& !person.getBirthName().isEmpty()) {
						labelContent += " "
								+ Main.getTranslator()
										.getTranslation(
												"birth",
												Translator.LanguageFile.EDIT_PERSON_DIALOG)
								+ person.getBirthName();
					}
				} else {
					delete.setEnabled(false);
				}
				name.setText(labelContent);

			}

		});

		this.setLayout(new BorderLayout());
		JPanel left = new JPanel();
		left.add(name);

		JPanel right = new JPanel();

		right.add(change);

		right.add(delete);
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);

	}

}
