package dannypiper.pong;

import java.util.Random;

public class ball {
	public int ballX;
	public int ballY;
	public int velY;
	public int velX;
	public state status;
	
	public int randint(int b) {
		Random rand = new Random();
		return rand.nextInt(b);
	}
	
	public void initBall(int ballRadius) {	//sets random positions for the ball
		this.status = state.PLAYING;
		this.ballX = 400 - ballRadius; //Screen is 800 pixels wide
		this.ballY = randint(750)+25; //Between 25 and 775
		if(randint(1)==1) {
			this.velX = randint(6)+2; //between 4 and 1
		} else {
			this.velX = -(randint(6)+2);
		}
		if(randint(1)==1) {
			this.velY = randint(4)+2; //between 4 and 1
		} else {
			this.velY = -(randint(4)+2);
		}
	}
	
	public void moveBall() {	//moves by ball velocity
		if(this.status==state.PLAYING) {
			this.ballX += this.velX;
			this.ballY += this.velY;
		}
		//Ball bounces next to the edge
		if(this.ballX<=20 || this.ballX>=780) {
			this.velX = -this.velX;
			if(this.velX>0 && this.velX<8) {
				this.velX+=randint(2);
			}
			
			if(this.velX<0 && this.velX>-8) {
				this.velX+=-randint(2);
			}
		}
		if(this.ballY<=20 || this.ballY>=780) {
			this.velY = -this.velY;
			if(this.velY>0 && this.velY<8) {
				this.velY+=randint(2);
			}

			if(this.velY<0 && this.velY>-8) {
				this.velY+=-randint(2);
			} 
		}
		
		if(this.ballY>800) {
			this.ballY = 779;
		}
		if(this.ballX>800) {
			this.ballX = 779;
		}
		if(this.ballY<0) {
			this.ballY = 21;
		}
		if(this.ballX<0) {
			this.ballX = 21;
		}
	}
}
enum state {
	PLAYING,
	STOPPED;
}