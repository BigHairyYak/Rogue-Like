package davidchen;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Dungeon extends JPanel implements ActionListener {
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
	Platform plat1A, plat1B, plat1C;
	Platform[] room2Platforms = new Platform[3];
	Platform plat2A, plat2B, plat2C;
	Platform[] room3Platforms = new Platform[4];
	Platform plat3A, plat3B, plat3C, plat3D;
	Platform[] room4Platforms = new Platform[5];
	Platform plat4A, plat4B, plat4C, plat4D, plat4E;
	Platform[] room5Platforms = new Platform[5];
	Platform plat5A, plat5B, plat5C, plat5D, plat5E;
	Platform[] room6Platforms = new Platform[3];
	Platform plat6A, plat6B, plat6C;
	Platform[] room7Platforms = new Platform[5];
	Platform plat7A, plat7B, plat7C, plat7D, plat7E;
	Random r;
	Player player;
	Timer t;
	int roomCounter, counter, ticksSinceAttackStart, ticksSinceBombDropped;
	Random ran = new Random();

	public Dungeon(JApplet j) {
		j.setContentPane(this);

		r = new Random();
		
		roomMobs.add(roomMob0);
		roomMobs.add(roomMob1);
		roomMobs.add(roomMob2);
		roomMobs.add(roomMob3);
		roomMobs.add(roomMob4);
		roomMobs.add(roomMob5);
		roomMobs.add(roomMob6);
		
		for (int i = 0; i < 7; i++) {
			int numOfMobs = r.nextInt(3) + 3;
			System.out.println(numOfMobs);
			for (int x = 0; x < numOfMobs; x++) {
				Mob mob = new Mob(r.nextInt(1280), r.nextInt(1024), 100, 100, 2);
				roomMobs.get(i).add(mob);
			}
		}
		
		player = new Player(590, 500, 100, 100, this);
		
		RoomOrder = new Room[5];

		//room1

		plat1A = new Platform(50, 600, 250, 50);
		plat1B = new Platform(400, 400, 400, 50);
		plat1C = new Platform(900, 200, 250, 50);
		room1Platforms[0] = plat1A; room1Platforms[1] = plat1B; room1Platforms[2] = plat1C;

		//room2

		plat2A = new Platform(50, 300, 400, 50);
		plat2B = new Platform(500, 550, 200, 50);
		plat2C = new Platform(750, 300, 400, 50);
		room2Platforms[0] = plat2A; room2Platforms[1] = plat2B; room2Platforms[2] = plat2C;

		//room3

		plat3A = new Platform(50, 600, 100, 50);
		plat3B = new Platform(350, 400, 500, 50);
		plat3C = new Platform(450, 250, 300, 50);
		plat3D = new Platform(1050, 600, 100, 50);
		room3Platforms[0] = plat3A; room3Platforms[1] = plat3B; room3Platforms[2] = plat3C; room3Platforms[3] = plat3D;

		//room4

		plat4A = new Platform(75, 575, 250, 50);
		plat4B = new Platform(75, 350, 350, 50);
		plat4C = new Platform(955, 575, 250, 50);
		plat4D = new Platform(855, 350, 350, 50);
		plat4E = new Platform(125, 150, 1030, 50);
		room4Platforms[0] = plat4A; room4Platforms[1] = plat4B; room4Platforms[2] = plat4C; room4Platforms[3] = plat4D; room4Platforms[4] = plat4E;

		//room5

		plat5A = new Platform(150, 200, 150, 50);
		plat5B = new Platform(150, 600, 150, 50);
		plat5C = new Platform(565, 400, 150, 50);
		plat5D = new Platform(980, 600, 150, 50);
		plat5E = new Platform(980, 200, 150, 50);
		room5Platforms[0] = plat5A; room5Platforms[1] = plat5B; room5Platforms[2] = plat5C; room5Platforms[3] = plat5D; room5Platforms[4] = plat5E;

		//room6

		plat6A = new Platform(980, 600, 250, 50);
		plat6B = new Platform(480, 400, 400, 50);
		plat6C = new Platform(130, 200, 250, 50);
		room6Platforms[0] = plat6A; room6Platforms[1] = plat6B; room6Platforms[2] = plat6C;

		//room7

		plat7A = new Platform(150, 200, 150, 50);
		plat7B = new Platform(150, 600, 150, 50);
		plat7C = new Platform(565, 400, 150, 50);
		plat7D = new Platform(980, 600, 150, 50);
		plat7E = new Platform(980, 200, 150, 50);
		room7Platforms[0] = plat7A; room7Platforms[1] = plat7B; room7Platforms[2] = plat7C; room7Platforms[3] = plat7D; room7Platforms[4] = plat7E;
		


		room.add(new Room(room1Platforms, roomMobs.get(0), player));
		room.add(new Room(room2Platforms, roomMobs.get(1), player));
		room.add(new Room(room3Platforms, roomMobs.get(2), player));
		room.add(new Room(room4Platforms, roomMobs.get(3), player));
		room.add(new Room(room5Platforms, roomMobs.get(4), player));
		room.add(new Room(room6Platforms, roomMobs.get(5), player));
		room.add(new Room(room7Platforms, roomMobs.get(6), player));


		t = new Timer(9, this);

		RoomOrder[0] = room.remove(r.nextInt(7));
		RoomOrder[1] = room.remove(r.nextInt(6));
		RoomOrder[2] = room.remove(r.nextInt(5));
		RoomOrder[3] = room.remove(r.nextInt(4));
		RoomOrder[4] = room.remove(r.nextInt(3));
		
		setFocusable(true);
		this.addKeyListener(player.getKeyAdapter());
		t.start();
	}
	public void actionPerformed(ActionEvent e) {
		Object a = e.getSource();
		if (a == t) {
			if (counter % 2 == 0) {
				player.move();
				for (Mob mob : RoomOrder[roomCounter].roomMob)
					mob.move(player);
			if (player.x < 0 && roomCounter > 0) {
					roomCounter--;
					player.x = 1280;
				}
			else if (player.x > 1280 && roomCounter < 4) {
					roomCounter++;
					player.x = 0;
				}
			}
			
			if (player.health < 1)
				System.out.println("game over");
			
			if (player.isAttacking) {
				ticksSinceAttackStart++;
			}
			
			if (player.bombDropped) {
				ticksSinceBombDropped++;
			}
			
			if (ticksSinceBombDropped > 100) { 
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
				for (Mob mob: RoomOrder[roomCounter].roomMob) {
					if (player.attackUp().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth();
					}
				}
			if (player.isAttackingDown())
				for (Mob mob: RoomOrder[roomCounter].roomMob) {
					if (player.attackDown().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth();
					}
				}
			if (player.isAttackingLeft())
				for (Mob mob: RoomOrder[roomCounter].roomMob) {
					if (player.attackLeft().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth();
					}
				}
			if (player.isAttackingRight())
				for (Mob mob: RoomOrder[roomCounter].roomMob) {
					if (player.attackRight().intersects(mob) && (getTicksSinceStart() == 10)) {
						mob.lowerHealth();
					}
				}
			
			if (player.bombDropped)
				for (Mob mob : RoomOrder[roomCounter].roomMob)
					if (player.bomb.intersects(mob) && getTicksSinceBombDropped() == 100)
						mob.lowerHealth();		
			repaint();
			counter++;
		}
	}
	public int getTicksSinceStart() {
		return ticksSinceAttackStart;
	}
	public int getTicksSinceBombDropped() {
		return ticksSinceBombDropped;
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
		RoomOrder[roomCounter].draw(g);
		//g2.drawString("" + x.velX, 0, 20);
		//g2.drawString("" + x.velY, 0, 20);
		
		
		g2.setColor(Color.BLUE);
		if (player.isAttackingUp())
			g2.fill((Shape)player.attackUp());
		else if (player.isAttackingDown())
			g2.fill((Shape)player.attackDown());
		else if (player.isAttackingLeft())
			g2.fill((Shape)player.attackLeft());
		else if (player.isAttackingRight())
			g2.fill((Shape)player.attackRight());
		
		g2.setColor(Color.BLACK);
		g2.fill((Shape)player);
		
		g2.setColor(Color.ORANGE);
		for (Platform platform : RoomOrder[roomCounter].roomPlatform)
			g2.fill((Shape)platform);
		
		g2.setColor(Color.BLUE);
		if (player.bombDropped)
			g2.fill((Shape)player.bomb);
	}
}
