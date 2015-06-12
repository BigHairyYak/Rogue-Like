package davidchen;

import java.util.*;
import java.awt.*;

public class Room
{
	Platform[] roomPlatform;
	ArrayList<Mob> roomMob = new ArrayList<Mob>();
	//Image roomImage;
	Player playerCharacter;
	Graphics2D g2;

	public Room(Platform[] platforms, ArrayList<Mob> mobs,/* Image image, */Player player)
	{
		roomMob = mobs;
		roomPlatform = platforms;
		//roomImage = image;
		playerCharacter = player;
	}

	public void draw(Graphics g)
	{
		g2 = (Graphics2D) g;
		g2.setColor(Color.GRAY);
		for (Platform platform : roomPlatform)
			g2.fill((Shape)platform);
		g2.setColor(Color.RED);
		for (int i = 0; i < roomMob.size(); i++)
			g2.fill((Shape)roomMob.get(i));
		g2.setColor(Color.BLUE);
		g2.fill((Shape)playerCharacter);
	}
}