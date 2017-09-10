package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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

	private CarList car;
	private long time; 

	public CarTimeScreen(Game game, CarList carChosen, Long timeTaken){
		stage=new Stage();
		createBasicSkin();
		textSkin=new Skin(Gdx.files.internal("uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		this.game=game;
		this.prefs=Gdx.app.getPreferences("My Preferences");

		if (carChosen!=null){
			this.time=timeTaken;
			this.car=carChosen;
			


			long oldTime=prefs.getLong(car.toString());
			//takes the old time

			postInfo=new Label("Car: " + car.toString() +"  Time Taken "+ parseTime(time), textSkin);

			//info on top, add a record broken label later.

			if (time<oldTime || oldTime==0){
				//if the new Time is faster or the oldTime doesn't exist, replace the old time
				prefs.putLong(car.toString(), time);
				record= new Label("New Record",textSkin);
				record.setPosition((int) Gdx.graphics.getWidth()/2-record.getWidth()/2, (int) Gdx.graphics.getHeight()/2+150);
				record.setColor(Color.RED);
				stage.addActor(record);
				prefs.flush();
			}
		}else{
			postInfo=new Label("Best Times", textSkin);			
		}
		
		postInfo.setColor(Color.WHITE);
		postInfo.setPosition((int) Gdx.graphics.getWidth()/2-postInfo.getWidth()/2, (int) Gdx.graphics.getHeight()/2+170);
		stage.addActor(postInfo);

		constructTable();		
	}

	public void constructTable(){
		Table table=new Table();
		table.setPosition((int) Gdx.graphics.getWidth()/2-table.getWidth()/2, (int) Gdx.graphics.getHeight()/2);

		for (CarList car: CarList.values()){
			String name=parseTime(prefs.getLong(car.toString()));

			table.add(new Label(car.toString(),textSkin)).width(100).uniform();
			table.add(new Label(name, textSkin));


			table.row().height(50);
			table.row();
		}

		TextButton back=new TextButton("Main Menu",skin);		

		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
		});

		table.add(back).colspan(2);

		stage.addActor(table);
	}	


	public String parseTime(long inputTime){
		if (inputTime==0){
			return ("No Results");			
		}else{

			long millis=(inputTime)%1000;
			long seconds=((inputTime)/1000)%60;
			long minutes=((inputTime)/1000)/60;	


			return String.format("%02d"+":"+ "%02d"+ ":"+"%02d", minutes, seconds, millis);
		}
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
;


	}

}
