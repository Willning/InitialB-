package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class FourWheelDriveCar extends BasicCar{

	public FourWheelDriveCar(World world, Vector2 location) {
		super(world, location);
		
	}
	
	public void fitTires(World world){
		//OverRide based on drive configuration
		//Do a template method for this probably
		setMaxGrip();
		setDriveForce();
		setMaxSpeed();
	

		for(int i=0;i<2;i++){
			tires[i]=new Tire(world, tirePositions[i], true, false, this); //powered but not steered
			tires[i].setDriveForce(0.5f*driveForce);
			tires[i].setMaxSpeed(maxSpeed);
			tires[i].MAXLOAD=maxGrip; 
		}

		for (int i=2;i<4;i++){
			tires[i]=new Tire(world, tirePositions[i], true, true, this); //steered and 20% powered
			tires[i].setDriveForce(0.5f*driveForce);
			tires[i].setMaxSpeed(maxSpeed);
			tires[i].MAXLOAD=maxGrip; 
		}

	}

}
