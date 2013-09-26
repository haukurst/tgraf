package is.ru.tgra;

public class SmallMeteor extends Collidable {
	
	public SmallMeteor(){
		super(new float[]{-10,30, 10,30, 20,0, 10,-30, -10,-30, -20,0},0,0,0,0,0,0);
		this.size = 0;
	}

	public SmallMeteor(float x, float y, float speed_x, float speed_y, int heading, int size) 
	{
		super(new float[]{-10,30, 10,30, 20,0, 10,-30, -10,-30, -20,0}, x, y, 
				(float) (Math.cos(Math.toRadians(heading)) * speed_x),
				(float) (Math.sin(Math.toRadians(heading)) * speed_y), 
				heading, size);

	}
	
	public void update(){
		this.updatePos();
		this.heading += 2;
	}

}
