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

import gameLogic.GameManager;
import gameLogic.InputController;
import vehicleParts.BasicCar;
import vehicleParts.CarFactory;
import vehicleParts.CarFactory.CarList;

public class GameScreen implements Screen {
	//TODO offload rendering to another class for better ENCAPSULATION

	private final Game game;


	final float PPM = 2f; //pixels per meter, might rejig this into a constants class. 

	public SpriteBatch batch;
	public BitmapFont font;

	private ShapeRenderer shapeColor;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugCamera;
	private World world;
	private InputController controller;
	private Texture img; 
	private Sprite sprite;	
	private BasicCar car;

	private Texture backgroundTexture;	

	private boolean setStraight=false;

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
		camera.zoom=0.3f;
		//setup the camera;

		world=new World(new Vector2(0,0), false);
		//World for Box2d objects to interact


		controller=new InputController();
		Gdx.input.setInputProcessor(controller);
		//setup the controller

		CarFactory factory=CarFactory.getInstance();
		factory.setParams(world, new Vector2(180,120));
		
		this.car=factory.makeCar(setCar);
		//load the car from the factory, the input will be controlled by the main menu later on 

		img=car.getCarImage();
		sprite=new Sprite(img);

		backgroundTexture=new Texture("crudeBackGround.png");
		//load this in with a track factory later
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Blank out the screen

		//push off all rendering duties to a World Renderer class later on;

		update();

		debugCamera.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
		shapeColor.setProjectionMatrix(camera.combined);

		//render order should be render background, render tires, render car.		
		renderBackground();
		renderTires();
		renderCar();

	}
	
	public void renderBackground(){
		batch.begin();
		batch.draw(backgroundTexture, 0, 0);
		
		batch.end();
	}


	public void renderCar(){
		batch.begin();
		//This is going to be a method, renderCar();
		
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

		cameraPosition.x=camera.position.x+(car.getChassis().getWorldCenter().x-camera.position.x)*0.05f;
		cameraPosition.y=camera.position.y+(car.getChassis().getWorldCenter().y-camera.position.y)*0.051f;
		camera.position.set(cameraPosition);
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
