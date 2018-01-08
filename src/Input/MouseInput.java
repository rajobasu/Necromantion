package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import framework.Utilities;
import maps.Location;

public class MouseInput implements MouseListener {

	private Lock lock = new ReentrantLock();
	private boolean activated;
	public Location latestLocation;

	public MouseInput() {
		activated = false;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if (!activated) return;

		lock.lock();
		try {
			latestLocation = new Location(me.getX(), me.getY());
		} finally {
			lock.unlock();
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	/**
	 * 
	 * @return The grid location on which the latest mouse click ocuured. It
	 *         does not reset the latest location.
	 */
	public Location peekLatestLocation() {
		lock.lock();
		try {
			return Utilities.getMouseLocation(latestLocation);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 
	 * @return The grid location on which the latest mouse click ocuured. It
	 *         resets the latest location.
	 */
	public Location popLatestLocation() {
		lock.lock();
		try {
			Location toRet = Utilities.getMouseLocation(latestLocation);
			latestLocation = null;
			return toRet;
		} finally {
			lock.unlock();
		}
	}

	public void activate() {
		activated = true;
	}

	public void deactivate() {
		activated = false;
	}

}
