package gameLogic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ListenerClass implements ContactListener{
	
	
//Used in starting and ending the timer in the overlay
	
	private boolean GameOver=false;

	@Override
	public void beginContact(Contact contact) {
		Body a=contact.getFixtureA().getBody();
		Body b=contact.getFixtureB().getBody();
		
		if (a.getUserData() instanceof String&&b.getUserData() instanceof String){
			//Finish line works now.
			System.out.println("finish");
			GameOver=true;
		}

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
	
	public boolean checkGameOver(){
		//Check this every tick 
		return GameOver;
	}

}
