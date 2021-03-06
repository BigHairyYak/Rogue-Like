package davidchen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PausePanel extends JPanel implements ActionListener, MouseListener
{
	KevButton startResume, exit;
	public Image background, izudu;
	static Image button;
	
	int whichSplash;
	double lastTime, fastestTime;
	
	public PausePanel()
	{
		lastTime = fastestTime = 9999999;
		startResume = new KevButton(280, 200, 720, 200, "START");
		exit = new KevButton(280, 500, 720, 200, "EXIT");
		
		addMouseListener(this);
		
			background = DriverFrame.background;
			button = DriverFrame.button;
			izudu = DriverFrame.izudu;
		
		YakEngine.start(this);
		YakEngine.createSystem(140, 250, 7.0f, 1);
		YakEngine.createSystem(140, 600, 7.0f, 1);
		YakEngine.createSystem(1140, 250, 7.0f, 1);
		YakEngine.createSystem(1140, 600, 7.0f, 1);
		
		this.setVisible(true);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 1280, 1024, this);
		startResume.draw(g);
		exit.draw(g);
		g.drawImage(izudu, 1050, 750, 250, 250, this);
		g.setFont(g.getFont().deriveFont(40f));
		String splash = "";
		if (whichSplash == 0)
			splash = "DO NOT UNDERESTIMATE THE BOSS: THE GAME";
		else if (whichSplash == 1)
			splash = "THE BOSS IS SUPER HARD: THE GAME";
		else if (whichSplash == 2)
			splash = "YOU WILL LOSE: THE GAME";
		else if (whichSplash == 3)
			splash = "1,729 LINES OF CODE!";
		else if (whichSplash == 4)
			splash = "NO TURNING BACK; IT'S CODED THAT WAY";
		g.drawString(splash, 275, 120);
		
		g.drawString("LAST RUN: " + lastTime + " seconds", 400, 750);
		g.drawString("FASTEST TIME: " + fastestTime + " seconds", 400, 830);
		
		g.setFont(g.getFont().deriveFont(15f));
		g.drawString("Noah Bock, David Chen, Kevin Guo, and Sam Yakovlev", 20, 970);
		YakEngine.draw(g);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//System.out.println("tick");
		if (Driver.gameStarted == true)
			startResume.function = "RESUME";
		if (Driver.gameEnded == true || Driver.view.dungeon.player.dead)
			startResume.function = "RESTART";
		YakEngine.act();
		repaint(); 
	}
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		//System.out.println("CLICK");
		if (startResume.getBounds().contains(e.getPoint()))
		{
			if (Driver.gameStarted == false)
			{
				Driver.gameStarted = true;
				Driver.view.dungeon.generate();
				Driver.bossTheme.stop();
				//Driver.normalTheme.loop();
			}
			if (Driver.gameEnded == true || Driver.view.dungeon.player.dead)
			{
				Driver.gameEnded = false;
				Driver.gameStarted = true;
				Driver.view.dungeon.generate();
				Driver.bossTheme.stop();
				Driver.normalTheme.loop();
			}
			Driver.view.swapPanes();
		}
		if (exit.getBounds().contains(e.getPoint()))
			System.exit(0);
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}

class KevButton extends Rectangle implements MouseListener
{
	int x, y, w, h;
	String function;
	public KevButton(int X, int Y, int W, int H, String Function)
	{
		x = X; y = Y; w = W; h = H; function = Function;
	}
	public void mouseClicked(MouseEvent e) 
	{
		if (this.contains(e.getPoint()))
		{
			System.out.println(function + " CLICKED");
		}
	}
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, w, h);
	}
	public void draw(Graphics g)
	{
		g.drawImage(PausePanel.button, x, y, w, h, Driver.view.menu);
		g.setColor(Color.ORANGE);
		g.setFont(g.getFont().deriveFont(180.0f));
		int fw = g.getFontMetrics().stringWidth(function);
		g.drawString(function, x + w/2 - fw/2, y + h/2 + 70);
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}