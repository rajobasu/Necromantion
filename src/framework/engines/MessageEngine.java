package framework.engines;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;

import framework.Constants;
import framework.Utilities;
import imageHandlers.ImageLoader;
import imageHandlers.Images;

public class MessageEngine extends Engine {

	private static LinkedList<StringColorPair> messages;

	private static final int FONT_SIZE = 12;
	private static int MAX_SIZE = 8;

	public MessageEngine() {
		messages = new LinkedList<>();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		// g.setColor(Color.LIGHT_GRAY);
		int u = Constants.UNIT_DISTANCE;
		ImageLoader imgLdr = ImageLoader.INSTANCE();
		g.translate(offsetX, offsetY);
		// g.fillRect(offsetX, offsetY, breadth*Constants.UI_UNIT_DISTANCE,
		// length*Constants.UI_UNIT_DISTANCE);

		g.drawImage(imgLdr.getImage(Images.HUD_BACKGROUND, 0), 0, 0, breadth * u, length * u, null);
		g.setColor(Color.YELLOW);

		Font prevFont = g.getFont();
		g.setFont(new Font("Consolas", Font.PLAIN, FONT_SIZE));

		int y = 11;
		g.drawString(":: Status ::", 2, y);
		synchronized (messages) {

			for (StringColorPair s : messages) {
				// y+=g.getFontMetrics().getHeight();
				g.setColor(s.c);
				y = Utilities.drawParagraph(2, y, g, s.s, breadth * Constants.UNIT_DISTANCE);
			}
		}
		g.setFont(prevFont);
		g.translate(-offsetX, -offsetY);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugPrint() {

	}

	public static void writeToScreen(String s) {
		synchronized (messages) {

			messages.offer(new StringColorPair(s, Color.YELLOW));
			while (messages.size() > MAX_SIZE) {
				messages.removeFirst();
			}
		}
	}

	public static void writeToScreen(String s, Color c) {
		synchronized (messages) {

			messages.offer(new StringColorPair(s, c));
			while (messages.size() > MAX_SIZE) {
				messages.removeFirst();
			}
		}
	}

}

class StringColorPair {
	String s;
	Color c;

	public StringColorPair(String s, Color c) {
		super();
		this.s = s;
		this.c = c;
	}

}
