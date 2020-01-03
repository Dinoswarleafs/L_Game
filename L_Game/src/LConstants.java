public final class LConstants {
	
	// --- Graphical Constants ---
	//		- Primarily used in LGameManager.java and Board.java
	
	// Button Constants
	// Confirm Button
	public static final int COMFIRM_BUTTON_X = 400;
	public static final int COMFIRM_BUTTON_Y = 650;
	public static final int COMFIRM_BUTTON_WIDTH = 200;
	public static final int COMFIRM_BUTTON_HEIGHT = 50;
	public static final String COMFIRM_BUTTON_TEXT = "Confirm";
	
	// Undo Button
	public static final int UNDO_BUTTON_X = 400;
	public static final int UNDO_BUTTON_Y = 725;
	public static final int UNDO_BUTTON_WIDTH = 200;
	public static final int UNDO_BUTTON_HEIGHT = 50;
	public static final String UNDO_BUTTON_TEXT = "Undo";
	
	// Text Constants
	// Text Font Stuff
	public final static int TEXT_SIZE = 32;
	public final static String TEXT_FONT = "Calibri";
	
	// Text Locations
	public final static int PLAYER_STATUS_LINE_X = 280;
	public final static int PLAYER_STATUS_LINE_Y = 150;
	public final static int PLAYER_STATUS_OFFSET_X = 105;
	public final static int PLAYER_STATUS_OFFSET_Y = 38;
	public final static int MOVE_STATUS_OFFSET_X = 270;
	public final static int MOVE_STATUS_OFFSET_X_2 = 185;
	public final static int MOVE_STATUS_OFFSET_X_3 = 12;
	public final static int MOVE_STATUS_OFFSET_Y = 105;
	public final static int GAME_OVER_LINE_X = 305;
	public final static int GAME_OVER_OFFSET_X = 105;
	public final static int GAME_OVER_OFFSET_Y = 150;
	
	// Color Constants
	// Player Colors
	public final static int PLAYER_1_COLOR = 0xFF9696F0;
	public final static int PLAYER_2_COLOR = 0xFFF09696;
	
	// Button Colors
	public final static int UNACTIVE_BUTTON_COLOR = 0xFFC8C8C8;
	public final static int ACTIVE_BUTTON_COLOR = 0xFFD2D2E6;
	public final static int HOVERED_BUTTON_COLOR = 0xFFD2E6D2;
	public final static int CLICKED_BUTTON_COLOR = 0xFFA0B4A0;
	
	// Other Colors
	public final static int BACKGROUND_COLOR = 0xFFFFFFFF;
	public final static int NEUTRAL_COLOR = 0xFFB4B4B4;
	public final static int BLANK_COLOR = 0xFFFFFFFF;
	public final static int MISSED_COLOR = 0x00000000;
	public final static int DEFAULT_TEXT_COLOR = 0x00000000;

	// Shape Constants
	// Screen Dimensions
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 800;
	
	// Board Dimensions
	public final static int START_X = 200;
	public final static int START_Y = 200;
	public final static int SQUARE_WIDTH = 100;
	
	// --- Code Constants ---
	// 
	//
	
	// LGameManager.java Codes
	public final static int PLAYER_1 = 0;
	public final static int PLAYER_2 = 1;
	public final static int MOVE_PLAYER = 0;
	public final static int MOVE_NEUTRAL = 1;
	public final static int GAME_OVER = -1;
	public final static int RESET_KEY = ' ';
	
	// Button Codes
	public final static int UNACTIVE_BUTTON = 0;
	public final static int ACTIVE_BUTTON = 1;
	public final static int HOVERED_BUTTON = 2;
	public final static int CLICKED_BUTTON = 3;
	
	
	// Board.java Codes
	public final static int BOARD_LENGTH = 4;
	public final static int BOARD_HEIGHT = 4;
	public final static int NUM_PLAYER_PIECES = 2;
	public final static int NUM_NEUTRAL_PIECES = 2;
	public final static char BLANK_SPACE = '-';
	public final static char NEUTRAL_SPACE = 'o';
	public final static char PLAYER_LENGTH = 3;
	public final static char PLAYER_1_SPACE = 'b';
	public final static char PLAYER_2_SPACE = 'r';
	public final static char NONE_CHAR = '?';
	public final static int LEFT = -1;
	public final static int RIGHT = 1;
	public final static int UP = -1;
	public final static int DOWN = 1;
	public final static int NONE = 0;
	public final static Piece NONE_N_PIECE = new NPiece(-1, -1, NONE_CHAR);
	public final static Piece NONE_L_PIECE = new LPiece(-1, -1, NONE, NONE, NONE, NONE, NONE_CHAR, 0);
	public final static char ROTATE_LEFT = 'z';
	public final static char ROTATE_RIGHT = 'x';
	public final static char MIRROR = ' ';

}