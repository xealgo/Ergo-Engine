package ryad.games.ergoengine.components;

import ryad.games.ergoengine.GameComponent;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.events.DamageEvent;
import ryad.games.ergoengine.interfaces.ActionEvent;
import ryad.games.ergoengine.interfaces.Damageable;

/**
 * A basic component for tracking a game object's health.
 * @author Jason Welch
 */
public class LifeComponent extends GameComponent implements Damageable {
	//===================================
	// Members
	//===================================
	protected float maxHealth;
	protected float currentHealth;
	protected GameObject owner;
	
	//===================================
	// Constructor
	//===================================
	public LifeComponent(GameObject owner, float maxHealth) {
		super("life");
		this.owner = owner;
		this.setMaxHealth(maxHealth);
		this.currentHealth = maxHealth;
	}

	//===================================
	// Getters/Setters
	//===================================
	public void setMaxHealth(float health){
		this.maxHealth = health;
	}
	
	public float getMaxHealth(){
		return this.maxHealth;
	}
	
	public float getHealth(){
		return this.currentHealth;
	}
	
	//===================================
	// Methods
	//===================================
	@Override protected void onStartup() {
		super.onStartup();
		this.currentHealth = this.maxHealth;
	}
	
	@Override public void eventCallback(ActionEvent event) {
		if(event instanceof DamageEvent){
			DamageEvent de = ((DamageEvent)event);
			this.onTakeDamage(de);
		}
	}
	
	@Override public void onTakeDamage(DamageEvent e) {
		if(this.owner.getActive()) {
			this.currentHealth -= e.getDamage();
		}
	}

	@Override protected void onUpdate(float time) {
		super.onUpdate(time);
		
		if(this.owner.getActive()) {
			if(this.currentHealth < 0) {
				//System.out.println("Life is below zero for "+this.owner.getId());
				this.owner.setActive(false);
				this.owner.setVisible(false);
			}
		}
	}	
}
