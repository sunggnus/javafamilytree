package tree.gui.search;

public interface Filter {

	/**
	 * filters the entries by a certain String specification
	 * 
	 * @param text
	 * @param spec
	 *            the given specification
	 * @return true if the text fulfills the filter specification
	 */
	public boolean filter(Object person, String spec);

}
