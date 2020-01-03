import processing.core.PApplet;

public class LGameDriver extends PApplet {

	private LGameManager game; 
	
	public static void main(String[] args) {
		PApplet.main("LGameDriver");
	}
	
	public void settings() {
		size(LConstants.SCREEN_WIDTH, LConstants.SCREEN_HEIGHT);
	}
	
	public void setup() {
		game = new LGameManager(this);
	}
	
	public void draw() {
		game.mouseInput();
		game.display();
	}
	
	
	public void keyPressed() {
		game.keyInput(key);
	}
}
