import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class LGameManager {
	private PApplet p;
	private Board b;
	
	private PFont font;
	private Button comButton;
	private Button undoButton;
	private int CURRENT_PLAYER;
	private int CURRENT_MOVE;
	
	private final static int TEXT_SIZE = 32;
	private final static String TEXT_FONT = "Calibri";
	private final static int PLAYER_1 = 0;
	private final static int PLAYER_2 = 1;
	private final static int MOVE_PLAYER = 0;
	private final static int MOVE_NEUTRAL = 1;
	private final static int GAME_OVER = -1;
	
	private final static int START_X = 200;
	private final static int START_Y = 200;
	private final static int SQUARE_WIDTH = 100;
	
	public LGameManager(PApplet p) {
		this.p = p;
		comButton = new Button(p, 400, 650, 200, 50, "Comfirm");
		undoButton = new Button(p, 400, 725, 200, 50, "Undo");
		font = p.createFont(TEXT_FONT, TEXT_SIZE);
		b = new Board(p, START_X, START_Y, SQUARE_WIDTH);
	}
	
	public void display() {
		p.background(255);
		displayText();
		b.display();
		handleButtons();
	}
	
	public void displayText() {
		String playerText = CURRENT_PLAYER == PLAYER_1 ? "Player 1" : "Player 2";
		int dispColor = CURRENT_PLAYER == PLAYER_1 ? p.color(150, 150, 240) : p.color(240, 150, 150);
		String moveText = CURRENT_MOVE == MOVE_PLAYER ? "player" : "neutral";
		p.textAlign(PConstants.LEFT);
		p.fill(dispColor);
		p.textFont(font);
		p.text(playerText, 280, 150);
		p.fill(p.color(0));
		p.text(" is placing", 280 + 105, 150);
		p.text("Moving ", 270, 150 + 38);
		int dispOffset = CURRENT_MOVE == MOVE_PLAYER ? 0 : 12;
		p.text(" piece", 270 + 185 + dispOffset, 150 + 38);
		dispColor = CURRENT_MOVE == MOVE_PLAYER ? dispColor : p.color(180);
		p.fill(dispColor);
		p.text(moveText, 270 + 105, 150 + 38);
	}
	
	public void mouseInput() {
		b.mouseInput();
	}
	
	public void handleButtons() {
		if (comButton.isClicked()) {
			CURRENT_MOVE = Math.abs(CURRENT_MOVE - 1);
			if (CURRENT_MOVE == 0) {
				CURRENT_PLAYER = Math.abs(CURRENT_PLAYER - 1);
			}
			b.setPlayerAndMove(CURRENT_PLAYER, CURRENT_MOVE);
			System.out.println("CURRENT MOVE: " + CURRENT_MOVE + "\nCURRENT PLAYER: " + CURRENT_PLAYER);
		}
		comButton.display();
		undoButton.display();
	}

	public void keyInput(char key) {
		b.modifyPiece(key);
	}
}
