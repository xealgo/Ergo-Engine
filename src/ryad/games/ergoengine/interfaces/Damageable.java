package ryad.games.ergoengine.interfaces;

import ryad.games.ergoengine.events.DamageEvent;

/**
 * Interface for all anything that can be damaged.
 * @author Jason Welch
 */
public interface Damageable {
	public void onTakeDamage(DamageEvent e);
}
