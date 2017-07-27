package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BasicCar{
	//This class will contain and construct all the requisites of a car, i.e Engine+tires
	//TODO flick of forward force from wheels upon turning to reduce understeer


	protected float width=2;
	protected float length=4.5f;	
	protected Texture sprite;
	//Sprite attached to image

	protected Tire[] tires=new Tire[4];
	//Array of tires, change this to choose what type of tires you want

	protected Vector2[] tirePositions=new Vector2[4];
	//Array of tirePositions

	protected Vector2 driveAndBrake;
	//Vector containing the driving force and braking force of the engine


	protected int maxTurnAngle=40;
	//sets maximum lock to lock angle, by defaultits 40;
	protected int currentTurn;



	public boolean forward;
	public boolean backward;
	public boolean left;
	public boolean right;



	protected Body chassis;	
	RevoluteJointDef[] jointArray=new RevoluteJointDef[4];
	//Using this body, things will be attached to it

	public BasicCar (World world, Vector2 location){

		driveAndBrake=new Vector2(75,50);

		BodyDef def= new BodyDef();
		def.type=BodyDef.BodyType.DynamicBody;		
		def.position.set(location);

		PolygonShape shape=new PolygonShape();
		shape.setAsBox(width,length);
		//probably in future use stored length and width


		this.chassis=world.createBody(def);

		FixtureDef fixtureDef= new FixtureDef();
		fixtureDef.shape=shape;
		fixtureDef.density=1f;		
		Fixture fixture=chassis.createFixture(fixtureDef);
		shape.dispose();
		//Rectangle for body has now been constructed
		setPositions();
		fitTires(world);



		for (int i=0;i<4;i++){
			jointArray[i]=new RevoluteJointDef();
			jointArray[i].bodyA=chassis;
			jointArray[i].bodyB=tires[i].getTire();
			jointArray[i].collideConnected=false;	
			jointArray[i].enableLimit=true;

			if (tires[i].checkSteered()){
				jointArray[i].lowerAngle=-maxTurnAngle;
				jointArray[i].upperAngle=maxTurnAngle;
			}else{
				jointArray[i].lowerAngle=0;
				jointArray[i].upperAngle=0;
			}

			//Bottom will be done dependent on state

			jointArray[i].localAnchorA.set(tirePositions[i]); //define where onto the chassis it connects
			world.createJoint(jointArray[i]);
		}

	}


	public void fitTires(World world){
		//OverRide based on drive configuration
		setDriveAndBrake();

		for(int i=0;i<2;i++){
			tires[i]=new Tire(world, tirePositions[i], true, false, this); //powered but not steered
			tires[i].setForces(driveAndBrake);

		}

		for (int i=2;i<4;i++){
			tires[i]=new Tire(world, tirePositions[i], false, true, this); //steered but not powered
			tires[i].setForces(driveAndBrake);

		}

	}

	protected void setDriveAndBrake(){
		driveAndBrake=new Vector2(50,50);
	}

	public void setPositions(){
		//Override based on new tirePositions
		//sets the positions of the tires

		//change locations to be relative to the chassis

		tirePositions[0]=new Vector2(2f,-3f);// Rear
		tirePositions[1]=new Vector2(-2f,-3f); 

		tirePositions[2]=new Vector2(2f,3f); //Fronts
		tirePositions[3]=new Vector2(-2f,3f);
	}


	public void update(){		
		System.out.println(Math.round(3.6*getForwardVelocity().len()));
		
		for (int i=0;i<4;i++){
			tires[i].forward=this.forward;
			tires[i].backward=this.backward;			
			tires[i].update();
		}
		steer();		

	}

	public void steer(){	
		float angle=(float) Math.toDegrees(chassis.getAngle());		
		//Angle in which the chassis is facing

		for (int i=0;i<4;i++){
			if(tires[i].checkSteered()){
				if(left||right){
					tires[i].removeUnderSteer();
					if (currentTurn<maxTurnAngle){
						currentTurn+=5;
						//increment by 5 degrees per tick, smooths out steering
					}
					//maybe have to add a bit of power if speedmatch to get rid of horrendous understeer;

				}
				if (left){
					tires[i].SetAngle(angle+currentTurn);

				}else if(right){
					tires[i].SetAngle(angle-currentTurn);
				}else{
					currentTurn=0;
					tires[i].SetAngle(angle);
				}
			}
		}
	}

	public void drift(){

		for (int i=0;i<2;i++){
			//Slideable backs
			tires[i].drifting=true;
		}
		//TODO takes the two rear tires and lowers max load creating for mean slides;

	}

	public void unDrift(){
		for (int i=0;i<2;i++){
			//Slideable backs
			tires[i].drifting=false;
		}
	}

	public void SetAngle(float angle){
		//sets angle of tire, used for steering
		//input is in degrees
		this.chassis.setTransform(chassis.getWorldCenter(), (float) Math.toRadians(angle));
	}
	
	private Vector2 getDirection(){
		Vector2 direction=new Vector2(0,1);
		return direction.rotate((float)Math.toDegrees(this.chassis.getAngle()));
	}
	
	public Vector2 getForwardVelocity(){
		//used here to get forward motion of the car, used in speedometer and tire speed limits
		Vector2 forwardDirection=getDirection();
		Vector2 linVelocity=chassis.getLinearVelocity();

		Vector2 forwardVelocity=forwardDirection.scl(linVelocity.dot(forwardDirection)/forwardDirection.dot(forwardDirection));

		return forwardVelocity;
	}


	public Body getChassis(){
		return chassis;
	}

	public float getWidth(){
		return width;
	}

	public float getLength(){
		return length;
	}

	public Tire returnTire(int i){
		if (tires[i]!=null){
			return tires[i];
		}
		return null;
	}

	public Texture getCarImage(){
		return sprite;
	}


}
