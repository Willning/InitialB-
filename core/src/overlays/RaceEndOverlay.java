package overlays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import screens.CarTimeScreen;
import vehicleParts.CarFactory.CarList;

public class RaceEndOverlay implements Disposable {

	private SpriteBatch batch;
	private Label finishLabel;
	private TextButton finish; 
	private Stage stage;
	private Viewport viewPort;
	private Skin skin;
	private Game game;
	private CarList carType;
	private Long finishTime;
	
	private boolean clickController=false; 

	public RaceEndOverlay(final Game game, SpriteBatch batch){ 
		//Pass an instance of game into here
		this.batch=batch;
		this.game=game;

		viewPort=new FitViewport(720,480,new OrthographicCamera());
		stage=new Stage(viewPort,batch);

		finishLabel = new Label(String.format(""), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		finishLabel.setPosition(220, 400);

		createBasicSkin();

		finish=new TextButton("Finish", skin); 
		finish.setPosition(330, 300);

		finish.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new CarTimeScreen(game,carType,finishTime));
				//TODO Change this to go to a new EndScreen later on
			}

		});

		finishLabel.setFontScale(2, 2);
		stage.addActor(finishLabel);
		stage.addActor(finish);
	}


	public void renderEnd(String endTime){
		//Add the cartype here and then we're good
		
		if (!clickController){
			Gdx.input.setInputProcessor(stage);
			clickController=true;
		}
		
		finishLabel.setText("Finish " + endTime);
		stage.act();
		stage.draw();

	}
	
	public void setFinishDetails(CarList car, Long finishTime){
		this.carType=car;
		this.finishTime=finishTime;
	}

	public void createBasicSkin(){
		//Shuffle this to some other class, will be used more later
		skin=new Skin();
		BitmapFont font =new BitmapFont();
		skin.add("default", font);

		Pixmap pixmap= new Pixmap((int) Gdx.graphics.getWidth()/8, (int) Gdx.graphics.getHeight()/8, Pixmap.Format.RGB888 );
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));

		TextButton.TextButtonStyle textButtonStyle=new TextButton.TextButtonStyle();

		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.up = skin.newDrawable("background", Color.RED);
		textButtonStyle.down = skin.newDrawable("background", Color.FIREBRICK);
		textButtonStyle.checked = skin.newDrawable("background", Color.RED);
		textButtonStyle.over = skin.newDrawable("background", Color.RED);

		skin.add("default", textButtonStyle);
	}



	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();

	}

}
