package davidchen;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Mob extends Entity 
{
	double angle = 0;
	double velX, velY;
	
	boolean bossSpawn = false;
	
	int cycle;
	
	public Mob (int x, int y, int width, int height, int health) 
	{ //initialize object
		super(x, y, width, height);
		velX = velY = 0;
		this.health = health;
		dead = (health <= 0); //if it is spawned with negative health, that's pretty dumb
		setXMultiplier(1.5);
		setYMultiplier(1.5);
	}
	
	public void draw(Graphics g)
	{
		int directionalW = 110;
		int directionalX = x;
		if (velX < 0)
		{
			directionalX = x + 110;
			directionalW = -110;
		}
		if (!bossSpawn)
			g.drawImage(Driver.view.dungeon.enemy.getSubimage((int)(cycle%5 * 110.2), (int)(cycle/5 * 101.33), 110, 101) //subimage area
				, directionalX, y, directionalW, 101, null); //drawing coordinates
		
		else
			g.drawImage(Driver.view.dungeon.bossEnemy.
					getSubimage((int)(cycle%5 * 110.2), (int)(cycle/5 * 101.33), 110, 101) //subimage area
				, directionalX, y, directionalW, 101, null); //drawing coordinates)
		g.drawRect(x, y, width, height);
	}
	
	/*
	 * MOVE
	 * Progress directly towards the given Entity
	 * at a constant speed, like an old 'bat' AI
	 */
	public void move(Entity other) 
	{ 
		cycle++;
		if (cycle > 13)
			cycle = 0;
		angle = Math.atan2((other.getY() - y), (other.getX() - x));

		velX = XSpeed * Math.cos(angle) * XMultiplier;
		velY = YSpeed * Math.sin(angle) * YMultiplier;

		x += velX; y += velY;
		if (Driver.RNG.nextInt(30) == 0)
		{
			YakEngine.createSystem(x + width/2, y + height/2, 2.5f, 4);
		}
	}
}