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

public class BasicTrack {
	//Render this using box2d, only track for now
	//TODO set objects as collisions, checkpoints and timing areas.

	TiledMap track;
	private World world;

	private MapRenderer render;
	private MapObjects objects;
	public Array<Body> bodies= new Array<Body>();


	public BasicTrack(World world){
		//pass a reference from the world as well as a spritebatch from game
		this.world=world;
		track=new TmxMapLoader().load("track.tmx");		
		render=new OrthogonalTiledMapRenderer(track,1/2f);

		extractCollisionObjects();

		//load in the tmx Trackfile we made at half size. 

	}

	public void extractCollisionObjects(){
		MapLayer layer = track.getLayers().get(0);
		//Getting the wrong layer right now
		objects = layer.getObjects();		

		Shape shape;

		//now build all the bodies based on the objects
		for (MapObject object: objects){
			//set the lines as static shapes
			if (object instanceof PolylineMapObject){
							
				shape = getPolyline((PolylineMapObject)object);


				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				Body body = world.createBody(bd);
				body.createFixture(shape, 1);

				bodies.add(body);

				shape.dispose();
			}
		}

	}

	public void render(OrthographicCamera camera){
		render.setView(camera);
		render.render();
		System.out.println(bodies.size);
		//This method will be used to render the tilemap

	}

	private static ChainShape getPolyline(PolylineMapObject polylineObject) {
		float[] vertices = polylineObject.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for (int i = 0; i < vertices.length / 2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i * 2] / 2; //PPM is 2
			worldVertices[i].y = vertices[i * 2 + 1] / 2;
		}

		ChainShape chain = new ChainShape(); 
		chain.createChain(worldVertices);
		return chain;
	}
}
