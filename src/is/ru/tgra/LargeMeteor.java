package is.ru.tgra;

public class LargeMeteor extends Collidable {

	public LargeMeteor(){
		super(new float[]{-20,60, 40,60, 80,0, 40,-60, -20,-60, -60,0},0,0,0,0,0,0);
		this.size = 0;
	}
	
	public LargeMeteor(float x, float y, float speed_x, float speed_y, int heading, int size) {
		super(new float[]{-20,60, 40,60, 80,0, 40,-60, -20,-60, -60,0}, x, y, 
				(float) (Math.cos(Math.toRadians(heading)) * speed_x),
				(float) (Math.sin(Math.toRadians(heading)) * speed_y),
				heading, size);
	}
	
	public void update() {
		this.updatePos();
		this.heading -= 2;
	}

}
