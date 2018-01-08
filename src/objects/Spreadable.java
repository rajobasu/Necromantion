package objects;

import maps.Map;

/**
 * This is a interface to be implemented to all objects that can logically
 * spread, a finite number of turns, in the {@code Map}.
 * 
 * @author rajarshibasu
 *
 */
public interface Spreadable {
	/**
	 * This method is used to check whether this object can spread anymore.
	 * 
	 * @return A boolean, true if it can, false otherwise.
	 */
	boolean canSpread();

	/**
	 * This method, when called, spreads the object in suitable directions based
	 * on the implementations.
	 * 
	 * @param map
	 *            The current {@code Map} in which this objet can spread.
	 */
	void spread(Map map);
}
