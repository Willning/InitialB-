package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import gameLogic.GameManager;

public class MainMenuScreen extends AbstractScreen{

	//This class is used to represent the splash screen displayed upon starting the game

	final Game game;	
	TextButton playButton;	
	TextButton exitButton;

	GameManager superGame;

	public MainMenuScreen(final Game racerGame){
		this.game=racerGame;
		superGame =(GameManager)game;

		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		//Set the stage and use the stage for input

		//set the Skin to be something
		createBasicSkin();

		playButton=new TextButton("Start Game", skin);
		playButton.setPosition((int) Gdx.graphics.getWidth()/2-playButton.getWidth()+50, (int) Gdx.graphics.getHeight()/2-130);
		
		exitButton=new TextButton("Exit", skin);
		exitButton.setPosition((int) Gdx.graphics.getWidth()/2-playButton.getWidth()+50, (int) Gdx.graphics.getHeight()/2-200);

		stage.addActor(playButton);
		stage.addActor(exitButton);

		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new CarSelectScreen(game));
				dispose();
			}
		});
		
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				dispose();
				Gdx.app.exit();				
			}
		});

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


		superGame.batcher.begin();  
		//Title of game is going to be an image.
		//So play button is a small text button

		superGame.batcher.end();

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
		stage.dispose();
		skin.dispose();	
		
	}

}
