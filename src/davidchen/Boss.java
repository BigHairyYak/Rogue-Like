package davidchen;

public class Boss extends Mob
{
	int x, y, w, h, health;
	public Boss(int x, int y, int width, int height, int health) 
	{
		super(x, y, width, height, health);
		health = 20;
		this.x = x; this.y = y; w = width; h = height; this.health = health;
	}
	public void spawn(int amount)
	{
		for (int q = 0; q < amount; q++)
		{
			int dx = x + w/2 + (Driver.RNG.nextInt(201)-200);
			int dy = y + h/2 + (Driver.RNG.nextInt(201)-200);
			
		}
	}
}
