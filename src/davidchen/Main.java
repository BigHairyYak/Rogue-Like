package davidchen;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Main extends JApplet{
	RogueLikeStart startPlay;
	RogueLikeStart stop;
	Image image;
	Toolkit tk;
	String gameString = "Izudu";
	public void init(){
		Container c = getContentPane();
		tk = Toolkit.getDefaultToolkit();
		setSize(1500,1400);
		setContentPane(new DrawingPanel());
		setVisible(true);

		image = tk.getImage("Rogue legacy logo.jpg");
		startPlay = new RogueLikeStart(560, 370, 180, 80, "Start", Color.green, this);
		stop = new RogueLikeStart(560, 520, 180, 80, "Exit", Color.red, this);
	}

	//Creates an inner class that extends a JPanel
	public class DrawingPanel extends JPanel
	{
		public void paintComponent(Graphics g){
			startPlay.draw(g);
			stop.draw(g);
			g.setFont(g.getFont().deriveFont(24.0f));
			g.drawString(gameString, 620, 770);
			g.drawImage(image, 520, 50, null);
			repaint();
		}
	}
}