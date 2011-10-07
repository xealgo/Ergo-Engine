package ryad.games.ergoengine;

import ryad.games.ergoengine.interfaces.ActionEvent;
import ryad.games.ergoengine.interfaces.ActionListener;

/**
 * Base for all game components
 * @author Jason Welch
 */
public abstract class GameComponent implements ActionListener {
	//===================================
	// Members
	//===================================
	protected boolean active;
	protected boolean initialized;
	//protected BaseObject owner;
	
	protected String type = "GameComponent";
	
	//===================================
	// Constructors
	//===================================
	public GameComponent(String type) {
		super();
		this.active = true;
		this.initialized = false;
		this.type = type;
		//this.owner = null;
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public boolean isActive(){
		return this.active;
	}
	
	public void activate(){
		this.active = true;
	}
	
	public void deactivate(){
		this.active = false;
	}
	
	public boolean isInitialized(){
		return this.initialized;
	}
	
	public String getType(){
		return this.type;
	}
	
	/**public void setOwner(BaseObject owner){
		this.owner = owner;
	}
	
	public BaseObject getOwner(){
		return this.owner;
	}*/
	
	//===================================
	// Methods
	//===================================
	
	/**
	 * Handle incoming events.
	 * @param event
	 */
	@Override public void eventCallback(ActionEvent event) {
		
	}
	
	/**
	 * Call this method during initialization.
	 */
	public void startup(){
		if(!this.initialized){
			this.onStartup();
			this.initialized = true;
		}
	}
	
	/**
	 * Override for specific functionality.
	 */
	protected void onStartup() { }
	
	/**
	 * Call this method to shutdown/stop this component.
	 */
	public void shutdown(){
		if(this.initialized){
			this.active = false;
			this.onShutdown();
		}
	}
	
	/**
	 * Override for specific functionality.
	 */
	protected void onShutdown() { }
	
	/**
	 * Public update method. Implement onUpdate for your
	 * components.
	 * @param time
	 */
	public void update(float time) {
		if(this.active){
			this.onUpdate(time);
		}
	}
	
	/**
	 * Override this method in your component.
	 * @param time
	 */
	protected void onUpdate(float time) { }
	
}
