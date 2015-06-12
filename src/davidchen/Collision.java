package davidchen;

import java.awt.*;
import java.util.*;
public class Collision {
	ArrayList<Platform> platforms;
	int floorHeight;
	public static boolean isColliding(Entity other, Rectangle platforms)
	{
		return (platforms.intersects(new Rectangle((int)other.getX(), (int)(other.getY() + other.getHeight()-5), (int)other.getWidth(), 10)) == true && other.getYSpeed() >= 0);
	}
	public static boolean isCollidingFloor(Entity other, int floorHeight)
	{
		return (other.getHeight() + other.getY() > floorHeight);
	}
}