package game;

import java.awt.Graphics;

/*
CLASS: Polygon
DESCRIPTION: A polygon is a sequence of points in space defined by a set of
             such points, an offset, and a rotation. The offset is the
             distance between the origin and the center of the shape.
             The rotation is measured in degrees, 0-360.
USAGE: You are intended to instantiate this class with a set of points that
       forever defines its shape, and then modify it by repositioning and
       rotating that shape. In defining the shape, the relative positions
       of the points you provide are used, in other words: {(0,1),(1,1),(1,0)}
       is the same shape as {(9,10),(10,10),(10,9)}.
NOTE: You don't need to worry about the "magic math" details.
*/

class Polygon {

	// instance variables
	private Point[] shape; // An array of points.
	public Point position; // The offset mentioned above.
	public double rotation; // Zero degrees is due east.

	// constructor
	public Polygon(Point[] inShape, Point inPosition, double inRotation) {
		shape = inShape;
		position = inPosition;
		rotation = inRotation;

		// First, we find the shape's top-most left-most boundary, its origin.
		Point origin = shape[0].clone();
		for (Point p : shape) {
			if (p.x < origin.x)
				origin.x = p.x;
			if (p.y < origin.y)
				origin.y = p.y;
		}

		// Then, we orient all of its points relative to the real origin.
		for (Point p : shape) {
			p.x -= origin.x;
			p.y -= origin.y;
		}
	}

	// "getPoints" applies the rotation and offset to the shape of the polygon.
	public Point[] getPoints() {
		Point center = findCenter();
		Point[] points = new Point[shape.length];
		for (int i = 0; i < shape.length; i++) {
			Point p = shape[i];
			double x = ((p.x - center.x) * Math.cos(Math.toRadians(rotation)))
					- ((p.y - center.y) * Math.sin(Math.toRadians(rotation))) +
						center.x / 2 + position.x;
			double y = ((p.x - center.x) * Math.sin(Math.toRadians(rotation)))
					+ ((p.y - center.y) * Math.cos(Math.toRadians(rotation))) +
						center.y / 2 + position.y;
			points[i] = new Point(x, y);
		}
		return points;
	}

	// "contains" implements some magical math (i.e. the ray-casting algorithm).
	public boolean contains(Point point) {
		Point[] points = getPoints();
		double crossingNumber = 0;
		for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
			if ((((points[i].x < point.x) && (point.x <= points[j].x))
					|| ((points[j].x < point.x) && (point.x <= points[i].x)))
					&& (point.y > points[i].y
							+ (points[j].y - points[i].y) /
							(points[j].x - points[i].x) * (point.x - points[i].x))) {
				crossingNumber++;
			}
		}
		return crossingNumber % 2 == 1;
	}

	/*
	 * collides method takes in a polygon object and tracks whether its Points
	 * intersect with current object Points. This is done via a for each loop that
	 * calls the contains method defined above. Boolean is returned whether the
	 * parameter object and current object collide.
	 */
	public boolean collides(Polygon other) {
		for (Point p : other.getPoints()) {
			if (this.contains(p)) {
				return true;
			}
		}
		return false;
	}

	// defining rotation method
	public void rotate(int degrees) {
		rotation = (rotation + degrees) % 360;
	}

	// "findArea" implements some more magic math.
	private double findArea() {
		double sum = 0;
		for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
			sum += shape[i].x * shape[j].y - shape[j].x * shape[i].y;
		}
		return Math.abs(sum / 2);
	}

	// "findCenter" implements another bit of math.
	private Point findCenter() {
		Point sum = new Point(0, 0);
		for (int i = 0, j = 1; i < shape.length; i++, j = (j + 1) % shape.length) {
			sum.x += (shape[i].x + shape[j].x) *
					(shape[i].x * shape[j].y - shape[j].x * shape[i].y);
			sum.y += (shape[i].y + shape[j].y) *
					(shape[i].x * shape[j].y - shape[j].x * shape[i].y);
		}
		double area = findArea();
		return new Point(Math.abs(sum.x / (6 * area)),
							Math.abs(sum.y / (6 * area)));
	}

	public Point getPosition() {
		return position;
	}

}