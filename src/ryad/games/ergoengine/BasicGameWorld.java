package ryad.games.ergoengine;

import java.util.ArrayList;
import java.util.List;

import ryad.games.ergoengine.interfaces.ActionEvent;
import ryad.games.ergoengine.interfaces.ActionListener;
import ryad.games.ergoengine.interfaces.Subsystem;

/**
 * Manages game objects and subsystems.
 * @author Jason Welch
 */
public abstract class BasicGameWorld implements ActionListener {
	//===================================
	// Members
	//===================================
	protected List<BaseObject> objects;
	protected List<BaseObject> objectsToAdd;
	protected List<BaseObject> objectsToRemove;
	protected List<Subsystem> subsystems;
	
	/**
	 * Is our game world paused?
	 */
	protected boolean paused = false;
	
	/**
	 * Cancel loading/updating the game world!
	 */
	protected boolean cancel = false; 
	
	//===================================
	// Constructors
	//===================================
	public BasicGameWorld(){
		this.objects = new ArrayList<BaseObject>();
		this.objectsToAdd = new ArrayList<BaseObject>();
		this.objectsToRemove = new ArrayList<BaseObject>();
		this.subsystems = new ArrayList<Subsystem>();
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public void pause() { this.paused = true; }
	public void resume() { this.paused = false; }
	
	//===================================
	// Methods
	//===================================
	
	/**
	 * Adds a new game object to the world.
	 * @param object
	 */
	public void addObject(BaseObject object){
		if(object != null){
			object.setId("obj_"+this.objects.size()+this.objectsToAdd.size());
			object.setWorld(this);
			this.objectsToAdd.add(object);
			System.out.println("Spawned object: "+object.getId());
		}
	}
	
	/**
	 * Registers a subsystem
	 * @param system
	 */
	public void registerSubsystem(Subsystem system){
		if(system != null){	
			this.subsystems.add(system);
		}
	}
	
	/**
	 * Initializes the game world.
	 */
	public void init() {
		if(this.cancel) return;
		
		if(!this.objectsToAdd.isEmpty()){
			for(BaseObject obj:this.objectsToAdd){
				obj.init();
				obj.load();
				this.objects.add(obj);
			}
			this.objectsToAdd.clear();
		}
		
		if(this.subsystems.size() > 0){
			for(Subsystem system:this.subsystems){
				system.startup();
			}
		}
		
		this.onInit();
	}
	
	/**
	 * Override for anything useful..
	 */
	protected void onInit() { }
	
	/**
	 * Update the world
	 * @param time
	 */
	public void update(float time) {
		if(this.cancel || this.paused) return;
		
		// take our recently added objects and ads them
		// to our list of objects to update.
		if(!this.objectsToAdd.isEmpty()){
			for(BaseObject obj:this.objectsToAdd){
				obj.init();
				obj.load();
				this.objects.add(obj);
			}
			this.objectsToAdd.clear();
		}
		
		if(!this.objects.isEmpty()){
			for(BaseObject obj:this.objects){
				if(obj.active && !obj.flaggedForRemoval()){
					obj.update(time);
				}
				
				if(obj.flaggedForRemoval()){
					obj.setActive(false);
					this.objectsToRemove.add(obj);
				}
			}
		}
		
		if(this.subsystems.size() > 0){
			for(Subsystem system:this.subsystems){
				system.update(time);
			}
		}
	}
	
	/**
	 * Cleanup the world..
	 */
	public void destroy(){
		if(!this.objectsToRemove.isEmpty()){
			for(BaseObject obj:this.objectsToRemove){
				obj.destroy();
			}
		}
		
		this.objects.clear();
		this.objectsToAdd.clear();
		this.objectsToRemove.clear();
	}
	
	/**
	 * Handle incoming events
	 * @param event
	 */
	@Override public void eventCallback(ActionEvent event) {
		// empty for now..
	}
}
