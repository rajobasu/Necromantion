package objects;

/**
 * 
 * This is also an interface to mark those objects that are turnbased in nature,
 * that is, they will be updated only when the whole game environment is updated
 * due to some vaild interaction of the player. It also specifies that the turns
 * left of that given object must decrease each time the object is updated.
 * 
 * @author rajarshibasu
 *
 */
public interface TurnBased {

	/**
	 * This method should be called every time the object is updated. However,
	 * it is not strictly enforced, hence it should be taken care of that theis
	 * method is implemented.
	 */
	void decreaseTurnsLeft();

}
