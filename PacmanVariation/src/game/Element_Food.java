package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;

/* CLASS: 		Element_Food class extends Polygon.
 * DESCRIPTION: This class defines a food object in the pacman game. It has 4
 * 				instance variables (2 arrays of coordinates x and y, a
 * 				Coordinates interface object, and Random object). 
 * USAGE:		This class is used to instantiate a food object used in
 * 				the Pacman class via composition (HAS-A) relationship.			
 */
public class Element_Food extends Polygon {

	// instance variables
	private Random r;
	private  Coordinates c;
	private int[] coorX, coorY;

	// constructor
	public Element_Food(Point[] inShape, Point inPosition, double inRotation) {
		// calling Polygon class super constructor
		super(inShape, inPosition, inRotation);

		// initializing coordinate array instance variables
		r = new Random();
		coorX = new int[inShape.length];
		coorY = new int[inShape.length];

		// using lambda expression to set x and y array coordinates
		c = (Point[] inShape2) -> {

			/*
			 * for loop iterates through Shape coordinates and uses Point
			 * class getters to assign values to coorX and coorY
			 */
			for (int indx = 0; indx < inShape2.length; indx++) {
					coorX[indx] = (int) inShape2[indx].getX();
					coorY[indx] = (int) inShape2[indx].getY();
			}
		};
	}

	/*
	 * setPosition method uses Randomizer inner class to assign a new random
	 * position to current food object
	 */
	public void setPosition() {
		Randomizer rand = new Randomizer();
		super.position = new Point(rand.positionX, rand.positionY);
	}

	/*
	 * setPosition method takes in any double and sets rotation angle of head
	 * @param Point p
	 */
	public void setRotation(double d) {
		super.rotation = d;
	}

	/*
	 * CLASS: 		Randomizer is an inner class that provides random
	 * 				integer coordinates
	 * DESCRIPTION: This class has 2 int instance variable and 1 Random object
	 * USAGE: 		This class is used to generate random coordinates for ghost
	 * 				positioning
	 * 
	 */
	public class Randomizer {

		// instance variables
		Random r;
		int positionX;
		int positionY;

		// constructor
		public Randomizer() {
			r = new Random();
			positionX = r.nextInt(700);
			positionY = r.nextInt(500);			
		}
	}
	
	/* Element_Food class paint method draws and fills food polygon */
	public void paint(Graphics brush) {
		// setting brush color to red
		brush.setColor(Color.RED);

		// calling lambda expression to assign array of coordinates
		c.getCoordinates(getPoints());

		// drawing and filling polygon
		brush.drawPolygon(coorX, coorY, super.getPoints().length);
		brush.fillPolygon(coorX, coorY, super.getPoints().length);
	}
}
