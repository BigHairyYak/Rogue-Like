package davidchen;
import java.awt.*;
@SuppressWarnings("serial")
public class Entity extends Rectangle {
	protected double XSpeed, YSpeed;
	protected double XMultiplier, YMultiplier;
	protected int health;
	public double height;
	protected final double DEFAULT_MULTIPLIER = 1.0, DEFAULT_SPEED = 2.0;
	public Entity(int x, int y, int width, int height) { //initialize object
		super(x, y, width, height);
		this.height = height;
		setXMultiplier(DEFAULT_MULTIPLIER);
		setYMultiplier(DEFAULT_MULTIPLIER);
		setXSpeed(DEFAULT_SPEED);
		setYSpeed(DEFAULT_SPEED);
	}
	public void setXSpeed(double speed) {
		XSpeed = speed;
	}
	public void setYSpeed(double speed) {
		YSpeed = speed;
	}
	public void setXMultiplier(double multiplier) {
		XMultiplier = multiplier;
	}
	public void setYMultiplier(double multiplier) {
		YMultiplier = multiplier;
	}
	public double getHeight() {
		return height;
	}
	public double getWidth() {
		return width;
	}
	public double getYSpeed() {
		return YSpeed;
	}
	public Rectangle union(Rectangle r) {
		super.union(r);
		return this;
	}
	public void move() { //gets current location and adds speed * multiplier position **Absolute moving
		setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier))); // Player move
	}
}