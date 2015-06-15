package davidchen;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
@SuppressWarnings("serial")
public class Player extends Entity {
	protected final double DEFAULT_MULTIPLIER = 1.0, DEFAULT_SPEED = 10.0, DEFAULT_ACCELERATION = 20, DEFAULT_JUMP_HEIGHT = 400;
	private KeyAdapter keyAdapter;
	public boolean isAttackingLeft = false, isAttackingRight = false, isAttackingUp = false, isAttackingDown = false, isAttacking = false, bombDropped = true;
	public Rectangle bomb;
	private String upgrade = "bomb";
	public boolean BombDamage = false, movingLeft = false, movingRight = false;
	int counter = 0;
	Dungeon dungeon;
	public Player(int x, int y, int width, int height, int health, Dungeon dungeon) 
	{ //initialize
		super(x, y, width, height);
		setXSpeed(0);
		setYSpeed(0);
		setXMultiplier(DEFAULT_MULTIPLIER);
		setYMultiplier(DEFAULT_MULTIPLIER);
		bomb = new Rectangle(0,0,0,0);
		bombDropped = false;
		keyAdapter = new CustomKeyAdapter();
		this.health = health; //player needs some health value, was not set before
		this.dungeon = dungeon;
	}
	public void move() 
	{
		boolean collision = true;
		for (Platform platform : dungeon.RoomOrder[dungeon.roomCounter].roomPlatform)
		{
			if (this.intersects(platform) && YSpeed > 0)
			{
				YSpeed = 0;
				setY((int)(platform.y - height));
				collision = true;
			}
			else if (this.intersects(dungeon.RoomOrder[dungeon.roomCounter].floor))
			{
				YSpeed = 0;
				setY((int)(dungeon.RoomOrder[dungeon.roomCounter].floor.y-height));
				collision = true;
			}
			else if (y < 600) 
			{
				collision = false;
				//YSpeed += DEFAULT_ACCELERATION;
				//setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier * 0.1)));
			}
			else 
			{
				collision = true;
				if (YSpeed > 0)
					YSpeed = 0;
			}			
		}
		if (!collision)
			YSpeed += DEFAULT_ACCELERATION;
		if (this.intersects(dungeon.RoomOrder[dungeon.roomCounter].leftWall)) {
			ArrayList<BoundingRectangle> r = createBoundingRectangles(dungeon.RoomOrder[dungeon.roomCounter].leftWall);
			if (r.get(3).intersects(this)) {
				YSpeed = 0;
				setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier * 0.1)));
				setY(r.get(3).y + r.get(3).height);
			}
			else if (r.get(2).intersects(this)) {
				XSpeed = 0;
				setX(r.get(2).x + r.get(2).width);
			}		
			setX((int)dungeon.RoomOrder[dungeon.roomCounter].leftWall.x+dungeon.RoomOrder[dungeon.roomCounter].leftWall.width);
		}
		else if (this.intersects(dungeon.RoomOrder[dungeon.roomCounter].rightWall)) {
			XSpeed = 0;
			setX((int)dungeon.RoomOrder[dungeon.roomCounter].rightWall.x-width);
		}
		setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier * 0.1)));
		
		/*for (Upgrade upgrade : Driver.upgrades){
			if (Collision.isColliding(this, upgrade)) {
				if (upgrade.getUpgrade() == 1) {
					this.upgrade = "Bomb";
				}
				if (upgrade.getUpgrade() == 2) {
					this.upgrade = "Fire";
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
	public void draw(Graphics2D g) 
	{
		if (movingLeft)
		{
			if (counter <= 3)
			{
				g.drawImage(dungeon.playerWalkingLeft.get(0), x, y+30, dungeon);
			}
			else if (counter > 3)
			{
				g.drawImage(dungeon.playerWalkingLeft.get(1), x, y+30, dungeon);
			}
			else if (counter <= 6) 
			{
				g.drawImage(dungeon.playerWalkingLeft.get(1), x, y+30, dungeon);
				counter = 0;
			}
		}
		else if (movingRight) 
		{			
			if (counter <= 3) 
			{				
				g.drawImage(dungeon.playerWalkingRight.get(0), x, y+30, dungeon);
			}
			else if (counter > 3)
			{
				g.drawImage(dungeon.playerWalkingRight.get(1), x, y+30, dungeon);
			}
			else if (counter <= 6) 
			{
				g.drawImage(dungeon.playerWalkingRight.get(1), x, y+30, dungeon);
				counter = 0;
			}
		}
		
	}
	public ArrayList<BoundingRectangle> createBoundingRectangles(Rectangle r)
	{
	    ArrayList<BoundingRectangle> list = new ArrayList<BoundingRectangle>();
	    int brWidth = 1;

	    // Create left rectangle
	    Rectangle left = new Rectangle(r.x, r.y, brWidth, r.height);
	    list.add(new BoundingRectangle(left, "left"));

	    // Create top rectangle
	    Rectangle top = new Rectangle(r.x, r.y, r.width, brWidth);
	    list.add(new BoundingRectangle(top, "top"));

	    // Create right rectangle
	    Rectangle right = new Rectangle(r.x + r.width - brWidth, r.y, brWidth,
	            r.height);
	    list.add(new BoundingRectangle(right, "right"));

	    // Create bottom rectangle
	    Rectangle bottom = new Rectangle(r.x, r.y + r.height - brWidth,
	            r.width, brWidth);
	    list.add(new BoundingRectangle(bottom, "bottom"));

	    return list;
	}

	public class BoundingRectangle extends Rectangle{
	    private Rectangle rectangle;
	    private String position;

	    public BoundingRectangle(Rectangle rectangle, String position) {
	        super(rectangle);
	    	this.rectangle = rectangle;
	        this.position = position;
	    }

	    public Rectangle getRectangle() {
	        return rectangle;
	    }

	    public String getPosition() {
	        return position;
	    }

	    public boolean intersects(Rectangle r) {
	        return rectangle.intersects(r);
	    }

	}
	public class CustomKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				setXSpeed(-DEFAULT_SPEED);
				counter++;
				if (counter > 6)
					counter = 0;
				movingLeft = true;
				movingRight = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				setXSpeed(DEFAULT_SPEED);
				counter++;
				if (counter > 6)
					counter = 0;
				movingRight = true;
				movingLeft = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				for (Platform platform : dungeon.RoomOrder[dungeon.roomCounter].roomPlatform)
					if (YSpeed == 0 || Collision.isColliding(dungeon.player, platform))
						setYSpeed(-DEFAULT_JUMP_HEIGHT);	
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				isAttackingUp = true;
				isAttacking = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				isAttackingDown = true;
				isAttacking = true;				
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				isAttackingLeft = true;
				isAttacking = true;	
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				isAttackingRight = true;
				isAttacking = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) 
			{
				if (upgrade.equalsIgnoreCase("Bomb")) 
				{
					if(!bombDropped)
					{
						bombDropped = true;
						bomb = new Rectangle(x - width/2, y - width/2, 250, 250);
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE)
				Driver.view.swapPanes();
			else 
			{
				System.out.println("Unused Key");
			}
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				setXSpeed(0);
				counter = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				setXSpeed(0);
				counter = 0;
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
