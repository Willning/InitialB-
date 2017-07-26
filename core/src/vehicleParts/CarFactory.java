package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class CarFactory {
	//Singleton car factory

	private static CarFactory instance=null;

	public enum CarList{
		TOWNCAR, ECOLANCER, BIVIC;
	}

	private static World world;
	private static Vector2 location;

	public static CarFactory getInstance() {
		if(instance == null) {
			instance = new CarFactory(world, location);
		}
		return instance;
	}

	public void setParams(World world, Vector2 location){
		this.world=world;
		this.location=location;
	}

	private static CarFactory instant=null;

	private CarFactory(World world, Vector2 location){
		this.world=world;
		this.location=location;
	}

	public BasicCar makeCar(CarList car){
		if (car==CarList.TOWNCAR){
			return new TownCar(world, location);
		}else if (car==CarList.ECOLANCER){
			return new EcoLancer(world,location);
		}else if (car==CarList.BIVIC){
			return new BondaBivic(world,location);
		}
		return null;

	}

}
