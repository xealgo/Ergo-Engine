package ryad.games.ergoengine.interfaces;

import ryad.games.ergoengine.GameComponent;

/**
 * Subsystems are components of the game that manage various
 * game components and are used to separate logic.
 * @author Jason Welch
 */
public interface Subsystem<Component extends GameComponent> {
	public void startup();
	public void shutdown();
	public void update(float time);
	public void register(Component component);
	public void unregister(Component component);
}
