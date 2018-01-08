package objects;

/**
 * This is a functional interface, to be implemented by all objects that can be
 * logically thrown to a spot on the {@code Map}.
 * 
 * @author rajarshibasu
 *
 */
public interface Throwable {

	/**
	 * It shifts the position of the object from its current location to the
	 * desired one. Animations may take place during this shift, although the
	 * shift itself will probably be implemented as a constant time operation.
	 * 
	 * @param x
	 *            The X co-ordiante of the location this object is to be thrown
	 *            to.
	 * @param y
	 *            The Y co-ordiante of the location this object is to be thrown
	 *            to.
	 */
	void throwTo(int x, int y);

}
