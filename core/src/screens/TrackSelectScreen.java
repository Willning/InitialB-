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

import trackStuff.TrackFactory;
import trackStuff.TrackFactory.TrackList;
import vehicleParts.CarFactory.CarList;

public class TrackSelectScreen extends AbstractScreen{

	private final Game game;

	private TextButton backButton;
	private TextButton nextButton;

	private Table table;
	private TrackList track;

	private Label label;

	private Texture texture;
	private Image mapPreview;

	public TrackSelectScreen(Game game){
		this.game=game;

		stage=new Stage();

		createBasicSkin();
		createCarSelectSkin();

		table=new Table();		
		table.setPosition((int) Gdx.graphics.getWidth()/2, 350);

		HorizontalGroup group=new HorizontalGroup();
		TextButton buttonOne=new TextButton("BasicTrack", carSelectSkin);

		buttonOne.addListener(new ClickListener(){ 
			@Override
			public void clicked(InputEvent event, float x, float y){
				track=TrackList.BASICTRACK;
				texture=new Texture("./Assets/Tracks/basicTrack.png");
			}
		});



		Gdx.input.setInputProcessor(stage);
		createBasicSkin();

		label=new Label(String.format("Track: "), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		label.setPosition((int) Gdx.graphics.getWidth()/2-label.getWidth()/2-50, 280);
		stage.addActor(label);

		group.addActor(buttonOne);

		table.add(group);

		makePlayAndBack();

		stage.addActor(table);

	}

	public void displayCurrentChoice(){		

		if (track!=null){			
			label.setText("Track: " + track.toString());

			if (mapPreview!=null){
				mapPreview.remove();
			}

			mapPreview=new Image(texture);
			mapPreview.setSize(200, 200);
			mapPreview.setPosition((int) Gdx.graphics.getWidth()/2-100, (int) Gdx.graphics.getHeight()/2-180);
			stage.addActor(mapPreview);

		}else{
			label.setText("Track: None Chosen");
		}
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


		nextButton=new TextButton("Next", skin);
		nextButton.setPosition(10, (int) Gdx.graphics.getHeight()/2-150);

		nextButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (track!=null){
					game.setScreen(new CarSelectScreen(game,track));
					dispose();
				}
			}
		});

		stage.addActor(backButton);
		stage.addActor(nextButton);
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
		// TODO Auto-generated method stub

	}

}
