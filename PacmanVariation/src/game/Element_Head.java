package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;

/* CLASS: 		Element_Head class extends Polygon.
 * DESCRIPTION: This class defines a head object in the pacman game. It has 3
 * 				instance variables (2 arrays of coordinates x and y and a
 * 				Coordinates interface object). 
 * USAGE:		This class is used to instantiate a head object used in
 * 				the Pacman class via composition (HAS-A) relationship.			
 */
public class Element_Head extends Polygon {

	// instance variables
	private Coordinates c;
	private int[] coorX, coorY;

	// inherited instance variables from Polygon: inShape, inPosition, and
	// inRotation

	// constructor
	public Element_Head(Point[] inShape, Point inPosition, double inRotation) {
		// calling Polygon class super constructor
		super(inShape, inPosition, inRotation);
		// initializing coordinate array instance variables
		coorX = new int[inShape.length];
		coorY = new int[inShape.length];

		// using lambda expression to set x and y array coordinates
		c = (Point[] Shape) -> {

			/*
			 * for loop iterates through Shape coordinates and
			 * uses Point class getters to assign values to coorX and coorY
			 */
			for (int indx = 0; indx < Shape.length; indx++) {
				coorX[indx] = (int) Shape[indx].getX();
				coorY[indx] = (int) Shape[indx].getY();
			}
		};
	}

	/*
	 * setPosition method takes in any point and sets new position
	 * of head element
	 * @param Point p
	 */
	public void setPosition(Point p) {
		super.position = p;
	}

	/*
	 * setPosition method takes in any double and sets rotation angle of head
	 * @param Point p
	 */
	public void setRotation(double d) {
		super.rotation = d;
	}

	/* Element_Head class paint method draws and fills head polygon */
	public void paint(Graphics brush) {
		// setting brush color to yellow
		brush.setColor(Color.YELLOW);

		// calling lambda expression to assign array of coordinates
		c.getCoordinates(getPoints());

		// drawing and filling polygon
		brush.drawPolygon(coorX, coorY, super.getPoints().length);
		brush.fillPolygon(coorX, coorY, super.getPoints().length);

	}

}
