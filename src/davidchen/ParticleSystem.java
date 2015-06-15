package davidchen;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
public class ParticleSystem 
{
	int x, y; float I;
	boolean readyForRemoval;
	ArrayList<Particle> particles; //contains the actual particles and their respective paths
	public ParticleSystem(int x, int y, float intensity)
	{
		this.x = x; this.y = y; I = intensity; //position of source - intensity corresponds to particle count
		particles = new ArrayList<Particle>();
		readyForRemoval = false;
	}
	public void act()
	{
		for (Particle p : particles)
		{
			p.act();
			/*
			 * Each particle behaves according to its act() method
			 * If a particle is ready to be removed (too small to draw efficiently),
			 * it is removed from the array. The break statement prevents a 
			 * ConcurrentModificationException from being thrown and allows
			 * the array to update properly.
			 */
			if (p.readyForRemoval)
			{
				particles.remove(p); 
				break;
			}
			readyForRemoval = (particles.size() == 0); //sets the system to be removed if no particles remain
		}
	}
	public void draw(Graphics G){}
}

/*
 * FIRE SYSTEM
 * Looks like fire, creates cloud of particles which rise upwards and fade
 * Regenerates after certain number of particles (refreshTrigger) remain
 */

class FireSystem extends ParticleSystem //constant system, making more particles over time
{
	private int refreshTrigger; //Number of particles remaining to trigger a regeneration
	private float regenRadius;
	public boolean mobPoof;
	public FireSystem(int X, int Y, float intensity)
	{
		super(X, Y, intensity);
		generate(x, y, I);
	}
	public void draw(Graphics G)
	{
		for (Particle p: particles)
		{
			if (!p.readyForRemoval)		
			{
				G.setColor(new Color(255, (int)(255 * p.life / p.Ilife), 0, (int)(255 * p.life / p.Ilife))); //standard flame, red-yellow
				//G.setColor(new Color((int)(255 * p.life / p.Ilife), 0, 255, (int)(255 * p.life / p.Ilife))); //pink-blue, for funsies
				
				G.fillRect((int)p.pos.x - (int)(2 * p.life), (int)p.pos.y - (int)(2 * p.life), (int)(4 * p.life), (int)(4 * p.life));	
			}
		}
		G.fillRect(x -  particles.size(), y - particles.size(), 2 * particles.size(), 2 *particles.size());
	}
	
	public void act()
	{
		/*
		 * Override of parent method to check for how many particles remain, to allow
		 * a 'natural' flaring regeneration of the flame
		 */
		
		if (particles.size() <= refreshTrigger && !mobPoof)
		{
			generate(x, y, regenRadius);
		}
		
		super.act(); //all particles fade
	}

	public void generate(int X, int Y, float intensity) //regeneration method, only FireSystem has this
	{
		x = X; y = Y; 
		regenRadius = intensity; //System.out.println("Initial radius: " + regenRadius);
		Vector2D[] cloud = YakEngine.generateVectorCloud(x, y, (int) regenRadius * 5, (int) regenRadius * 5, intensity);
		
		for (Vector2D v : cloud)
		{
			int velY = -(YakEngine.RNG.nextInt(5) + 5);
			particles.add(new FireParticle(v, new Vector2D(0, velY), YakEngine.RNG.nextInt(5)+2));
		}
			refreshTrigger = YakEngine.RNG.nextInt(5) + 8;
	}
}

/*
 * BOOM SYSTEM
 * Used for both explosions and sparks
 * Generates only once, declares itself ready for deletion when all particles fade
 */

class BoomSystem extends ParticleSystem //works like a FireSystem, but with different velocity allocation and no regen
{
	public BoomSystem(int x, int y, float intensity)
	{
		super(x, y, intensity);
		float expRadius = intensity * 5;
		Vector2D origin = new Vector2D(x, y);
		Vector2D[] cloud = YakEngine.generateVectorCloud(x, y, (int)expRadius, (int)expRadius, intensity * 5);
		
		for (Vector2D v : cloud)
		{
			Vector2D p = v; //sets up position for new particle
			v = v.subtract1(origin); //changes v from a coordinate to a velocity
			//System.out.println(p + " : " + v);
			particles.add(new BoomParticle(p, v, I));
		}		
	}
	public void draw(Graphics G)
	{
		for (Particle p: particles)
		{
			if (!p.readyForRemoval)		
			{
				G.setColor(new Color(255, (int)(255 * p.life / p.Ilife), 0, (int)(255 * p.life / p.Ilife))); //standard flame, red-yellow
				//G.setColor(new Color((int)(255 * p.life / p.Ilife), 0, 255, (int)(255 * p.life / p.Ilife))); //pink-blue, for funsies
				
				G.fillRect((int)p.pos.x - (int)(6 * p.life), (int)p.pos.y - (int)(6 * p.life), (int)(12 * p.life), (int)(12 * p.life));	
			}
		}
		G.fillRect(x - (int)(2.5 * particles.size()), y - (int)(2.5 * particles.size()), 5 * particles.size(), 5 *particles.size());
	}
	public void act()
	{
		/*for (Particle p : particles)
			p.act();*/
		super.act();
	}
}


/*
 * DUSTSYSTEM
 * Simulates a 'poof' of dust generated when something hits the ground
 */
class DustSystem extends ParticleSystem //one-time system, fades after one iteration
{
	public DustSystem(int x, int y, float intensity)
	{
		super(x, y, intensity);
	}
	public void generate(int x, int y, float intensity)
	{
		
	}
}

class Particle
{
	float deathpoint; //amount of life at which particle is clear for deletion
	float life, Ilife; Vector2D pos, vel;
	boolean readyForRemoval; //determines when the particle can be removed from the system
	public Particle (Vector2D position, Vector2D velocity, float lifeSpan)
	{
		pos = position; 
		vel = velocity;
		life = Ilife = lifeSpan;
		deathpoint = .3f;
		readyForRemoval = false;
	}
	public void act()
	{
		life *= YakEngine.FADE;
		if (life <= deathpoint)
		{
			readyForRemoval = true;
		}
	}
}
class FireParticle extends Particle
{
	public FireParticle(Vector2D position, Vector2D velocity, float lifeSpan)
	{
		super(position, velocity, lifeSpan);
	}
	public void act()
	{		
		vel.factor(YakEngine.FADE);
		pos.add(vel);
		super.act();
	}
}

class BoomParticle extends Particle
{
	public BoomParticle(Vector2D position, Vector2D velocity, float lifeSpan) 
	{
		super(position, velocity, lifeSpan);		
		//deathpoint = .7f; //dies out sooner for a 'quick' explosion effect
	}
	public void act()
	{
		vel.add(YakEngine.GRAV);
		pos.add(vel);
		super.act();
	}
}

class DustParticle extends Particle
{

	public DustParticle(Vector2D position, Vector2D velocity, float lifeSpan)
	{
		super(position, velocity, lifeSpan);
	}
	public void act()
	{
		vel.add(YakEngine.GRAV);
		pos.add(vel);
		super.act();
	}
}
