package davidchen;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Dungeon extends JPanel implements ActionListener 
{
	Room[] RoomOrder;
	ArrayList<Room> room = new ArrayList<Room>();
	
	ArrayList<ArrayList<Mob>> roomMobs = new ArrayList<ArrayList<Mob>>();
	ArrayList<Mob> roomMob0, roomMob1, roomMob2, roomMob3, roomMob4, roomMob5, roomMob6;
	
	Platform[] room1Platforms, room2Platforms, room3Platforms, room4Platforms, room5Platforms, room6Platforms, room7Platforms; 
	
	BufferedImage playerSpriteSheet;
	ArrayList<Image> playerWalkingLeft, playerWalkingRight, playerAttackingLeft, playerAttackingRight, playerAttackingUpLeft, playerAttackingDownLeft, playerAttackingUpRight, playerAttackingDownRight;

	Player player;
	Timer t;
	
	public Image background, floor, wall, platform, bomb;
	public BufferedImage enemy;
	int roomCounter, counter, ticksSinceAttackStart, ticksSinceBombDropped, ticksSinceAttacked;
	
	int ticks;

	public Dungeon()
	{
		/*
		 * IMAGE LOADING CODE
		 */
		try
		{
			floor = ImageIO.read(new File("../res/background_2.png"));
			wall = ImageIO.read(new File("../res/wall.png"));
			platform = ImageIO.read(new File("../res/platform.png"));
			background = ImageIO.read(new File("../res/background_1.png"));
			bomb = Driver.tk.createImage("../res/bomb.gif"); //toolkit usage is needed for proper .gif animation
			enemy = ImageIO.read(new File("../res/bird_final.png"));
		}
		catch(IOException e)
		{
			
		}
		//Miscellaneous stuff
		RoomOrder = new Room[5];
		t = new Timer(9, this);
		
		playerWalkingLeft = new ArrayList<Image>();
		playerWalkingRight = new ArrayList<Image>();
		playerAttackingLeft = new ArrayList<Image>();
		playerAttackingUpLeft = new ArrayList<Image>();
		playerAttackingDownLeft = new ArrayList<Image>();
		playerAttackingRight = new ArrayList<Image>();
		playerAttackingUpRight = new ArrayList<Image>();
		playerAttackingDownRight = new ArrayList<Image>();
		
		try {
			playerSpriteSheet = ImageIO.read(new File("../res/player.png"));
			for (int i = 0; i < 2; i++) {
				playerWalkingRight.add(playerSpriteSheet.getSubimage(60*i, 0, 60, 47).getScaledInstance(120, 80, Image.SCALE_DEFAULT));
				playerWalkingLeft.add(playerSpriteSheet.getSubimage(60*i, 59, 60, 47).getScaledInstance(120, 80, Image.SCALE_DEFAULT));
			}				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player = new Player(0, 700, 100, 100, 10, this);
		
		room1Platforms = new Platform[3];
		room2Platforms = new Platform[3];
		room3Platforms = new Platform[4];
		room4Platforms = new Platform[5];
		room5Platforms = new Platform[5];
		room6Platforms = new Platform[3];
		room7Platforms = new Platform[5];
		
		roomMob0 = new ArrayList<Mob>();
		roomMob1 = new ArrayList<Mob>();
		roomMob2 = new ArrayList<Mob>();
		roomMob3 = new ArrayList<Mob>();
		roomMob4 = new ArrayList<Mob>();
		roomMob5 = new ArrayList<Mob>();
		roomMob6 = new ArrayList<Mob>();
		
		
		//Sets up each room's mobs, although they have no mobs in each array list
		roomMobs.add(roomMob0);
		roomMobs.add(roomMob1);
		roomMobs.add(roomMob2);
		roomMobs.add(roomMob3);
		roomMobs.add(roomMob4);
		roomMobs.add(roomMob5);
		roomMobs.add(roomMob6);
		
		//room1 platforms
		room1Platforms[0] = new Platform(50, 600, 250, 20);
		room1Platforms[1] = new Platform(400, 400, 400, 20);
		room1Platforms[2] = new Platform(900, 200, 250, 20);

		//room2 platforms

		room2Platforms[0] = new Platform(50, 300, 400, 20);
		room2Platforms[1] = new Platform(500, 550, 200, 20);
		room2Platforms[2] = new Platform(750, 300, 400, 20);

		//room3 platforms

		room3Platforms[0] = new Platform(50, 600, 100, 20);
		room3Platforms[1] = new Platform(350, 400, 500, 20);
		room3Platforms[2] = new Platform(450, 250, 300, 20);
		room3Platforms[3] = new Platform(1050, 600, 100, 20);

		//room4 platforms

		room4Platforms[0] = new Platform(75, 575, 250, 20);
		room4Platforms[1] = new Platform(75, 350, 350, 20);
		room4Platforms[2] = new Platform(955, 575, 250, 20);
		room4Platforms[3] = new Platform(855, 350, 350, 20);
		room4Platforms[4] = new Platform(125, 150, 1030, 20);

		//room5 platforms

		room5Platforms[0] = new Platform(150, 200, 150, 20);
		room5Platforms[1] = new Platform(150, 600, 150, 20);
		room5Platforms[2] = new Platform(565, 400, 150, 20);
		room5Platforms[3] = new Platform(980, 600, 150, 20);
		room5Platforms[4] = new Platform(980, 200, 150, 20);
		
		//room6 platforms

		room6Platforms[0] = new Platform(980, 600, 250, 20);
		room6Platforms[1] = new Platform(480, 400, 400, 20);
		room6Platforms[2] = new Platform(130, 200, 250, 20);

		//room7 platforms

		room7Platforms[0] = new Platform(150, 200, 150, 20);
		room7Platforms[1] = new Platform(150, 600, 150, 20);
		room7Platforms[2] = new Platform(565, 400, 150, 20);
		room7Platforms[3] = new Platform(980, 600, 150, 20);
		room7Platforms[4] = new Platform(980, 200, 150, 20);
		
		//testing room to determine cause of room speed differences
		/*Platform[] testRoom = new Platform[10];
		testRoom[0] = new Platform(0, 0, 0, 0);
		testRoom[1] = new Platform(0, 0, 0, 0);
		testRoom[2] = new Platform(0, 0, 0, 0);
		testRoom[3] = new Platform(0, 0, 0, 0);
		testRoom[4] = new Platform(0, 0, 0, 0);
		testRoom[5] = new Platform(0, 0, 0, 0);
		testRoom[6] = new Platform(0, 0, 0, 0);
		testRoom[7] = new Platform(0, 0, 0, 0);
		testRoom[8] = new Platform(0, 0, 0, 0);
		testRoom[9] = new Platform(0, 0, 0, 0);*/
	}
	public void generate() 
	{
		//Clears all rooms; there would be three rooms left
		room.clear();
		
		//Clears Mobs; no mobs in any room
		for (ArrayList<Mob> roomMobs : roomMobs)
			roomMobs.clear();
		
		//Generating Mobs; different number of Mobs and random locations
		for (int i = 0; i < 7; i++)
		{
			int numOfMobs = Driver.RNG.nextInt(5) + 3;
			System.out.println(numOfMobs);
			for (int x = 0; x < numOfMobs; x++)
			{
				Mob mob = new Mob(Driver.RNG.nextInt(1280), Driver.RNG.nextInt(800), 100, 100, 2);
				roomMobs.get(i).add(mob);
			}
		}
		
		//Generating Pool of Rooms; new mobs though room platforms are the same
		room.add(new Room(room1Platforms, roomMobs.get(0)));
		room.add(new Room(room2Platforms, roomMobs.get(1)));
		room.add(new Room(room3Platforms, roomMobs.get(2)));
		room.add(new Room(room4Platforms, roomMobs.get(3)));
		room.add(new Room(room5Platforms, roomMobs.get(4)));
		room.add(new Room(room6Platforms, roomMobs.get(5)));
		room.add(new Room(room7Platforms, roomMobs.get(6)));

		//Generating Rooms; five rooms are selected from pool and removed from list of array list to prevent duplicates
		RoomOrder[0] = room.remove(Driver.RNG.nextInt(7));
		RoomOrder[1] = room.remove(Driver.RNG.nextInt(6));
		RoomOrder[2] = room.remove(Driver.RNG.nextInt(5));
		RoomOrder[3] = room.remove(Driver.RNG.nextInt(4));
		RoomOrder[4] = room.remove(Driver.RNG.nextInt(3));//new Room(testRoom, new ArrayList<Mob>(0))
		
		//Reset Player Location; sets X and Y to default values
		player.setX(0);
		player.setY(700);
		
		//Reset Player Dungeon Location; sets room number back to zero
		roomCounter = 0;
		
		setFocusable(true); //lets key listener work
		this.addKeyListener(player.getKeyAdapter()); //gets key adapter for player
		t.start(); //starts the base game timer
		
		YakEngine.start(this); //starts YakEngine.EngineTimer with this as a listener
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == t)
		{
			ticks++;
			
			if (counter % 2 == 0) //Movement of player and mobs
			{
				player.move();
				repaint();
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
				if (player.isAttackingUp())
					for (Mob mob: RoomOrder[roomCounter].roomMobs) {
						if (player.attackUp().intersects(mob) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
					}
				else if (player.isAttackingDown())
					for (Mob mob: RoomOrder[roomCounter].roomMobs) {
						if (player.attackDown().intersects(mob) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
					}
				else if (player.isAttackingLeft())
					for (Mob mob: RoomOrder[roomCounter].roomMobs) {
						if (player.attackLeft().intersects(mob) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
					}
				else if (player.isAttackingRight())
					for (Mob mob: RoomOrder[roomCounter].roomMobs) {
						if (player.attackRight().intersects(mob) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
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
			}
			
			if (player.bombDropped)
			{
				int checkCounter = roomCounter;
				ticksSinceBombDropped++;					
				for (Mob mob : RoomOrder[roomCounter].roomMobs)
					if (player.bomb.intersects(mob) && getTicksSinceBombDropped() == 100)
					{
						mob.lowerHealth(4);		
					}
				if (checkCounter != roomCounter)
				{
					player.bombDropped = false;
					player.BombDamage = false;
					ticksSinceBombDropped = 0;
				}
				if (ticksSinceBombDropped > 100) 
				{ 
					YakEngine.createSystem(player.bomb.x+125, player.bomb.y+125, 7f, 2);
					player.bombDropped = false;
					player.BombDamage = true;
					bomb = Driver.tk.createImage("../res/bomb.gif");
					ticksSinceBombDropped = 0;
				}
			}
			for (Mob mob : RoomOrder[roomCounter].roomMobs)
				if (player.intersects(mob))
					ticksSinceAttacked++;
			/*
			 * Checks for 'safe frame' period
			 * The player gains 40 ticks of invincibility after every hit
			 */
			if (ticksSinceAttacked > 40) 
			{
				player.lowerHealth(1);
				ticksSinceAttacked = 0;
			}			
			RoomOrder[roomCounter].update(); //updates current room to remove dead enemies
		}
		
		if (e.getSource() == YakEngine.EngineTimer)
			YakEngine.act();
		
		System.out.println("PLAYER HEALTH: " + player.health);
		
		if (player.dead)
		{
			t.stop();
			YakEngine.EngineTimer.stop();
		}
		repaint();
		counter++;
	}
	
	
	public int getTicksSinceStart() {
		return ticksSinceAttackStart;
	}
	public int getTicksSinceBombDropped() {
		return ticksSinceBombDropped;
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		setBackground(Color.BLACK);
		g2.drawImage(background, 0, 0, 1280, 1024, this);
	
		RoomOrder[roomCounter].draw(g);
		//g2.drawString("" + x.velX, 0, 20);
		//g2.drawString("" + x.velY, 0, 20);
		
		g2.setColor(Color.PINK);
		player.draw(g2);

		g2.setColor(Color.BLUE);
		if (player.isAttackingUp())
			g2.draw((Shape)player.attackUp());
		else if (player.isAttackingDown())
			g2.draw((Shape)player.attackDown());
		else if (player.isAttackingLeft())
			g2.draw((Shape)player.attackLeft());
		else if (player.isAttackingRight())
			g2.draw((Shape)player.attackRight());

		g2.setColor(Color.BLUE);
		if (player.bombDropped)
			g.drawImage(bomb, player.bomb.x + 125, player.bomb.y + 125, this);

		YakEngine.draw(g);
	}
	
	public void pauseResume()
	{
		if (t.isRunning())
			t.stop();
		else
			t.start();
	}
}
