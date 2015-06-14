package davidchen;

import javax.swing.*;

import java.util.ArrayList;
	@SuppressWarnings("serial")
	public class Driver extends JApplet
	{
		/*static ArrayList<Mob> mobs;
		static ArrayList<Upgrade> upgrades;
		static Player player;
		static volatile boolean BombDamage;*/

		ArrayList<Platform> platforms;
		int counter;
		public void init() 
		{
			Dungeon dungeon = new Dungeon();
			dungeon.generate();
			setContentPane(dungeon);
			setSize(1280, 1024);
		}
	}

