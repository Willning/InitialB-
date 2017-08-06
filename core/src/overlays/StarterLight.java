package overlays;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StarterLight implements Disposable {
	//TODO add some graphics
	
	private SpriteBatch batch;
	private Stage stage;
	private Viewport viewPort;
	private boolean started=false;
	private long startTime;

	public StarterLight(SpriteBatch batch){
		this.batch=batch;
		
		viewPort=new FitViewport(720,480,new OrthographicCamera());
		stage=new Stage(viewPort,batch);
		startTime=TimeUtils.millis();
	}
	
	public void renderLights(){
		//Add some pretty starting lights
	}
	
	public void tick(){		
		if (TimeUtils.timeSinceMillis(startTime)>3000){			
			started=true;
		}
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		
	}
	
	public boolean isStarted(){
		return started;
	}

}
