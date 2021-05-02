package tree.model;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import javax.swing.JFrame;

import main.Config;
import main.OptionList;

public class Utils {

	static public final int TREE_UP = 1;

	static public final int TREE_DOWN = 2;

	static public final int TREE_SEARCH = 3;

	static public final int NO_MONTH = -1;
	
	
	private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	static public String calendarToString(GregorianCalendar time) {

		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("EEE, dd MMMM yyyy");

		return calendarToString(time, terminlayout);

	}
	
	static public String calendarToReadableString(GregorianCalendar time) {
		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("dd MMM yyyy");

		return calendarToString(time, terminlayout);
	}
	
	static public String calendarToXmlStorageString(GregorianCalendar time) {
		SimpleDateFormat terminlayout = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

		terminlayout.applyPattern("dd MMM yyyy");

		return calendarToString(time, terminlayout);
	}

	static public String calendarToSimpleString(GregorianCalendar time) {
		SimpleDateFormat terminlayout = new SimpleDateFormat();

		terminlayout.applyPattern("dd.MM.yyyy");

		return calendarToString(time, terminlayout);
	}

	static public String calendarToString(GregorianCalendar time,
			SimpleDateFormat layout) {
		if (time == null) {
			return "";
		}

		return layout.format(time.getTime());

	}

	static public GregorianCalendar stringToCalendar(String str) {
		str = str.replace("AFT", "");
		str = str.replace("BEF", "");
		str = str.replace("ABT", "");
		str = str.replace("CAL", "");
		str = str.replace("ca.", "");
		str = str.replace("(err.)", "");
		str = str.replace("err.", "");
		str = str.replace("ff", "");
		str = str.replace("Mai", "May");
		str = str.trim();
		str = str.toUpperCase();
		int month = determinesMonth(str);
		if (month == NO_MONTH) {
			if (str.contains("O.")) {
				str = str.split("O")[0];
			}
			if (str.isEmpty()) {
				return null;
			}
			return new GregorianCalendar(Integer.parseInt(str.trim()), 0, 1);
		}
		String[] split = str.split(getMonthName(month));

		if (!str.startsWith(getMonthName(month))) {
			int day = Integer.parseInt(split[0].trim());
			int year = Integer.parseInt(split[1].trim());
			return new GregorianCalendar(year, month, day);
		} else {
			int year = Integer.parseInt(str.replace(getMonthName(month), "")
					.trim());
			return new GregorianCalendar(year, month, 1);
		}
	}

	static private String getMonthName(int month) {
		switch (month) {
		case GregorianCalendar.JANUARY:
			return "JAN";
		case GregorianCalendar.FEBRUARY:
			return "FEB";
		case GregorianCalendar.MARCH:
			return "MAR";
		case GregorianCalendar.APRIL:
			return "APR";
		case GregorianCalendar.MAY:
			return "MAY";
		case GregorianCalendar.JUNE:
			return "JUN";
		case GregorianCalendar.JULY:
			return "JUL";
		case GregorianCalendar.AUGUST:
			return "AUG";
		case GregorianCalendar.SEPTEMBER:
			return "SEP";
		case GregorianCalendar.OCTOBER:
			return "OCT";
		case GregorianCalendar.NOVEMBER:
			return "NOV";
		case GregorianCalendar.DECEMBER:
			return "DEC";
		default:
			return "NO_MONTH";
		}
	}

	static private int determinesMonth(String ostr) {
		String str = ostr.toUpperCase();
		if (str.contains("JAN")) {
			return GregorianCalendar.JANUARY;
		}
		if (str.contains("FEB")) {
			return GregorianCalendar.FEBRUARY;
		}
		if (str.contains("MAR")) {
			return GregorianCalendar.MARCH;
		}
		if (str.contains("APR")) {
			return GregorianCalendar.APRIL;
		}
		if (str.contains("MAY")) {
			return GregorianCalendar.MAY;
		}
		if (str.contains("JUN")) {
			return GregorianCalendar.JUNE;
		}
		if (str.contains("JUL")) {
			return GregorianCalendar.JULY;
		}
		if (str.contains("AUG")) {
			return GregorianCalendar.AUGUST;
		}
		if (str.contains("SEP")) {
			return GregorianCalendar.SEPTEMBER;
		}
		if (str.contains("OCT")) {
			return GregorianCalendar.OCTOBER;
		}
		if (str.contains("NOV")) {
			return GregorianCalendar.NOVEMBER;
		}
		if (str.contains("DEC")) {
			return GregorianCalendar.DECEMBER;
		}
		return NO_MONTH;
	}

	static public BufferedImage convertByteToImage(byte[] bytearray) {
		Image image = Toolkit.getDefaultToolkit().createImage(bytearray);
		JFrame frm = new JFrame();
		MediaTracker mt = new MediaTracker(frm);
		mt.addImage(image, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException ex) {

		}
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		while (!bi.createGraphics().drawImage(image, 0, 0, null)) {

		}
		return bi;
	}

