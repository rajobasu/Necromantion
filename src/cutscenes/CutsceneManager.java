package cutscenes;

import java.awt.Graphics2D;

public class CutsceneManager {

	private Cutscene currentCutscene;

	public CutsceneManager() {
	}

	public void updateCutscene() {
		currentCutscene.tick();
	}

	public void renderCutscene(Graphics2D g) {
		currentCutscene.render(g);
	}

	public void playCutscene(Cutscene cutScene) {
		this.currentCutscene = cutScene;
	}

	public void endCurrentCutscene() {
		currentCutscene = null;
	}

	public boolean hasPendingCutscene() {
		if (currentCutscene == null) return false;
	
		return !currentCutscene.hasEnded();
	}

}
