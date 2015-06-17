package davidchen;

import java.applet.*;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class DriverFrame extends JFrame
{
	Driver driver = new Driver();
	static JFrame frame = new JFrame();
	

	public static AudioClip normalTheme, bossTheme, jump, hurt;
	
	public static void main(String[] Args)
	{		
		Driver driver = new Driver();
		driver.init();
		frame.getContentPane().add(driver);
		frame.pack();
		frame.setContentPane(driver);
		frame.getContentPane().setVisible(true);
		
	}
}
