package davidchen;

import javax.swing.*;

import java.util.ArrayList;
	@SuppressWarnings("serial")
	public class Driver extends JApplet {
		static ArrayList<Mob> mobs;
		static ArrayList<Upgrade> upgrades;
		static Player player;
		static volatile boolean BombDamage;
		static volatile int ticksSinceAttackStart, ticksSinceBombDropped;
		
		Timer t;
		//DrawingPanel DrawingPanel;
		ArrayList<Platform> platforms;
		int counter;
		public void init() {
			Dungeon dungeon = new Dungeon(this);
			setSize(1280, 1024);
		}
		/*public void init() {
			DrawingPanel = new DrawingPanel();
			setContentPane(DrawingPanel);
			platforms = new ArrayList<Platform>();
			platforms.add(new Platform(300, 500, 200, 30));
			t = new Timer(9, this);
			mobs = new ArrayList<Mob>();
			mobs.add(new Mob(0, 0, 100, 100, 5));
			mobs.add(new Mob(1180, 824, 100, 100, 5));
			upgrades = new ArrayList<Upgrade>();
			//player = new Player(590, 500, 100, 100, this);
			this.addKeyListener(player.getKeyAdapter());
			setSize(1280, 1024);
			setFocusable(true);
			t.start();
			System.out.println(platforms.size());
		}
		public void actionPerformed(ActionEvent e) {
			Object a = e.getSource();
			if (a == t) {
				if (counter % 2 == 0) {
					player.move();
					mobs.get(0).move(player);
					mobs.get(1).move(player);
				}
				
				if (player.isAttacking) {
					ticksSinceAttackStart++;
				}
				
				if (player.bombDropped) {
					ticksSinceBombDropped++;
					System.out.println(ticksSinceBombDropped);
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
					for (Mob mob: Driver.mobs) {
						if (Driver.player.attackUp().intersects(mob) && (Driver.getTicksSinceStart() == 10)) {
							mob.lowerHealth();
						}
					}
				if (player.isAttackingDown())
					for (Mob mob: Driver.mobs) {
						if (Driver.player.attackDown().intersects(mob) && (Driver.getTicksSinceStart() == 10)) {
							mob.lowerHealth();
						}
					}
				if (player.isAttackingLeft())
					for (Mob mob: Driver.mobs) {
						if (Driver.player.attackLeft().intersects(mob) && (Driver.getTicksSinceStart() == 10)) {
							mob.lowerHealth();
						}
					}
				if (player.isAttackingRight())
					for (Mob mob: Driver.mobs) {
						if (Driver.player.attackRight().intersects(mob) && (Driver.getTicksSinceStart() == 10)) {
							mob.lowerHealth();
						}
					}
				
				if (player.bombDropped)
					for (Mob mob : Driver.mobs)
						if (player.bomb.intersects(mob) && Driver.getTicksSinceBombDropped() == 100)
							mob.lowerHealth();		
				repaint();
				counter++;
			}
		}
		public static int getTicksSinceStart() {
			return ticksSinceAttackStart;
		}
		public static int getTicksSinceBombDropped() {
			return ticksSinceBombDropped;
		}
		public class DrawingPanel extends JPanel {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				super.paintComponent(g);
				
				g2.setColor(Color.GREEN);
				for (Mob mob : mobs)
					g2.fill((Shape)mob);
				
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
				for (Platform platform : platforms)
					g2.fill((Shape)platform);
				
				g2.setColor(Color.BLUE);
				if (player.bombDropped)
					g2.fill((Shape)player.bomb);
			}
		}*/
	}

