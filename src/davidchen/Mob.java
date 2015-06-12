package davidchen;

import java.awt.Dimension;
@SuppressWarnings("serial")
public class Mob extends Entity 
{
	double angle = 0;
	double velX, velY;
	int health;
	boolean dead;
	public Mob (int x, int y, int width, int height, int health) 
	{ //initialize object
		super(x, y, width, height);
		velX = velY = 0;
		this.health = health;
		dead = (health <= 0); //if it is spawned with negative health, that's pretty dumb
	}
	
	/*
	 * MOVE
	 * Progress directly towards the given Entity
	 * at a constant speed, like an old 'bat' AI
	 */
	public void move(Entity other) 
	{ 
		angle = Math.atan2((other.getY() - y), (other.getX() - x));

		velX = XSpeed * Math.cos(angle) * XMultiplier;
		velY = YSpeed * Math.sin(angle) * YMultiplier;

		x += velX; y += velY;
	}
	
	/*
	 * LOWERHEALTH
	 * Decreases the mob's health by the given increment
	 * If it drops below 0, the mob dies
	 */
	public void lowerHealth(int increment)
	{
		health -= increment;
		//System.out.println(health);
		dead = (health <= 0);
	}
}