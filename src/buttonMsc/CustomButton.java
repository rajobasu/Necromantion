package buttonMsc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import objects.Collectable;

/**
 * This is a CustomButton class that can be used on a Canvas instead of a normal
 * Button Component. This button has to be rendered and updated along with the
 * rest of the game state. With each button, an associated object can be set, a
 * reference to which is returned when a ButtonEvent is generated. This button
 * is used when the button needs to be rendered on the game canvas and can be
 * needed to be removed and hid arbitrarily.
 * 
 * @author Rajarshi
 *
 */
public final class CustomButton implements MouseListener {

	// private BufferedImage image;
	private String text = "";
	private boolean pressed = false;
	private boolean hovering = false;

	private int x;
	private int y;
	private int width;
	private int height;

	private Color currentColor, defaultColor, hoverColor, pressColor;
	private Point mousePoint = new Point(0, 0);
	private ButtonListener btnListener = null;
	private Canvas canvasObject;

	private BufferedImage image;
	private BufferedImage darkImage;
	private BufferedImage backgroundImage;

	private String actionCommand = "default";
	private Collectable object;

	private boolean hasPendingUpdate;
	private ButtonEvent buttonEvent;

	private boolean enabled;

	private boolean dummy;

	/*
	 * private CustomButton(Canvas canvasObject,JFrame frame) { this.x=100;
	 * this.y=100; this.width=100; this.height=100;
	 * 
	 * canvasObject.addMouseListener(this); currentColor=new Color(255,255,255);
	 * defaultColor=new Color(255,255,255); hoverColor=new Color(255,255,255);
	 * pressColor=new Color(255,255,255); }
	 */
	/**
	 * Creates a dummy button, basically for testing purposes.Cannot do
	 * anything.Not recommended to use this constructor.
	 */
	public CustomButton() {
		System.out.println("Dummy button created");
		dummy = true;
	}

