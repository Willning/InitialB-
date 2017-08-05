package trackStuff;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class BasicTrack extends AbstractTrack{
	//Render this using box2d, only track for now
	//TODO set objects as collisions, checkpoints and timing areas.


	public BasicTrack(World world){
		//pass a reference from the world as well as a spritebatch from game
		this.world=world;
		track=new TmxMapLoader().load("basicTrack.tmx");		
		render=new OrthogonalTiledMapRenderer(track,1/2f);

		extractCollisionObjects();

		//load in the tmx Trackfile we made at half size. 

	}

	
}
