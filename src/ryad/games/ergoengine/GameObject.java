package ryad.games.ergoengine;

import ryad.games.ergoengine.graphics.Material;

import com.badlogic.gdx.math.Vector2;

/**
 * GameObjects are all objects that can be seen, collided with, moved, etc. 
 * This basic class does little more than store data in which components use 
 * to communicate to each other through.
 * @author Jason Welch
 */
public class GameObject extends BaseObject {
	//===================================
	// Members
	//===================================
	protected GameObject parent;
	protected int drawOrder;
	protected boolean visible;
	protected Material material;
	protected float rotation;
	protected Vector2 position;
	protected Vector2 scale;
	protected Vector2 velocity;
	protected Vector2 targetVelocity;
	protected Vector2 acceleration;
	protected Vector2 center;
	protected Vector2 facing;
	protected boolean ignoresCollisions;
	public Vector2 impulse = new Vector2(0,0);
	
	//===================================
	// Constructors
	//===================================
	public GameObject() {
		this.parent = null;
		this.visible = true;
		this.drawOrder = 0;
		this.material = new Material();
		this.rotation = 0;
		this.position = new Vector2(0,0);
		this.velocity = new Vector2(0,0);
		this.acceleration = new Vector2(1,1);
		this.targetVelocity = new Vector2(0,0);
		this.scale = new Vector2(1,1);
		this.facing = new Vector2(-1,0);
		this.center = new Vector2(1,1);
		this.ignoresCollisions = false;
	}
	
	//===================================
	// Getters and Setters
	//===================================	
	
	// parent relation
	public GameObject getParent(){ 
		return this.parent; 
	}
	
	public void setParent(GameObject parent){ 
		this.parent = parent; 
	}
	
	// texture, color, etc.
	public Material getMaterial() { 
		return this.material; 
	}
	
	public void setMaterial(Material mat){ 
		this.material = mat;
	}
	
	// order at which to render this game object
	public void setDrawOrder(int drawOrder){
		this.drawOrder = drawOrder;
	}
	
	public int getDrawOrder(){
		return this.drawOrder;
	}
	
	// to draw or not to draw, that is the question!
	public boolean isVisible(){
		return this.visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	// angular rotation
	public float getRotation(){
		return this.rotation;
	}
	public void setRotation(float rot){
		this.rotation = rot;
	}
	
	// spatial position
	public Vector2 getPosition(){
		return this.position;
	}
	
	public void setPosition(Vector2 pos){
		this.position = pos;
	}
	
	public void setPosition(float x, float y){
		this.position.set(x, y);
	}
	
	// size scaling
	public Vector2 getScale(){
		return this.scale;
	}
	
	public void setScale(Vector2 scale){
		this.scale = scale;
	}
	
	public void setScale(float x, float y){
		this.scale.set(x,y);
	}
	
	// movement velocity
	public Vector2 getVelocity() {
		return this.velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(float x, float y){
		this.velocity.set(x, y);
	}
	
	// target velocity to reach
	public Vector2 getTargetVelocity() {
		return this.targetVelocity;
	}
	
	public void setTargetVelocity(Vector2 velocity) {
		this.targetVelocity = velocity;
	}
	
	public void setTargetVelocity(float x, float y){
		this.targetVelocity.set(x, y);
	}

	// acceleration ( used more in vehicles, rockets, etc)
	public Vector2 getAcceleration() {
		return this.acceleration;
	}
	
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}
	
	public void setAcceleration(float x, float y){
		this.acceleration.set(x, y);
	}
	
	public Vector2 getFacing() {
		return this.facing;
	}
	
	public void setFacing(Vector2 facing) {
		this.facing = facing;
	}
	
	public void setFacing(float x, float y){
		this.facing.set(x, y);
	}
	
	// center of this game object
	public void setCenter(float x, float y) {
		this.center.set(x, y);
	}
	
	public Vector2 getCenter(){
		return this.center;
	}
	
	public boolean ignoresCollisions() {
		return ignoresCollisions;
	}

	public void setIgnoresCollisions(boolean ignoresCollisions) {
		this.ignoresCollisions = ignoresCollisions;
	}

	//===================================
	// Methods
	//===================================	
	/**
	 * Collision event handler..
	 * @param other GameObject
	 */
	public void handleCollision(GameObject other){
		if(!this.ignoresCollisions){
			this.onCollision(other);
		}
	}
	
	/**
	 * The actual collision event processing method...override to do
	 * anything useful.
	 * @param other GameObject
	 */
	protected void onCollision(GameObject other) {
		//empty for now..
	}
}
