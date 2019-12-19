
public class Point {
	
	private int r;
	private int c;
	
	public Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	// Don't even ask:
	
	public int getRow() {
		return r;
	}
	
	public int getCol() {
		return c;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Point)) {
			return false;
		}
		Point temp = (Point) other;
		return temp.getCol() == c && temp.getRow() == r;
	}
	
	public String toString() {
		return "(" + super.toString() + ": (" + r + "," + c + "))";
	}
}
