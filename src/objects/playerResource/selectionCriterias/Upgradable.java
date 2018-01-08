package objects.playerResource.selectionCriterias;

import objects.SelectionCriteria;

public interface Upgradable extends SelectionCriteria {
	/**
	 * upgrade the object on which this method was invoked.
	 */
	void upgrade();
}
