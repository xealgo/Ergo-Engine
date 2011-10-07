package ryad.games.ergoengine.interfaces;


/**
 * Generic event interface
 * @author Jason Welch
 */
public interface ActionEvent<Listener> {
	public Object getSender();
	public Listener getListener();
	public void setListener(Listener listener);
	public void handleEvent(ActionEvent event);
}
