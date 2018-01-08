package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import framework.Utilities;

public class TextDisplayer {
	
	private int x;
	private int y;
	private int width;
	
	private String text;
	private int size;
	
	public void setSize(int size){
		this.size=size;
	}
	
	public TextDisplayer(int x, int y, String text,int width) {
		super();
		this.x = x;
		this.y = y;
		this.text = text;
		this.width=width;
		validateText();
		size=15;

	}
	
	private void validateText(){
		if(this.text==null){
			text="No Information is available";
		}
		//System.out.println(text);
	}


	public void displayText(Graphics2D g){
		Font prevFont=g.getFont();
		//System.out.println(size);
		g.setFont(new Font("Consolas", Font.BOLD, size));
		
		g.setColor(Color.GREEN);
		//drawString("HELLLLLLL",5, 0);
		Utilities.drawParagraph(x,y,g, text, width);
		g.setFont(prevFont);
	}
	
}
