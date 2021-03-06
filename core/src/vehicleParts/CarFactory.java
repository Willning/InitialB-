package vehicleParts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class CarFactory {
	//Singleton car factory

	private static CarFactory instance=null;

	public enum CarList{
		TOWNCAR, ECOLANCER, BIVIC, YAMBO, ABARTH;
	}

	private static World world;
	private static Vector2 location;

	public static CarFactory getInstance() {
		if(instance == null) {
			instance = new CarFactory();
		}
		return instance;
	}

	public void setParams(World world, Vector2 location){
		this.world=world;
		this.location=location;
	}
	
	private CarFactory(){
		
	}

	public BasicCar makeCar(CarList car){
		if (car==CarList.TOWNCAR){
			return new TownCar(world, location);
		}else if (car==CarList.ECOLANCER){
			return new EcoLancer(world,location);
		}else if (car==CarList.BIVIC){
			return new BondaBivic(world,location);
		}else if (car==CarList.YAMBO){
			return new Yambo(world,location);
		}else if (car==CarList.ABARTH){
			return new Abarth(world,location);
		}
		return null;


	}

}
