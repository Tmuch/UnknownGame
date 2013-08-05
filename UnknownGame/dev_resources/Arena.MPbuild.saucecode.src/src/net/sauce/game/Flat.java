package net.sauce.game;
import org.lwjgl.opengl.GL11;


public class Flat {

	float width, height;
	int repeats = 1;

	public Flat(float a, float b){
		width = a;
		height = b;
	}

	public void render(){
		TextureManager.textureFloor.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(width,0, height);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-width,0, height);
		GL11.glTexCoord2f(repeats, 0);
		GL11.glVertex3f(-width,0,-height);
		GL11.glTexCoord2f(repeats, repeats);
		GL11.glVertex3f(width,0,-height);
		GL11.glTexCoord2f(0, repeats);
		GL11.glEnd();
	}
}
