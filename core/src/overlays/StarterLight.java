package overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	private ShapeRenderer render;



	public StarterLight(SpriteBatch batch){
		this.batch=batch;

		viewPort=new FitViewport(720,480,new OrthographicCamera());
		stage=new Stage(viewPort,batch);
		startTime=TimeUtils.millis();
		render=new ShapeRenderer();
	}

	public void renderLights(long count){
		//Add some pretty starting lights
		int horiPos=250;
		int vertPos=100;

		render.begin(ShapeType.Filled);
		render.setColor(Color.GRAY);


		if (count>450){
			render.rect(horiPos, vertPos, 200, 70);
		}

		//Set 420 milliseconds for the camera to lerp to the car initially.


		if (count<4420){
			render.setColor(Color.RED);
		}else{
			render.setColor(Color.GREEN);
		}

		if (count>1420){			
			render.circle(horiPos+30, vertPos+35, 20);
		}

		if (count>2420){
			render.circle(horiPos+100, vertPos+35, 20);
		}
		if (count>3420){
			render.circle(horiPos+170, vertPos+35, 20);
		}		


		render.end();		
	}

	public void tick(){
		long count= TimeUtils.timeSinceMillis(startTime);
		if (count>4300){			
			started=true;
		}

		if (count<5000){
			renderLights(count);
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
