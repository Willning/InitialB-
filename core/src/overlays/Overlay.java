package overlays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import vehicleParts.BasicCar;

public class Overlay implements Disposable {
	//Overlay used to contain the hud, things like the speedo 
	
	BasicCar car; 
	//this will be measured for speed for the speedometer
	SpriteBatch batch;
	Stage stage; 
	
	public Overlay(BasicCar car, SpriteBatch batch){
		this.car=car;
		this.batch=batch;
		
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		// TODO Auto-generated method stub
		
	}
	
	

}
