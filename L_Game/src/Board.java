import processing.core.PApplet;
import processing.core.PConstants;

public class Board {
	
	private int playerIndex;
	private int moveIndex;
	
	private PApplet p;
	private char[][] board;
	private LPiece[] lPieces;
	private NPiece[] nPieces;
	private Piece currPicked;
	private DPiece disPiece;
	private Piece savedPiece;
	private boolean pHeldDown;
	
	public Board(PApplet p) {
		System.out.println("Initialization board.");
		this.p = p;
		board = new char[LConstants.BOARD_LENGTH][LConstants.BOARD_HEIGHT];
		lPieces = new LPiece[LConstants.NUM_PLAYER_PIECES];
		nPieces = new NPiece[LConstants.NUM_NEUTRAL_PIECES];
		resetBoard();
		System.out.println("Finished initializing board.");
	}
	
	private void initializePieces() {
		System.out.println("Initializing pieces.");
		nPieces[0] = new NPiece(0, 0, LConstants.NEUTRAL_SPACE);
		nPieces[1] = new NPiece(LConstants.BOARD_LENGTH - 1, LConstants.BOARD_LENGTH - 1, LConstants.NEUTRAL_SPACE);
		lPieces[0] = new LPiece(3, 2, LConstants.NONE, LConstants.LEFT, LConstants.UP, LConstants.NONE, LConstants.PLAYER_1_SPACE, LConstants.PLAYER_LENGTH);
		lPieces[1] = new LPiece(0, 1, LConstants.NONE, LConstants.RIGHT, LConstants.DOWN, LConstants.NONE, LConstants.PLAYER_2_SPACE, LConstants.PLAYER_LENGTH);
		System.out.println("Finished initializing pieces.");
	}
	
	private void updateBoard() {
		System.out.println("Updating board.");
		System.out.println("Placing background");
		for (int i = 0; i < LConstants.BOARD_LENGTH; i++) {
			for (int j = 0; j < LConstants.BOARD_HEIGHT; j++) {
				board[i][j] = LConstants.BLANK_SPACE;
			}
		}
		System.out.println("Placing neutral pieces");
		for (int i = 0; i < LConstants.NUM_NEUTRAL_PIECES; i++) {
			placePiece(nPieces[i]);
		}
		System.out.println("Placing player pieces");
		for (int i = 0; i < LConstants.NUM_PLAYER_PIECES; i++) {
			placePiece(lPieces[i]);
		}
		System.out.println("Finished updating board.");
		printBoard();
	}
	
	private void printBoard() {
		System.out.println("Current board: ");
		for (int i = 0; i < LConstants.BOARD_LENGTH; i++) {
			System.out.print("[ " + board[i][0]);
			for (int j = 1; j < LConstants.BOARD_HEIGHT; j++) {
				System.out.print(", " + board[i][j]);
			}
			System.out.println("]");
		}	
	}
	
	public void setPlayerAndMove(int playerIndex, int moveIndex) {
		this.playerIndex = playerIndex;
		this.moveIndex = moveIndex;
	}
	
	public boolean mouseInput() {
		boolean res = false;
		if (p.mousePressed) {
			if (!pHeldDown) {
				pHeldDown = true;
				selectPiece();
			}
		} else {
			if (pHeldDown) {
				pHeldDown = false;
				res = placeDisplayPiece();
			}
		}
		return res;
	}
	
	public boolean nonePickedUp() {
		return disPiece == null;
	}
	
	public void savePlayer(int index) {
		LPiece save = new LPiece(lPieces[index].getSpace());
		save.setPoints(lPieces[index].getPoints());
		savedPiece = save;
	}
	
	public boolean playerMoved(int index) {
		return savedPiece != null && notOverlap(savedPiece, lPieces[index]);
	}
	
	public void resetPlayer(int index) {
		lPieces[index].setPoints(savedPiece.getPoints());
		savedPiece = null;
		updateBoard();
	}
	
	private boolean selectPiece() {
		Point pt = new Point((p.mouseY - LConstants.START_Y) / LConstants.SQUARE_WIDTH, (p.mouseX - LConstants.START_X) / LConstants.SQUARE_WIDTH);
		if (currPicked == null && removePiece(pt.getRow(), pt.getCol())) {
			disPiece = new DPiece(currPicked, pt, (p.mouseY - LConstants.START_Y) % LConstants.SQUARE_WIDTH, (p.mouseX - LConstants.START_X) % LConstants.SQUARE_WIDTH);
			return true;
		}
		return false;
	}
	
