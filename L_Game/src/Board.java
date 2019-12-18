import processing.core.PApplet;

public class Board {
	
	private final int BOARD_LENGTH = 4;
	private final int BOARD_HEIGHT = 4;
	private final int NUM_PLAYER_PIECES = 2;
	private final int NUM_NEUTRAL_PIECES = 2;
	private final char BLANK_SPACE = '-';
	private final char NEUTRAL_SPACE = 'o';
	private final char PLAYER_LENGTH = 3;
	private final char PLAYER_1_SPACE = 'b';
	private final char PLAYER_2_SPACE = 'r';
	private final Piece NONE_N_PIECE = new NPiece(-1, -1, '?');
	private final Piece NONE_L_PIECE = new LPiece(-1, -1, 0, 0, 0, 0, '?', 0);
	
	private PApplet p;
	private char[][] board;
	private LPiece[] lPieces;
	private NPiece[] nPieces;
	private Piece currPicked;
	
	public Board(PApplet p) {
		System.out.println("Initialization board.");
		this.p = p;
		board = new char[BOARD_LENGTH][BOARD_HEIGHT];
		lPieces = new LPiece[NUM_PLAYER_PIECES];
		nPieces = new NPiece[NUM_NEUTRAL_PIECES];
		resetBoard();
		System.out.println("Finished initializing board.");
	}
	
	private void initializePieces() {
		System.out.println("Initializing pieces.");
		nPieces[0] = new NPiece(0, 0, NEUTRAL_SPACE);
		nPieces[1] = new NPiece(BOARD_LENGTH - 1, BOARD_LENGTH - 1, NEUTRAL_SPACE);
		lPieces[0] = new LPiece(3, 2, 0, -1, -1, 0, PLAYER_1_SPACE, PLAYER_LENGTH);
		lPieces[1] = new LPiece(0, 1, 0, 1, 1, 0, PLAYER_2_SPACE, PLAYER_LENGTH);
		System.out.println("Finished initializing pieces.");
	}
	
	private void updateBoard() {
		System.out.println("Updating board.");
		System.out.println("Placing background");
		for (int i = 0; i < BOARD_LENGTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				board[i][j] = BLANK_SPACE;
			}
		}
		System.out.println("Placing neutral pieces");
		for (int i = 0; i < NUM_NEUTRAL_PIECES; i++) {
			placePiece(nPieces[i]);
		}
		System.out.println("Placing player pieces");
		for (int i = 0; i < NUM_PLAYER_PIECES; i++) {
			placePiece(lPieces[i]);
		}
		System.out.println("Finished updating board.");
		System.out.println("Current board: ");
		for (int i = 0; i < BOARD_LENGTH; i++) {
			System.out.print("[ " + board[i][0]);
			for (int j = 1; j < BOARD_HEIGHT; j++) {
				System.out.print(", " + board[i][j]);
			}
			System.out.println("]");
		}
		
	}
	
	public boolean canPlace(Piece piece) {
		System.out.print("Checking if " + piece + " can be placed: ");
		Point[] points = piece.getPoints();
		for (int i = 0; i < points.length; i++) {
			if (!allChecksPass(piece, points[i])) {
				System.out.println("no");
				return false;
			}
		}
		System.out.println("yes");
		return true;
	}
	
	private boolean allChecksPass(Piece piece, Point point) {
		return cordsInBounds(point) && !overNeutral(piece, point) 
				&& !overOtherPlayer(piece, point);
	}
	
	public void placePiece(Piece piece) {
		if (piece == NONE_L_PIECE || piece == NONE_N_PIECE) {
			return;
		}
		if (!canPlace(piece)) {
			throw new IllegalArgumentException("placePiece. the piece must be in bounds of the board");
		}
		Point[] pts = piece.getPoints();
		for (int i = 0; i < pts.length; i++) {
			board[pts[i].getRow()][pts[i].getCol()] = piece.getSpace();
		}
	}
	
	private boolean overOtherPlayer(Piece p, Point currPoint) {
		for (LPiece lP : lPieces) {
			if (lP != p) {
				for (Point lPts : lP.getPoints()) {
					if (currPoint.getRow() == lPts.getRow() && currPoint.getCol() == lPts.getCol()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private boolean cordsInBounds(Point p) {
		return !(p.getRow() < 0 || p.getRow() >= BOARD_LENGTH || p.getCol() < 0 || p.getCol() >= BOARD_HEIGHT);
	}
	
	private boolean overNeutral(Piece curr, Point p) {
		if (!(curr instanceof NPiece)) {
			for (NPiece n : nPieces) {
				if (p.getRow() == n.getX() && p.getCol() == n.getY()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void removePiece(Piece piece) {
		System.out.println("Removing " + piece);
		if (!canPlace(piece)) {
			throw new IllegalArgumentException("placePiece. the piece must be in bounds of the board");
		}
		Point[] pts = piece.getPoints();
		for (int i = 0; i < pts.length; i++) {
			board[pts[i].getRow()][pts[i].getCol()] = BLANK_SPACE;
		}
	}
	
	public void display(int startX, int startY, int spaceWidth) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				p.fill(getFillColor(board[row][col]));
				p.rect(mapToScreen(startX, col, spaceWidth), mapToScreen(startX, row, spaceWidth), spaceWidth, spaceWidth);
			}
		}
	}
	
	private int getFillColor(char c) {
		switch (c) {
		case PLAYER_1_SPACE: return p.color(150, 150, 240);
		case PLAYER_2_SPACE: return p.color(240, 150, 150);
		case NEUTRAL_SPACE: return p.color(180);
		case BLANK_SPACE: return p.color(255);
		}
		System.out.println("Warning! No color detected for passed character " + c);
		return p.color(0);
	}
	
	private int mapToScreen(int startNum, int num, int scale) {
		return startNum + num * scale;
	}
	
	public void resetBoard() {
		initializePieces();
		updateBoard();
	}
	
	public boolean removePiece(int row, int col) {
		System.out.println("Checking if the piece can be removed.");
		Point tmp = new Point(row, col);
		if (!cordsInBounds(tmp)) {
			System.out.println("The piece could not be removed! The points weren't in bounds");
			return false;
		}
		if (searchPiecesToRemove(nPieces, tmp)) {
			updateBoard();
			return true;
		}
		else if (searchPiecesToRemove(lPieces, tmp)) {
			updateBoard();
			return true;
		}
		System.out.println("No piece at row col could be found! No removal");
		return false;
	}
	
	private boolean searchPiecesToRemove(Piece[] pieces, Point tgt) {
		for (int i = 0; i < pieces.length; i++) {
			for (Point pt : pieces[i].getPoints()) {
				if (pt.equals(tgt)) {
					currPicked = pieces[i];
					if (pieces[i].getSpace() == NEUTRAL_SPACE) {
						pieces[i] = NONE_N_PIECE;
					} else {
						pieces[i] = NONE_L_PIECE;
					}
					return true;
				}
			}
		}
		return false;
	}

	public void placeBack() {
		System.out.println("Placing back " + currPicked);
		if (currPicked != null) {
			if (!placeBackPiece(lPieces)) {
				placeBackPiece(nPieces);
			}
		}
		updateBoard();
	}

	private boolean placeBackPiece(Piece[] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i].getSpace() == '?') {
				pieces[i] = currPicked;
				currPicked = null;
				return true;
			}
		}
		return false;
	}
	
	
}
