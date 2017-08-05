package trackStuff;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import gameLogic.ListenerClass;

public abstract class AbstractTrack {
	//TODO this class is responsible for sending startTime and endTime events. 

	protected TiledMap track;
	protected World world;

	protected MapRenderer render;
	protected MapObjects objects;
	protected Array<Body> bodies= new Array<Body>();

	protected Array<Body>startAndFinish=new Array<Body>();

	protected ListenerClass listener;

	public void extractCollisionObjects(){
		MapLayer layer = track.getLayers().get(1);

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

				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape=shape;
				fixtureDef.friction=5f;

				body.createFixture(fixtureDef);

				bodies.add(body);

				shape.dispose();
			}

			if(object instanceof RectangleMapObject){
				//Use the name later on to either start the timer or stop the timer.

				shape=getRectangle((RectangleMapObject)object);
				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				Body body = world.createBody(bd);

				body.setUserData(object.getName());

				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape=shape;
				fixtureDef.isSensor=true;

				body.createFixture(fixtureDef);
				

				startAndFinish.add(body);

				shape.dispose();

			}
		}

	}


	public void render(OrthographicCamera camera){
		render.setView(camera);
		render.render();

		//This method will be used to render the tilemap
	}

	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) /2,
				(rectangle.y + rectangle.height * 0.5f ) / 2);
		polygon.setAsBox(rectangle.width * 0.5f / 2f,
				rectangle.height * 0.5f / 2f,
				size,
				0.0f);
		return polygon;
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
