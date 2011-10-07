package ryad.games.ergoengine.components;

import ryad.games.ergoengine.GameComponent;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.events.MovementEvent;
import ryad.games.ergoengine.events.ScriptEvent;
import ryad.games.ergoengine.interfaces.ActionEvent;

import com.badlogic.gdx.math.Vector2;

/**
 * Generic controller for "controlling" game objects.
 * 
 * to do: add methods such as "moveTo" and also, use the objects
 * acceleration vs the speed var. Also, add checks for min/max velocity.
 * A collision response component may later need to alter the velocity/
 * position...but that all will be done before rendering...
 * 
 * todo: UPDATE TO USE THE GAME OBJECTS IMPULSE VS TARGET VELOCITY..
 * 
 * @author Jason Welch
 */
public abstract class ControllerComponent extends GameComponent {
	//===================================
	// Members
	//===================================
	protected GameObject gameObject;
	protected Vector2 maxVelocity;
	
	//===================================
	// Constructors
	//===================================
	public ControllerComponent(GameObject gameObject) {
		super("controller");
		this.gameObject = gameObject;
		this.maxVelocity = new Vector2(25,25);
	}
	//===================================
	// Getters and Setters
	//===================================
	public void setMaxVelocity(float x, float y){
		this.maxVelocity.set(x,y);
	}
	
	public Vector2 getMaxVelocity(){
		return this.maxVelocity;
	}
	
	//===================================
	// Methods
	//===================================
	/**
	 * Handle incoming events
	 * @param event
	 */
	@Override public void eventCallback(ActionEvent event) {
		if(event instanceof MovementEvent){
			MovementEvent e = ((MovementEvent)event);
			if(e.getAction() == MovementEvent.Actions.EAction_MoveBackward) this.onMoveBackward(e);
			else if(e.getAction() == MovementEvent.Actions.EAction_MoveForward) this.onMoveForward(e);
			else if(e.getAction() == MovementEvent.Actions.EAction_MoveLeft) this.onMoveLeft(e);
			else if(e.getAction() == MovementEvent.Actions.EAction_MoveRight) this.onMoveRight(e);
			else if(e.getAction() == MovementEvent.Actions.EAction_None) this.gameObject.getTargetVelocity().set(0,0);
		}
	}

	/**
	 * Update this component
	 * @param time
	 */
	@Override protected void onUpdate(float time) {
		if(this.gameObject == null) return;
	}
	
	/**
	 * Move the game object left
	 * @param e
	 */
	protected void onMoveLeft(MovementEvent e){
		this.gameObject.getTargetVelocity().x = this.maxVelocity.x * -1;
	}
	
	/**
	 * Move the game object right
	 * @param e
	 */
	protected void onMoveRight(MovementEvent e){
		this.gameObject.getTargetVelocity().x = this.maxVelocity.x;
	}
	
	/**
	 * Move the game object forward
	 * @param e
	 */
	protected void onMoveForward(MovementEvent e){
		this.gameObject.getTargetVelocity().y = this.maxVelocity.y;
	}
	
	/**
	 * Move the game object backward
	 * @param e
	 */
	protected void onMoveBackward(MovementEvent e){
		this.gameObject.getTargetVelocity().y = -this.maxVelocity.y;
	}
}
