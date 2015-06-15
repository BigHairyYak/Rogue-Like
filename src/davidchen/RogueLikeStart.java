package davidchen;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Rectangle;

public class RogueLikeStart implements MouseListener
{
	Rectangle r;
	int x, y, height, width, mouseX, mouseY;
	String selection;
	Color backColor;
	Boolean buttonClicked = false;

	//Creates a button object
	public RogueLikeStart(int x, int y, int width, int height, String selection, Color backColor, JApplet app)
	{
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.selection = selection;
		this.backColor = backColor;
		r = new Rectangle(x, y, width, height);

		app.addMouseListener(this);
	}

	//Draws a button based on the coordinates entered
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(backColor);
		g2.draw(r);
		g2.fill(r);

		g2.setColor(Color.black);
		g2.setFont(g2.getFont().deriveFont(18.0f));
		g2.drawString(selection, x+(width/3)+13, y+(height/2)+5);
	}

	//Detects collision by mouse
	public void mouseClicked(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();

		//If the click is inside the rectangle, click is returned as true
		if(r.contains(mouseX,  mouseY))
		{
			buttonClicked = true;
			System.out.println(selection + " button is clicked.");
		}

	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}