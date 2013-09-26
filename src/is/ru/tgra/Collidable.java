package is.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;

public class Collidable {
	protected Polygon poly;
	protected float speed_x, speed_y;
	protected int heading;
	protected int size;
	
	public Collidable(float[] vertices, float x, float y, float speed_x, float speed_y, int heading, int size)
	{
		poly = new Polygon(vertices);
		poly.setPosition(x,y);
		poly.setOrigin(0, 0);
		
		this.speed_x = speed_x;
		this.speed_y = speed_y;
		this.heading = heading;
		this.size = size;
	}
	
	public Polygon getPoly(){
		return this.poly;
	}
	
	public float getSize() {
		return (float)size;
	}
	
	public float getX() {
		return this.poly.getX();
	}
	
	public float getY(){
		return this.poly.getY();
	}
	
	public void setPos(float x, float y){
		this.poly.setPosition(x, y);
	}
	
	public int getHeading(){
		return this.heading - 90;
	}
	
	public float[] getVertices(){
		return this.poly.getVertices();
	}
	
	public void updatePos(){
		poly.setPosition(this.getX() + this.speed_x, this.getY() + this.speed_y);
		
		if(this.getY() > Gdx.graphics.getHeight() + this.size){
        	this.poly.setPosition(poly.getX(), -this.size);
        }
        if(this.getY() < -this.size){
        	this.poly.setPosition(poly.getX(), Gdx.graphics.getHeight() + this.size);
        }
        if(this.getX() > Gdx.graphics.getWidth() + this.size)
        {
        	this.poly.setPosition(-this.size, poly.getY());
        }
        if(this.getX() < -this.size)
        {
        	this.poly.setPosition(Gdx.graphics.getWidth() + this.size, poly.getY());
        }
	}
}
