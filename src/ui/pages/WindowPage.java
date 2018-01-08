package ui.pages;

import java.awt.Graphics2D;

public abstract class WindowPage {

	public WindowPage() {
		// TODO Auto-generated constructor stub
	}
	abstract public void tick();
	abstract public void render(Graphics2D g);

}
