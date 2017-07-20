package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import vehicleParts.*; 

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	
	//TODO camera control, sprite over car, menu

	final float PPM = 50f; //pixels per meter, might rejig this into a constants class. 
	SpriteBatch batch;
	private OrthographicCamera camera;
	private World world;
	
	private Box2DDebugRenderer debugCamera;
	private Texture img; 
	private Sprite sprite;
	
	private BasicChassis car;


	@Override
	public void create () {
		float w=Gdx.graphics.getWidth();
		float h=Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		img=new Texture("car.png");
		sprite=new Sprite(img);


		camera= new OrthographicCamera();
		camera.setToOrtho(false, w/2, h/2);
		//Set up Camera, used later to track car across the map

		world=new World(new Vector2(0,0), false);
		//World for Box2d objects to interact

		debugCamera=new Box2DDebugRenderer();
		Gdx.input.setInputProcessor(this);
		
		
				
		car=new BasicChassis(world, new Vector2(100,100), 2f, 4.5f);
		
		

	}

	@Override
	public void render () {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		debugCamera.render(world, camera.combined);
		update();


	}

	public void update(){
		//used in render to handle game logic
		//Debug camera for 
		car.update();		
		camera.update();

		//Tire Physics Testing Block
		

	}	

	@Override 
	public void resize(int width, int height){

	}

	public void createCar(BasicChassis car){
		//TODO takes in the basic chassis, constructs the bodies and combines them to form a car
	}


	@Override
	public void dispose () {
		world.dispose();
		batch.dispose();		
		debugCamera.dispose();
	}


	@Override
	public boolean keyDown(int keycode){
		if (keycode==Input.Keys.UP){
			car.forward=true;
			car.backward=false;
		}else if (keycode==Input.Keys.DOWN){
			car.backward=true;
			car.forward=false;
			
		}else if(keycode==Input.Keys.LEFT){
			car.left=true;
			car.right=false;
		}else if(keycode==Input.Keys.RIGHT){
			car.right=true;
			car.left=false;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode==Input.Keys.UP){
			car.forward=false;
		}else if (keycode==Input.Keys.DOWN){
			car.backward=false;
		}else if(keycode==Input.Keys.LEFT){
			car.left=false;
		}else if(keycode==Input.Keys.RIGHT){
			car.right=false;
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
