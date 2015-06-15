package davidchen;

@SuppressWarnings("serial")
public class Mob extends Entity 
{
	double angle = 0;
	double velX, velY;

	public Mob (int x, int y, int width, int height, int health) 
	{ //initialize object
		super(x, y, width, height);
		velX = velY = 0;
		this.health = health;
		dead = (health <= 0); //if it is spawned with negative health, that's pretty dumb
		setXMultiplier(1.5);
		setYMultiplier(1.5);
		
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
		if (Dungeon.r.nextInt(30) == 0)
		{
			YakEngine.createSystem(x + width/2, y + height/2, 2.5f, 4);
		}
	}
}