package objects.playerResource.holographs;

import imageHandlers.Images;
import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.Throwable;

public abstract class Holograph extends GameObject implements Collectable, Throwable {

	public Holograph(int x, int y, int imageCode) {
		super(x, y, imageCode);
		image = Images.SCROLL;
	}

	@Override
	public void throwTo(int x, int y) {

	}

	public void use(Map map) {
	}

	public void tick(Map map) {
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Holograph";
	}
}
