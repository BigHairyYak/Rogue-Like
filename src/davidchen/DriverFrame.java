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
	ResourceLoader rl = new ResourceLoader();
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
			floor = rl.getBufferedImage("background_2.png");
			wall = rl.getBufferedImage("wall.png");
			platform = rl.getBufferedImage("platform.png");
			background = rl.getBufferedImage("background_1.png");
			bomb = rl.getImage("bomb.gif"); //toolkit usage is needed for proper .gif animation
			enemy = rl.getBufferedImage("bird_final.png");
			bossEnemy = rl.getBufferedImage("bossBird.png");
			boss1 = rl.getBufferedImage("skull.png");
			boss2 = rl.getBufferedImage("skull_open.png");
			boss3 = rl.getBufferedImage("skull_open_bigger.png");
			healthBar = rl.getBufferedImage("health.PNG");
			playerSpriteSheet = rl.getBufferedImage("player.png");
			wasd = rl.getBufferedImage("wasd.png");
			arrowkeys = rl.getBufferedImage("arrowkeys.png");
			spacebar = rl.getBufferedImage("spacebar.png");	
			button = rl.getBufferedImage("background_1.png");
			izudu = rl.getBufferedImage("izudu.png");
			
			jump = rl.getAudioClip("jump.wav");
			hurt = rl.getAudioClip("hurt.wav");
			normalTheme = rl.getAudioClip("soundtrack_Cleanest.wav");
			bossTheme = rl.getAudioClip("bossMusic.wav");
		
		System.out.println("HEALTH: "  + healthBar);
		System.out.println("NORMAL THEME: " + normalTheme);
		
		//normalTheme.loop();
		
		
		driver.init();
	}
	public void refreshBomb()
	{
		
	}
}
