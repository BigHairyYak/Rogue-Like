package davidchen;

public class Vector2D 
{
	public float x, y;
	public Vector2D (float locX, float locY)
	{
		x = locX; y  = locY;
	}
	
	/*
	 * ADD
	 * Adds x and y values of a given vector to this.x and this.y
	 */
	public void add(Vector2D addedVector)
	{
		x += addedVector.x;
		y += addedVector.y;
	}
	/*
	 * SUBTRACT
	 * Subtracts x and y values of a given vector from this.x and this.y
	 */
	public void subtract(Vector2D subVector)
	{
		x -= subVector.x;
		y -= subVector.y;
	}
	/*
	 * SUBTRACT1
	 * Returns the resultant vector of a subtraction instead of manipulating this
	 * - used as a workaround for BoomSystems not having proper velocities
	 */
	public Vector2D subtract1(Vector2D subVector)
	{
		return new Vector2D(x - subVector.x, y - subVector.y);
	}
	/*
	 * SET
	 * Simple setter method affecting this.x and this.y
	 */
	public void set(float X, float Y)
	{
		x = X; y = Y;
	}
	/*
	 * FACTOR(Vector2D)
	 * Multiplies this.x and this.y by the given vector's x and y, respectively
	 */
	public void factor(Vector2D factorVector)
	{
		x *= factorVector.x;
		y *= factorVector.y;
	}
	/*
	 * FACTOR(float)
	 * Multiplies this.x and this.y by the given float
	 */
	public void factor(float factor)
	{
		x *= factor;
		y *= factor;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return (x + ", " + y);
	}
}
