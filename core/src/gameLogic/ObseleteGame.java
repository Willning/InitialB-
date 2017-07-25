package gameLogic;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import vehicleParts.*;
import vehicleParts.CarFactory.CarList; 

public class ObseleteGame extends Game {
	//OBSELETE AND ONLY USEFUL FOR MILD DOCUMENTATION PURPOSES

	//TODO make the input controller another class, for the sake of clarity
	

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
	private Texture backgroundTexture;	
	private BasicCar car;
	
	private boolean setStraight=false;


	@Override
	public void create () {
		float w=Gdx.graphics.getWidth();
		float h=Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		shapeColor=new ShapeRenderer();

		camera= new OrthographicCamera();
		camera.setToOrtho(false, w/2, h/2);		
		debugCamera=new Box2DDebugRenderer();		
		camera.zoom=0.3f;
		//Camera Setup
		
		world=new World(new Vector2(0,0), false);
		//World for Box2d objects to interact


		controller=new InputController();
		Gdx.input.setInputProcessor(controller);

		CarFactory factory=CarFactory.getInstance();
		factory.setParams(world, new Vector2(180,120));
		car=factory.makeCar(CarList.TOWNCAR);

		img=car.getCarImage();
		sprite=new Sprite(img);		
		
		backgroundTexture=new Texture("crudeBackground.png");		

	}

	@Override
	public void render () {
		//move all of this into GameScreen to get changeable screens.
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);		

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		debugCamera.render(world, camera.combined);
		update();

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, 800, 600);
		//Remove this later or something

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
		
		if (!setStraight){
			//Straighten car out here 
			car.SetAngle(0);
			setStraight=true;
		}
		
		useHandler();
		car.update();
		
		lerpCamera();
		camera.update();
	}

	public void basicCamera(){
		//Janky direct hover over the top, removes the jerkiness tho
		Vector2 cameraPosition= new Vector2(-camera.position.x, -camera.position.y);
		camera.translate(car.getChassis().getWorldCenter().add(cameraPosition));


	}


	public void lerpCamera(){
		Vector3 cameraPosition=camera.position;

		cameraPosition.x=camera.position.x+(car.getChassis().getWorldCenter().x-camera.position.x)*0.05f;
		cameraPosition.y=camera.position.y+(car.getChassis().getWorldCenter().y-camera.position.y)*0.051f;

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

}
