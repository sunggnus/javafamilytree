package tree.model;

import main.Config;
import main.OptionList;

public class RefreshTreeLayoutListener implements ConnectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5884753047439829417L;

	@Override
	public void connectionChanged(Person person) {
		if (Config.Y_POSITIONING_MODE == OptionList.Y_AUTO_POSITIONING){
			Utils.determineTreeGenerations(person);
		}
	}

}
