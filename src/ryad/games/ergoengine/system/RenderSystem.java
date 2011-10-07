package ryad.games.ergoengine.system;

import java.util.ArrayList;
import java.util.List;

import ryad.games.ergoengine.TextureManager;
import ryad.games.ergoengine.components.RenderComponent;
import ryad.games.ergoengine.graphics.OrthoCamera;
import ryad.games.ergoengine.interfaces.Renderable;
import ryad.games.ergoengine.interfaces.Subsystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The main render system - cycles through all renderables and calls their render method.
 * 
 * TODO: Currently, if a component was to get registered/unregistered within an update loop,
 * the engine would crash. To fix this, there needs to be two additional lists..one for new
 * components and one for components to be removed.
 * 
 * @author Jason Welch
 */
public class RenderSystem implements Subsystem<RenderComponent>{
	//===================================
	// Members
	//===================================
	protected SpriteBatch spriteBatch;
	protected List<Renderable> components;
	protected OrthoCamera camera;
	protected TextureManager textureManager;
	protected boolean drawSorting = false;
	
	//===================================
	// Constructors
	//===================================
	public RenderSystem(OrthoCamera camera){
		this.camera = camera;
		this.spriteBatch = new SpriteBatch();
		this.components = new ArrayList<Renderable>();
		this.textureManager = new TextureManager();
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public void setCamera(OrthoCamera camera){
		if(camera != null){
			this.camera = camera;
		}
	}
	
	public OrthoCamera getCamera(){
		return this.camera;
	}
	
	public TextureManager getTextureManager(){
		return this.textureManager;
	}
	
	//===================================
	// Methods
	//===================================
	/**
	 * Initializes this subsystem.
	 */
	@Override public void startup() {
		System.out.println("starting up render sub system");
	}
	
	/**
	 * Shut this subsystem down
	 */
	@Override public void shutdown(){
		this.components.clear();
		System.out.println("shutting down render sub system");
	}
	
	/**
	 * Bubble sort all entities by their order, which can really be anything.
	 * @param entities
	 */
	protected void sort(){
		if(!this.drawSorting) return;
		
		if(!components.isEmpty()){
			for(int i = 0; i < components.size() - 1; i++){
				Renderable r1 = components.get(i);
				Renderable r2 = components.get(i+1);
				if(r1 != null && r2 != null){
			
					if(r1.getOrder() > r2.getOrder()){
						components.set(i, r2);
						components.set(i+1, r1);
					}
				}
			}
		}
	}
	
	/**
	 * Register a rendering component
	 * @param component
	 */
	@Override public void register(RenderComponent component){
		if(component == null) return;
		try {
			//System.out.println("RenderSystem registering component...");
			if(component.getRenderSystem() != this){
				component.setRenderSystem(this);
			}
			this.components.add(component);
		} catch(Exception e){
			System.out.println("RenderSystem: Failed to register component!");
		}
	}
	
	/**
	 * Unregister a rendering component
	 * @param component
	 */
	@Override public void unregister(RenderComponent component){
		if(component == null) return;
		try {
			//System.out.println("RenderSystem unregistering component...");
			this.components.remove(component);
		} catch(Exception e){
			System.out.println("RenderSystem: Failed to unregister component!");
		}
	}
	
	/**
	 * Update this subsystem (Render all associated components)
	 * @param time float
	 */
	@Override public void update(float time) {
		if(this.components.isEmpty() || this.camera == null) return;
		try {
			this.sort();
			this.spriteBatch.setProjectionMatrix(this.camera.combined);
			this.spriteBatch.begin();
			
			// by passing the camera to the render call, the render component may
			// contain a spatial tree and can use the camera to determine what nodes
			// are visible...
			for(Renderable comp:this.components){
				comp.render(this.spriteBatch, this.camera);
			}
			
			this.spriteBatch.end();
			//System.out.println("draw calls: "+this.spriteBatch.renderCalls);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
