package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import vehicleParts.CarFactory.CarList;

public class CarTimeScreen extends AbstractScreen {

	//New version of highscores where we just take each car and show its fastest time. 
	
	private final Game game;
	
	public SpriteBatch batch;
	public BitmapFont font;
	private static Preferences prefs;
	private Skin textSkin;
	
	private Label postInfo;
	private Label record;

	private ShapeRenderer shapeColor;
	private CarList car;
	private long time; 
	
	public CarTimeScreen(Game game, CarList carChosen, Long timeTaken){
		stage=new Stage();
		createBasicSkin();
		
		this.game=game;
		this.time=timeTaken;
		this.car=carChosen;
		this.prefs=Gdx.app.getPreferences("My Preferences");
		
		textSkin=new Skin(Gdx.files.internal("uiskin.json"));
		
		
		long oldTime=prefs.getLong(car.toString());
		//takes the old time
		
		postInfo=new Label("Car: " + car.toString() +"  Time Taken "+ parseTime(time), textSkin);
		postInfo.setColor(Color.WHITE);
		postInfo.setPosition((int) Gdx.graphics.getWidth()/2-postInfo.getWidth()/2, (int) Gdx.graphics.getHeight()/2+190);
		//info on top, add a record broken label later.
		
		if (time<oldTime || oldTime==0){
			//if the new Time is faster or the oldTime doesn't exist, replace the old time
			prefs.putLong(car.toString(), time);
			record= new Label("New Record",textSkin);
			record.setPosition((int) Gdx.graphics.getWidth()/2-record.getWidth()/2, (int) Gdx.graphics.getHeight()/2+170);
			record.setColor(Color.RED);
			stage.addActor(record);
			prefs.flush();
		}
		
		stage.addActor(postInfo);
		
		//TODO, add a table with all the cars
	}
	
	public String parseTime(long inputTime){
		long millis=(inputTime)%1000;
		long seconds=((inputTime)/1000)%60;
		long minutes=((inputTime)/1000)/60;	


		return String.format("%02d"+":"+ "%02d"+ ":"+"%02d", minutes, seconds, millis);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