	public boolean modifyPiece(char key) {
		if (disPiece != null) {
			if (key == LConstants.ROTATE_LEFT) {
				disPiece.rotate(true);
			}
			else if (key == LConstants.ROTATE_RIGHT) {
				disPiece.rotate(false);
			}
			else if (key == LConstants.MIRROR) {
				disPiece.mirror();
			}
			return true;
		}
		return false;
	}
	
	private boolean placeDisplayPiece() {
		if (savedPiece == null) {
			throw new IllegalStateException("placeDisplayPiece. There must be a saved piece before placing");
		}
		Point pt = new Point((p.mouseY - LConstants.START_Y) / LConstants.SQUARE_WIDTH, (p.mouseX - LConstants.START_X) / LConstants.SQUARE_WIDTH);
		if (disPiece != null) {
			disPiece.shiftSquares(pt);
			boolean canPlace = canPlace(disPiece) && notOverlap(currPicked, disPiece);
			if (canPlace) {
				currPicked.setPoints(disPiece.getPoints());
			}
			disPiece = null;
			placeBack();
			return canPlace;
		}
		return false;
	}
	
	public boolean canPlace(Piece piece) {
		//System.out.print("Checking if " + piece + " can be placed: ");
		if (piece == null) {
			System.out.println("Piece detected as null");
			return false;
		}
		Point[] points = piece.getPoints();
		for (int i = 0; i < points.length; i++) {
			if (!allChecksPass(piece, points[i])) {
				//System.out.println(points[i] + ": " + cordsInBounds(points[i]) + ", " + !overNeutral(piece, points[i]) + ", " + !overOtherPlayer(piece, points[i]));
				//System.out.println("no");
				return false;
			}
		}
		//System.out.println("yes");
		return true;
	}
	
	private boolean allChecksPass(Piece piece, Point point) {
		return cordsInBounds(point) && !overNeutral(piece, point) 
				&& !overOtherPlayer(piece, point);
	}
	
	public void placePiece(Piece piece) {
		if (piece == LConstants.NONE_L_PIECE || piece == LConstants.NONE_N_PIECE) {
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
	
	public boolean playerCanWin(int index) {
		if (disPiece != null) {
			throw new IllegalStateException("playerCanWin. The state shouldn't be checked while a piece is picked up.");
		}
		System.out.println("--- Checking if player " + index + " can still win: ");
		int[][] checkVals = {{LConstants.NONE, LConstants.UP, LConstants.LEFT, LConstants.NONE}, {LConstants.NONE, LConstants.DOWN, LConstants.LEFT, LConstants.NONE}, 
				{LConstants.LEFT, LConstants.NONE, LConstants.NONE, LConstants.DOWN}, {LConstants.RIGHT, LConstants.NONE, LConstants.NONE, LConstants.DOWN}, 
				{LConstants.NONE, LConstants.DOWN, LConstants.RIGHT, LConstants.NONE}, {LConstants.NONE, LConstants.UP, LConstants.RIGHT, LConstants.NONE}, 
				{LConstants.RIGHT, LConstants.NONE, LConstants.NONE, LConstants.LEFT}, {LConstants.LEFT, LConstants.NONE, LConstants.NONE, LConstants.LEFT}};
		removePlayer(lPieces[index]);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				for (int k = 0; k < checkVals.length; k++) {
					LPiece temp = new LPiece(i, j, checkVals[k][0], checkVals[k][1], checkVals[k][2], checkVals[k][3], LConstants.PLAYER_1_SPACE, LConstants.PLAYER_LENGTH);
					if (canPlace(temp) && notOverlap(currPicked, temp)) {
						System.out.printf("Piece can be placed at (%d, %d)\n", i, j);
						placePlayerBack(lPieces[index].getSpace());
						return true;
					}
				}
			}
		}
		System.out.println("No place for piece found. End of game.");
		placePlayerBack(lPieces[index].getSpace());
		return false;
	}
	
	private boolean notOverlap(Piece player, Piece temp) {
		Point[] curr = player.getPoints();
		Point[] other = temp.getPoints();
		if (curr.length != other.length) {
			return true;
		}
		int count = 0;
		for (Point pt : curr) {
			for (Point oPt : other) {
				if (pt.equals(oPt)) {
					count++;
				}
			}
		}
		return count != curr.length;
	}
	
