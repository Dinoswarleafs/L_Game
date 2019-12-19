public class DPiece implements Piece {
	
	private Point[] points;
	private Point selectedPoint;
	private char space;
	private int pixelX;
	private int pixelY;
	
	public DPiece(Piece piece, Point p, int pixelX, int pixelY) {
		points = new Point[piece.getPoints().length];
		space = piece.getSpace();
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		initializePiece(piece, p);
	}
	
	private void initializePiece(Piece piece, Point p) {
		Point[] temp = piece.getPoints();
		for (int i = 0; i < temp.length; i++) {
			points[i] = temp[i];
			if (temp[i].equals(p)) {
				selectedPoint = points[i];
			}
		}
	}
	
	public void shiftSquares(Point mP) {
		int dispRow = selectedPoint.getRow() - mP.getRow();
		int dispCol = selectedPoint.getCol() - mP.getCol();
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(points[i].getRow() - dispRow, points[i].getCol() - dispCol);
		}
	}
	
	public void rotate(boolean rotLeft) {
		for (int i = 0; i < points.length; i++) {
			Point curr = points[i];
			int rowMult = rotLeft ? 1 : -1;
			int colMult = rotLeft ? -1 : 1;
			int newRow = selectedPoint.getCol() + rowMult * (selectedPoint.getCol() - curr.getCol());
			int newCol = selectedPoint.getRow() + colMult * (selectedPoint.getRow() - curr.getRow());
			points[i] = new Point(newRow, newCol);
		}
	}
	
	public int[][] getDisplayData() {
		int[][] pixelData = new int[points.length][4];
		for (int i = 0; i < points.length; i++) {
			Point tPoint = points[i];
			pixelData[i] = new int[] {tPoint.getRow() - selectedPoint.getRow(), tPoint.getCol() - selectedPoint.getCol(),
										pixelX, pixelY};
		}
		return pixelData;
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
