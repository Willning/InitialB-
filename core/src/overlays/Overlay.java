package overlays;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import trackStuff.AbstractTrack;
import vehicleParts.BasicCar;

public class Overlay implements Disposable {
	//Overlay used to contain the hud, things like the speedo
	//TODO add a timing overlay and a minimap

	BasicCar car; 
	//this will be measured for speed for the speedometer
	SpriteBatch batch;
	Stage stage;
	private Viewport viewPort;
	private Integer speed;	
	private Long startTime;
	private Label speedText;
	private Label time;
	private Texture texture;
	private Image dial;
	private Image needle;
	private AbstractTrack track;


	public Overlay(BasicCar car, SpriteBatch batch, AbstractTrack track){
		this.car=car;
		this.batch=batch;
		this.track=track;
		//Use track to check for start as well as minimapping
		
		viewPort=new FitViewport(720,480,new OrthographicCamera());
		stage=new Stage(viewPort,batch);
		
		createSpeedo();
		createTimer();
		
		//create a timer here;


	}
	
	private void createTimer(){
		startTimer();
		
		time=new Label(String.format("%03d",0), new Label.LabelStyle(new BitmapFont(), Color.BLACK));		
		time.setPosition(dial.getX()+20, 25);
		
		stage.addActor(time);
	}
	
	private void createSpeedo(){
		texture=new Texture("dial.png");
		dial= new Image(texture);
		
		texture=new Texture("needle.png");
		needle=new Image(texture);		
		
		
		dial.setHeight(150);
		dial.setWidth(150);
		dial.setPosition(550, 50);
		//dial of speedometer
		
		needle.setHeight(60);
		needle.setWidth(10);
		needle.setOrigin(5, 2);
		
		needle.setPosition(dial.getX()+70, dial.getY()+73);
		//Needle of speedometer
			
		speedText=new Label(String.format("%03f",speed), new Label.LabelStyle(new BitmapFont(), Color.BLACK));		
		speedText.setPosition(dial.getX()+50,dial.getY()+30);
		//Place speedText within the dial.
		
		stage.addActor(dial);
		stage.addActor(needle);		
		stage.addActor(speedText);
	}
	
	public void startTimer(){
		startTime=TimeUtils.millis();
	}
	
	public void stepTimer(){
		
		long millis=TimeUtils.timeSinceMillis(startTime)%1000;
		long seconds=(TimeUtils.timeSinceMillis(startTime)/1000)%60;
		long minutes=(TimeUtils.timeSinceMillis(startTime)/1000)/60;		
		
		time.setText(String.format("Time: " +"%02d"+":"+ "%02d"+ ":"+"%03d", minutes, seconds, millis));
	}
	
	public void update(){
				
		stepTimer();				
		
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
