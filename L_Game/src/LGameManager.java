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
	private boolean playerMoved;
	private int playerWon;
	
	public LGameManager(PApplet p) {
		this.p = p;
		comButton = new Button(p, LConstants.COMFIRM_BUTTON_X, LConstants.COMFIRM_BUTTON_Y, 
				LConstants.COMFIRM_BUTTON_WIDTH, LConstants.COMFIRM_BUTTON_HEIGHT, 
				LConstants.COMFIRM_BUTTON_TEXT);
		undoButton = new Button(p, LConstants.UNDO_BUTTON_X, LConstants.UNDO_BUTTON_Y, 
				LConstants.UNDO_BUTTON_WIDTH, LConstants.UNDO_BUTTON_HEIGHT, 
				LConstants.UNDO_BUTTON_TEXT);
		font = p.createFont(LConstants.TEXT_FONT, LConstants.TEXT_SIZE);
		b = new Board(p);
		b.savePlayer(CURRENT_PLAYER);
	}
	
	public void display() {
		p.background(LConstants.BACKGROUND_COLOR);
		displayText();
		b.display();
		if (CURRENT_PLAYER != LConstants.GAME_OVER) {
			handleButtons();
		}
	}
	
	public void displayText() {
		if (CURRENT_PLAYER != LConstants.GAME_OVER) {
			String playerText = CURRENT_PLAYER == LConstants.PLAYER_1 ? "Player 1" : "Player 2";
			int dispColor = CURRENT_PLAYER == LConstants.PLAYER_1 ? LConstants.PLAYER_1_COLOR : LConstants.PLAYER_2_COLOR;
			String moveText = CURRENT_MOVE == LConstants.MOVE_PLAYER ? "player" : "neutral";
			p.textAlign(PConstants.LEFT);
			p.fill(dispColor);
			p.textFont(font);
			p.text(playerText, LConstants.PLAYER_STATUS_LINE_X, LConstants.PLAYER_STATUS_LINE_Y);
			p.fill(LConstants.DEFAULT_TEXT_COLOR);
			p.text(" is placing", LConstants.PLAYER_STATUS_LINE_X + LConstants.PLAYER_STATUS_OFFSET_X, LConstants.PLAYER_STATUS_LINE_Y);
			p.text("Moving ", LConstants.MOVE_STATUS_OFFSET_X, LConstants.PLAYER_STATUS_LINE_Y + LConstants.PLAYER_STATUS_OFFSET_Y);
			int dispOffset = CURRENT_MOVE == LConstants.MOVE_PLAYER ? 0 : LConstants.MOVE_STATUS_OFFSET_X_3;
			p.text(" piece", LConstants.MOVE_STATUS_OFFSET_X + LConstants.MOVE_STATUS_OFFSET_X_2 + dispOffset, LConstants.PLAYER_STATUS_LINE_Y + LConstants.PLAYER_STATUS_OFFSET_Y);
			dispColor = CURRENT_MOVE == LConstants.MOVE_PLAYER ? dispColor : LConstants.NEUTRAL_COLOR;
			p.fill(dispColor);
			p.text(moveText, LConstants.MOVE_STATUS_OFFSET_X + LConstants.PLAYER_STATUS_OFFSET_X, LConstants.PLAYER_STATUS_LINE_Y + LConstants.PLAYER_STATUS_OFFSET_Y);
		} else {
			String playerText = "Player " + (playerWon + 1);
			int dispColor = playerWon == LConstants.PLAYER_1 ? LConstants.PLAYER_1_COLOR : LConstants.PLAYER_2_COLOR;
			p.textAlign(PConstants.LEFT);
			p.fill(dispColor);
			p.textFont(font);
			p.text(playerText, LConstants.GAME_OVER_LINE_X, LConstants.GAME_OVER_OFFSET_Y);
			p.fill(LConstants.DEFAULT_TEXT_COLOR);
			p.text(" wins", LConstants.GAME_OVER_LINE_X + LConstants.GAME_OVER_OFFSET_X, LConstants.GAME_OVER_OFFSET_Y);
			p.fill(LConstants.DEFAULT_TEXT_COLOR);
			p.text("Press space to restart", 270, LConstants.GAME_OVER_OFFSET_Y + LConstants.PLAYER_STATUS_OFFSET_Y);
		}
	}
	
	public void mouseInput() {
		if (CURRENT_PLAYER != LConstants.GAME_OVER && b.mouseInput()) {
			playerMoved = true;
		}
	}
	
	public void handleButtons() {
		if (comButton.isClicked()) {
			CURRENT_MOVE = Math.abs(CURRENT_MOVE - 1);
			if (CURRENT_MOVE == 0) {
				CURRENT_PLAYER = Math.abs(CURRENT_PLAYER - 1);
				System.out.println(CURRENT_PLAYER);
				b.savePlayer(CURRENT_PLAYER);
				playerMoved = false;
				if (!b.playerCanWin(CURRENT_PLAYER)) {
					playerWon = CURRENT_PLAYER == LConstants.PLAYER_2 ? LConstants.PLAYER_1 : LConstants.PLAYER_2;	
					CURRENT_PLAYER = LConstants.GAME_OVER;
					CURRENT_MOVE = LConstants.GAME_OVER;
				}
			}
			b.setPlayerAndMove(CURRENT_PLAYER, CURRENT_MOVE);
			System.out.println("CURRENT MOVE: " + CURRENT_MOVE + "\nCURRENT PLAYER: " + CURRENT_PLAYER);
		}
		if (undoButton.isClicked()) {
			b.resetPlayer(CURRENT_PLAYER);
			b.savePlayer(CURRENT_PLAYER);
		}
		comButton.setActive(b.nonePickedUp() && playerMoved && b.playerMoved(CURRENT_PLAYER));
		undoButton.setActive(b.nonePickedUp() && CURRENT_MOVE == LConstants.MOVE_PLAYER && b.playerMoved(CURRENT_PLAYER));
		comButton.display();
		undoButton.display();
	}

	public void keyInput(char key) {
		if (CURRENT_PLAYER == LConstants.GAME_OVER && key == LConstants.RESET_KEY) {
			resetGame();
			b.resetBoard();
		}
		b.modifyPiece(key);
	}
	
	private void resetGame() {
		CURRENT_PLAYER = LConstants.PLAYER_1;
		CURRENT_MOVE = LConstants.MOVE_PLAYER;
		playerWon = LConstants.GAME_OVER;
	}
}