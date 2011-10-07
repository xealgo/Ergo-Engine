package ryad.games.ergoengine.graphics;

import ryad.games.ergoengine.TextureManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Deal with both visual and physical properties of an entity.
 * Textures, colors, physics data (such as density) etc. A lot
 * left to do!
 * @author Jason Welch
 */
public class Material {
	//===================================
	// Members
	//===================================
	protected Color color;
	protected TextureRegion textureRegion;
	protected String textureFile;
	protected TextureRegion[][] textureRegions;
	protected Texture sourceTexture;
	protected Vector2 scrollAmount;
	protected boolean flipX = false;
	protected boolean flipY = false;
	protected float density = 0.0f;
	
	//===================================
	// Constructors
	//===================================
	public Material(){
		this.color = new Color(1,1,1,1);
		this.textureRegion = null;
		this.textureFile = "";
		this.textureRegions = null;
		this.sourceTexture = null;
	}
	
	//===================================
	// Getters and Setters
	//===================================
	public TextureRegion[][] getTextureRegions(){
		return this.textureRegions;
	}
	
	public void setTextureRegions(TextureRegion[][] regions){
		this.textureRegions = regions;
	}

	public Texture getSourceTexture(){
		return this.sourceTexture;
	}
	
	public void setSourceTexture(Texture source){
		this.sourceTexture = source;
	}
	
	public TextureRegion getTextureRegion(){
		return this.textureRegion;
	}
	
	public void setTextureRegion(TextureRegion region){
		this.textureRegion = region;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void setColor(float r, float g, float b, float a){
		this.color.set(r, g, b, a);
	}
	
	public void setTextureFile(String ifn){
		this.textureFile = ifn;
	}
	
	public String getTextureFile(){
		return this.textureFile;
	}
	
	public float getWidth() {
		if(this.getTextureRegion() != null){
			return Math.abs(this.getTextureRegion().getRegionWidth());
		}
		return 0;
	}
	
	public float getHeight() {
		if(this.getTextureRegion() != null){
			return Math.abs(this.getTextureRegion().getRegionHeight());
		}
		return 0;
	}
	
	public void setScroll(float x, float y){
		if(this.scrollAmount == null) this.scrollAmount = new Vector2();
		this.scrollAmount.set(x,y);
	}
	
	public Vector2 getScroll(){
		if(this.scrollAmount == null) this.scrollAmount = new Vector2();
		return this.scrollAmount;
	}
	
	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	//===================================
	// Methods
	//===================================
	public void flipX() {
		this.flipX = true;
	}
	
	public void flipY() {
		this.flipY = true;
	}
	
	public void flipRegion(boolean x, boolean y){
		if(this.textureRegion == null) return;
		this.textureRegion.flip(x,y);
	}
	
	/**
	 * Load the texture region
	 */
	public boolean loadTexture(TextureManager tm){
		if(this.getTextureFile() != "" && tm != null){
			this.setSourceTexture(tm.getTexture(this.getTextureFile()));
			return true;
		} 
		return false;
	}

	/**
	 * Load the texture region
	 */
	public boolean loadTextureRegion(TextureManager tm){
		if(this.getTextureFile() != "" && tm != null){
			this.setTextureRegion(tm.getTextureRegion(this.getTextureFile(), this.flipX, this.flipY));
			return true;
		} 
		return false;
	}
	
	/**
	 * Load the texture region
	 */
	public boolean loadTextureRegions(TextureManager tm, int cellWidth, int cellHeight){
		if(this.getTextureFile() != "" && tm != null){
			this.setSourceTexture(tm.getTexture(this.getTextureFile()));
			this.setTextureRegions(tm.prepareTextureRegions(this.getSourceTexture(), cellWidth, cellHeight,  this.flipX, this.flipY));
			return true;
		}
		return false;
	}
	
	/**
	 * Destroy the material
	 */
	public void destroy(){
		this.textureRegion = null;
		this.color = null;
		this.textureRegions = null;
		this.sourceTexture = null;
	}
}
