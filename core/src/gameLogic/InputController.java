package gameLogic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {

	public boolean left;
	public boolean right;
	public boolean up;
	public boolean down;

	public boolean drift;
	
	public boolean exit;

	@Override
	public boolean keyDown(int keycode) {
		boolean keyDown=false;
		switch (keycode)
		{
		case Keys.LEFT:
			left=true;
			keyDown=true;
			break;
		case Keys.RIGHT:
			right=true;
			keyDown=true;
			break;
		case Keys.UP:
			up=true;
			keyDown=true;
			break;
		case Keys.DOWN:
			down=true;
			keyDown=true;
			break;
		case Keys.SPACE:
			drift=true;
			keyDown=true;
			break;
		case Keys.ESCAPE:
			exit=true;
		}
		return keyDown;
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean keyUp=false;
		switch (keycode)
		{
		case Keys.LEFT:
			left=false;
			keyUp=true;
			break;
		case Keys.RIGHT:
			right=false;
			keyUp=true;
			break;
		case Keys.UP:
			up=false;
			keyUp=true;
			break;
		case Keys.DOWN:
			down=false;
			keyUp=true;
			break;
		case Keys.SPACE:
			drift=false;
			keyUp=true;

		}

		return keyUp;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	//Use this instead of having all controls wedged into MygGdxGame 

}
