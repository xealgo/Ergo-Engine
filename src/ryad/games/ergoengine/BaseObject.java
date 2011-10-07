package ryad.games.ergoengine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import ryad.games.ergoengine.interfaces.ActionEvent;
import ryad.games.ergoengine.interfaces.ActionListener;

/**
 * Basic game object class. Serves as a container for
 * components and contains all of the properties that
 * ALL of our game objects are going to need.
 * @author Jason Welch
 */
public abstract class BaseObject implements ActionListener {
	//===================================
	// Members
	//===================================
	protected BasicGameWorld world;
	protected boolean active;
	protected String type; 
	protected String id;
	protected boolean loaded;
	protected boolean initialized;
	protected boolean remove;
	
	// component lists
	protected HashMap<String, GameComponent> components;
	protected HashMap<String, GameComponent> componentsToAdd;
	
	//===================================
	// Constructors
	//===================================
	public BaseObject(){
		this.world = null;
		this.setActive(true);
		this.setType("entity");
		this.setId("");
		this.remove = false;
		this.loaded = false;
		this.initialized = false;
		this.components = new HashMap<String, GameComponent>();
		this.componentsToAdd = new HashMap<String, GameComponent>();
	}
	
	//===================================
	// Getters and setters
	//===================================	
	// game world this object belongs to
	public BasicGameWorld getWorld() { 
		return world; 
	}
	
	public void setWorld(BasicGameWorld world) { 
		this.world = world; 
	}
	
	public final void setId(String id){
		this.id = id.toLowerCase();
	}
	
	public final String getId(){
		return id;
	}
	
	/**
	 * Get this entities type
	 * @return
	 */
	public final String getType(){
		return this.type;
	}
	
	/**
	 * Set this entities type
	 * @param type
	 */
	public final void setType(String type){
		this.type = type;
	}
	
	/**
	 * Is this object flagged for removal??
	 * @return
	 */
	public final boolean getRemoveState() {
		return this.remove;
	}

	/**
	 * Flag this object for removal
	 */
	public final void flagForRemoval() {
		this.remove = true;
	}
	
	/**
	 * Is this object to be removed?
	 * @return
	 */
	public final boolean flaggedForRemoval(){
		return this.remove;
	}
	
	/**
	 * Set active
	 * @param a
	 */
	public final void setActive(boolean a) {
		this.active = a;
	}
	
	/**
	 * Get active
	 * @return
	 */
	public final boolean getActive(){
		return this.active;
	}
	
	/**
	 * If this entity has been loaded...
	 * @return
	 */
	public final boolean isLoaded(){
		return this.loaded;
	}
	
	/**
	 * If this entity has been initialized...
	 * @return
	 */
	public final boolean isInitialized(){
		return this.initialized;
	}
	
	/**
	 * Override this method to add your additional values.
	 * @param properties
	 */
	public void setPropertiesFromHashmap(HashMap<String,String> properties){
		if(properties != null){
			if(properties.containsKey("id")){
				this.id = properties.get("id");
			}
			if(properties.containsKey("active")){
				this.active = Boolean.parseBoolean(properties.get("active"));
			}
		}
	}
	
	//===================================
	// Methods
	//===================================
	/**
	 * Add a component to this object.
	 * @param component
	 */
	public void addComponent(GameComponent component){
		if(component != null){
			component.startup();
			this.componentsToAdd.put(component.getType(), component);
		}
	}
	
	/**
	 * Helper methods for adding our recently added components to
	 * the main components list.
	 * @throws Exception  
	 */
	protected void addComponents() {
		if(!this.componentsToAdd.isEmpty()){
			for(Iterator<Map.Entry<String, GameComponent>> iter = this.componentsToAdd.entrySet().iterator(); iter.hasNext();){
				Map.Entry<String, GameComponent> entry = iter.next();
				GameComponent component = entry.getValue();
				
				if(!this.components.containsKey(component.getType())){
					//System.out.println("Adding "+component.getType()+" component.");
					this.components.put(component.getType(), component);
					this.componentsToAdd.remove(entry);
				} else {
					System.out.println("Can not add duplicated component type: "+component.getType());
				}
			}
			
			this.componentsToAdd.clear();
		}
	}
	
