package tree.model;

import java.io.Serializable;

public interface ConnectionListener extends Serializable {

	public void connectionChanged(Person person);

}
