package davidchen;

public class Boss extends Mob
{
	int x, y, w, h, health;
	int phase;
	Room room;
	public Boss(int x, int y, int width, int height, int health, Room bossRoom) 
	{
		super(x, y, width, height, health);
		health = 20; room = bossRoom;
		this.x = x; this.y = y; w = width; h = height; this.health = health;
	}
	public void act()
	{
		switch(phase)
		{
			case 1:
			{
				
			}break;
			case 2:
			{
				
			}break;
			case 3:
			{
				
			}break;
		}
		YakEngine.createSystem(x+w/2, y+h/2, 15f, 4);
	}
	public void move()
	{
		
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
		}
		room.addMobs(newMobs);
	}
}
