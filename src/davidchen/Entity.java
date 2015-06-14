package davidchen;
import java.awt.*;
@SuppressWarnings("serial")
public class Entity extends Rectangle {
	protected double XSpeed, YSpeed;
	protected double XMultiplier, YMultiplier;
	protected int health;
	public double height;
	protected final double DEFAULT_MULTIPLIER = 1.0, DEFAULT_SPEED = 2.0;
	boolean dead;
	public Entity(int x, int y, int width, int height) { //initialize object
		super(x, y, width, height);
		this.height = height;
		setXMultiplier(DEFAULT_MULTIPLIER);
		setYMultiplier(DEFAULT_MULTIPLIER);
		setXSpeed(DEFAULT_SPEED);
		setYSpeed(DEFAULT_SPEED);
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
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
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getXSpeed() {
		return XSpeed;
	}
	public double getYSpeed() {
		return YSpeed;
	}
	/*
	 * LOWERHEALTH
	 * Decreases the mob's health by the given increment
	 * If it drops below 0, the mob dies
	 */
	public void lowerHealth(int increment)
	{
		health -= increment;
		//System.out.println(health);
		dead = (health <= 0);
	}
	public void move() { //gets current location and adds speed * multiplier position **Absolute moving
		setLocation((int)(getBounds().x + (XSpeed * XMultiplier)), (int)(getBounds().y + (YSpeed * YMultiplier))); // Player move
	}
}