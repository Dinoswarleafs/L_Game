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
		b.display(200, 200, 100);
	}
}
