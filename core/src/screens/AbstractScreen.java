package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class AbstractScreen implements Screen{
	
	protected Skin skin;
	protected Stage stage;
	protected Skin carSelectSkin;

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
	
	public void createCarSelectSkin(){
		carSelectSkin=new Skin();
		BitmapFont font =new BitmapFont();
		skin.add("default", font);
		
		Pixmap pixmap= new Pixmap((int) Gdx.graphics.getWidth()/8, (int) Gdx.graphics.getHeight()/8, Pixmap.Format.RGB888 );
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		skin.add("background", new Texture(pixmap));
		
		TextButton.TextButtonStyle textButtonStyle=new TextButton.TextButtonStyle();		
		
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);		
		textButtonStyle.over = skin.newDrawable("background", Color.DARK_GRAY);	
		
		carSelectSkin.add("default", textButtonStyle);
		
	}
	

}
