package davidchen;

import java.awt.*;
@SuppressWarnings("serial")
public class Platform extends Rectangle 
{
	int x, y, w, h;
	public Platform(int x, int y, int width, int height) 
	{
		super(x, y, width, height);
		this.x = x; this.y = y; w = width; h = height;
	}
	public void draw(Graphics g)
	{
		g.drawImage(Driver.view.dungeon.platform, x, y, w, h, null);
	}
}
