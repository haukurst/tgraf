package is.ru.tgra;

public class Lazor extends Collidable{
	private static float lazorSpeed = 4;
	private long age;
	
	public Lazor(float x, float y, float shipSpeedX, float shipSpeedy, int heading, int size){
		super(new float[]{1,0, 0,1, 0,0}, 
				x+7, y+15,
				(float) (Math.cos(Math.toRadians(heading)) * lazorSpeed),
				(float) (Math.sin(Math.toRadians(heading)) * lazorSpeed),
				heading, size);
		age = System.currentTimeMillis();
	}
	
	public long getAge(){
		return age;
	}
}
