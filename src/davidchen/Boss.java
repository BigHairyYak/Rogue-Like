package davidchen;

import java.awt.Graphics;

public class Boss extends Mob
{
	int x, y, w, h, health;
	int phase = 1;
	Room room;
	
	public Boss(int x, int y, int width, int height, int health, Room bossRoom) 
	{
		super(x, y, width, height, health);
		health = 30; room = bossRoom;
		this.x = x; this.y = y; w = width; h = height; this.health = health;
	}
	/*
	 * MOVE
	 * Should technically be act()
	 * but I'm lazy and running out
	 * of time. 
	 */
	@Override
	public void move()
	{
		switch(phase)
		{
			case 1:
			{
				if (Driver.RNG.nextInt(151) == 150)
					spawn(Driver.RNG.nextInt(5) + 1);
			}break;
			case 2:
			{
				if (Driver.RNG.nextInt(251) == 250)
				{
					x = Driver.RNG.nextInt(800) + 200;
					y = Driver.RNG.nextInt(400) + 200;
				}
				if (Driver.RNG.nextInt(151) == 150)
					spawn(Driver.RNG.nextInt(5) + 1);
			}break;
			case 3:
			{
				move(Driver.view.dungeon.player);
				if (Driver.RNG.nextInt(251) == 250)
				{
					x = Driver.RNG.nextInt(800) + 200;
					y = Driver.RNG.nextInt(400) + 200;
				}
				if (Driver.RNG.nextInt(151) == 150)
					spawn(Driver.RNG.nextInt(5) + 1);
			}break;
		}
		YakEngine.createSystem(x+w/2+10, y+h/2+10, 6f * 30/health, 4);
	}
	public void draw(Graphics g)
	{
		if (phase == 1)
			g.drawImage(Driver.view.dungeon.boss1, x, y, w, h, null);
		g.drawRect(x, y, w, h);
		g.drawString("BOSS HEALTH: " + health, 50, 100);
	}
	public void spawn(int amount)
	{
		Mob[] newMobs = new Mob[amount];
		for (int q = 0; q < amount; q++)
		{
			int dx = x + w/2 + (Driver.RNG.nextInt(201)-200);
			int dy = y + h/2 + (Driver.RNG.nextInt(201)-200);
			
			/*
			 * Creates new mobs that are twice as fast
			 * as normal, should be extra spooky
			 */
			newMobs[q] = new Mob(dx, dy, 100, 100, 4);
			newMobs[q].setXMultiplier(3);
			newMobs[q].setYMultiplier(3);
			newMobs[q].bossSpawn = true;
		}
		room.addMobs(newMobs);
	}
}
