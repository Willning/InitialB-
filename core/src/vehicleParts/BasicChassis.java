package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BasicChassis{
	//This class will contain and construct all the requisites of a car, i.e Engine+tires

	protected int weight;
	protected float width;
	protected float length;	
	protected Image sprite;
	//Sprite attached to image

	protected Tire[] tires=new Tire[4];
	//Array of tires
	protected Vector2[] tirePositions=new Vector2[4];
	//Array of tirePositions
	protected int maxTurnAngle=40;
	protected int currentTurn;


	public boolean forward;
	public boolean backward;
	public boolean left;
	public boolean right;

	protected Engine engine;
	//Use this to generate power for tires in future

	protected Body chassis;	
	RevoluteJointDef[] jointArray=new RevoluteJointDef[4];
	//Using this body, things will be attached to it

	public BasicChassis (World world, Vector2 location,float width, float length){
		BodyDef def= new BodyDef();
		def.type=BodyDef.BodyType.DynamicBody;		
		def.position.set(location);	


		PolygonShape shape=new PolygonShape();
		shape.setAsBox(width,length); 


		this.chassis=world.createBody(def);

		FixtureDef fixtureDef= new FixtureDef();
		fixtureDef.shape=shape;
		fixtureDef.density=0.6f;
		Fixture fixture=chassis.createFixture(fixtureDef);
		shape.dispose();
		//Rectangle for body has now been constructed
		fitTires(world);
		setPositions();



		for (int i=0;i<4;i++){
			jointArray[i]=new RevoluteJointDef();
			jointArray[i].bodyA=chassis;
			jointArray[i].bodyB=tires[i].getTire();
			jointArray[i].collideConnected=false;	
			jointArray[i].enableLimit=true;

			if (tires[i].checkSteered()){
				jointArray[i].lowerAngle=-40;
				jointArray[i].upperAngle=40;
			}else{
				jointArray[i].lowerAngle=0;
				jointArray[i].upperAngle=0;
			}

			//Bottom will be done dependent on state

			jointArray[i].localAnchorA.set(tirePositions[i]); //define where onto the chassis it connects
			world.createJoint(jointArray[i]);
		}

		this.width=width;
		this.length=length;

	}

	private void fitTires(World world){
		for(int i=0;i<2;i++){
			tires[i]=new Tire(world, new Vector2(0,0), true, false, this); //powered but not steered

		}

		for (int i=2;i<4;i++){
			tires[i]=new Tire(world, new Vector2(0,0), false, true, this); //steered but not powered

		}

	}

	private void setPositions(){
		//sets the positions of the tires
		tirePositions[0]=new Vector2(2f,-2.25f);// Rear
		tirePositions[1]=new Vector2(-2f,-2.25f); 

		tirePositions[2]=new Vector2(2f,2.5f); //Fronts
		tirePositions[3]=new Vector2(-2f,2.5f);
	}


	public void update(){
		for (int i=0;i<4;i++){
			tires[i].forward=this.forward;
			tires[i].backward=this.backward;
			tires[i].update();
			steer();		

		}

	}

	public void steer(){
		//Used a weld joint, aesthetics will just use a sprite to mask everything, fixtures will be hidden
		float angle=(float) Math.toDegrees(chassis.getAngle());		
		//Angle in which the chassis is facing
		//TODO change this to have a gradual steer instead of a straight rip
		for (int i=0;i<4;i++){
			if(tires[i].checkSteered()){

				if(left||right){
					if (currentTurn<maxTurnAngle){
						currentTurn+=5;
					}
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

	public float getWeight(){
		//return the mass of the car for the sake of Impulse calculations
		return chassis.getMass();
	}

	public Vector2 getLocation(){
		return chassis.getWorldCenter();
	}

}
