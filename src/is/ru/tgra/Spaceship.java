package is.ru.tgra;

public class Spaceship extends Collidable {
	
	public float[] vertices;
	public final float speed_change = 0.2f;
	private final int heading_change = 5;
	
	public Spaceship(float x, float y)
	{
		super(new float[] { -7,-7, 7,-7, 0,15},
				x, y, 0, 0, 90, 10);
	};

	public void accelerate() {
		this.speed_x += (float) (Math.cos(Math.toRadians(this.heading)) * this.speed_change);
		if (this.speed_x > 3f)
			this.speed_x = 3f;
		this.speed_y += (float) (Math.sin(Math.toRadians(this.heading)) * this.speed_change);
		if (this.speed_y > 3f)
			this.speed_y = 3f;
	}
	
	public void stop(){
		this.speed_x = 0;
		this.speed_y = 0;
	}
	
	public void turnRight(){
		this.heading = (this.heading - heading_change) % 360;
	}
	
	public void turnLeft(){
		this.heading = (this.heading + heading_change) % 360;
	}
	
	public Lazor fire(){
		return new Lazor(this.poly.getX(), this.poly.getY(), this.speed_x, this.speed_y, this.heading, 1);
	}
}
