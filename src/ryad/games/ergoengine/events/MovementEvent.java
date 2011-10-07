package ryad.games.ergoengine.events;

import com.badlogic.gdx.math.Vector2;

import ryad.games.ergoengine.Event;
import ryad.games.ergoengine.interfaces.ActionListener;

/**
 * Movement event.. gets sent to controller components.
 * @author Jason Welch
 */
public class MovementEvent extends Event {
	
	public enum Actions {
		EAction_None,
		EAction_MoveLeft,
		EAction_MoveRight,
		EAction_MoveForward,
		EAction_MoveBackward
	}
	
	//===================================
	// Members
	//===================================
	protected Actions action;
	
	//===================================
	// Constructors
	//===================================
	public MovementEvent(Object sender, ActionListener listener) {
		super(sender, listener);
		this.action = Actions.EAction_None;
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public void setAction(Actions action){
		this.action = action;
	}
	
	public Actions getAction(){
		return this.action;
	}
}
