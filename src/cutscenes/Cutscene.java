package cutscenes;

import java.awt.Graphics2D;

public abstract class Cutscene{

	protected boolean hasEnded;
	
	public Cutscene() {
		  
	}

	public abstract void tick();
	public abstract void render(Graphics2D g);
	
	public boolean hasEnded(){
		return hasEnded;
	}
}
