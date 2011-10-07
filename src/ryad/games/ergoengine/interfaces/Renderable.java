package ryad.games.ergoengine.interfaces;

import ryad.games.ergoengine.graphics.OrthoCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interface for anything that can be rendered.
 * @author Jason Welch
 */
public interface Renderable {
	public int getOrder();
	public void render(SpriteBatch batch, OrthoCamera cam);
}
