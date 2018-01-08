package objects.playerResource.selectionCriterias;

import objects.SelectionCriteria;

public interface Identifyable extends SelectionCriteria {
	/**
	 * Identifies the osbject on which this is invoked upon.
	 */
	void identify();

}