	/**
	 * This constructor creates a new button with default colour of all states
	 * as white.
	 * 
	 * @param x
	 *            - The upper left x-coordinate of the button
	 * @param y
	 *            - The upper left y-coordinate of the button
	 * @param width
	 *            - The width of the button
	 * @param height
	 *            - The height of the button
	 * @param canvasObject
	 *            - The canvas on which the button will be drawn.
	 */
	public CustomButton(int x, int y, int width, int height, Canvas canvasObject) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.canvasObject = canvasObject;
		canvasObject.addMouseListener(this);
		currentColor = Color.GREEN;
		currentColor = new Color(255, 255, 255);
		defaultColor = new Color(255, 255, 255);
		hoverColor = new Color(255, 255, 255);
		pressColor = new Color(255, 255, 255);
		enabled = true;
	}

	/**
	 * Creates a new CustomButton with default color white for all Button
	 * states.
	 * 
	 * @param x
	 *            - The upper left x-coordinate of the button
	 * @param y
	 *            - The upper left y-coordinate of the button
	 * @param width
	 *            - The width of the button
	 * @param height
	 *            - The height of the button
	 * @param canvasObject
	 *            - The canvas on which the button will be drawn.
	 * @param image
	 *            - The image which will be displayed on the Canvas object.
	 * @param object
	 *            - A collectable object that is associated with this
	 *            CustomButton, a reference to which is returned when a
	 *            ButtonEvent is generated.
	 */
	public CustomButton(int x, int y, int width, int height, Canvas canvasObject, BufferedImage image,
			Collectable object) {
		this.image = image;
		this.darkImage = darkenImage(image);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		canvasObject.addMouseListener(this);
		currentColor = Color.GREEN;
		currentColor = new Color(255, 255, 255);
		defaultColor = new Color(255, 255, 255);
		hoverColor = new Color(255, 255, 255);
		pressColor = new Color(255, 255, 255);
		this.canvasObject = canvasObject;
		this.object = object;

		enabled = true;
	}

	public void setImage(BufferedImage image) {
		if (dummy)
			return;
		this.image = image;
	}

	/**
	 * Sets the image which is drawn in place of the given image(if any) when
	 * the Button is not active. Not recommended for using.
	 * 
	 * @param image
	 *            - The image which is drawn when the Button is inactive, which
	 *            is usually a darker shade of the given image.
	 */
	public void setDarkImage(BufferedImage image) {
		if (dummy)
			return;
		this.darkImage = image;
	}

	public void setBackgroundImage(BufferedImage image) {
		if (dummy)
			return;
		this.backgroundImage = image;
	}

	/**
	 * This method renders the Button using the Graphics context. This method
	 * sould be called each time the game environment is updated. This method
	 * either displays the text or the image, if set, but not both at the same
	 * time. If both can be rendered, Image gets the preference.
	 * 
	 * @param g
	 *            - The graphics context using which the Button is rendered.
	 */
	public void render(Graphics g) {
		if (dummy)
			return;

		if (image == null) {
			g.setColor(currentColor);
			if (hovering) {
				g.fillRect(this.x - 5, this.y - 5, width + 10, height + 10);

			} else if (!pressed)
				g.fillRect(this.x, this.y, width, height);
			else
				g.fill3DRect(this.x, this.y, width, height, true);

			g.setColor(Color.BLACK);
			g.drawString(text, this.x + 10, this.y + 15);

		} else {
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, x, y, width, height, null);
			}

			if (enabled) {
				if (hovering) {
					g.drawImage(image, x - 5, y - 5, width + 10, height + 10, null);

				} else
					g.drawImage(image, x, y, width, height, null);
			} else {
				g.drawImage(darkImage, x, y, width, height, null);
			}
		}
	}

	/**
	 * 
	 * @return The rectangle that encompasses the CustomButton.
	 */

	public Rectangle getBounds() {
		// if (dummy) return;
		return new Rectangle(x, y, width, height);

	}

	/**
	 * Updates the state of the Button.
	 */
	public void tick() {
		if (dummy)
			return;
		if (!enabled)
			return;

		mousePoint = getMouseLocation();
		changeColor();

		if (hasPendingUpdate)
			btnListener.buttonClicked(buttonEvent);

		hasPendingUpdate = false;
		buttonEvent = null;
	}

	/**
	 * 
	 * @return The current mouse Location on the Canvas.
	 */
	private Point getMouseLocation() {
		if (dummy)
			return null;
		int x = 0;
		int y = 0;

		try {
			x = (int) (canvasObject.getMousePosition().getX());
			y = (int) (canvasObject.getMousePosition().getY());
		} catch (NullPointerException nl) {
		}

		return new Point(x, y);
	}

	/**
	 * Changes the color based on the state of the Button. Here, the
	 * currentColor is set to some other predefined state color like hoverColor,
	 * pressColor or defaultColor.
	 */
	public void changeColor() {
		if (dummy)
			return;
		hovering = false;
		if (!enabled)
			return;
		if (!pressed) {
			if (getBounds().contains(mousePoint)) {
				hovering = true;
				currentColor = hoverColor;
			} else
				currentColor = defaultColor;
		} else {
			currentColor = pressColor;
		}

	}

	public void addButtonListener(ButtonListener btnListener) {
		if (dummy)
			return;
		this.btnListener = btnListener;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Creates a new ButtonEvent when the mouseClicked event is trapped.
	 */
	public void mouseClicked(MouseEvent e) {
		if (dummy)
			return;
		if (enabled) {
			if (btnListener != null) {

				if (getBounds().contains(mousePoint)) {
					ButtonID id = ButtonID.UNDETERMINABLE;
					if (e.getButton() == MouseEvent.BUTTON1)
						id = ButtonID.LEFT;
					if (e.getButton() == MouseEvent.BUTTON2)
						id = ButtonID.RIGHT;

					// btnListener.buttonClicked(new ButtonEvent(id, object,
					// actionCommand));
					hasPendingUpdate = true;
					buttonEvent = new ButtonEvent(id, object, actionCommand);
				}
			}
		}
	}

	/**
	 * checks if the Button contains the current MouseLocation.If so , then
	 * changes the state to pressed.
	 */
	public void mousePressed(MouseEvent e) {
		if (dummy)
			return;
		if (getBounds().contains(mousePoint))
			pressed = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (dummy)
			return;
		pressed = false;
	}

	public void setActionCommand(String actionCommand) {
		if (dummy)
			return;
		this.actionCommand = actionCommand;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (dummy)
			return;
		this.text = text;
	}

	public void setDefaultColor(Color c) {
		if (dummy)
			return;
		defaultColor = c;
	}

	public void setHoverColor(Color c) {
		if (dummy)
			return;
		hoverColor = c;
	}

	public void setPressColor(Color c) {
		if (dummy)
			return;
		pressColor = c;
	}

	public Collectable getObject() {
		return object;
	}

	public void setObject(Collectable object) {
		if (dummy)
			return;
		this.object = object;
	}

	/**
	 * removes this instance from canvas's list of MouseListeners.
	 */
	public void destroy() {
		if (dummy)
			return;
		canvasObject.removeMouseListener(this);
	}

	public void disable() {
		if (dummy)
			return;
		enabled = false;
	}

	public void enable() {
		if (dummy)
			return;
		enabled = true;
	}

	public boolean isEnabled() {
		// if (dummy) return;
		return enabled;
	}

	/**
	 * Creates a new Image with a darker shade than that of the given image.
	 * 
	 * @param image
	 *            - The image that has to be made darker.
	 * @return A darker shade of the input image.
	 */
	private BufferedImage darkenImage(BufferedImage image) {
		if (dummy)
			return null;
		int width = image.getWidth();
		int height = image.getHeight();
		image = deepCopy(image);

		WritableRaster raster = image.getRaster();

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] -= 50;
				pixels[1] -= 50;
				pixels[2] -= 50;

				pixels[0] = Math.max(pixels[0], 0);
				pixels[1] = Math.max(pixels[0], 0);
				pixels[2] = Math.max(pixels[0], 0);

				raster.setPixel(xx, yy, pixels);
			}
		}
		return image;
	}
	/**
	 * Makes a complete copy of the BufferedImage and then returns it.
	 * @param bi
	 * @return A copy of the BufferedImage.
	 */
	private BufferedImage deepCopy(BufferedImage bi) {
		if (dummy)
			return null;
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
