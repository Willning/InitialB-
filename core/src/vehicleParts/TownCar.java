package vehicleParts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class TownCar extends BasicCar{

	
	public TownCar(World world, Vector2 location) {
		super(world, location);
		sprite=new Texture("townCar.png");
		maxTurnAngle=40;
			
	}
	
	
	@Override
	protected void setDriveAndBrake(){
		driveAndBrake=new Vector2(1200,40);
	}


}
