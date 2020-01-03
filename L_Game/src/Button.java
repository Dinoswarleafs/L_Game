import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Button {
	
	private PApplet p;
	
	private final static int TEXT_SIZE = 32;
	private final static String TEXT_FONT = "Calibri";
	
	private int xPos, yPos;
	private int width, height;
	private String displayText;
	private int[] color;
	private PFont font;
	private boolean prevPressed;
	private boolean isActive;
	
	public Button(PApplet p, int xPos, int yPos, int width, int height, String displayText) {
		this.p = p;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.displayText = displayText;
		isActive = true;
		color = new int[] {p.color(200), p.color(210, 210, 230), p.color(210, 230, 210), p.color(160, 180, 160)};
		font = p.createFont(TEXT_FONT, TEXT_SIZE);
	}
	
	public void display() {
		p.rectMode(PConstants.CENTER);
		p.fill(getColor());
		p.rect(xPos, yPos, width, height);
		p.textFont(font);
		p.textAlign(PConstants.CENTER);
		p.fill(0);
		p.text(displayText, xPos, yPos + height/4);
		update();
	}
	
	private int getColor() {
		if (isActive) {
			if (mouseOver()) {
				if (p.mousePressed) {
					return color[3];
				} 
				return color[2];
			} 
			return color[1];
		} 
		return color[0];
	}
	
	private void update() {
		if (!p.mousePressed && prevPressed) {
			prevPressed = false;
		}
		else if (p.mousePressed && !prevPressed) {
			prevPressed = true;
		}
	}
	
	public boolean isClicked() {
		return !p.mousePressed && prevPressed && mouseOver();
	}
	
	// BRB EATING MY BRAIN NEEDS BREAK
	
	private boolean mouseOver() {
		int xRad = width/2;
		int yRad = height/2;
		boolean xCheck = p.mouseX >= xPos - xRad && p.mouseX <= xPos + xRad;
		boolean yCheck = p.mouseY >= yPos - yRad && p.mouseY <= yPos + yRad;
		return xCheck && yCheck;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
