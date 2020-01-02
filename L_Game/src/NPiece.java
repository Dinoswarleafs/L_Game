
public class NPiece implements Piece {
	Point[] points;
	private char space;
	
	public NPiece(int startX, int startY, char space) {
		points = new Point[] {new Point(startX, startY)};
		this.space = space;
	}
	
	public void setPoints(Point[] pts) {
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = new Point(pts[i].getRow(), pts[i].getCol());
		}
	}
	
	public char getSpace() {
		return space;
	}
	
	public Point[] getPoints() {
		return points;
	}
	
	public int getCol() {
		return points[0].getCol();
	}
	
	public int getRow() {
		return points[0].getRow();
	}
}
