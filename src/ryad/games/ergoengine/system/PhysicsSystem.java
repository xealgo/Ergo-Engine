package ryad.games.ergoengine.system;

import java.util.ArrayList;
import java.util.List;

import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.components.PhysicsComponent;
import ryad.games.ergoengine.interfaces.Dynamic;
import ryad.games.ergoengine.interfaces.Subsystem;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Wraps up box2D and adds some higher level functionality.
 * Manages physics components.
 * 
 * TODO: createCharacterBox was only suppose to be used for a test..
 * It makes more sense to have it defined within a physics component
 * via the createBody method.
 * 
 * @author Jason Welch
 */
public class PhysicsSystem implements Subsystem<PhysicsComponent>, ContactListener {
	//===================================
	// Members
	//===================================
	protected final float PixelRatio = 30f; // how many pixels to a meter?
	
	protected World physicsWorld;
	protected List<PhysicsComponent> components;
    protected Box2DDebugRenderer renderer;
    protected boolean debug;
    protected Camera cam;
	
	//===================================
	// Constructor
	//===================================
	public PhysicsSystem(Camera cam, boolean debug) {
		this.physicsWorld = new World(new Vector2(0,0),true);
		this.components = new ArrayList<PhysicsComponent>();
		this.renderer = new Box2DDebugRenderer();   
		this.cam = cam;
		this.debug = debug;		
		this.physicsWorld.setContactListener(this);
	}
	
	//===================================
	// Getters and setters
	//===================================
	public List<PhysicsComponent> getComponents() {
		return components;
	}

	public void setComponents(List<PhysicsComponent> components) {
		this.components = components;
	}

	public float getPixelRatio() {
		return PixelRatio;
	}

	public World getPhysicsWorld() {
		return physicsWorld;
	}
	
	//===================================
	// Methods
	//===================================
	/**
	 * Initialize this sub system
	 */
	@Override public void startup() {
		//this.createFloor();
	}

	/**
	 * Shut this sub system down
	 */
	@Override public void shutdown() {
		
	}

	/**
	 * Update this sub system
	 * @param time float
	 */
	@Override public void update(float time) {
		this.physicsWorld.step(time, 8, 3);
		
		if(this.debug){
			renderer.render(this.physicsWorld, cam.combined.scale(PixelRatio, PixelRatio, 0));
		}
	}

	/**
	 * Registers a physics component instance.
	 * @param component PhysicsComponent
	 */
	@Override public void register(PhysicsComponent component) {
		if(component == null) return;
		try {
			System.out.println("PhysicsSystem registering component...");
			if(component.getPhysicsSystem() != this){
				component.setPhysicsSystem(this);
			}
			this.components.add(component);
		} catch(Exception e){
			System.out.println("PhysicsSystem: Failed to register component!");
		}
	}

	/**
	 * Unregister a component..
	 */
	@Override public void unregister(PhysicsComponent component) {
		
	}
	
	/**
	 * Creates a new physics body object
	 * TODO: make configurable via params.
	 * @param density
	 * @param userData
	 * @return body Body
	 */
	public Body createCharacterBox(float density, PhysicsComponent physicsComponent) {
		PolygonShape shape = new PolygonShape();
		Vector2 size = new Vector2(	physicsComponent.getGameObject().getMaterial().getWidth()/(2*PixelRatio)*physicsComponent.getGameObject().getScale().x,
									physicsComponent.getGameObject().getMaterial().getHeight()/(2*PixelRatio)*physicsComponent.getGameObject().getScale().y);
		shape.setAsBox(size.x,size.y);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = physicsComponent.getGameObject().getPosition().x/PixelRatio;
		bodyDef.position.y = physicsComponent.getGameObject().getPosition().y/PixelRatio;
		bodyDef.allowSleep = true;
		bodyDef.angle = (float) (physicsComponent.getGameObject().getRotation()*com.badlogic.gdx.math.MathUtils.degreesToRadians);
		bodyDef.linearDamping = 1.0f;
		bodyDef.angularDamping = 0.7f;
		bodyDef.active = true;
		bodyDef.fixedRotation = true;
		
		Body body = this.physicsWorld.createBody(bodyDef);
		FixtureDef boxFixture = new FixtureDef();
		boxFixture.density = density;
		boxFixture.friction = 4.0f;
		boxFixture.restitution = 0.5f;
		boxFixture.shape = shape;
		//boxFixture.isSensor = true;
		body.createFixture(boxFixture);
		shape.dispose();
		
		body.setUserData(physicsComponent);
		return body;
	}

	@Override public void beginContact(Contact point) {
		PhysicsComponent obj1 = (PhysicsComponent)(point.getFixtureA().getBody().getUserData());
		PhysicsComponent obj2 = (PhysicsComponent)(point.getFixtureB().getBody().getUserData());
		
		if(obj1 == null || obj2 == null) return;
		
		obj1.handleCollision(obj2.getGameObject());
		obj2.handleCollision(obj1.getGameObject());
	}

	@Override public void endContact(Contact arg0) {
		
	}

	@Override public void postSolve(Contact arg0, ContactImpulse arg1) {
		
	}

	@Override public void preSolve(Contact arg0, Manifold arg1) {
		
	}
}
