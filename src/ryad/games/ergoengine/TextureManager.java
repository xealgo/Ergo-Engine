package ryad.games.ergoengine;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A simple texture manager.. needs a lot of features and improvements..
 * @author Jason Welch
 */
public class TextureManager {

	protected HashMap<String, Texture> textures;
	protected HashMap<String, TextureRegion> textureRegions;
	
	/**
	 * Constructor
	 */
	public TextureManager(){
		textures = new HashMap<String, Texture>();
		textureRegions = new HashMap<String, TextureRegion>();
	}
	
	/**
	 * Get the number of textures
	 * @return
	 */
	public int getTextureCount(){
		return textures.size();
	}
	
	/**
	 * Get the number of texture regions
	 * @return
	 */
	public int getTextureRegionCount(){
		return textureRegions.size();
	}
	
	/**
	 * Load a texture or get it from the previously loaded textures
	 * @param filename
	 * @return
	 */
	public Texture getTexture(String filename){
		filename = filename.toLowerCase();
		if(textures.containsKey(filename)){
			return textures.get(filename);
		} else {
			Texture texture = null;
			try {
				texture = new Texture(Gdx.files.internal("assets/"+filename));
				if(texture != null){
					textures.put(filename, texture);
				} 
			} catch(Exception e){
				System.out.println("Failed to load texture "+filename);
			}
			
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return texture;
		}
	}
	
	/**
	 * Load a texture region or get it from the previously loaded texture regions
	 * @param filename
	 * @return
	 */
	public TextureRegion getTextureRegion(String filename, boolean horizontal, boolean vertical){
		filename = filename.toLowerCase();
		
		if(textureRegions.containsKey(filename)){
			return new TextureRegion(textureRegions.get(filename));
		} else {
			Texture texture = this.getTexture(filename);
			TextureRegion region = null;
			if(texture != null){
				region = new TextureRegion(texture);
				if(region != null){
					region.flip(horizontal,vertical);
					textureRegions.put(filename, region);
					System.out.println("Created new texture region "+filename);
				} else {
					System.out.println("Failed to create new texture region "+filename);
				}
			} else {
				System.out.println("Could not create texture region for "+filename);
			}
			return region;
		}
	}
	
	/**
	 * Generates an array of texture regions based on the parameter.
	 * @param source
	 * @return TextureRegion[][]
	 */
	public TextureRegion[][] prepareTextureRegions(Texture source, int frameWidth, int frameHeight, boolean horizontal, boolean vertical){
		if(source != null){
			TextureRegion[][] regions = TextureRegion.split(source,frameWidth,frameHeight);
			
			if(vertical || horizontal){
				this.flipTextureRegions(regions,vertical,horizontal);
			}
			return regions;
		}
		return null;
	}
	
	/**
	 * Goes through a list of texture regions and "flips" them.
	 * @param textures
	 */
	public void flipTextureRegions(TextureRegion[][] textures, boolean horizontal, boolean vertical){
		if(textures != null){
			for(int i = 0; i < textures.length-1; i++){
				for(TextureRegion region:textures[i]){
					region.flip(horizontal,vertical);
				}
			}
		}
	}
	
	/**
	 * Cleanup
	 */
	public void dispose(){
		if(!textureRegions.isEmpty()){
			System.out.println("Disposing of "+textureRegions.size()+" texture regions");
			textureRegions.clear();
		}
		if(!textures.isEmpty()){
			System.out.println("Disposing of "+textures.size()+" textures");
			for(Texture texture:textures.values()){
				texture.dispose();
			}
			textures.clear();
		}
	}
}
