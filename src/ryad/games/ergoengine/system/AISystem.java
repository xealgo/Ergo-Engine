package ryad.games.ergoengine.system;

import java.util.ArrayList;
import java.util.List;

import ryad.games.ergoengine.components.AIComponent;
import ryad.games.ergoengine.events.MovementEvent;
import ryad.games.ergoengine.interfaces.Subsystem;

/**
 * @author Jason Welch
 * This system manages ai components.
 */
public class AISystem implements Subsystem<AIComponent> {
	//===================================
	// Members
	//===================================
	protected List<AIComponent> actors;
	protected MovementEvent event;
	
	//===================================
	// Constructors
	//===================================
	public AISystem() {
		this.actors = new ArrayList<AIComponent>();
		this.event = null;
	}
	
	//===================================
	// Getters and Setters
	//===================================
	
	
	//===================================
	// Methods
	//===================================
	/**
	 * Called when this component should startup.
	 */
	@Override public void startup() {
	
	}

	/**
	 * Called when this component should shutdown.
	 */
	@Override public void shutdown() {
		
	}
	
	@Override public void register(AIComponent actor){
		if(actor == null) return;
		this.actors.add(actor);
	}
	
	@Override public void unregister(AIComponent actor){
		if(actor == null) return;
		this.actors.remove(actor);
	}
	
	public void update(float time){
		if(this.actors.size() <=0) return;
	
		for(AIComponent actor:this.actors){
			actor.update(time);
		}
	}
}
