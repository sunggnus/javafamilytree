package tree.model.io;

import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;
import java.util.List;

import tree.model.Person;
import tree.model.Person.Sex;

public class XmlPerson {
	/**
	 * contains the family name of the person
	 */
	public String familyName;
	/**
	 * contains the given Name of the person
	 */
	public String givenName;
	/**
	 * contains the birth name of the person
	 */
	public String birthName;

	/**
	 * contains the location where this person is born
	 */
	public String location;

	/**
	 * contains the trade of the person
	 */
	public String trade;
	/**
	 * contains comments
	 */
	public String commentOne;
	/**
	 * this boolean is true if the person is still alive
	 */
	public boolean alive;
	/**
	 * sex of the person 
	 * 
	 */
	public Sex sex;

	/**
	 * ID of the father -1 = unknown
	 */
	public long father;
	/**
	 * ID of the mother  -1 = unknown
	 */
	public long mother;

	/**
	 * the partners of a person the last entry within the list is the actual
	 * partner
	 */
	public List<Long> partners;

	/**
	 * the children of a person
	 */
	public List<Long> children;

	/**
	 * the birth date of a person
	 */

	public GregorianCalendar birthDate;

	/**
	 * the death date of a person it is null if the person is still alive or it
	 * is not known
	 */

	public GregorianCalendar deathDate;

	/**
	 * contains the generation in which this person was born the oldest
	 * generation has the number 0 than 1 and so on
	 */
	public int generation;

	/**
	 * contains the relative x position of the person within the tree this is
	 * important for positioning the person when drawing the tree the
	 * positioning starts with zero
	 * 
	 */
	public int xPosition;

	/**
	 * this boolean is true if the person should be visible within the family
	 * tree else false
	 */
	public boolean visible;

	/**
	 * contains the reference id to the image -1 = no image
	 */
	public long picture;

	public long ID;
}
