/**
 * 
 */
package dannypiper.pong;

import dannypiper.pong.ball;
import javafx.scene.text.Font;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;

import javax.swing.JComponent;
import javax.swing.JFrame;
import dannypiper.pong.Player;
/**
 * @author Dannyy
 *
 */
public class main extends Canvas{

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	static int ballRadius = 10;
	static int paddleheight = 200;
	static int paddlewidth = 20;
	static int aiPaddleY = 400;
	static int frameRate = 1000/90;
	static ball Ball = new ball();
	static Player player = new Player();
	static JFrame frame = new JFrame();
	static controlType CType = controlType.MOUSE;
	static Boolean dead = false;
	
	public static void main(String[] args) throws InterruptedException {
		//init
		Ball.initBall(ballRadius);
		if(args.length>0) {
			if(args[0].toLowerCase()=="keyboard") {
				CType = controlType.KEYBOARD;
			}
		}
		player.init(paddleheight);
		
		//frame stuff
		frame.setName("Pong");
		frame.setVisible(true);
		frame.setBounds(50, 0, 800, 800);

		Canvas canvas = new main();
		canvas.setSize(800, 800);
		if(CType==controlType.MOUSE) {
			canvas.addMouseListener(new MouseListener() {
	
				@Override
				public void mouseEntered(MouseEvent e) {
					
				}
	
				@Override
				public void mouseExited(MouseEvent e) {
					
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
				    int x=e.getX();
				    int y=e.getY();
				    System.out.println(x+","+y);	//these co-ords are relative to the component
				    player.posY = y;				
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
	
				    int x=e.getX();
				    int y=e.getY();
				    System.out.println(x+","+y);	//these co-ords are relative to the component
				    player.posY = y;
				}
	
				@Override
				public void mouseClicked(MouseEvent e) {
				    int x=e.getX();
				    int y=e.getY();
				    System.out.println(x+","+y);	//these co-ords are relative to the component
				    player.posY = y;
				}
			});
		} else if(CType==controlType.KEYBOARD) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

	            public boolean dispatchKeyEvent(KeyEvent ke) {
	            	switch (ke.getID()) {
	                    case KeyEvent.KEY_PRESSED:
	                        if (ke.getKeyCode() == KeyEvent.VK_W) {
	                            player.posY+=20;
	                        }
	                        if (ke.getKeyCode() == KeyEvent.VK_S) {
	                            player.posY+=-20;
	                        }
	                        break;
	            	}
	            	return false;
	            }
	        });
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add("Center", canvas);

		//render the frame
		while(true) {
			frame.setBackground(Color.BLACK);
			frame.setForeground(Color.BLACK);
			
			//manage the ball pos and stuff
		
			Ball.moveBall();
			//Moves the AI paddle to be in line with the ball
		    aiPaddleY = Ball.ballY;
			//Bounces off player paddle
			if(Ball.ballY>=player.posY 
					&& Ball.ballY<=player.posY+paddleheight
					&& Ball.ballX<=paddlewidth) {
				Ball.velX=-Ball.velX;
				player.score++;
				if(Ball.ballY<=player.posY+10) {
					Ball.velY = -10;
				} else if(Ball.ballY>=player.posY+paddleheight-10) {
					Ball.velY = 10;
				}
			} else if(Ball.ballX<=paddlewidth) {
				dead = true;
			}
			//Bounces off AI paddle
			if(Ball.ballY>=player.posY 
					&& Ball.ballY<=player.posY+paddleheight
					&& Ball.ballX>=800-paddlewidth) {
				Ball.velX=-Ball.velX;
				if(Ball.velY>8) {
					Ball.velY = 8;
				} else if(Ball.velY<-8) {
					Ball.velY = -8;
				}
			}

			//renders the ball and what not
			canvas.repaint();
			//sleeps thread to wait to draw next frame
			Thread.sleep(frameRate);
		}
		
	}
	public void paint(Graphics g) {
		if(!dead) {
			g.setColor(Color.BLACK);
	        g.fillRect(0, 0, 800, 800);
			g.setColor(Color.WHITE);
	        g.fillRect(395, 0, 10, 800);
	        g.fillRect(0, player.posY, paddlewidth, paddleheight);
	        g.fillRect(800-paddlewidth-16, Ball.ballY, paddlewidth, paddleheight);
	        g.fillOval(Ball.ballX, Ball.ballY, ballRadius*2, ballRadius*2);
	        g.drawString("Your score is: "+String.valueOf(player.score), 500, 100);
	        g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.RED);
			g.fillRect(0, 0, 800, 800);
			g.setColor(Color.WHITE);
			g.drawString("You died with a score of: "+String.valueOf(player.score), 400, 400);
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dead = false;
			player.init(paddleheight);
			Ball.initBall(ballRadius);
		}
    }
}

enum controlType {
	MOUSE,
	KEYBOARD;
}
