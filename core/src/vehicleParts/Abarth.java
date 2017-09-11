package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Abarth extends FrontWheelDriveCar{

	public Abarth(World world, Vector2 location) {
		super(world, location);
		maxTurnAngle=25;
		sprite = new Texture("./Assets/Cars/Abarth.png"); 
	}
	
	@Override
	public void setPositions(){
		//Override based on new tirePositions
		//sets the positions of the tires
		//Closer widthwise and lengthwise, do later
		tirePositions[0]=new Vector2(1.6f,-2f);// Rear
		tirePositions[1]=new Vector2(-1.6f,-2f); 

		tirePositions[2]=new Vector2(1.6f,2f); //Fronts
		tirePositions[3]=new Vector2(-1.6f,2f);
	}
	
	public void setDriveForce(){
		driveForce=1500f;
	}
	
	public void setMaxSpeed(){
		maxSpeed=60f;
	}
	
	public void setMaxGrip(){
		maxGrip=15f;
	}
	
	public void setSize(){
		length=3.7f;
		width=1.7f;
	}

}
