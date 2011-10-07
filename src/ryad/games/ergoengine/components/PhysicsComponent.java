package ryad.games.ergoengine.components;

import ryad.games.ergoengine.GameComponent;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.interfaces.Dynamic;
import ryad.games.ergoengine.system.PhysicsSystem;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A basic physics component. Any object that uses
 * this component, will be linked with the physics
 * engine which will probably remain Box2D for the
 * ErgoEngine.
 * 
 * @author Jason Welch
 */
public class PhysicsComponent extends GameComponent {
	//===================================
	// Members
	//===================================
	protected PhysicsSystem physicsSystem;
	protected Body physicsBody;
	protected GameObject gameObject;
	protected Vector2 tmp = new Vector2();
	protected boolean touched = false;
	
	//===================================
	// Constructor
	//===================================
	public PhysicsComponent(PhysicsSystem physicsSystem, GameObject gameObject) {
		super("physics");
		this.physicsSystem = physicsSystem;
		this.gameObject = gameObject;
		this.physicsBody = null;
	}
	
	//===================================
	// Getters and setters
	//===================================
	public PhysicsSystem getPhysicsSystem(){
		return this.physicsSystem;
	}
	
	public void setPhysicsSystem(PhysicsSystem physicsSystem){
		this.physicsSystem = physicsSystem;
	}
	
	public Body getPhysicsBody(){
		return this.physicsBody;
	}
	
	public void setPhysicsBody(Body physicsBody) {
		this.physicsBody = physicsBody;
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	//===================================
	// Methods
	//===================================
	@Override protected void onStartup() {
		if(this.physicsSystem != null){
			this.physicsSystem.register(this);
			this.createBody();
		}
	}
	
	/**
	 * Create our physics body
	 */
	protected void createBody(){
		//empty
	}

	@Override protected void onShutdown() {
		if(this.physicsSystem != null){
			this.physicsSystem.unregister(this);
		}
	}

	@Override protected void onUpdate(float time) {
		if(this.gameObject == null || this.physicsBody == null) return;
		
		float ratio = this.physicsSystem.getPixelRatio();
			
		if(this.gameObject.getActive()){
		
			this.gameObject.setPosition(ratio*this.physicsBody.getPosition().x-this.getGameObject().getMaterial().getWidth()/2,
										ratio*this.physicsBody.getPosition().y-this.getGameObject().getMaterial().getHeight()/2);
			
			this.gameObject.setRotation((float)(this.physicsBody.getAngle())*com.badlogic.gdx.math.MathUtils.radiansToDegrees);
		} else {
			this.physicsBody.setActive(false);
		}
	}
	
	/**
	 * Public interface method
	 * @param other GameObject
	 */
	public void handleCollision(GameObject other){
		this.onCollision(other);
	}
	
	/**
	 * Override for specific functionality..
	 * @param other GameObject
	 */
	protected void onCollision(GameObject other){
		this.touched = true;
		//System.out.println("collision call for "+this.gameObject.getId());
	}
}