	/**
	 * returns a list which contains every node of the tree
	 * 
	 * @param caller
	 * @return list of tree nodes
	 */
	static public List<Person> getTreeAsList(Person caller) {
		return generateSubTree(caller, null, TREE_SEARCH);
	}

	/**
	 * generates a sub tree of the whole family tree
	 * 
	 * @param caller
	 *            the person which is the connection point between the rest of
	 *            the tree and the generated sub tree
	 * @param direction
	 *            the search direction
	 * @return
	 */
	static public List<Person> generateSubTree(Person caller, int direction) {
		List<Person> subTree = new LinkedList<Person>();
		if (direction == TREE_UP) {
			subTree = generateSubTree(caller, null, direction);
		} else if (direction == TREE_DOWN) {
			subTree.add(caller);
			for (Person partner : getPartnerConnections(caller)) {
				subTree.add(partner);
			}
			subTree = generateSubTree(caller, subTree, direction);
		}

		subTree.remove(caller);
		for (Person partner : getPartnerConnections(caller)) {
			if (subTree.contains(partner)) {
				subTree.remove(partner);
			}
		}
		return subTree;
	}

	/**
	 * generates a sub tree of the whole family tree
	 * 
	 * @param caller
	 *            the person which is the connection point between the rest of
	 *            the tree and the generated sub tree
	 * @param subTree
	 *            the generated subTree
	 * @param direction
	 *            the search direction
	 * @return
	 */
	static private List<Person> generateSubTree(Person caller,
			List<Person> subTree, int direction) {
		if (subTree == null) {
			subTree = new LinkedList<Person>();
			subTree.add(caller);
		}
		switch (direction) {
		case TREE_UP:
			searchParentGeneration(caller, subTree);
			break;
		case TREE_DOWN:
			searchChildGeneration(caller, subTree);
			break;
		case TREE_SEARCH:
			searchParentGeneration(caller, subTree);
			searchChildGeneration(caller, subTree);
			break;
		default: // do nothing
		}

		return subTree;
	}

	static private void searchParentGeneration(Person caller,
			List<Person> subTree) {
		if (caller.getMother() != null && !subTree.contains(caller.getMother())) {
			subTree.add(caller.getMother());
			generateSubTree(caller.getMother(), subTree, TREE_SEARCH);
		}
		if (caller.getFather() != null && !subTree.contains(caller.getFather())) {
			subTree.add(caller.getFather());
			generateSubTree(caller.getFather(), subTree, TREE_SEARCH);
		}
	}

	static private void searchChildGeneration(Person caller,
			List<Person> subTree) {
		for (Person child : caller.getChildren()) {
			if (!subTree.contains(child)) {
				subTree.add(child);
				generateSubTree(child, subTree, TREE_SEARCH);
			}
		}
		for (Person partner : getPartnerConnections(caller)) {
			if (!subTree.contains(partner)) {
				subTree.add(partner);
				generateSubTree(partner, subTree, TREE_SEARCH);
			}
		}
	}

	/**
	 * calculate the Y-Position of a person
	 * 
	 * @param person
	 */
	static public void determineTreeGenerations(Person person) {
		List<Person> everyPerson = getTreeAsList(person);

		// 0 generation first
		for (Person inTree : everyPerson) {
			inTree.setGeneration(1);

		}
		for (Person inTree : everyPerson) {
			if (!inTree.hasParents())
				incGen(inTree);
		}

		// close gaps
		normalizeYPosition(everyPerson);

		if (Config.TREE_ORDERING_MODE == OptionList.TREE_ORDERING_YOUNGEST_ON_TOP) {
			int depth = 0;
			for (Person inTree : everyPerson)
				if (inTree.getGeneration() > depth)
					depth = inTree.getGeneration();

			for (Person inTree : everyPerson)
				inTree.setGeneration(depth - inTree.getGeneration() + 1);
		}

	}

	static public void changeOrderingMode(Person person) {
		List<Person> everyPerson = getTreeAsList(person);

		int depth = 0;
		for (Person inTree : everyPerson)
			if (inTree.getGeneration() > depth)
				depth = inTree.getGeneration();

		for (Person inTree : everyPerson)
			inTree.setGeneration(depth - inTree.getGeneration() + 1);

	}

