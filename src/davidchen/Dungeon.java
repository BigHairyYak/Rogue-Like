package davidchen;

import java.applet.AudioClip;
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
	Platform[] room0Platforms, room1Platforms, room2Platforms, room3Platforms, room4Platforms, room5Platforms, room6Platforms, room7Platforms; 
	BufferedImage playerSpriteSheet;
	AudioClip bossTheme;
	Player player;
	Timer t;
	
	boolean bossBattle = false;
	
	public Image background, floor, openDoor, closedDoor, wall, platform, bomb, wasd, arrowkeys, spacebar;
	public Image boss1, boss2, boss3;
	public BufferedImage enemy, bossEnemy, healthBar;
	int roomCounter, counter, ticksSinceAttackStart, ticksSinceBombDropped, ticksSinceAttacked, ticks;
	
	public Dungeon()
	{
		/*
		 * IMAGE LOADING CODE
		 */
		try
		{	
			floor = ImageIO.read(new FileInputStream(new File("../resources/background_2.png")));
			wall = ImageIO.read(new FileInputStream(new File("../resources/wall.png")));
			platform = ImageIO.read(new FileInputStream(new File("../resources/platform.png")));
			background = ImageIO.read(new File("../resources/background_1.png"));
			bomb = Driver.tk.createImage("../resources/bomb.gif"); //toolkit usage is needed for proper .gif animation
			enemy = ImageIO.read(new File("../resources/bird_final.png"));
			bossEnemy = ImageIO.read(new File("../resources/bossBird.png"));
			boss1 = ImageIO.read(new File("../resources/skull.png"));
			boss2 = ImageIO.read(new File("../resources/skull_open.png"));
			boss3 = ImageIO.read(new File("../resources/skull_open_bigger.png"));
			healthBar = ImageIO.read(new FileInputStream(new File("../resources/health.PNG")));
			playerSpriteSheet = ImageIO.read(new File("../resources/player.png"));
			wasd = ImageIO.read(new File("../resources/wasd.png"));
			arrowkeys = ImageIO.read(new File("../resources/arrowkeys.png"));
			spacebar = ImageIO.read(new File("../resources/spacebar.png"));
		}
		catch(IOException e)
		{
			
		}

		System.out.println("BOSS ENEMY: " + bossEnemy);
		//System.exit(0);
		//Miscellaneous stuff
		RoomOrder = new Room[7];
		t = new Timer(9, this);
		
		//Driver.normalTheme.play();
		
		player = new Player(0, 700, 54, 64, 10, this);
		
		room0Platforms = new Platform[1];
		room1Platforms = new Platform[3];
		room2Platforms = new Platform[3];
		room3Platforms = new Platform[4];
		room4Platforms = new Platform[3];
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
		
		
		room0Platforms[0] = new Platform(0, 0, 1280, 20);
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
		room3Platforms[1] = new Platform(390, 400, 500, 20);
		room3Platforms[2] = new Platform(490, 250, 300, 20);
		room3Platforms[3] = new Platform(1050, 600, 100, 20);

		//room4 platforms
		room4Platforms[0] = new Platform(75, 425, 350, 20);
		room4Platforms[1] = new Platform(855, 425, 350, 20);
		room4Platforms[2] = new Platform(125, 225, 1030, 20);

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
	}
	public void generate() 
	{
		//Clears all rooms; there would be three rooms left
		room.clear();
		
		ticks = 0;
		player.health = 20;
		
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
		RoomOrder[0] = new Room(room0Platforms, new ArrayList<Mob>());
		RoomOrder[1] = room.remove(Driver.RNG.nextInt(7));
		RoomOrder[2] = room.remove(Driver.RNG.nextInt(6));
		RoomOrder[3] = room.remove(Driver.RNG.nextInt(5));
		RoomOrder[4] = room.remove(Driver.RNG.nextInt(4));
		RoomOrder[5] = room.remove(Driver.RNG.nextInt(3));//new Room(testRoom, new ArrayList<Mob>(0))
		
		RoomOrder[6] = new Room(room1Platforms, new ArrayList<Mob>());
		RoomOrder[6].roomMobs.add(new Boss(430, 150, 160, 200, 5000 /*this number is irrelevant*/, RoomOrder[5]));
		
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
				//for (Mob mob : RoomOrder[roomCounter].roomMobs)
					//mob.move(player);
				/*
				 * SWITCHED FROM FOR EACH LOOP TO REGULAR FOR LOOP
				 * This prevents a ConcurrentModificationException
				 * from being thrown by the boss's spawn() method
				 */
				for (int q = 0; q < RoomOrder[roomCounter].roomMobs.size(); q++)
				{
					RoomOrder[roomCounter].roomMobs.get(q).move(player);
				}
				if (player.x < 0 && roomCounter > 0) 
				{
					roomCounter--;
					player.x = 1280;
				}
				else if (player.x > 1280 && roomCounter < 6) 
				{
					roomCounter++;
					YakEngine.clear();
					player.x = 0;
				}
				
				if (roomCounter == 5 && !bossBattle) //last room
				{
					bossBattle = true;
					Driver.normalTheme.stop();
					Driver.bossTheme.play();
				}
			}
			
			if (player.isAttacking) 
			{
				ticksSinceAttackStart++;
				if (player.isAttackingUp)
					for (Mob mob: RoomOrder[roomCounter].roomMobs)
					{
						if (player.attackUp().intersects(mob.getBounds()) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
					}
				else if (player.isAttackingDown)
					for (Mob mob: RoomOrder[roomCounter].roomMobs) 
					{
						if (player.attackDown().intersects(mob.getBounds()) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
					}
				else if (player.isAttackingLeft)
					for (Mob mob: RoomOrder[roomCounter].roomMobs)
					{
						if (player.attackLeft().intersects(mob.getBounds()) && (getTicksSinceStart() == 10)) {
							mob.lowerHealth(1);
						}
					}
				else if (player.isAttackingRight)
					for (Mob mob: RoomOrder[roomCounter].roomMobs)
					{
						if (player.attackRight().intersects(mob.getBounds()) && (getTicksSinceStart() == 10)) {
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
					bomb = Driver.tk.createImage("../resources/bomb.gif"); //resets Bomb gif
					ticksSinceBombDropped = 0;
				}
			}
			for (Mob mob : RoomOrder[roomCounter].roomMobs)
				if (player.intersects(mob.getBounds()))
					ticksSinceAttacked++;
			/*
			 * Checks for 'safe frame' period
			 * The player gains 80 ticks of invincibility after every hit
			 */
			if (ticksSinceAttacked > 80) 
			{
				player.lowerHealth(1);
				ticksSinceAttacked = 0;
				Driver.hurt.play();
			}			
			RoomOrder[roomCounter].update(); //updates current room to remove dead enemies
			if (RoomOrder[5].roomMobs.size() == 0)
				Driver.gameEnded = true;
		}
		
		if (e.getSource() == YakEngine.EngineTimer)
			YakEngine.act();
		
		//System.out.println("PLAYER HEALTH: " + player.health);
		
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
		
		g2.drawImage(background, 0, 0, 1280, 1024, this);
	
		RoomOrder[roomCounter].draw(g2);
		//g2.drawString("" + x.velX, 0, 20);
		//g2.drawString("" + x.velY, 0, 20);
	
		g2.draw(player);
		player.draw(g2);
		g2.drawLine(0, 800, 1024, 800);
		
		g.setFont(g.getFont().deriveFont(18f));
		
		if (roomCounter == 0) {
			g.drawString("Movement", 255, 300);
			g.drawString("Attack", 575, 300);
			g.drawString("Use Item", 900, 300);
			g2.drawImage(wasd, 200, 325, 200, 150, this);
			g2.drawImage(arrowkeys, 500, 300, 200, 200, this);
			g2.drawImage(spacebar, 800, 400, 300, 50, this);
		}
		
		if (player.isAttackingUp)
			g2.draw((Shape)player.attackUp());
		else if (player.isAttackingDown)
			g2.draw((Shape)player.attackDown());
		else if (player.isAttackingLeft)
			g2.draw((Shape)player.attackLeft());
		else if (player.isAttackingRight)
			g2.draw((Shape)player.attackRight());

		//System.out.println(healthBar);
		g2.drawImage(healthBar.getSubimage(0, (int)(17 * (player.health/2)), 184, 14), 20, 750, 368, 34, this);
		
		if (player.bombDropped)
			g2.drawImage(bomb, player.bomb.x + 125, player.bomb.y + 125, this);
		
		g.setFont(g.getFont().deriveFont(18f));
		g.setColor(Color.ORANGE);
		g.drawString("USE WASD TO MOVE!   -   USE THE ARROW KEYS TO SWING YOUR SWORD!   -   USE SPACE TO DROP BOMBS! - BOMBS DON'T HURT YOU!", 20, 930);
		
		g.setFont(g.getFont().deriveFont(40f));
		g2.drawString("" + Math.round(((double)ticks)/90.00 * 100.0)/100.0 + "s", 550, 800);
		
		YakEngine.draw(g2);
	}
	
	public void pauseResume()
	{
		if (t.isRunning())
			t.stop();
		else
			t.start();
	}
}
