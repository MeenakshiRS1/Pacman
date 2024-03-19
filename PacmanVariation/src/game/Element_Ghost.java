package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/* CLASS: 		Element_Ghost class extends Polygon.
 * DESCRIPTION: This class defines a food object in the pacman game. It has 4
 * 				instance variables (2 arrays of coordinates x and y, a
 * 				Coordinates interface object, and Random object). 
 * USAGE:		This class is used to instantiate a ghost object used in
 * 				the Pacman class via composition (HAS-A) relationship.			
 */
public class Element_Ghost extends Polygon {

	// instance variables
	private Coordinates c;
	private int[] coorX, coorY;

	// inherited instance variables from Polygon: inShape, inPosition, and
	// inRotation

	// constructor
	public Element_Ghost(Point[] inShape, Point inPosition, double inRotation) {
		// calling Polygon class super constructor
		super(inShape, inPosition, inRotation);
		// initializing coordinate array instance variables
		coorX = new int[inShape.length];
		coorY = new int[inShape.length];

		// using lambda expression to set x and y array coordinates
		c = (Point[] Shape) -> {

			/*
			 * for loop iterates through Shape coordinates and uses Point class
			 * getters to assign values to coorX and coorY
			 */
			for (int indx = 0; indx < Shape.length; indx++) {
				coorX[indx] = (int) Shape[indx].getX();
				coorY[indx] = (int) Shape[indx].getY();
			}
		};
	}

	/* Element_Ghost class paint method draws and fills ghost polygon */
	public void paint(Graphics brush) {
		// setting brush color to white
		brush.setColor(Color.WHITE);
		
		// calling lambda expression to assign array of coordinates
		c.getCoordinates(getPoints());

		// drawing and filling polygon
		brush.drawPolygon(coorX, coorY, super.getPoints().length);
		brush.fillPolygon(coorX, coorY, super.getPoints().length);

	}

	/*
	 * setPosition method takes in any Point and sets ghost object's new point
	 * @param Point p
	 */
	public void setPosition(Point p) {
		super.position = new Point((p.getX()), p.getY());
	}

}
