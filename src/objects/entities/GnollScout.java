package objects.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import framework.Constants;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import maps.Map;

public class GnollScout extends Enemy {

	public GnollScout(int x, int y, int imageCode) {
		super(x, y, imageCode, 15, 8, 6);
		image = Images.GNOLL_SCOUT;
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, 0), x * u, y * u, u, u, null);
		super.render(g);
	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}

	public void tick(Map map) {
		super.tick(map);

		if (life <= 0)
			return;

		if (p == null)
			p = Player.getINSTANCE();
		/*
		 * if(location.distanceTo(p.getLocation())<20){
		 * 
		 * }
		 **/
		if (p.isNearby(x, y)) {
			int damage=attackEnemy(p);
			String s="";
			if(damage==Constants.DODGED){
				s+="You dodged "+getName()+"'s attack";
			}else{
				s="Gnoll attacked you for ";
				s+=damage;
				s+=" damage !.";
				
			}
			
			MessageEngine.writeToScreen(s, Color.RED);
		} else {
			move(map);
		}
		if (life == 0) {
			MessageEngine.writeToScreen("Enemy is Dead");
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Gnoll";
	}

}