	static private void normalizeYPosition(List<Person> list) {
		for (Person person : list) {
			// mother part
			if (person.getMother() != null) {
				if (person.getGeneration() - 1 > person.getMother()
						.getGeneration()) {
					List<Person> subtree = generateSubTree(person, TREE_UP);
					if (!(!person.getChildren().isEmpty() && subtree
							.contains(person.getChildren().get(0)))) {
						int diff = person.getGeneration() - 1
								- person.getMother().getGeneration();
						for (Person inSub : subtree) {
							inSub.setGeneration(inSub.getGeneration() + diff);
						}
					}
				}
			}
			// father part analog
			if (person.getFather() != null) {
				if (person.getGeneration() - 1 > person.getFather()
						.getGeneration()) {
					List<Person> subtree = generateSubTree(person, TREE_UP);
					if (!(!person.getChildren().isEmpty() && subtree
							.contains(person.getChildren().get(0)))) {
						int diff = person.getGeneration() - 1
								- person.getFather().getGeneration();
						for (Person inSub : subtree) {
							inSub.setGeneration(inSub.getGeneration() + diff);
						}
					}
				}
			}

		}
		int gen = 0;
		if (!list.isEmpty())
			gen = list.get(0).getGeneration();
		for (Person person : list) {
			if (gen > person.getGeneration())
				gen = person.getGeneration();
		}
		if (gen > 1) {
			int diff = gen - 1;
			diff = 0 - diff;
			for (Person person : list) {
				person.setGeneration(person.getGeneration() + diff);
			}
		}

	}

	static private void incGen(Person person) {

		List<Person> partners = getPartnerConnections(person);
		int maxGen = person.getGeneration();
		for (Person partner : partners) {
			if (partner.getGeneration() > maxGen)
				maxGen = partner.getGeneration();
		}
		for (Person partner : partners) {
			partner.setGeneration(maxGen);
		}
		person.setGeneration(maxGen);

		incChildGen(person);
		for (Person partner : partners) {
			incChildGen(partner);
		}

	}

	static private void incChildGen(Person person) {
		List<Person> childs = person.getChildren();
		for (Person child : childs) {
			if (child.getGeneration() < person.getGeneration() + 1) {
				child.setGeneration(person.getGeneration() + 1);
				incGen(child);

			}
		}
	}

	static public void basicXPositioning(Person top) {
		List<Person> everyPerson = getTreeAsList(top);
		int maxGen = 0;
		for (Person person : everyPerson) {
			person.setXPosition(1, false);
			if (person.getGeneration() > maxGen)
				maxGen = person.getGeneration();
		}

		for (int i = 0; i < maxGen + 1; i++) {
			int x = 1;
			for (Person person : everyPerson) {
				if (person.getGeneration() == i && person.getXPosition() == 1) {
					person.setXPosition(x, false);
					x += 2;

					for (Person partner : getPartnerConnections(person)) {
						partner.setXPosition(x, false);
						x += 2;
					}
					x += 2;
				}
			}
		}
	}

	/**
	 * return every partner to partner to partner etc connection
	 * 
	 * @return
	 */
	static protected List<Person> getPartnerConnections(Person person) {
		LinkedList<Person> connectives = new LinkedList<>();

		for (Person partner : person.getPartners()) {

			if (!connectives.contains(partner)) {

				Stack<Person> partnerStack = new Stack<>();
				partnerStack.push(partner);

				while (!partnerStack.isEmpty()) {

					Person first = partnerStack.pop();
					for (Person ipartners : first.getPartners()) {

						if (!connectives.contains(ipartners)) {
							partnerStack.push(ipartners);
							connectives.add(ipartners);
						}
					}

				}
			}
		}

		return connectives;
	}
	
	
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] hexStringToByteArray(String s) {
		s = s.trim();
	    int len = s.length();
	    
	    if(len % 2 == 1){
	    	len -= 1;
	    }
	    
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	

	/**
	 * the upper most person of the tree
	 * 
	 * @param person
	 * @return
	 */
	/*
	 * static private Person getTopLevelPerson(List<Person> persons){
	 * 
	 * class PersonWrap{ public Person person; public int deep;
	 * 
	 * public PersonWrap getFather(){ PersonWrap result = new PersonWrap();
	 * result.person = person.getFather(); result.deep = deep + 1; return
	 * result; }
	 * 
	 * public PersonWrap getMother(){ PersonWrap result = new PersonWrap();
	 * result.person = person.getFather(); result.deep = deep + 1; return
	 * result; }
	 * 
	 * private PersonWrap getTopLevelPerson(PersonWrap person){ PersonWrap
	 * result = null;
	 * 
	 * 
	 * if(person.getFather() != null){ result =
	 * getTopLevelPerson(person.getFather()); } if(person.getMother() != null){
	 * PersonWrap mother = getTopLevelPerson(person.getMother()); if(result !=
	 * null && mother != null && result.deep < mother.deep){ result = mother; }
	 * 
	 * } if(result == null) return person; else return result; } } PersonWrap
	 * best = null; for(Person person : persons){ PersonWrap wrap = new
	 * PersonWrap();
	 * 
	 * wrap.person = person; wrap.deep = 0; wrap = wrap.getTopLevelPerson(wrap);
	 * if(best == null || wrap.deep > best.deep) best = wrap;
	 * 
	 * 
	 * 
	 * }
	 * 
	 * return best.person; }
	 */

}
