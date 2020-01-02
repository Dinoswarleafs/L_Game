import processing.core.PApplet;

public class LGameDriver extends PApplet {

	private LGameManager game; 
	
	public static void main(String[] args) {
		PApplet.main("LGameDriver");
	}
	
	public void settings() {
		size(800, 800);
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
