package davidchen;

import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
public class Driver extends JApplet
{
	public static ViewFrame view;
	public static boolean gameStarted;
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	public static Random RNG;
	
	public static AudioClip normalTheme;
	
	public void init() 
	{
		RNG = new Random();
		 normalTheme = getAudioClip(getDocumentBase(), "../res/SteinsGate.wav" );
		
		gameStarted = false;
		view = new ViewFrame();
		view.setVisible(true);
		setSize(1280, 1024);
	
		 //System.out.println(bossTheme);
	}
}
class ViewFrame extends JFrame
{
	public PausePanel menu;
	public Dungeon dungeon;
	
	public ViewFrame()
	{

		menu = new PausePanel();
		dungeon = new Dungeon();
		//dungeon.generate();

		setSize(1280, 1024);
		dungeon.setVisible(true);
		menu.setVisible(true);
		setContentPane(menu);
	}
	public void swapPanes()
	{
		YakEngine.clear();
		if (getContentPane() == menu)
		{
			menu.setVisible(false);
			setContentPane(dungeon);
			dungeon.setVisible(true);
			dungeon.repaint();
			dungeon.requestFocus();
			dungeon.t.start();
		}
		else 
		{
			dungeon.t.stop();
			dungeon.setVisible(false);
			setContentPane(menu);
			menu.setVisible(true);
			menu.repaint();
			menu.requestFocus();
		}
	}
}