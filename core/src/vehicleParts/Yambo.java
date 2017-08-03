package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Yambo extends FourWheelDriveCar{

	public Yambo(World world, Vector2 location) {
		super(world, location);
		length=5f;
		width=2f;		
		sprite=new Texture("yambo.png");
		maxTurnAngle=30;
	}
	
	@Override
	public void setPositions(){
		//Override based on new tirePositions
		//sets the positions of the tires
		//Closer widthwise and lengthwise, do later
		tirePositions[0]=new Vector2(2f,-2.5f);// Rear
		tirePositions[1]=new Vector2(-2f,-2.5f); 

		tirePositions[2]=new Vector2(2f,2.8f); //Fronts
		tirePositions[3]=new Vector2(-2f,2.8f);
	}
	
	
	public void setDriveForce(){
		driveForce=2000f;
	}
	
	public void setMaxSpeed(){
		maxSpeed=120f;
	}
	
	public void setMaxGrip(){
		maxGrip=25f;
	}

}
