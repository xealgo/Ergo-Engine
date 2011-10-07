package ryad.games.ergoengine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Was suppose to be a useful wrapper to LibGDX's default orthographic camera,
 * but over time, it's slowly gotten less and less useful..I still use it for
 * my projects though.
 * @author Jason Welch
 */
public class OrthoCamera extends OrthographicCamera {
	//==============================================
	// Member vars
	//==============================================
	private final Vector3 tmp = new Vector3();
	protected Vector2 drag; // camera velocity slow down
	protected Vector2 old_position;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	protected Vector2 min_position;
	protected Vector2 max_position;
	protected Vector2 min_velocity; // how slow can the camera be, before stopping.
	protected Vector2 max_velocity;
	protected Vector2 bounce;
	protected Rectangle cam_rect;
		
	/**
	 * Constructor
	 */
	public OrthoCamera() {
		super();
		this.setDefaults();
	}

	/**
	 * Constructor 
	 * @param viewportWidth
	 * @param viewportHeight
	 */
	public OrthoCamera(float viewportWidth, float viewportHeight) {
		super(viewportWidth, viewportHeight);
		this.setDefaults();
	}
	
	//==============================================
	// Getters and setters
	//==============================================
	public void setMinVelocity(float x, float y){
		//make sure not less than zero!
		this.min_velocity.set(x,y);
	}
	
	public void setMaxVelocity(float x, float y){
		//make sure not less than zero!
		this.max_velocity.set(x,y);
	}
	
	//==============================================
	// Control methods
	//==============================================
	public void moveRight(){
		velocity.x += acceleration.x;
	}

	public void moveLeft(){
		velocity.x += -acceleration.x;
	}

	public void moveUp(){
		velocity.y += -acceleration.y;
	}
	
	public void moveDown(){
		velocity.y += acceleration.y;
	}
		
	//==============================================
	// Methods
	//==============================================	
	/**
	 * Helper method to avoid copying code for both constructors
	 * @return void
	 */
	private void setDefaults(){
		//this.up.set(0,-1,0);
		this.drag = new Vector2(0.01f,0.01f);
		this.bounce = new Vector2(-0.5f,0);
		this.old_position = new Vector2();
		this.velocity = new Vector2(0,0);
		this.acceleration = new Vector2(50,25);
		this.min_position = new Vector2(5,-500);
		this.max_position = new Vector2(2250,500);
		this.min_velocity = new Vector2(-1000f,-500f);
		this.max_velocity = new Vector2(1000f,500f);
		this.cam_rect = new Rectangle();
	//	this.zoom = 1;
	}
	
	/**
	 * test if a rectangle shapes is within the bounds of the view frustum..
	 * @param rect The rectangle shape
	 * @return boolean
	 */
	public boolean rectInBounds(Rectangle rect){
		return this.cam_rect.overlaps(rect);
	}
	
	/**
	 * Helper method to determine if the camera
	 * has changed positions since the last frame.
	 * @return boolean
	 */
	public boolean hasMoved(){
		return (Math.abs(((position.x - old_position.x)+(position.y - old_position.y))) > 0);
	}
	
	/**
	 * Check the position againts the max position and adjust accordingly.
	 * @return void
	 */
	protected void checkBounds(){
		if(position.x >= max_position.x){
			position.x = max_position.x;
			velocity.x *= bounce.x; // makes bounce
		}
		
		if(position.x <= min_position.x){
			position.x = min_position.x;
			velocity.x *= bounce.x; // makes bounce
		}
		
		if(position.y >= max_position.y){
			position.y = max_position.y;
			velocity.y *= bounce.y;
		}
		
		if(position.y <= min_position.y){
			position.y = min_position.y;
			velocity.y *= bounce.y;
		}
	}
	
	/**
	 * Cap or floor velocity values.
	 * @return void
	 */
	protected void checkVelocity(){
		// cap velocity x
		if(velocity.x > max_velocity.x) velocity.x = max_velocity.x;

		// floor velocity x
		if(velocity.x < min_velocity.x) velocity.x = min_velocity.x;
						
		// cap velocity y
		if(velocity.y > max_velocity.y) velocity.y = max_velocity.y;
				
		// floor velocity y
		if(velocity.y < min_velocity.y) velocity.y = min_velocity.y;
	}
	
	/**
	 * Update method
	 * @param time
	 * @return void
	 */
	public void update(float time) {
		//to do: make this configurable via a boolean check
		//this.checkVelocity();
		
		//old_position.x = position.x;
		//old_position.y = position.y;
		
	//	velocity.x *= 1-drag.x;
		//velocity.y *= 1-drag.y;
		
		//position.x += (velocity.x * time);
		//position.y += (velocity.y * time);	
		
		//to do: make this configurable via a boolean check
		//this.checkBounds();
		
		// now adjust the camera's rectangle region
		//this.cam_rect.set(zoom*(position.x-viewportWidth/2), zoom*(position.y-viewportHeight/2), zoom*(position.x+viewportWidth+viewportWidth/2), zoom*(position.y+viewportHeight+viewportHeight/2));
		this.cam_rect.set(this.position.x - this.viewportWidth / 2.0f, this.position.y - this.viewportHeight / 2.0f,
				this.viewportWidth, this.viewportHeight);
		
		super.update();
	}
}
