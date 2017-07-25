package gameLogic;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MainMenuScreen;

public class GameManager extends Game {
	//used on launch, starts an instance of the Splash Screen

	public SpriteBatch batcher;
	public BitmapFont font;

	@Override
	public void create(){
		batcher = new SpriteBatch();
		font= new BitmapFont();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render(){
		super.render();
	}
	
	@Override
	public void dispose(){
		batcher.dispose();
		font.dispose();
	}

}



