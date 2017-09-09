package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import vehicleParts.CarFactory.CarList;

public class HighScoreScreen extends AbstractScreen {
	//TODO have a textfield where a name can be entered as a key to a highscore
	
	private final Game game;
	
	public SpriteBatch batch;
	public BitmapFont font;
	private static Preferences prefs;
	private Skin textSkin;
	
	private Label postInfo;
	
	private TextField nameEntry;
	private TextButton saveScore;
	

	private ShapeRenderer shapeColor;
	private CarList car;
	private long time; 
	
	
	
	public HighScoreScreen(Game game, CarList carChoice, Long elapsedTime){
		stage=new Stage();
		createBasicSkin();
		
		this.game=game;
		this.time=elapsedTime;
		this.car=carChoice;
		this.prefs=Gdx.app.getPreferences("My Preferences");
		
		textSkin=new Skin(Gdx.files.internal("uiskin.json"));
		
		Gdx.input.setInputProcessor(stage);
		
		postInfo=new Label(carChoice.toString() +": "+ elapsedTime, textSkin);
		postInfo.setColor(Color.WHITE);
		postInfo.setPosition((int) Gdx.graphics.getWidth()/2-postInfo.getWidth()/2, (int) Gdx.graphics.getHeight()/2+190);
		
		nameEntry=new TextField("", textSkin);
		nameEntry.setMessageText("Enter Name");
		//change Location of NameEntry
		nameEntry.setPosition((int) Gdx.graphics.getWidth()/2-nameEntry.getWidth()/2, (int) Gdx.graphics.getHeight()/2+150);
		nameEntry.selectAll();
		
		saveScore=new TextButton("Save HighScore", textSkin);
		saveScore.setPosition((int) Gdx.graphics.getWidth()/2-saveScore.getWidth()/2, (int) Gdx.graphics.getHeight()/2+110);
		
		saveScore.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				
				if (!nameEntry.getText().contains(" ")){
				System.out.println(nameEntry.getText());
				prefs.putLong(nameEntry.getText(), time);
				prefs.putString(nameEntry.getText(), car.toString());
				
				prefs.flush();
				//TODO, shift to table view of all highscores on click 
				
				}
			}
			
		});
		
		stage.addActor(postInfo);
		stage.addActor(nameEntry);
		stage.addActor(saveScore);
		
	
	}

	@Override
	public void show() {
		//called at the start
		
		
		
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
