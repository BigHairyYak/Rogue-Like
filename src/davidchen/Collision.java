package davidchen;

import java.awt.*;
import java.util.*;
public class Collision {
	ArrayList<Platform> platforms;
	int floorHeight;
	public static boolean isColliding(Entity other, Rectangle platforms) {
		if (platforms.intersects(new Rectangle((int)other.getX(), (int)(other.getY() + other.getHeight()-5), (int)other.getWidth(), 10)) == true && other.getYSpeed() >= 0)
			return true;
		else 
			return false;
	}
	public static boolean isColliding(Entity other, Entity player) {
		if (player.intersects(new Rectangle((int)other.getX(), (int)(other.getY()), (int)other.getWidth(), (int)other.getHeight())) == true)
			return true;
		else 
			return false;
	}
	public static boolean isCollidingFloor(Entity other, int floorHeight) {
		if (other.getHeight() + other.getY() > floorHeight)
			return true;			
		else 
			return false;
	}
}