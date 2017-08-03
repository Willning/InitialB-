package overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import vehicleParts.BasicCar;

public class Overlay implements Disposable {
	//Overlay used to contain the hud, things like the speedo
	//TODO add a timing overlay

	BasicCar car; 
	//this will be measured for speed for the speedometer
	SpriteBatch batch;
	Stage stage;
	private Viewport viewPort;
	private Integer speed;
	private Label speedText;
	private Texture texture;
	private Image dial;
	private Image needle;


	public Overlay(BasicCar car, SpriteBatch batch){
		this.car=car;
		this.batch=batch;
		texture=new Texture("dial.png");
		dial= new Image(texture);
		
		texture=new Texture("needle.png");
		needle=new Image(texture);
		
		viewPort=new FitViewport(720,480,new OrthographicCamera());
		stage=new Stage(viewPort,batch);
		
		
		dial.setHeight(150);
		dial.setWidth(150);
		dial.setPosition(550, 25);
		//dial of speedometer
		
		needle.setHeight(60);
		needle.setWidth(10);
		needle.setOrigin(5, 2);
		
		needle.setPosition(dial.getX()+70, dial.getY()+73);
		//Needle of speedometer
			
		speedText=new Label(String.format("%03d",speed), new Label.LabelStyle(new BitmapFont(), Color.BLACK));		
		speedText.setPosition(dial.getX()+50,dial.getY()+30);
		//Place speedText within the dial.
		
		stage.addActor(dial);
		stage.addActor(needle);		
		
		stage.addActor(speedText);

	}
	
	public void update(){
		
		speed=(int) (4.5*car.getForwardVelocity().len());
		
				
		speedText.setText(String.format("%03d" +" km/h", speed));
		needle.setRotation(-speed+90);
		stage.act();
		stage.draw();
		
		
	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
		stage.dispose();		

	}



}
