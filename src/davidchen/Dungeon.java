package davidchen;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Dungeon extends JPanel implements ActionListener 
{
	Room[] RoomOrder;
	ArrayList<Room> room = new ArrayList<Room>();
	ArrayList<Mob>roomMob0 = new ArrayList<Mob>();
	ArrayList<Mob>roomMob1 = new ArrayList<Mob>();
	ArrayList<Mob>roomMob2 = new ArrayList<Mob>();
	ArrayList<Mob>roomMob3 = new ArrayList<Mob>();
	ArrayList<Mob>roomMob4 = new ArrayList<Mob>();
	ArrayList<Mob>roomMob5 = new ArrayList<Mob>();
	ArrayList<Mob>roomMob6 = new ArrayList<Mob>();
	
	ArrayList<ArrayList<Mob>> roomMobs = new ArrayList<ArrayList<Mob>>();
	
	Platform[] room1Platforms = new Platform[3];
	Platform[] room2Platforms = new Platform[3];
	Platform[] room3Platforms = new Platform[4];
	Platform[] room4Platforms = new Platform[5];
	Platform[] room5Platforms = new Platform[5];
	Platform[] room6Platforms = new Platform[3];
	Platform[] room7Platforms = new Platform[5];
	Random r = new Random();
	static Player player;
	Timer t;
	int roomCounter, counter, ticksSinceAttackStart, ticksSinceBombDropped;

	public Dungeon() 
	{
		
		roomMobs.add(roomMob0);
		roomMobs.add(roomMob1);
		roomMobs.add(roomMob2);
		roomMobs.add(roomMob3);
		roomMobs.add(roomMob4);
		roomMobs.add(roomMob5);
		roomMobs.add(roomMob6);
		
		for (int i = 0; i < 7; i++)
		{
			int numOfMobs = r.nextInt(5) + 3;
			System.out.println(numOfMobs);
			for (int x = 0; x < numOfMobs; x++)
			{
				Mob mob = new Mob(r.nextInt(1280), r.nextInt(1024), 100, 100, 2);
				roomMobs.get(i).add(mob);
			}
		}
		
		player = new Player(590, 500, 100, 100, this);
		
		RoomOrder = new Room[5];

		//room1

		room1Platforms[0] = new Platform(50, 600, 250, 50);
		room1Platforms[1] = new Platform(400, 400, 400, 50);
		room1Platforms[2] = new Platform(900, 200, 250, 50);

		//room2

		room2Platforms[0] = new Platform(50, 300, 400, 50);
		room2Platforms[1] = new Platform(500, 550, 200, 50);
		room2Platforms[2] = new Platform(750, 300, 400, 50);

		//room3

		room3Platforms[0] = new Platform(50, 600, 100, 50);
		room3Platforms[1] = new Platform(350, 400, 500, 50);
		room3Platforms[2] = new Platform(450, 250, 300, 50);
		room3Platforms[3] = new Platform(1050, 600, 100, 50);

		//room4

		room4Platforms[0] = new Platform(75, 575, 250, 50);
		room4Platforms[1] = new Platform(75, 350, 350, 50);
		room4Platforms[2] = new Platform(955, 575, 250, 50);
		room4Platforms[3] = new Platform(855, 350, 350, 50);
		room4Platforms[4] = new Platform(125, 150, 1030, 50);

		//room5

		room5Platforms[0] = new Platform(150, 200, 150, 50);
		room5Platforms[1] = new Platform(150, 600, 150, 50);
		room5Platforms[2] = new Platform(565, 400, 150, 50);
		room5Platforms[3] = new Platform(980, 600, 150, 50);
		room5Platforms[4] = new Platform(980, 200, 150, 50);
		
		//room6

		room6Platforms[0] = new Platform(980, 600, 250, 50);
		room6Platforms[1] = new Platform(480, 400, 400, 50);
		room6Platforms[2] = new Platform(130, 200, 250, 50);

		//room7

		room7Platforms[0] = new Platform(150, 200, 150, 50);
		room7Platforms[1] = new Platform(150, 600, 150, 50);
		room7Platforms[2] = new Platform(565, 400, 150, 50);
		room7Platforms[3] = new Platform(980, 600, 150, 50);
		room7Platforms[4] = new Platform(980, 200, 150, 50);
		
		//testing room to determine cause of room speed differences
		Platform[] testRoom = new Platform[10];
		testRoom[0] = new Platform(0, 0, 0, 0);
		testRoom[1] = new Platform(0, 0, 0, 0);
		testRoom[2] = new Platform(0, 0, 0, 0);
		testRoom[3] = new Platform(0, 0, 0, 0);
		testRoom[4] = new Platform(0, 0, 0, 0);
		testRoom[5] = new Platform(0, 0, 0, 0);
		testRoom[6] = new Platform(0, 0, 0, 0);
		testRoom[7] = new Platform(0, 0, 0, 0);
		testRoom[8] = new Platform(0, 0, 0, 0);
		testRoom[9] = new Platform(0, 0, 0, 0);

		room.add(new Room(room1Platforms, roomMobs.get(0)));
		room.add(new Room(room2Platforms, roomMobs.get(1)));
		room.add(new Room(room3Platforms, roomMobs.get(2)));
		room.add(new Room(room4Platforms, roomMobs.get(3)));
		room.add(new Room(room5Platforms, roomMobs.get(4)));
		room.add(new Room(room6Platforms, roomMobs.get(5)));
		room.add(new Room(room7Platforms, roomMobs.get(6)));

		RoomOrder[0] = room.remove(r.nextInt(7));
		RoomOrder[1] = room.remove(r.nextInt(6));
		RoomOrder[2] = room.remove(r.nextInt(5));
		RoomOrder[3] = room.remove(r.nextInt(4));
		RoomOrder[4] = new Room(testRoom, new ArrayList<Mob>(0));//room.remove(r.nextInt(3));
		//RoomOrder[5] = new Room(testRoom, new ArrayList<Mob>(0));
		
		setFocusable(true);
		this.addKeyListener(player.getKeyAdapter());
		
		t = new Timer(9, this);
		t.start(); //starts the base game timer
		
		YakEngine.start(this); //starts YakEngine.EngineTimer with this as a listener
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == t)
		{
			if (counter % 2 == 0) 
			{
				player.move();
				for (Mob mob : RoomOrder[roomCounter].roomMobs)
					mob.move(player);
				if (player.x < 0 && roomCounter > 0) 
				{
					roomCounter--;
					player.x = 1280;
				}
				else if (player.x > 1280 && roomCounter < 4) 
				{
					roomCounter++;
					player.x = 0;
				}
			}
			
			if (player.isAttacking) 
			{
				ticksSinceAttackStart++;
			}
			
			if (player.bombDropped)
			{
				ticksSinceBombDropped++;
			}
			
			if (ticksSinceBombDropped > 100) 
			{ 
				player.bombDropped = false;
				player.BombDamage = true;
				ticksSinceBombDropped = 0;
			}
			
			if (ticksSinceAttackStart > 25)
			{
				player.isAttacking = false;
				player.isAttackingLeft = false;
				player.isAttackingRight = false;
				player.isAttackingUp = false;
				player.isAttackingDown = false;
				ticksSinceAttackStart = 0;
			}
			
			if (player.isAttackingUp())
				for (Mob mob: RoomOrder[roomCounter].roomMobs) {
					if (player.attackUp().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth(1);
					}
				}
			if (player.isAttackingDown())
				for (Mob mob: RoomOrder[roomCounter].roomMobs) {
					if (player.attackDown().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth(1);
					}
				}
			if (player.isAttackingLeft())
				for (Mob mob: RoomOrder[roomCounter].roomMobs) {
					if (player.attackLeft().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth(1);
					}
				}
			if (player.isAttackingRight())
				for (Mob mob: RoomOrder[roomCounter].roomMobs) {
					if (player.attackRight().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth(1);
					}
				}
			
			if (player.bombDropped)
			{
				if (getTicksSinceBombDropped() == 100)
					YakEngine.createSystem(player.bomb.x+125, player.bomb.y+125, 7f, 2);
				for (Mob mob : RoomOrder[roomCounter].roomMobs)
					if (player.bomb.intersects(mob) && getTicksSinceBombDropped() == 100)
					{
						mob.lowerHealth(4);		
					}
			}
			
			RoomOrder[roomCounter].update(); //updates current room to remove dead enemies
		}
		
		if (e.getSource() == YakEngine.EngineTimer)
			YakEngine.act();
		
		repaint();
		counter++;
	}
	
	
	public int getTicksSinceStart() {
		return ticksSinceAttackStart;
	}
	public int getTicksSinceBombDropped() {
		return ticksSinceBombDropped;
	}
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		RoomOrder[roomCounter].draw(g);
		//g2.drawString("" + x.velX, 0, 20);
		//g2.drawString("" + x.velY, 0, 20);
		
		setBackground(Color.BLACK);
		
		g2.setColor(Color.BLUE);
		if (player.isAttackingUp())
			g2.draw((Shape)player.attackUp());
		else if (player.isAttackingDown())
			g2.draw((Shape)player.attackDown());
		else if (player.isAttackingLeft())
			g2.draw((Shape)player.attackLeft());
		else if (player.isAttackingRight())
			g2.draw((Shape)player.attackRight());
		
		/*g2.setColor(Color.BLACK);
		g2.fill((Shape)player);
		
		g2.setColor(Color.ORANGE);
		for (Platform platform : RoomOrder[roomCounter].roomPlatform)
			g2.fill((Shape)platform);*/
		
		g2.setColor(Color.BLUE);
		if (player.bombDropped)
			g2.draw((Shape)player.bomb);
		
		YakEngine.draw(g);
	}
}
