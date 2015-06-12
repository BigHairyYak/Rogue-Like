package davidchen;

import java.awt.Dimension;
@SuppressWarnings("serial")
public class Mob extends Entity {
	double angle = 0;
	double velX, velY;
	int health;
	public Mob (int x, int y, int width, int height, int health) { //initialize object
		super(x, y, width, height);
		velX = velY = 0;
		this.health = health;
	}
	public void move(Entity other) { //moves toward the position of other entity **ai-moving, like a bat
		angle = Math.atan2((other.getY() - y), (other.getX() - x));

		velX = XSpeed * Math.cos(angle) * XMultiplier;
		velY = YSpeed * Math.sin(angle) * YMultiplier;

		x += velX; y += velY;

		//setLocation((int)(/*getBounds().*/x + (XSpeed * Math.cos(angle) * XMultiplier)),
		//(int)(/*getBounds().*/y + (YSpeed * Math.sin(angle) * YMultiplier)));
		//System.out.println(angle);
	}
	public void lowerHealth() {
		health--;
		System.out.println(health);
		if (health < 1)
			this.setSize(new Dimension(0, 0));
	}
}