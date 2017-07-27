package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SportsTire extends Tire {

	public SportsTire(World world, Vector2 location, boolean powered, boolean steered, BasicCar chassis){
		super(world, location, powered, steered, chassis);

		MAXLOAD=17;

	}
}


