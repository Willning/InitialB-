package trackStuff;

import com.badlogic.gdx.physics.box2d.World;

import vehicleParts.CarFactory;

public class TrackFactory{

	private static TrackFactory instance=null;
	
	public enum TrackList{
		BASICTRACK;
	}
	
	private static World world;
	
	public void setWorld(World world){
		this.world=world;
	}
	
	public static TrackFactory getInstance() {
		if(instance == null) {
			instance = new TrackFactory();
		}
		return instance;
	}
	
	private TrackFactory(){
		
	}
	
	public AbstractTrack makeTrack(TrackList track){
		if (track==TrackList.BASICTRACK){
			return new BasicTrack(world);
		}
		
		return null;
		
	}
	
	

}
