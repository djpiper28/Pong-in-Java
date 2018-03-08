/**
 * 
 */
package dannypiper.pong;

/**
 * @author Danny
 *
 */
public class Player {
	public int posY;
	public int score=0;
	
	public void init(int paddleheight) {
		this.posY=400-paddleheight;
		this.score=0;
	}
}
