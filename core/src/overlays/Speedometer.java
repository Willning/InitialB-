package overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import trackStuff.AbstractTrack;
import vehicleParts.BasicCar;

public class Speedometer implements Disposable {
	//Overlay used to contain the hud, things like the speedo
	//TODO add a countdown method to be called at the start of the game.

	BasicCar car; 
	//this will be measured for speed for the speedometer
	private SpriteBatch batch;
	private Stage stage;
	private Viewport viewPort;
	private Integer speed;	
	private Long startTime;
	private Long endTime;
	private Label speedText;
	private Label time;
	private Texture texture;
	private Image dial;
	private Image needle;
	private AbstractTrack track;

	private boolean GameOver;
	private boolean started=false;
	private boolean timerStop=false;


	public Speedometer(BasicCar car, SpriteBatch batch, AbstractTrack track){
		this.car=car;
		this.batch=batch;
		this.track=track;
		//Use track to check for start as well as minimapping

		viewPort=new FitViewport(720,480,new OrthographicCamera());
		stage=new Stage(viewPort,batch);

		createSpeedo();
		createTimer();

	}

	private void createTimer(){

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
		started=true;
	}

	public String stopTimer(){
		if (!timerStop){
			endTime=TimeUtils.millis();
			timerStop=true;
		}

		//Now we can carry the time data over to the next screen

		long millis=(endTime-startTime)%1000;
		long seconds=((endTime-startTime)/1000)%60;
		long minutes=((endTime-startTime)/1000)/60;	


		return String.format("Time: " +"%02d"+":"+ "%02d"+ ":"+"%03d", minutes, seconds, millis);
	}

	public String stepTimer(){

		long millis=TimeUtils.timeSinceMillis(startTime)%1000;
		long seconds=(TimeUtils.timeSinceMillis(startTime)/1000)%60;
		long minutes=(TimeUtils.timeSinceMillis(startTime)/1000)/60;	

		return String.format("Time: " +"%02d"+":"+ "%02d"+ ":"+"%03d", minutes, seconds, millis);
	}

	public void update(){

		String timeData;
		if(!started){
			timeData=String.format("Time: " +"%02d"+":"+ "%02d"+ ":"+"%03d", 0,0,0);
		}else{
			if (!GameOver){
				timeData=stepTimer();
			}else{
				timeData=stopTimer();
			}
		}
		time.setText(timeData);

		speed=(int) (4.5*car.getForwardVelocity().len());		

		speedText.setText(String.format("%03d" +" km/h", speed));
		needle.setRotation(-speed+90);		

		stage.act();
		stage.draw();		

	}

	public void gameOver(){
		GameOver=true;
	}
	
	public boolean checkGameOver(){
		return GameOver;
	}
	
	public long getTimeLong(){
		return (endTime-startTime);
	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
		stage.dispose();		

	}



}
