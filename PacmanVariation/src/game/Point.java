package game;

/*
CLASS: Point
DESCRIPTION: Defines a coordinate point that will be used to create
			geometric objects.
*/

public class Point implements Cloneable {

	// instance variables
	public double x, y;

	// constructor
	public Point(double inX, double inY) {
		x = inX;
		y = inY;
	}

	// getters
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	// setters
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	// Cloneable interface clone implementation
	public Point clone() {
		return new Point(x, y);
	}
}