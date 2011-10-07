package ryad.games.ergoengine.interfaces;

import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.system.PhysicsSystem;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Basic physics interface
 * @author Jason Welch
 */
public interface Dynamic {
	public PhysicsSystem getPhysicsSystem();
	public void setPhysicsSystem(PhysicsSystem physicsSystem);
	public Body getPhysicsBody();
	public void setPhysicsBody(Body physicsBody);
	public GameObject getGameObject();
	public void setGameObject(GameObject gameObject);
	public void handleCollision(GameObject gameObject);
}