	private boolean overOtherPlayer(Piece p, Point currPoint) {
		for (LPiece lP : lPieces) {
			if (lP != p && lP != currPicked) {
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
		return !(p.getRow() < 0 || p.getRow() >= LConstants.BOARD_LENGTH || p.getCol() < 0 || p.getCol() >= LConstants.BOARD_HEIGHT);
	}
	
	private boolean overNeutral(Piece curr, Point p) {
		if (!(curr instanceof NPiece)) {
			for (NPiece n : nPieces) {
				if (p.getRow() == n.getRow() && p.getCol() == n.getCol()) {			
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
			board[pts[i].getRow()][pts[i].getCol()] = LConstants.BLANK_SPACE;
		}
	}
	
	public void display() {
		p.rectMode(PConstants.CORNER);
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				p.fill(getFillColor(board[row][col]));
				p.rect(mapToScreen(LConstants.START_X, col, LConstants.SQUARE_WIDTH), mapToScreen(LConstants.START_X, row, LConstants.SQUARE_WIDTH), LConstants.SQUARE_WIDTH, LConstants.SQUARE_WIDTH);
			}
		}
		if (disPiece != null) {
			int[][] displayData = disPiece.getDisplayData();
			p.fill(getFillColor(disPiece.getSpace()));
			for (int i = 0; i < displayData.length; i++) {
				int[] thisData = displayData[i];
				p.rect((p.mouseX - thisData[3]) + LConstants.SQUARE_WIDTH * thisData[1], (p.mouseY - thisData[2]) + LConstants.SQUARE_WIDTH * thisData[0], LConstants.SQUARE_WIDTH, LConstants.SQUARE_WIDTH);
			}
		}
	}
	
	private int getFillColor(char c) {
		switch (c) {
		case LConstants.PLAYER_1_SPACE: return LConstants.PLAYER_1_COLOR;
		case LConstants.PLAYER_2_SPACE: return LConstants.PLAYER_2_COLOR;
		case LConstants.NEUTRAL_SPACE: return LConstants.NEUTRAL_COLOR;
		case LConstants.BLANK_SPACE: return LConstants.BLANK_COLOR;
		}
		System.out.println("Warning! No color detected for passed character " + c);
		return LConstants.MISSED_COLOR;
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
		System.out.println("No piece was removed.");
		return false;
	}
	
	private boolean searchPiecesToRemove(Piece[] pieces, Point tgt) {
		for (int i = 0; i < pieces.length; i++) {
			for (Point pt : pieces[i].getPoints()) {
				if (pt.equals(tgt) && (checkPieceTurn(pieces[i]))) {
					currPicked = pieces[i];
					if (pieces[i].getSpace() == LConstants.NEUTRAL_SPACE) {
						pieces[i] = LConstants.NONE_N_PIECE;
					} else {
						pieces[i] = LConstants.NONE_L_PIECE;
					}
					return true;
				}
				else if (pt.equals(tgt)) {
					System.out.println("Piece selected was not correct for the turn. Ignored.");
				}
			}
		}
		return false;
	}
	
	private boolean checkPieceTurn(Piece piece) {
		boolean playerCheck = moveIndex == 0 && piece instanceof LPiece && lPieces[playerIndex] == piece;
		boolean neutralCheck = moveIndex == 1 && piece instanceof NPiece;
		return playerCheck || neutralCheck;
	}
	
	private void removePlayer(LPiece tgt) {
		if (disPiece != null) {
			throw new IllegalStateException("removePlayer. A piece shouldn't be removed while one is already picked up.");
		}
		Point[] pts = tgt.getPoints();
		currPicked = tgt;
		for (Point pt : pts) {
			board[pt.getRow()][pt.getCol()] = LConstants.BLANK_SPACE;
		}
		printBoard();
	}
	
	private void placePlayerBack(char space) {
		System.out.println("Placing player back.");
		if (currPicked == null) {
			throw new IllegalStateException("removePlayer. A piece can't be placed back when no piece is picked up.");
		}
		Point[] pts = currPicked.getPoints();
		for (Point pt : pts) {
			board[pt.getRow()][pt.getCol()] = space;
		}
		currPicked = null;
		printBoard();
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
			if (pieces[i].getSpace() == LConstants.NONE_CHAR) {
				pieces[i] = currPicked;
				currPicked = null;
				return true;
			}
		}
		return false;
	}
	
}