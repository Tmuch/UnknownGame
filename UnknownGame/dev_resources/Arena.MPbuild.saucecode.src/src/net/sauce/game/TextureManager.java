package net.sauce.game;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class TextureManager {

	static Texture textureFloor, textureWall;
	
	public void init(){
		try {
			textureFloor = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/floor.png"));
			textureWall = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/wall.jpg"));
		} catch (IOException e) {
			System.err.println("Failed to load texture.");
			e.printStackTrace();
		}
	}
}
