package davidchen;

import java.awt.event.*;
import java.awt.*;
@SuppressWarnings("serial")
public class Player extends Entity {
	protected final double DEFAULT_MULTIPLIER = 1.0, DEFAULT_SPEED = 5.0, DEFAULT_ACCELERATION = 5, DEFAULT_JUMP_HEIGHT = 200;
	private KeyAdapter keyAdapter;
	public volatile boolean isAttackingLeft = false, isAttackingRight = false, isAttackingUp = false, isAttackingDown = false;
	public volatile boolean isAttacking = false, bombDropped = true;
	public Rectangle bomb;
	private String upgrade = "bomb";
	public volatile boolean BombDamage = false;
	int health = 5;
	Dungeon dungeon;
	public Player(int x, int y, int width, int height, Dungeon dungeon) { //initialize
		super(x, y, width, height);
		setXSpeed(0);
		setYSpeed(0);
		setXMultiplier(DEFAULT_MULTIPLIER);
		setYMultiplier(DEFAULT_MULTIPLIER);
		bomb = new Rectangle(0,0,0,0);
		keyAdapter = new CustomKeyAdapter();
		this.dungeon = dungeon;
	}
	public void move() {
		for (Platform platform : dungeon.RoomOrder[dungeon.roomCounter].roomPlatform)
			if (Collision.isColliding(this, platform) && YSpeed > 0) {
				YSpeed = 0;
				setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(platform.y - height));	
			}
		for (Mob mob : dungeon.RoomOrder[dungeon.roomCounter].roomMob)
			if (Collision.isColliding(this, mob))
				health--;				
		if (Collision.isCollidingFloor(this, 800)) {
			YSpeed = 0;
			setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier * 0.1)));
		}
		else if (y < 600) {
			YSpeed += DEFAULT_ACCELERATION;
			setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier * 0.1)));
		}
		else {
			if (YSpeed > 0)
				YSpeed = 0;
			setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier * 0.1)));
		}
		
		/*for (Upgrade upgrade : Driver.upgrades){
			if (Collision.isColliding(this, upgrade)) {
				if (upgrade.getUpgrade() == 1) {
					this.upgrade = "Bomb";
				}
				if (upgrade.getUpgrade() == 2) {
					this.upgrade = "Fire";//code hat //gruber was here
				}
			}
		}*/
	}
	public boolean isAttackingLeft() {
		return isAttackingLeft;
	}
	public boolean isAttackingRight() {
		return isAttackingRight;
	}
	public boolean isAttackingUp() {
		return isAttackingUp;
	}
	public boolean isAttackingDown() {
		return isAttackingDown;
	}	
	public Rectangle attackLeft() {
		return new Rectangle(x - 50, y - 50, width, (int) (2*height));
	}
	public Rectangle attackRight() {
		return new Rectangle(x + 50, y - 50, width, (int) (2*height));
	}
	public Rectangle attackUp() {
		return new Rectangle(x - 50, y - 50, 2*width, (int)height);
	}
	public Rectangle attackDown() {
		return new Rectangle(x - 50, y + 50, 2*width, (int)height);
	}	
	public KeyAdapter getKeyAdapter() {
		return keyAdapter;
	}
	public class CustomKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				setXSpeed(-DEFAULT_SPEED);
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				setXSpeed(DEFAULT_SPEED);
			}
			else if (e.getKeyCode() == KeyEvent.VK_W) {
				for (Platform platform : dungeon.RoomOrder[dungeon.roomCounter].roomPlatform)
					if (YSpeed == 0 || Collision.isColliding(dungeon.player, platform))
						setYSpeed(-DEFAULT_JUMP_HEIGHT);	
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP) {
				isAttackingUp = true;
				isAttacking = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				isAttackingDown = true;
				isAttacking = true;				
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				isAttackingLeft = true;
				isAttacking = true;	
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				isAttackingRight = true;
				isAttacking = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (upgrade.equalsIgnoreCase("Bomb")) {
					bombDropped = true;
					bomb = new Rectangle(x - width/2, y - width/2, 250, 250);
				}
			}
			else {
				System.out.println("Unused Key");
			}
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				setXSpeed(0);
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				setXSpeed(0);
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP) {
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {		
			}
		}
		public void keyTyped(KeyEvent e) {
		}
	}
}
