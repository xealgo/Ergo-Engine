package ryad.games.ergoengine.components;

import ryad.games.ergoengine.GameComponent;
import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.graphics.OrthoCamera;
import ryad.games.ergoengine.interfaces.Renderable;
import ryad.games.ergoengine.system.RenderSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A component for sending draw calls to the RenderSystem.
 * 
 * @author Jason Welch
 */
public abstract class RenderComponent extends GameComponent implements Renderable {
	//===================================
	// Members
	//===================================
	protected GameObject owner;
	protected RenderSystem renderSystem;
	
	//===================================
	// Constructors
	//===================================
	public RenderComponent(RenderSystem renderSystem, GameObject owner) {
		super("render");
		this.renderSystem = renderSystem;
		this.owner = owner;
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public RenderSystem getRenderSystem( ){
		return this.renderSystem;
	}
	
	public void setRenderSystem(RenderSystem renderSystem){
		this.renderSystem = renderSystem;
	}

	//===================================
	// Methods
	//===================================
	
	/**
	 * Gets the order for when this object should get drawn.
	 */
	@Override public int getOrder(){
		return this.owner.getDrawOrder();
	}
	
	/**
	 * Initializes this component
	 */
	@Override protected void onStartup() {
		//System.out.println("RenderComponent onStartup");
		this.renderSystem.register(this);
	}

	/**
	 * Shuts this component down
	 */
	@Override protected void onShutdown() {
		//System.out.println("RenderComponent onShutdown");
		this.renderSystem.unregister(this);
	}
	
	/**
	 * Our public drawing method..get's called by the render system.
	 * @param batch
	 * @param cam
	 */
	@Override public void render(SpriteBatch batch, OrthoCamera cam){
		if(this.owner.isVisible()){
			this.onRender(batch, cam);
		}
	}
	
	/**
	 * Override to do any real drawing
	 * @param batch
	 * @param cam
	 */
	protected void onRender(SpriteBatch batch, OrthoCamera cam) {
		// empty
	}
}
