package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import gameLogic.CarRenderer;
import gameLogic.GameManager;
import gameLogic.InputController;
import gameLogic.ListenerClass;
import overlays.Overlay;
import trackStuff.BasicTrack;
import vehicleParts.BasicCar;
import vehicleParts.CarFactory;
import vehicleParts.CarFactory.CarList;

public class GameScreen implements Screen {
	//TODO add another class responsible for an overlay UI Speedometer etc.
	//Add torque curves and perhaps even shifting?

	private final Game game;

	final float PPM = 2f; //pixels per meter, might rejig this into a constants class. 

	public SpriteBatch batch;
	public BitmapFont font;

	private ShapeRenderer shapeColor;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugCamera;
	private World world;
	private InputController controller;
	private BasicCar car;
	private Overlay overlay;
	private BasicTrack track;
	
	private CarRenderer renderer;
	//Trialing something here
	
	private Texture backgroundTexture;
	//Not neccesary here

	private boolean setStraight=false;
	
	private float currentZoom=0.15f;

	public GameScreen(final Game game, CarList setCar){
		this.game=game;
		
		GameManager superGame =(GameManager)game;

		batch = superGame.batcher;
		shapeColor=new ShapeRenderer();

		float w=Gdx.graphics.getWidth();
		float h=Gdx.graphics.getHeight();

		camera= new OrthographicCamera();
		camera.setToOrtho(false, w/2, h/2);		
		debugCamera=new Box2DDebugRenderer();		
		camera.zoom=currentZoom;
		//setup the camera;
		
		ListenerClass listener=new ListenerClass();

		world=new World(new Vector2(0,0), false);
		world.setContactListener(listener);
		
		controller=new InputController();
		Gdx.input.setInputProcessor(controller);
		//setup the controller

		CarFactory factory=CarFactory.getInstance();
		factory.setParams(world, new Vector2(40,20));		
		this.car=factory.makeCar(setCar);
		//load the car from the factory, the input will be controlled by through the constructor which will take in a carList enum value from the choose car menu
		track=new BasicTrack(world);
		
		overlay=new Overlay(car, batch,track);		
		renderer=new CarRenderer(car,batch);
		//Rendering of cars has now been pushed off onto another class, this means that we can potentially render two or more cars 
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(96/255f, 142/255f, 66/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Blank out the screen

		//push off all rendering duties to a World Renderer class later on;

		update();

		debugCamera.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
		shapeColor.setProjectionMatrix(camera.combined);

		//render order should be render background, render tires, render car.		
		renderBackground();
		
		renderer.renderCar(camera, car);
		
		//renderer gets passed in the camera position and the 
		
		overlay.update();

	}
	
	public void renderBackground(){
		track.render(camera);
	}



	public void update(){
		//used in render to handle game logic

		if (!setStraight){
			//Straighten car out here, not 100% neccesary but maybe use for resetting?
			car.SetAngle(0);
			setStraight=true;
		}

		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		useHandler();
		car.update();
		lerpCamera();

	}

	public void lerpCamera(){
		Vector3 cameraPosition=camera.position;
		float MaxZoom=0.28f;		
		float MinZoom=0.15f;				

		cameraPosition.x=camera.position.x+(car.getChassis().getWorldCenter().x-camera.position.x)*0.08f;
		cameraPosition.y=camera.position.y+(car.getChassis().getWorldCenter().y-camera.position.y)*0.08f;
		camera.position.set(cameraPosition);
		
		if (car.getChassis().getLinearVelocity().len()>20){
			if (currentZoom<MaxZoom){
				currentZoom+=0.0001f*car.getChassis().getLinearVelocity().len();
			}				
		}else{
			if(currentZoom>MinZoom){
				currentZoom-=0.0008f;
			}
		}		
		camera.zoom=currentZoom;
		camera.update();

	}

	@Override
	public void dispose () {
		world.dispose();
		batch.dispose();		
		debugCamera.dispose();
		shapeColor.dispose();		
	}

	public void useHandler(){
		car.forward=controller.up;
		car.backward=controller.down;
		car.left=controller.left;
		car.right=controller.right;

		if (controller.drift){
			car.drift();
		}else{
			car.unDrift();
		}
		
		if (controller.exit){
			game.setScreen(new MainMenuScreen(game));
		}

	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}


}