	/**
	 * Removes a component from this object
	 * @param component
	 */
	public void removeComponent(String name){
		if(name.length() < 1) return;
		
		for(Iterator<Map.Entry<String, GameComponent>> iter = this.componentsToAdd.entrySet().iterator(); iter.hasNext();){
			Map.Entry<String, GameComponent> entry = iter.next();
			GameComponent component = entry.getValue();
			
			if(this.components.containsKey(name)){
				System.out.println("Removing "+name+" component.");
				this.components.get(name).shutdown();
				this.components.remove(name);
				iter.remove();
			} else {
				System.out.println("Does not contain a "+name+" component.");
			}
		}
	}
	
	/**
	 * Searches for a component within this object.
	 * @param name
	 * @return GameComponent or null if not found
	 */
	public GameComponent findComponent(String name){
		if(this.components.isEmpty()) return null;
		
		if(this.components.containsKey(name)){
			return this.components.get(name);
		}
		System.out.println("could not find component: "+name);
		return null;
	}
	
	/**
	 * Initialize this object.
	 */
	public final void init(){
		if(!this.initialized){
			
			this.onInit();
			this.initialized = true;
		}
	}
	
	/**
	 * Override for additional initialization of
	 * systems/pointers, etc.
	 */
	protected void onInit() { }
	
	/**
	 * Update all components
	 * @param time
	 */
	protected final void updateComponents(float time){
		this.addComponents();
		
		if(!this.components.isEmpty()){
			for(Iterator<Map.Entry<String, GameComponent>> iter = this.components.entrySet().iterator(); iter.hasNext();){
				Map.Entry<String, GameComponent> entry = iter.next();
				GameComponent component = entry.getValue();
				
				if(component != null){
					component.update(time);
				}
			}
		}
	}
	
	/**
	 * Shutdown / remove all components
	 */
	protected final void shutdownComponents(){
		if(!this.components.isEmpty()){
			for(Iterator<Map.Entry<String, GameComponent>> iter = this.components.entrySet().iterator(); iter.hasNext();){
				Map.Entry<String, GameComponent> entry = iter.next();
				GameComponent component = entry.getValue();
				
				if(component != null){
					component.shutdown();
					iter.remove();
				}
			}
		}
	}
	
	/**
	 * Load content for this entity. Must be called after init()
	 */
	public final void load() {
		if(!this.loaded && this.initialized){
			this.onLoad();
			
			this.addComponents();
			
			if(!this.components.isEmpty()){
				for(Iterator<Map.Entry<String, GameComponent>> iter = this.components.entrySet().iterator(); iter.hasNext();){
					Map.Entry<String, GameComponent> entry = iter.next();
					GameComponent component = entry.getValue();
					
					if(component != null){
						component.startup();
					}
				}
			}
			
			this.loaded = true;
		}
	}
	
	/**
	 * Load content for this entity
	 */
	protected void onLoad() { }
	
	/**
	 * Call when removing this object. The implementation
	 * details are object specific.
	 */
	public final void destroy(){
		this.shutdownComponents();
		this.onDestroy();
	}
	
	/**
	 * Should be overrode for your objects
	 * to unload data and free up any references.
	 */
	protected void onDestroy() { }
	
	/**
	 * public update method
	 * @param time
	 */
	public final void update(float time) {
		this.updateComponents(time);
		if(this.active){
			this.onUpdate(time);
		}
	}
	
	/**
	 * Gets called when the object is updated
	 * @param time
	 */
	protected void onUpdate(float time) { }
	
	/**
	 * Send the event to all components of this object.
	 * @param event
	 */
	protected void processEvent(ActionEvent event){
		if(!this.components.isEmpty()){
			
			if(!this.components.isEmpty()){
				for(Iterator<Map.Entry<String, GameComponent>> iter = this.components.entrySet().iterator(); iter.hasNext();){
					Map.Entry<String, GameComponent> entry = iter.next();
					GameComponent component = entry.getValue();
					
					if(component != null){
						if(component != event.getSender()){
							component.eventCallback(event);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Handles incoming events...
	 * Our objects can override this and prevent components from 
	 * receiving the event... or they can modify the event, or
	 * they can ignore it altogether. Typically this will be called
	 * by the object manager to send it system wide events such as
	 * GameOver, PlayerDeath, etc. Other objects can even call this
	 * such as triggers.
	 * @param event
	 */
	public void eventCallback(ActionEvent event) {
		this.processEvent(event);
	}
}
