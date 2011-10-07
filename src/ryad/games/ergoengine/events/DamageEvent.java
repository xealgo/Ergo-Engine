package ryad.games.ergoengine.events;

import ryad.games.ergoengine.Event;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.interfaces.ActionListener;

/**
 * The basic damage event type..
 * @author Jason Welch
 */
public class DamageEvent extends Event {
	//===================================
	// Members
	//===================================
	protected GameObject instigator;
	protected float damage;
	
	//===================================
	// Constructor
	//===================================
	public DamageEvent(Object sender, ActionListener listener) {
		super(sender, listener);
		this.damage = 0;
		this.instigator = null;
	}

	//===================================
	// Getters/Setters
	//===================================
	public GameObject getInstigator() {
		return instigator;
	}

	public void setInstigator(GameObject instigator) {
		this.instigator = instigator;
	}
	
	public void setDamage(float damage){
		this.damage = damage;
	}

	public float getDamage(){
		return this.damage;
	}
}
