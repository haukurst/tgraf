package is.ru.tgra;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.BufferUtils;

public class Asteroids implements ApplicationListener
{
	private FloatBuffer vertexBuffer = null;
	private Spaceship deathstar;
	private ArrayList<Lazor> shots;
	private ArrayList<LargeMeteor> largeMeteors;
	private ArrayList<SmallMeteor> smallMeteors;
	private Random rn;
	private boolean renderShip = true;
	private long timeDead;

	@Override
	public void create()
	{
		this.rn = new Random();
		this.deathstar = new Spaceship(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		this.shots = new ArrayList<Lazor>();
		this.largeMeteors = new ArrayList<LargeMeteor>();
		this.largeMeteors.add(new LargeMeteor(20, 20, 1.0f, 1.0f, this.rn.nextInt(360), 30));
		this.smallMeteors = new ArrayList<SmallMeteor>();
		this.smallMeteors.add(new SmallMeteor(100, 100, 1.0f, 1.0f, this.rn.nextInt(360), 15));
		
		this.vertexBuffer = BufferUtils.newFloatBuffer(30);
		
		for(float f : this.deathstar.getVertices())
		{
			this.vertexBuffer.put(f);
		}
		
		for(float f : new LargeMeteor().getVertices())
		{
			this.vertexBuffer.put(f);
		}
		
		for(float f : new SmallMeteor().getVertices())
		{
			this.vertexBuffer.put(f);
		}
		
		this.vertexBuffer.rewind();
		
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, this.vertexBuffer);
        
        // Enable vertex array.
        Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        
        // Select clear color for the screen.
        Gdx.gl11.glClearColor(.3f, .3f, .3f, 1f);
		
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}
	
	private void display()
	{
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
        Gdx.gl11.glLoadIdentity();
        Gdx.gl11.glColor4f(1f, 1f, 1f, 1f);
        
        
        if(this.renderShip == true){
        	this.renderShip();
        }
        else{
        	if(System.currentTimeMillis() - this.timeDead < 1000){
        		this.smashedShip();
        	}
        	
        }
        
        this.renderShots();
        this.renderLargeMeteors();
        this.renderSmallMeteors();
	}
	
	private void smashedShip(){
		Gdx.gl11.glLoadIdentity();
		Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(this.deathstar.getX(), this.deathstar.getY(), 0);
        Gdx.gl11.glDrawArrays(GL11.GL_LINES, 6, 6);
        Gdx.gl11.glPopMatrix();
	}
	
	private void renderShip(){
		//Render Spaceship
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(this.deathstar.getX(), this.deathstar.getY(), 0);
        Gdx.gl11.glRotatef(this.deathstar.getHeading(), 0, 0, 1);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 3);
        Gdx.gl11.glPopMatrix();
	}
	
	private void renderSmallMeteors(){
		SmallMeteor m;
		for(int i = 0; i < this.smallMeteors.size();){
			
			m = this.smallMeteors.get(i);
			m.update();
			
			if(Intersector.overlapConvexPolygons(this.deathstar.getPoly(), m.getPoly())){
				System.out.println("Small boom!");
			}
			
			Gdx.gl11.glLoadIdentity();
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(m.getX(), m.getY(), 0);
            Gdx.gl11.glRotatef(m.getHeading()/2, 0, 0, 1);
            Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, 6, 6);
            Gdx.gl11.glPopMatrix();
            
            i++;
		}
	}
	
	private void renderLargeMeteors(){
		LargeMeteor m;
		for(int i =  0; i < this.largeMeteors.size();){
			
    		m = this.largeMeteors.get(i);
    		m.update();
    		
    		if(Intersector.overlapConvexPolygons(this.deathstar.getPoly(), m.getPoly())){
    			System.out.println("Boom!");
    			this.renderShip = false;
    			this.timeDead = System.currentTimeMillis();
    		}
    		
    		
    		Gdx.gl11.glLoadIdentity();
    		Gdx.gl11.glPushMatrix();
            Gdx.gl11.glTranslatef(m.getX(), m.getY(), 0);
            Gdx.gl11.glRotatef(m.getHeading()/2, 0, 0, 1);
    		//Gdx.gl11.glScalef(m.getSize()/3, m.getSize()/3, 0);
            Gdx.gl11.glDrawArrays(GL11.GL_LINE_LOOP, 3, 6);
            Gdx.gl11.glPopMatrix();
            
            i++;
       	}
	}
	
	private void renderShots()
	{
		LargeMeteor m;
		SmallMeteor sm;
		Lazor s;
        for(int i = 0; i < shots.size();){
        	s = shots.get(i);
        	if(System.currentTimeMillis() - s.getAge() > 1500)
        		shots.remove(i);
        	else{
        		s.updatePos();
        		
        		for(int j = 0; j < this.largeMeteors.size();){
        			m = this.largeMeteors.get(j);
        			
        			if(Intersector.overlapConvexPolygons(s.getPoly(), m.getPoly())){
        				System.out.println("Derp!");
        				
        				this.largeMeteors.remove(m);
        				this.shots.remove(s);
        				
        				this.smallMeteors.add(new SmallMeteor(m.getX(), m.getY(), 1.0f, 1.0f,
        									  this.rn.nextInt(360), 15));
        				this.smallMeteors.add(new SmallMeteor(m.getX(), m.getY(), 0.3f, 0.3f,
        									  this.rn.nextInt(360), 15));
        			}
        			
        			j++;
        		}
        		
        		for(int k = 0; k < this.smallMeteors.size();){
        			sm = this.smallMeteors.get(k);
        			
        			if(sm.getPoly().contains(s.getX(), s.getY())){
        				System.out.println("hnjeee");
        				
        				this.smallMeteors.remove(sm);
        				this.shots.remove(s);
        			}
        			
        			/*if(Intersector.overlapConvexPolygons(s.getPoly(), sm.getPoly())){
        				System.out.println("smuuuuuuu");
        				
        				this.smallMeteors.remove(sm);
        				this.shots.remove(s);
        			}*/
        			
        			k++;
        		}
        		
        		Gdx.gl11.glLoadIdentity();
        		Gdx.gl11.glPushMatrix();
                Gdx.gl11.glTranslatef(s.getX(), s.getY(), 0);
                Gdx.gl11.glDrawArrays(GL11.GL_POINTS, 0, 1);
                Gdx.gl11.glPopMatrix();

        		i++;
        	}

        }
	}
	
	private void update()
	{
		//Arrowkeys
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
        {
			this.deathstar.turnRight(); 
        }
        
        if(Gdx.input.isKeyPressed(Keys.LEFT))
        {
        	this.deathstar.turnLeft();
        }
        
        if(Gdx.input.isKeyPressed(Keys.UP))
        {
        	this.deathstar.accelerate();
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN))
        {
        	this.deathstar.stop();
        }
        
        //fire
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
        	shots.add(deathstar.fire());
        }
        
        this.deathstar.updatePos();
	}

	@Override
	public void render() 
	{
		this.update();
		this.display();
	}

	@Override
	public void resize(int width, int height)
	{
		// Load the Project matrix. Next commands will be applied on that matrix.
        Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
        Gdx.gl11.glLoadIdentity();

        // Set up a two-dimensional orthographic viewing region.
        Gdx.glu.gluOrtho2D(Gdx.gl11, 0, width, 0, height);

        // Set up affine transformation of x and y from world coordinates to window coordinates
        Gdx.gl11.glViewport(0, 0, width, height);

        // Set the Modelview matrix back.
        Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);
		
	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub
		
	}

}
