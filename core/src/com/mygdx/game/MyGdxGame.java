package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import vehicleParts.*; 

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	//TODO  menu
	//TODO root out the cause of jittery max velocity

	final float PPM = 2f; //pixels per meter, might rejig this into a constants class. 

	private SpriteBatch batch;
	private ShapeRenderer shapeColor;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugCamera;
	private World world;	

	private Texture img; 
	private Sprite sprite;

	private Texture backgroundTexture;

	private BasicCar car;


	@Override
	public void create () {
		float w=Gdx.graphics.getWidth();
		float h=Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		shapeColor=new ShapeRenderer();

		camera= new OrthographicCamera();
		camera.setToOrtho(false, w/2, h/2);
		//Set up Camera, used later to track car across the map

		world=new World(new Vector2(0,0), false);
		//World for Box2d objects to interact

		debugCamera=new Box2DDebugRenderer();
		Gdx.input.setInputProcessor(this);
		camera.zoom=0.3f;

		//Make a carFactory to generate cars via menu
		car=new TownCar(world, new Vector2(180,120));
		img=car.getCarImage();
		sprite=new Sprite(img);

		backgroundTexture=new Texture("crudeBackground.png");

	}

	@Override
	public void render () {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);


		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		debugCamera.render(world, camera.combined);
		update();





		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(backgroundTexture, 0, 0, 800, 600);

		sprite.setSize(car.getWidth()*PPM, car.getLength()*PPM);

		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(car.getChassis().getPosition().x-sprite.getWidth()/2, car.getChassis().getPosition().y-sprite.getHeight()/2);
		sprite.setRotation((float) (Math.toDegrees(car.getChassis().getAngle())));
		sprite.draw(batch);
		batch.end();


		shapeColor.setProjectionMatrix(camera.combined);
		shapeColor.begin(ShapeType.Filled);
		shapeColor.setColor(new Color(0f,0f,0f, 0f));
		//TODO color all the tires

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

	public void update(){
		//used in render to handle game logic
		//Debug camera for 
		car.update();
		
		basicCamera();
		//lerpCamera();
		camera.update();
	}

	public void basicCamera(){
		//Janky direct hover over the top, removes the jerkiness tho
		Vector2 cameraPosition= new Vector2(-camera.position.x, -camera.position.y);
		camera.translate(car.getChassis().getWorldCenter().add(cameraPosition));


	}


	
	public void lerpCamera(){
		Vector3 cameraPosition=camera.position;
		
		cameraPosition.x=camera.position.x+(car.getChassis().getWorldCenter().x-camera.position.x)*0.1f;
		cameraPosition.y=camera.position.y+(car.getChassis().getWorldCenter().y-camera.position.y)*0.1f;
		
		camera.position.set(cameraPosition);
		
		
	}

	@Override 
	public void resize(int width, int height){

	}


	@Override
	public void dispose () {
		world.dispose();
		batch.dispose();		
		debugCamera.dispose();
		shapeColor.dispose();
	}


	@Override
	public boolean keyDown(int keycode){
		if (keycode==Input.Keys.UP){
			car.forward=true;
			car.backward=false;
		}else if (keycode==Input.Keys.DOWN){
			car.backward=true;
			car.forward=false;
		}

		if(keycode==Input.Keys.LEFT){
			car.left=true;
			car.right=false;
		}else if(keycode==Input.Keys.RIGHT){
			car.right=true;
			car.left=false;
		}
		
		if(keycode==Input.Keys.SPACE){
			car.drift();
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode==Input.Keys.UP){
			car.forward=false;
		}else if (keycode==Input.Keys.DOWN){
			car.backward=false;
		}
		
		
		if(keycode==Input.Keys.LEFT){
			car.left=false;
		}else if(keycode==Input.Keys.RIGHT){
			car.right=false;
		}
		
		if(keycode==Input.Keys.SPACE){
			car.unDrift();
		}
		return true;
	}




	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub

		return true;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}




	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
