package ryad.games.ergoengine.components;

import com.badlogic.gdx.math.Vector2;

import ryad.games.ergoengine.GameComponent;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.events.MovementEvent;
import ryad.games.ergoengine.interfaces.ActionEvent;

/**
 * A simple movement component. Moves the game object based on
 * it's velocity and time, while applying friction to slow it
 * down.
 * 
 * to do: make friction values configurable!
 * 
 * Note: this could be used in certain instances where physics
 * are not applied to objects OR in cases where the position/
 * velocity/etc of a physics object needs to change.
 * 
 * @author Jason Welch
 */
public class MovementComponent extends GameComponent {
	//===================================
	// Members
	//===================================
	//the object we're modifying
	protected GameObject gameObject;

	//===================================
	// Constructors
	//===================================
	public MovementComponent(GameObject gameObject) {
		super("movement");
		this.gameObject = gameObject;
	}

	//===================================
	// Methods
	//===================================
	/**
	 * updates the position based on velocity and time.
	 * @param time
	 */
	protected void updateVelocity(float time){
		// update the velocity
		this.gameObject.getVelocity().x += this.gameObject.getTargetVelocity().x;
		this.gameObject.getVelocity().y += this.gameObject.getTargetVelocity().y;
		// update position
		this.gameObject.getPosition().x += gameObject.getVelocity().x * time;
		this.gameObject.getPosition().y += gameObject.getVelocity().y * time;
	}
	
	/**
	 * Applies "drag" to the velocity..
	 */
	protected void applyDrag(){
		this.gameObject.getVelocity().mul(0.98f);
	}

	/**
	 * The main update method
	 * @param time
	 */
	@Override protected void onUpdate(float time) {
		this.updateVelocity(time);
		this.applyDrag();
	}
}
