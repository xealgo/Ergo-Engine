package ryad.games.ergoengine.components;

import ryad.games.ergoengine.GameComponent;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.events.MovementEvent;
import ryad.games.ergoengine.interfaces.ActionEvent;
import ryad.games.ergoengine.system.AISystem;

import com.badlogic.gdx.math.Vector2;

/**
 * Simple AI controller.
 * @author Jason Welch
 */
public class AIComponent extends GameComponent {
	//===================================
	// Members
	//===================================
	protected GameObject gameObject;
	protected AISystem aiSystem;
	protected GameObject player;
	
	protected float thinkRate; // how often to "think"
	protected float timeSinceLastThought; // how long has it been since the last thought?
	
	//===================================
	// Constructors
	//===================================
	public AIComponent(AISystem aiSystem, GameObject gameObject, GameObject player) {
		super("ai");
		this.gameObject = gameObject;
		this.aiSystem = aiSystem;
		this.player = player;
		this.thinkRate = 0.25f;
		this.timeSinceLastThought = 0.0f;
	}
	
	//===================================
	// Getters and setters
	//===================================
	public float getThinkRate() {
		return thinkRate;
	}

	public void setThinkRate(float thinkRate) {
		this.thinkRate = thinkRate;
	}

	public float getTimeSinceLastThought() {
		return timeSinceLastThought;
	}

	public void setTimeSinceLastThought(float timeSinceLastThought) {
		this.timeSinceLastThought = timeSinceLastThought;
	}
	
	//===================================
	// Methods
	//===================================
	/**
	 * Initialization method
	 */
	@Override protected void onStartup() {
		//this.aiSystem.register(this);
	}

	/**
	 * Shutdown, cleanup, etc.
	 */
	@Override protected void onShutdown() {
		this.aiSystem.unregister(this);
	}

	/**
	 * Handle incoming events.
	 */
	@Override public void eventCallback(ActionEvent event) {
		
	}

	/**
	 * Update with think time..
	 */
	@Override public void update(float time) {
		if(this.active) {
			this.timeSinceLastThought += time;
			if(this.timeSinceLastThought >= this.thinkRate){
				this.onUpdate(time);
				this.timeSinceLastThought = 0.0f;
			}
		}
	}
}
