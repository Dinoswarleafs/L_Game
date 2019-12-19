public class LPiece implements Piece {
	private Point[] points;
	private char space;
	
	public LPiece(int startRow, int startCol, int offRow, int offCol, int deltaRow, int deltaCol, char space, int length) {
		points = new Point[length + 1];
		initializePoints(startRow, startCol, offRow, offCol, deltaRow, deltaCol);
		this.space = space;
	}
	
	public LPiece(char space) {
		this.space = space;
	}
	
	private void initializePoints(int startRow, int startCol, int offRow, int offCol, int deltaRow, int deltaCol) {
		points[0] = new Point(startRow, startCol);
		int newRow = startRow + offRow;
		int newY = startCol + offCol;
		for (int i = 1; i < points.length; i++) {
			points[i] = new Point(newRow, newY);
			newRow += deltaRow;
			newY += deltaCol;
		}
	}
	
	public char getSpace() {
		return space;
	}
	
	public Point[] getPoints() {
		return points;
	}
	
	public void setPoints(Point[] pts) {
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = new Point(pts[i].getRow(), pts[i].getCol());
		}
	}
}
