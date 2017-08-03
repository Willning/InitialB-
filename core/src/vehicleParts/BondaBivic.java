package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BondaBivic extends FrontWheelDriveCar {

	public BondaBivic(World world, Vector2 location) {
		super(world, location);
		length=4.1f;
		width=1.7f;
		maxTurnAngle=25;
		sprite=new Texture("BondaBivic.png");
						
	}	
	
	
	@Override
	public void setPositions(){
		//Override based on new tirePositions
		//sets the positions of the tires
		//Closer widthwise and lengthwise, do later
		tirePositions[0]=new Vector2(1.6f,-2.5f);// Rear
		tirePositions[1]=new Vector2(-1.6f,-2.5f); 

		tirePositions[2]=new Vector2(1.6f,2.8f); //Fronts
		tirePositions[3]=new Vector2(-1.6f,2.8f);
	}
	
	public void setDriveForce(){
		driveForce=1000f;
	}
	
	public void setMaxSpeed(){
		maxSpeed=40f;
	}
	
	public void setMaxGrip(){
		maxGrip=15f;
	}


}
