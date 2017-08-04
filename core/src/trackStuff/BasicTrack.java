package trackStuff;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

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
