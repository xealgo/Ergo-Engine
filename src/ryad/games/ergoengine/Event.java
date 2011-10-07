package ryad.games.ergoengine;

import ryad.games.ergoengine.interfaces.ActionEvent;
import ryad.games.ergoengine.interfaces.ActionListener;

/**
 * Simple event class..should get sub typed to do anything
 * useful.
 * @author Jason Welch
 */
public class Event implements ActionEvent<ActionListener> {
	//===================================
	// Members
	//===================================
	protected Object sender;
	protected ActionListener listener;
	
	//===================================
	// Constructors
	//===================================
	public Event(Object sender, ActionListener listener) {
		this.sender = sender;
		this.listener = listener;
	}
	
	//===================================
	// Getters and Setters
	//===================================
	@Override public ActionListener getListener() {
		return this.listener;
	}

	@Override public void setListener(ActionListener listener) {
		this.listener = listener;
	}
	
	@Override public Object getSender() {
		return this.sender;
	}

	//===================================
	// Methods
	//===================================
	@Override public void handleEvent(ActionEvent event) {
		
	}
	
	public void send(){
		this.listener.eventCallback(this);
	}
}
