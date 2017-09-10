package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class EcoLancer extends FourWheelDriveCar {

	public EcoLancer(World world, Vector2 location) {
		super(world, location);
		sprite=new Texture("./Assets/ecoLancer.png");
		maxTurnAngle=35;
	}

	public void setDriveForce(){
		driveForce=1500f;
	}
	
	public void setMaxSpeed(){
		maxSpeed=50f;
	}
	
	public void setMaxGrip(){
		maxGrip=18f;
	}
	


}
