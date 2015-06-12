package davidchen;

@SuppressWarnings("serial")
public class Upgrade extends Entity {
	String s;
	public Upgrade(int x, int y, int width, int height, String s) {
		super(x, y, width, height);
		this.s = s;
	}
	public int getUpgrade() {
		if (s.equalsIgnoreCase("Bomb"))
			return 1;
		else if (s.equalsIgnoreCase("Fire"))
			return 2;
		else
			return 0;
	}
}
