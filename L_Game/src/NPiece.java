
public class NPiece implements Piece {
	Point[] points;
	private char space;
	
	public NPiece(int startX, int startY, char space) {
		points = new Point[] {new Point(startX, startY)};
		this.space = space;
	}
	
	public char getSpace() {
		return space;
	}
	
	public Point[] getPoints() {
		return points;
	}
	
	public int getX() {
		return points[0].getRow();
	}
	
	public int getY() {
		return points[0].getCol();
	}
}
