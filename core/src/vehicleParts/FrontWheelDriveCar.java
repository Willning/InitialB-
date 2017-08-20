package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class FrontWheelDriveCar extends BasicCar implements Parameters{

	public FrontWheelDriveCar(World world, Vector2 location) {
		super(world, location);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void fitTires(World world){
		setMaxGrip();
		setDriveForce();
		setMaxSpeed();
	
		
		for(int i=0;i<2;i++){
			tires[i]=new Tire(world, new Vector2(0,0), false, false, this); //no power, no steering
			tires[i].setDriveForce(driveForce);
			tires[i].setMaxSpeed(maxSpeed);
			tires[i].MAXLOAD=maxGrip; 
		}

		for (int i=2;i<4;i++){
			tires[i]=new Tire(world, new Vector2(0,0), true, true, this); //steered and powered
			tires[i].setDriveForce(driveForce);
			tires[i].setMaxSpeed(maxSpeed);
			tires[i].MAXLOAD=maxGrip; ;
		}
	}
	

}
