package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/* CLASS: 		Pacman extends Game and implements KeyListener interface
 * DESCRIPTION: Pacman initializes 3 element objects (head, food, and ghost)
 * 				and has a paint method that controls their movements.
 * 				The KeyListner interface's methods are used to control the
 * 				head element. This classes uses the Graphics API for
 * 				visualization. 
 * USAGE:		Pacman object will allow the user to play a game that is a
 * 				variation of the original pacman game.
 * NOTE:		Pacman object is created in the main method to demonstrate
 * 				the applications of this class!
 * */

class Pacman extends Game implements KeyListener {

	// static variables
	// boundaries for elements
	private static int maxX, maxY, minX = 40, minY = 40;

	// variable to determine food eaten
	private static boolean enterPressed = false;

	// rotational effect of food
	private static double myFoodRotation = 0.0;

	// variable to determine ghost direction
	private static int paintTimes = 0;

	// score variable to count food eaten
	private static int score = 0;

	// instance variables
	private Element_Head head; // head object
	private Element_Food food; // food object
	private Element_Ghost ghost; // ghost object
	KeyListener k; // KeyListner object for

	// variables tracking head and ghost coordinates
	private int headX = 40, headY = 40;
	private int ghostX = 400, ghostY = 400;

	// constructor
	public Pacman() {
		// calling Game class super constructor
		super("Pacman", 800, 600);

		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);

