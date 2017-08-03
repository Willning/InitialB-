package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import gameLogic.GameManager;
import vehicleParts.CarFactory.CarList;

public class CarSelectScreen extends AbstractScreen {

	final Game game;

	private TextButton backButton;
	private TextButton playButton;
	//Really bad structuring but it works for now.

	private Table table;
	private CarList car;
	private BitmapFont font;

	GameManager superGame;

	public CarSelectScreen(final Game racerGame){
		this.game=racerGame;
		superGame =(GameManager)game;
		font= superGame.font;


		stage=new Stage();

		Gdx.input.setInputProcessor(stage);
		createBasicSkin();
		createCarSelectSkin();

		table=new Table();		
		table.setPosition((int) Gdx.graphics.getWidth()/2, 350);


		HorizontalGroup group=new HorizontalGroup();
		TextButton buttonOne=new TextButton("Town Car", carSelectSkin);
		TextButton buttonTwo=new TextButton ("EcoLancer", carSelectSkin);		
		TextButton buttonThree=new TextButton("Bivic", carSelectSkin);
		TextButton buttonFour=new TextButton("Yambo", carSelectSkin);
		//Setup the buttons.

		buttonOne.addListener(new ClickListener(){ 
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.TOWNCAR;
			}
		});


		buttonTwo.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.ECOLANCER;
			}
		});

		buttonThree.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.BIVIC;
			}
		});
		
		buttonFour.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.YAMBO;
			}
		});


		group.addActor(buttonOne);
		group.addActor(buttonTwo);
		group.addActor(buttonThree);
		group.addActor(buttonFour);

		table.add(group);

		stage.addActor(table);

		makePlayAndBack();

	}

	public void makePlayAndBack(){
		//Add play and back button

		backButton=new TextButton("Back", skin);
		backButton.setPosition(10, (int) Gdx.graphics.getHeight()/2-220);		

		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}
		});


		playButton=new TextButton("Play", skin);
		playButton.setPosition(10, (int) Gdx.graphics.getHeight()/2-150);

		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (car!=null){
					game.setScreen(new GameScreen(game, car));
					dispose();
				}
			}
		});

		stage.addActor(backButton);
		stage.addActor(playButton);
	}

	public void displayCurrentChoice(){

		superGame.batcher.begin();
		if (car!=null){
			font.draw(superGame.batcher, "Car: " + car.toString(), 500, 200);
		}else{
			font.draw(superGame.batcher, "Car: None Chosen", 500, 200);
		}				
		superGame.batcher.end();

	}




	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		displayCurrentChoice();

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
		stage.dispose();
		skin.dispose();
		carSelectSkin.dispose();
		font.dispose();


	}

}


