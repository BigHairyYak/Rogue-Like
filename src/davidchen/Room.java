package davidchen;

import java.util.*;
import java.awt.*;

public class Room
{
	Platform[] roomPlatform;
	ArrayList<Mob> roomMobs = new ArrayList<Mob>();
	Rectangle floor, leftWall, rightWall;
	Graphics2D g2;

	public Room(Platform[] platforms, ArrayList<Mob> mobs)//, Image image, Player player)
	{
		roomMobs = mobs;
		roomPlatform = platforms;
		floor = new Rectangle(0, 700, 1280, 400);
		leftWall = new Rectangle (0, -30, 20, 580);
		rightWall = new Rectangle (1260, -30, 20, 580);
	}

	public void draw(Graphics g)
	{
		g2 = (Graphics2D) g;
		g2.setColor(Color.GRAY);
		for (Platform platform : roomPlatform)
			platform.draw(g);
		g2.drawImage(Driver.view.dungeon.wall, 1260, -30, 60, 580, null);
		g2.drawImage(Driver.view.dungeon.wall, -40, -30, 60, 580, null);
		g2.drawImage(Driver.view.dungeon.floor, 0, 700, 1280, 400, null);
		for (Mob m : roomMobs)
			m.draw(g);
	}
	public void addMobs(Mob[] newMobs)
	{
		for (Mob m : newMobs)
			 roomMobs.add(m);
	}
	/*
	 * UPDATE
	 * Checks whether or not mobs are dead
	 * If a mob is dead, it is removed and a break is forced
	 * to prevent a ConcurrentModificationException from being thrown
	 * while the ArrayList changes size
	 */
	public void update()
	{
		for (Mob m : roomMobs)
		{
			if (m.dead)
			{
				roomMobs.remove(m);
				break;
			}
		}
	}
}