		// initializing maximum x and y coordinates
		maxX = super.getWidth();
		maxY = super.getHeight();
	}

	// initialization block initializing head, food, and ghost objects
	{
		// initializing head object
		// Point array to hold head coordinates
		Point[] myHead = new Point[5];
		myHead[0] = new Point(0, 0);
		myHead[1] = new Point(100, 0);
		myHead[2] = new Point(50, 50);
		myHead[3] = new Point(100, 100);
		myHead[4] = new Point(0, 100);

		// initializing position of head object
		Point myHeadPosition = new Point(headX, headY);
		// initializing actual head object
		head = new Element_Head(myHead, myHeadPosition, 0.0);

		// initializing food object
		// Point array to hold food coordinates
		Point[] myFood = new Point[4];
		myFood[0] = new Point(0, 0);
		myFood[1] = new Point(35, 0);
		myFood[2] = new Point(35, 35);
		myFood[3] = new Point(0, 35);

		// initializing position of food object
		Point myFoodPosition = new Point(200, 200);
		// initializing actual food object
		food = new Element_Food(myFood, myFoodPosition, myFoodRotation);

		// initializing ghost object
		// Point array to hold ghost coordinates
		Point[] myGhost = new Point[6];
		myGhost[0] = new Point(15, 0);
		myGhost[1] = new Point(35, 0);
		myGhost[2] = new Point(50, 25);
		myGhost[3] = new Point(50, 50);
		myGhost[4] = new Point(0, 50);
		myGhost[5] = new Point(0, 25);

		// initializing position of ghost object
		Point myGhostPosition = new Point(ghostX, ghostY);
		// initializing actual ghost object
		ghost = new Element_Ghost(myGhost, myGhostPosition, 0.0);
	}

	// KeyListener methods
	// keyTyped method not implemented
	@Override
	public void keyTyped(KeyEvent e) {
	}

	// keyPressed method utilizes switch cases to control user input
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_RIGHT): // case: right arrow pressed
			if (headX < maxX - 80) {
				headX = headX + 25;
				headY = headY;
			}
			break;

		case (KeyEvent.VK_LEFT): // case: left arrow pressed
			if (headX > minX) {
				headX = headX - 25;
				headY = headY;
			}
			break;

		case (KeyEvent.VK_UP): // case: up arrow pressed
			if (headY > minY) {
				headX = headX;
				headY = headY - 25;
			}
			break;

		case (KeyEvent.VK_DOWN): // case: down arrow pressed
			if (headY < maxY - 80) {
				headX = headX;
				headY = headY + 25;
			}
			break;
		case (KeyEvent.VK_ENTER): // case: enter button pressed
			// rotating head to eat food
			head.setRotation(20);
			enterPressed = true;
		}
	}

	/*
	 * keyReleased method implemented from KeyListener interface method
	 * rotates head to original rotation once any key released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		head.setRotation(0);
	}

	/*
	 * Paint method implements all object classes' paint methods to create a
	 * functional game (this is demonstrated through different sections of code).
	 * This method is repainted every tenth of a second to give visual animation.
	 * 
	 */
	public void paint(Graphics brush) {

		// setting board color to black and filling board
		brush.setColor(Color.BLACK);
		brush.fillRect(0, 0, width, height);

		// calling food paint method
		food.paint(brush);

		if (!head.collides(ghost)) {

			// checking if food rotation is over 90 degrees and resetting
			if (myFoodRotation >= 90.0) {
				myFoodRotation = 0.0;
			}
			// rotating food
			food.setRotation(myFoodRotation = myFoodRotation + 10);

			// setting bounds for head
			if (headX < minX) {
				headX = minX;
			}
			if (headY < minY) {
				headY = minY;
			}
			if (headX > maxX) {
				headX = maxX;
			}

			if (headY > maxY) {
				headY = maxY;
			}

			//reseting head with updated position according to keyListner object
			head.setPosition(new Point(headX, headY));
			// calling head paint method
			head.paint(brush);

			// check if food and head collide and enter button is pressed
			if (head.collides(food) && enterPressed) {
				// using an anonymous class to perform incrementation
				sc.increment(score);
				
				// randomizing new position for food via set method in Elemeent_Food
				food.setPosition();
			}
			
			// resetting enterPressed until called again
			enterPressed = false;

			// calling ghost paint method
			ghost.paint(brush);
			
			// creating Randomizer instance (inner class)
			Randomizer r = new Randomizer();

			// checking for boundaries to ghost element x and y coordinates
			if (ghostX > maxX - 20 || ghostX < minX ||
					ghostY > maxY - 20 || ghostY < minY) {
				// setting ghost coordinates using Randomizer inner class
				ghostX = r.positionX;
				ghostY = r.positionY;

			}
			
			/* ghost x and y coordinates incrementation:
			 * to create square traveling path (incrementation done every 100
			 * times paint method called which is tracked by paintTimes
			 * static variable)
			 */
			if (paintTimes <= 100 && paintTimes >= 0) {
				ghost.setPosition(new Point(ghostX++, ghostY));
			} else if (paintTimes <= 200 && paintTimes > 100) {
				ghost.setPosition(new Point(ghostX, ghostY++));
			} else if (paintTimes <= 300 && paintTimes > 200) {
				ghost.setPosition(new Point(ghostX--, ghostY));
			} else if (paintTimes <= 400 && paintTimes > 300) {
				ghost.setPosition(new Point(ghostX, ghostY--));
			} else {
				// once paintTimes exceeds 400 new position randomized for ghost
				paintTimes = 0;
				ghostX = r.positionX;
				ghostY = r.positionY;
			}

			// visible score tracker through text in top corner of screen
			brush.setColor(Color.WHITE);
			brush.setFont(new Font("Monospaced", Font.BOLD, 20));
			brush.drawString("Score is " + score, 670, 20);

		}
		// else statement: head has collided with ghost and game is over
		else {
			// presenting "Game Over" message and final score
			brush.setFont(new Font("Serif", Font.ITALIC, 36));
			brush.drawString("Game Over", 300, 250);
			brush.drawString("Final Score: " + score, 300, 280);
		}
		
		// incrementing paintTimes static variable
		paintTimes++;
	}

	/* CLASS: 		Randomizer is an inner class that provides random
	 * 				integer coordinates
	 * DESCRIPTION: This class has 2 int instance variable and 1 Random object
	 * USAGE: 		This class is used to generate random coordinates for
	 * 				ghost positioning
	 * 
	 * */
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

	/* This section implements the anonymous class of the functional interface
	 * Score. The only method called increment is defined here.
	 */
	Score sc = new Score() {
		public int increment (int x){
		return score++;
	}
	};

	/* main method creates instance of pacman class to display the
	 * functionality of this game!
	 */
	public static void main(String[] args) {
		Pacman pacman = new Pacman();
		pacman.repaint();
	}
}