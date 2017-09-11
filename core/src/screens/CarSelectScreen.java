package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import trackStuff.TrackFactory.TrackList;
import vehicleParts.CarFactory.CarList;

public class CarSelectScreen extends AbstractScreen {

	final Game game;

	private TextButton backButton;
	private TextButton playButton;
	//Really bad structuring but it works for now.

	private Table table;
	private CarList car;
	private Label label;

	private Texture texture;
	private Image carPreview;
	
	private TrackList setTrack;



	public CarSelectScreen(final Game racerGame,TrackList setTrack){
		this.game=racerGame;
		this.setTrack=setTrack;

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
		TextButton buttonFive=new TextButton("Abarth", carSelectSkin);
		//Setup the buttons.

		buttonOne.addListener(new ClickListener(){ 
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.TOWNCAR;
				texture=new Texture("./Assets/Cars/townCar.png");
			}
		});


		buttonTwo.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.ECOLANCER;
				texture=new Texture("./Assets/Cars/ecoLancer.png");
			}
		});

		buttonThree.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.BIVIC;
				texture=new Texture("./Assets/Cars/BondaBivic.png");
			}
		});

		buttonFour.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.YAMBO;
				texture=new Texture("./Assets/Cars/yambo.png");
			}
		});

		buttonFive.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				car=CarList.ABARTH;
				texture=new Texture("./Assets/Cars/Abarth.png");				
			}
		});

		group.space(10);
		group.addActor(buttonOne);		
		group.addActor(buttonTwo);
		group.addActor(buttonThree);
		group.addActor(buttonFour);
		group.addActor(buttonFive);

		table.add(group);

		stage.addActor(table);

		makePlayAndBack();

		label=new Label(String.format("Car: "), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		label.setPosition((int) Gdx.graphics.getWidth()/2-label.getWidth()/2, (int) Gdx.graphics.getHeight()/2-220);
		stage.addActor(label);

	}

	public void makePlayAndBack(){
		//Add play and back button

		backButton=new TextButton("Back", skin);
		backButton.setPosition(10, (int) Gdx.graphics.getHeight()/2-220);		

		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new TrackSelectScreen(game));
				dispose();
			}
		});


		playButton=new TextButton("Play", skin);
		playButton.setPosition(10, (int) Gdx.graphics.getHeight()/2-150);

		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (car!=null){
					game.setScreen(new GameScreen(game, car, setTrack));
					dispose();
				}
			}
		});

		stage.addActor(backButton);
		stage.addActor(playButton);
	}

	public void displayCurrentChoice(){		

		if (car!=null){			
			label.setText("Car:" + car.toString());

			if (carPreview!=null){
				carPreview.remove(); //clears the existing image if it exists before slapping down a new one
			}
			
			carPreview=new Image(texture);			
			carPreview.setPosition((int) Gdx.graphics.getWidth()/2-carPreview.getWidth()/2, (int) Gdx.graphics.getHeight()/2-180);
			stage.addActor(carPreview);
		}else{
			label.setText("Car: None Chosen");
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


	}

}


