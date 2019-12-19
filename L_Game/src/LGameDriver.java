import processing.core.PApplet;

public class LGameDriver extends PApplet {

	public Board b = new Board(this);
	
	public static void main(String[] args) {
		PApplet.main("LGameDriver");
	}
	
	public void settings() {
		size(800, 800);
	}
	
	public void setup() {
		background(255);
		
	}
	
	public void draw() {
		background(255);
		b.mouseInput(200, 200, 100);
		b.display(200, 200, 100);
	}
	
	
	public void keyPressed() {
		if (key == 'z') {
			b.rotatePiece(true);
		} else {
			b.rotatePiece(false);
		}
	}
}
