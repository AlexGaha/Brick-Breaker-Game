package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;    //STARTING POSITION OF THR PADDLE
	
	//private int ballposX = 120;
	//private int ballposY = 350;
	//private int ballXdir = -1;
	//private int ballYdir = -2;
	
	
	
	Random random = new Random();
	int n = random.nextInt(2); 
	private int ballXdir = n;
	private int ballYdir = -n;
	private int ballposX = 220;
	private int ballposY = 350;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		new Random();
		
	}
	
	public void paint(Graphics g) {    //BACKGROUND
		g.setColor(Color.black);
		g.fillRect(1,1, 692,592);
		
		map.draw((Graphics2D)g);
		
		g.setColor(Color.yellow);       //BORDERS
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		g.setColor(Color.white);                       //SCORE
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		g.setColor(Color.green);              //THE PADDLE
		g.fillRect(playerX, 550, 100, 8);
		
		g.setColor(Color.yellow);             //THE BALL
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won", 260, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 230, 350);
			
		}
		
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score:", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter to Restart", 230, 350);
			
		}
		
		g.dispose();
		
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY,20, 20).intersects(new Rectangle(playerX, 550,100,8))) {   //INTERSECTION WITH A PADDLE
				ballYdir = -ballYdir;
			}
			
		A:	for(int i = 0; i < map.map.length; i++ ) {
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {           //IF BALL INTERSECTS WITH A BRICK
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width ) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {                 //LEFT BORDER
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {                 //TOP BORDER
				ballYdir = -ballYdir;
		    }
			if(ballposX > 670) {                 //RIGHT BORDER
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >=600) {
				playerX = 600;
			}else {
				moveRight();
			}
	}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			}else {
				moveLeft();
			}
		
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
				
			}
		}
	}
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
    }
}
