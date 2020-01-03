import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Button {
	
	private PApplet p;
	
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
		isActive = false;
		color = new int[] {LConstants.UNACTIVE_BUTTON_COLOR, LConstants.ACTIVE_BUTTON_COLOR,
				LConstants.HOVERED_BUTTON_COLOR, LConstants.CLICKED_BUTTON_COLOR};
		font = p.createFont(LConstants.TEXT_FONT, LConstants.TEXT_SIZE);
	}
	
	public void display() {
		p.rectMode(PConstants.CENTER);
		p.fill(getColor());
		p.rect(xPos, yPos, width, height);
		p.textFont(font);
		p.textAlign(PConstants.CENTER);
		p.fill(LConstants.DEFAULT_TEXT_COLOR);
		p.text(displayText, xPos, yPos + height/4);
		update();
	}
	
	private int getColor() {
		if (isActive) {
			if (mouseOver()) {
				if (p.mousePressed) {
					return color[LConstants.CLICKED_BUTTON];
				} 
				return color[LConstants.HOVERED_BUTTON];
			} 
			return color[LConstants.ACTIVE_BUTTON];
		} 
		return color[LConstants.UNACTIVE_BUTTON];
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
		return isActive && !p.mousePressed && prevPressed && mouseOver();
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