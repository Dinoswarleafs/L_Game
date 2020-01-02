import processing.core.PApplet;

public class LGameManager {
	private PApplet p;
	private Board b;
	
	private int CURRENT_PLAYER;
	private int CURRENT_MOVE;
	
	private final static int PLAYER_1 = 0;
	private final static int PLAYER_2 = 1;
	private final static int MOVE_PLAYER = 0;
	private final static int MOVE_NEUTRAL = 1;
	
	private final static int START_X = 200;
	private final static int START_Y = 200;
	private final static int SQUARE_WIDTH = 100;
	
	public LGameManager(PApplet p) {
		this.p = p;
		b = new Board(p, START_X, START_Y, SQUARE_WIDTH);
	}
	
	public void display() {
		p.background(255);
		b.display();
	}
	
	public void mouseInput() {
		b.mouseInput();
	}

	public void keyInput(char key) {
		if (key == 'j') {
			b.playerCanWin(CURRENT_PLAYER);
		} else {
			b.modifyPiece(key);
		}
	}
}
