package davidchen;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.Timer;
public final class YakEngine 
{
	BufferedImage dust;
	static BufferedImage fire;
	BufferedImage spark;
	static ArrayList<ParticleSystem> systems = new ArrayList<ParticleSystem>();
	
	static Timer EngineTimer;
	
	public static Random RNG = new Random();
	
	public final static float FADE = 0.9f;
	final static Vector2D GRAV = new Vector2D(0, 1.5f);	
	
	/*
	 * START
	 * Starts EngineTimer with the given listener
	 * - used as a workaround to Dave's timer being really, REALLY fast
	 */
	public static void start(ActionListener listener)
	{
		EngineTimer = new Timer(50, listener);
		EngineTimer.start();
	}
	
	/*
	 * GENERATEVECTORCLOUD
	 * Creates a 'cloud' of point vectors centered around (x, y)
	 * within the rectangle (x - xBound, y - yBound, x + xBound, y + yBound)
	 * Amount of vectors is dependent on intensity
	 */
	public static Vector2D[] generateVectorCloud(int x, int y, int xBound, int yBound, double intensity)
	{
		Vector2D[] cloud = new Vector2D[(int) intensity];
		for (int q = 0; q < (int)intensity; q++)
		{
			int dx = RNG.nextInt(xBound * 2) - xBound;
			int dy = RNG.nextInt(yBound * 2) - yBound;
			cloud[q] = new Vector2D(x + dx, y + dy);
		}
		return cloud;		
	}
	
	/*
	 * DRAW
	 * Draws all systems 
	 */
	public static void draw(Graphics G)
	{
		for (ParticleSystem PS : systems)
			PS.draw(G);
	}
	
	/*
	 * ACT
	 * Puts all systems through one iteration of their respective behaviors
	 */
	public static void act()
	{
		for (ParticleSystem PS : systems)
			PS.act();
	}
	
	/*
	 * CREATESYSTEM
	 * Generates a new system with a set location, intensity and particle type
	 */
	public static void createSystem(int x, int y, float intensity, int type)
	{
		System.out.println("Creating new system");
		switch (type)
		{
			case 1:
			{
				systems.add(new FireSystem(x, y, intensity));
			}break;
			case 2:
			{
				systems.add(new BoomSystem(x, y, intensity));				
			}break;
			case 3:
			{
				systems.add(new DustSystem(x, y, intensity));
			}break;
			
			default:{}break;
		}
	}
}

