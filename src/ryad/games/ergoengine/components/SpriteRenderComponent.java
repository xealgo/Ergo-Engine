package ryad.games.ergoengine.components;

import ryad.games.ergoengine.GameObject;
import ryad.games.ergoengine.graphics.Material;
import ryad.games.ergoengine.graphics.OrthoCamera;
import ryad.games.ergoengine.system.RenderSystem;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Sprite rendering component..can be extended for animated
 * sprites.
 * @author Jason Welch
 */
public class SpriteRenderComponent extends RenderComponent {
	//===================================
	// Members
	//===================================
	protected Sprite sprite;
	protected boolean loadTexture = true;
	
	//===================================
	// Constructors
	//===================================
	public SpriteRenderComponent(RenderSystem renderSystem, GameObject owner, boolean loadTexture) {
		super(renderSystem, owner);
		this.sprite = new Sprite();
		this.loadTexture = loadTexture;
	}
	
	//===================================
	// Getters and Setters
	//===================================

	
	//===================================
	// Methods
	//===================================
	/**
	 * Gets called during the initialization of this component.
	 */
	@Override protected void onStartup() {
		super.onStartup();
		if(this.loadTexture){
			this.owner.getMaterial().loadTextureRegion(this.renderSystem.getTextureManager());
		}
	}
	
	/**
	 * Overrides the basic renderer and adds support for objects
	 * with 'parents'.
	 */
	@Override public int getOrder(){
		if(this.owner.getParent() != null){
			return this.owner.getParent().getDrawOrder()+1;
		}
		return this.owner.getDrawOrder();
	}
	
	/**
	 * Render the sprite graphic.
	 * @param batch SpriteBatch to send draw calls to.
	 * @param cam OrthoCamera
	 */
	@Override protected void onRender(SpriteBatch batch, OrthoCamera cam){

		try {
			
			this.owner.setDrawOrder((int)-this.owner.getPosition().y);
			
			if(this.sprite == null || this.owner.getMaterial().getTextureRegion() == null || batch == null) {
				System.out.println("couldn't render sprite");
				return;
			}

			Material mat = this.owner.getMaterial();
			this.owner.getCenter().x = mat.getWidth()/2;
			this.owner.getCenter().y = mat.getHeight()/2;
			
			this.sprite.setRegion(mat.getTextureRegion());
			this.sprite.setSize(mat.getWidth(), mat.getHeight());
			this.sprite.setScale(owner.getScale().x,owner.getScale().y);
			this.sprite.setPosition(owner.getPosition().x, owner.getPosition().y);
			this.sprite.setRotation(owner.getRotation());
			this.sprite.setColor(mat.getColor());
			this.sprite.setOrigin(this.owner.getCenter().x, this.owner.getCenter().y);
			
			if(cam.rectInBounds(this.sprite.getBoundingRectangle())){
				this.sprite.draw(batch);
			} 
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
