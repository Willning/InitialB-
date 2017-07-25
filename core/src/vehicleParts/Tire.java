package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Tire{
	//This class will act as a wrapper for the tire body in order to be able to do calculations on it.
	//TODO, implement inputs to control power and steering
	//TODO attach this to a chassis

	protected float MAXLOAD=30; //tiwddle with these figures later on
	protected float currentMax=MAXLOAD; //this will change depending on drifting
	protected final float DRIFTLOAD=2;

	protected float DRIVEFORCE=75f; //Maximum forward impulse velocity where I=mv
	protected float BRAKEFORCE=30f; //Default values given here, can be changed via setForces which is called by the chassis.

	protected float currentLoad;

	public boolean forward;
	public boolean backward;

	public boolean drifting;


	protected final boolean powered, steered;
	//These two booleans define if the tire is steerable and powered

	protected BasicCar chassis;
	//Here is the reference to the chassis to which this tire is attached to 

	protected Body tire;
	//Body of tire

	public Tire(World world, Vector2 location, boolean powered, boolean steered, BasicCar chassis){
		BodyDef def=new BodyDef();
		def.type=BodyDef.BodyType.DynamicBody;
		def.position.set(chassis.getChassis().getWorldPoint(location));		

		PolygonShape shape=new PolygonShape();
		shape.setAsBox(0.2f,0.5f); //Tires are all the same size. 

		this.tire=world.createBody(def);

		FixtureDef fixtureDef= new FixtureDef();
		fixtureDef.shape=shape;
		fixtureDef.density=1f;

		Fixture fixture=tire.createFixture(fixtureDef);
		shape.dispose();

		this.powered=powered;
		this.steered=steered;
		this.chassis=chassis;

	}

	public void update(){		
		driftu();
		cancelLateral();
		updateDrag();
		takeInputs();
		
	}

	public Body getTire(){
		return tire;
	}

	private void cancelLateral(){
		//uses getLateralVelocity();
		//Now use a maximum Impulse so we can get a bit of skidding. 

		Vector2 Impulse= getLateralVelocity().scl(-tire.getMass());


		if (Impulse.len()>currentMax){			
			Impulse=Impulse.scl(currentMax/Impulse.len());
		}

		tire.applyLinearImpulse(Impulse, tire.getWorldCenter(), true);
	}

	private void updateDrag(){
		//bleeds off forward velocity with force proportional to forward velocity
		//also will bleed off excessive spinning;
		tire.applyAngularImpulse(-0.1f*tire.getInertia()*tire.getAngularVelocity(), true);
		//0.1 at the start changes the angular stopping rate 

		float dragForce=0.5f*getForwardVelocity().len();
		//Numero ten at the end will change the slowDown Rate

		tire.applyForceToCenter(getForwardVelocity().scl(-dragForce), true);


	}

	private Vector2 getDirection(){

		Vector2 direction=new Vector2(0,1);
		return direction.rotate((float)Math.toDegrees(this.tire.getAngle()));

	}

	public void SetAngle(float angle){
		//sets angle of tire, used for steering
		//input is in degrees
		this.tire.setTransform(tire.getWorldCenter(), (float) Math.toRadians(angle));
	}

	private Vector2 getLateralVelocity(){
		//vector project velocity onto the normal vector

		Vector2 normalDirection = new Vector2(getDirection().rotate90(1));
		Vector2 linVelocity= tire.getLinearVelocity();

		Vector2 lateralVelocity=normalDirection.scl((linVelocity.dot(normalDirection)/(normalDirection.dot(normalDirection))));
		//project linVelocity onto normalDirection to get lateral Velocity				
		return lateralVelocity;
	}

	private Vector2 getForwardVelocity(){
		//used here to get forward motion of the tire
		Vector2 forwardDirection=getDirection();
		Vector2 linVelocity=tire.getLinearVelocity();

		Vector2 forwardVelocity=forwardDirection.scl(linVelocity.dot(forwardDirection)/forwardDirection.dot(forwardDirection));

		return forwardVelocity;

	}

	private int speedMatch(){		
		//this is used in braking to find out if the direction of movement is the same as the direction of pointing
		//1 for same direction, 0 for still, -1 for opposite direction
		if (getForwardVelocity().equals(new Vector2(0,0))){
			return 0;
		}else if (getDirection().equals(getForwardVelocity().nor())){
			return 1;
		}else if(!getDirection().equals(getForwardVelocity().nor())){
			return -1;
		}
		return 0;
	}


	private void power() {	
		//add case that if velocity is opposite to direction, brakes.
		//i.e. when speedMatch is -1, moving opposite to direction
		
		if (speedMatch()<0){
			tire.applyLinearImpulse(getDirection().scl(tire.getMass()*BRAKEFORCE), tire.getWorldCenter(), true);

		}else{
			if (powered){				
				tire.applyLinearImpulse(getDirection().scl(tire.getMass()*(DRIVEFORCE-getForwardVelocity().len())), tire.getWorldCenter(), true);						
			}
		}

	}

	private void reverse(){
		//fix this so it applies an actual braking to stop, pauses a bit then reverses
		//brake if speedMatch is 1, i.e. moving forward, opposite of reversing

		if (speedMatch()>0){
			tire.applyLinearImpulse(getDirection().scl(tire.getMass()*-BRAKEFORCE), tire.getWorldCenter(), true);

		}else{
			if(powered){
				tire.applyLinearImpulse(getDirection().scl(tire.getMass()*(-DRIVEFORCE/2.5f+getForwardVelocity().len())), tire.getWorldCenter(), true);

			}
		}

	}

	public void takeInputs(){		
		if (forward){
			power();			
		}else if(backward){
			reverse();
		}
	}

	public boolean checkSteered(){
		return steered;
	}

	public void setForces(Vector2 driveAndBrake){
		DRIVEFORCE=driveAndBrake.x;
		BRAKEFORCE=driveAndBrake.y;		
		
	}

	private void driftu(){		

		if(drifting){
			currentMax=DRIFTLOAD;			

		}else{
			currentMax=MAXLOAD;

		}
	}
}
