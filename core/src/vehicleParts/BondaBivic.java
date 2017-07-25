package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class BondaBivic extends BasicCar {

	public BondaBivic(World world, Vector2 location) {
		super(world, location);
		length=4.1f;
		width=1.7f;
		maxTurnAngle=25;
		sprite=new Texture("BondaBivic.png");
				
		
	}
	
	@Override
	public void fitTires(World world){
		//Four wheel drive now
		setDriveAndBrake();	

		for(int i=0;i<2;i++){
			tires[i]=new Tire(world, new Vector2(0,0), false, false, this); //no power, no steering
			tires[i].setForces(driveAndBrake);

		}

		for (int i=2;i<4;i++){
			tires[i]=new Tire(world, new Vector2(0,0), true, true, this); //steered and powered
			tires[i].setForces(driveAndBrake);
		}
	}
	
	@Override
	protected void setDriveAndBrake(){
		driveAndBrake=new Vector2(90,120);
	}
	
	public void setPositions(){
		//Override based on new tirePositions
		//sets the positions of the tires
		//Closer widthwise and lengthwise, do later
		tirePositions[0]=new Vector2(1.6f,-2.5f);// Rear
		tirePositions[1]=new Vector2(-1.6f,-2.5f); 

		tirePositions[2]=new Vector2(1.6f,2.8f); //Fronts
		tirePositions[3]=new Vector2(-1.6f,2.8f);
	}

}
