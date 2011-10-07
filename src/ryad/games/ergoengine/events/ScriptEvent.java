package ryad.games.ergoengine.events;

import java.util.HashMap;
import java.util.Map;

import ryad.games.ergoengine.Event;
import ryad.games.ergoengine.interfaces.ActionListener;

/**
 * A special event type that allows us to add data generically
 * that can later be converted into the expected type. This could
 * work well for networked games (subclass to NetworkEvent) or for 
 * any other case when data needs to be sent in a generic way.
 * @author Jason Welch
 */
public class ScriptEvent extends Event {
	//===================================
	// Members
	//===================================
	protected String eventName;
	protected Map<String, String> eventData;

	//===================================
	// Constructor
	//===================================
	public ScriptEvent(Object sender, ActionListener listener, String eventName) {
		super(sender, listener);
		this.eventData = new HashMap<String,String>();
		this.setEventName(eventName);
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public String getEventName(){
		return this.eventName;
	}
	
	public void setEventName(String eventName){
		this.eventName = eventName;
	}
	
	public Map<String,String> getEventData(){
		return this.eventData;
	}
	
	public void setEventData(Map<String,String> eventData){
		this.eventData = eventData;
	}
	
	public void setVar(String key, String value){
		if(key.length() > 0){
			this.eventData.put(key, value);
		}
	}
	
	public String getVar(String key){
		if(key.length() > 0){
			if(this.eventData.containsKey(key)){
				return this.eventData.get(key);
			}
		}
		return null;
	}

}
