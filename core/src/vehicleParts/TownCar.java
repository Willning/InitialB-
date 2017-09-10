package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class TownCar extends RearWheelDriveCar{

	
	public TownCar(World world, Vector2 location) {
		super(world, location);
		sprite=new Texture("./Assets/townCar.png");
		maxTurnAngle=40;
			
	}
		
	public void setDriveForce(){
		driveForce=1200f;
	}
	
	public void setMaxSpeed(){
		maxSpeed=50f;
	}
	
	public void setMaxGrip(){
		maxGrip=15f;
	}

}
