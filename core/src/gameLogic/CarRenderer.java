package gameLogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import vehicleParts.BasicCar;

public class CarRenderer {

	final float PPM = 2f;  
	private ShapeRenderer shapeColor;
	private SpriteBatch batch;
	private BasicCar car;
	private Texture img; 
	private Sprite sprite;	


	public CarRenderer( BasicCar car, SpriteBatch batch){
		//This class is responsible for rendering the car, both sprite and tires
		shapeColor=new ShapeRenderer();
		this.batch=batch;
		
		img=car.getCarImage();
		//This may be changed to an asset class that will load all assets at the start		
		sprite=new Sprite(img);
	}

	public void renderCar(OrthographicCamera camera, BasicCar car){
		shapeColor.setProjectionMatrix(camera.combined);
		this.car=car;
		
		renderTires();
		renderBody();
	}

	public void renderBody(){
		batch.begin();		
		sprite.setSize(car.getWidth()*PPM, car.getLength()*PPM);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(car.getChassis().getPosition().x-sprite.getWidth()/2, car.getChassis().getPosition().y-sprite.getHeight()/2);
		sprite.setRotation((float) (Math.toDegrees(car.getChassis().getAngle())));
		sprite.draw(batch);
		batch.end();	
	}

	public void renderTires(){
		shapeColor.begin(ShapeType.Filled);
		shapeColor.setColor(new Color(0f,0f,0f, 0f));		
		for (int i=0;i<4;i++){
			if (car.returnTire(i)!=null){
				float width=0.5f;
				float height=1f;

				//fix up positioning, then we are done
				float x= car.returnTire(i).getTire().getPosition().x-width/2;				
				float y = car.returnTire(i).getTire().getPosition().y-height/2;

				float originX=width/2;
				float originY=height/2;				
				float degrees=(float) Math.toDegrees(car.returnTire(i).getTire().getAngle());

				shapeColor.rect(x, y, originX, originY, width, height, 1, 1, degrees);
			}
		}		
		shapeColor.end(); 
	}

}
