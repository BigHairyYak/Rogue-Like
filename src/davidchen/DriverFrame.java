package davidchen;

import java.applet.AudioClip;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import resources.ResourceLoader;

public class DriverFrame extends JFrame
{
	Driver driver = new Driver();
	public static AudioClip normalTheme, bossTheme, jump, hurt;
	
	public static BufferedImage background, floor, wall, openDoor,closedDoor, 
	platform, wasd, arrowkeys, spacebar, boss1, boss2, boss3, button, izudu;
	public static Image bomb;
	public static BufferedImage enemy;
	public static BufferedImage bossEnemy;
	public static BufferedImage healthBar;
	public static BufferedImage playerSpriteSheet;
	
	public static void main(String[] Args)
	{		
		new DriverFrame();
	}
	public DriverFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Driver driver = new Driver();
		
			//floor = ImageIO.read(new FileInputStream("src/res/background_2.png"));
			floor = ResourceLoader.getBufferedImage("background_2.png");
			wall = ResourceLoader.getBufferedImage("wall.png");
			platform = ResourceLoader.getBufferedImage("platform.png");
			background = ResourceLoader.getBufferedImage("background_1.png");
			bomb = ResourceLoader.getImage("bomb.gif"); //toolkit usage is needed for proper .gif animation
			enemy = ResourceLoader.getBufferedImage("bird_final.png");
			bossEnemy = ResourceLoader.getBufferedImage("bossBird.png");
			boss1 = ResourceLoader.getBufferedImage("skull.png");
			boss2 = ResourceLoader.getBufferedImage("skull_open.png");
			boss3 = ResourceLoader.getBufferedImage("skull_open_bigger.png");
			healthBar = ResourceLoader.getBufferedImage("health.PNG");
			playerSpriteSheet = ResourceLoader.getBufferedImage("player.png");
			wasd = ResourceLoader.getBufferedImage("wasd.png");
			arrowkeys = ResourceLoader.getBufferedImage("arrowkeys.png");
			//spacebar = ResourceLoader.getBufferedImage("spacebar.png");	
			button = ResourceLoader.getBufferedImage("background_1.png");
			izudu = ResourceLoader.getBufferedImage("izudu.png");
			
			jump = ResourceLoader.getAudioClip("jump.wav");
			hurt = ResourceLoader.getAudioClip("hurt.wav");
			normalTheme = ResourceLoader.getAudioClip("soundtrack_Cleanest.wav");
			bossTheme = ResourceLoader.getAudioClip("bossMusic.wav");
		
		System.out.println("HEALTH: "  + healthBar);
		System.out.println("NORMAL THEME: " + normalTheme);
		
		//normalTheme.loop();
		
		
		driver.init();
	}
	public void refreshBomb()
	{
		
	}
}
