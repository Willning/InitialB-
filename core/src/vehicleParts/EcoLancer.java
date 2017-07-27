package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class EcoLancer extends BasicCar {

	public EcoLancer(World world, Vector2 location) {
		super(world, location);
		sprite=new Texture("ecoLancer.png");
		maxTurnAngle=35;
	}

	@Override
	public void fitTires(World world){
		//Four wheel drive now
		setDriveAndBrake();	

		for(int i=0;i<2;i++){
			tires[i]=new SportsTire(world, new Vector2(0,0), true, false, this); //powered but not steered
			tires[i].setForces(driveAndBrake);

		}

		for (int i=2;i<4;i++){
			tires[i]=new SportsTire(world, new Vector2(0,0), true, true, this); //steered and powered
			tires[i].setForces(driveAndBrake);
		}
	}
	
	@Override
	protected void setDriveAndBrake(){
		driveAndBrake=new Vector2(900,50);
		//Keep in mind 4wd
	}


}
