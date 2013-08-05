package net.sauce.game;
import org.lwjgl.opengl.GL11;

public class Box {

	float centerX, centerY, centerZ, size, yTop;
	float red, green, blue;

	public Box(float a, float b, float c, float d) {
		centerX = -a;
		centerY = b;
		centerZ = -c;
		yTop = -(centerY + d/2);
		size = d;
		red = 128;
		green = 128;
		blue = 1;
	}

	public Box(float a, float b, float c, float d, float r, float g, float bl) {
		this(a, b, c, d);
		red = r;
		green = g;
		blue = bl;
	}

	public void render() {
		// Top
		TextureManager.textureWall.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTranslatef(-centerX, centerY, -centerZ);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-centerX + size, centerY + size, -centerZ - size);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-centerX - size, centerY + size, -centerZ - size);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-centerX - size, centerY + size, -centerZ + size);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-centerX + size, centerY + size, -centerZ + size);
		// Bottom
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-centerX + size, centerY - size, -centerZ + size);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-centerX - size, centerY - size, -centerZ + size);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-centerX - size, centerY - size, -centerZ - size);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-centerX + size, centerY - size, -centerZ - size);
		// One side
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-centerX + size, centerY + size, -centerZ + size);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-centerX - size, centerY + size, -centerZ + size);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-centerX - size, centerY - size, -centerZ + size);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-centerX + size, centerY - size, -centerZ + size);
		// Moar sides
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-centerX + size, centerY - size, -centerZ - size);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-centerX - size, centerY - size, -centerZ - size);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-centerX - size, centerY + size, -centerZ - size);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-centerX + size, centerY + size, -centerZ - size);
		// Last side
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-centerX - size, centerY + size, -centerZ + size);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-centerX - size, centerY + size, -centerZ - size);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-centerX - size, centerY - size, -centerZ - size);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-centerX - size, centerY - size, -centerZ + size);
		// Real last side
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-centerX + size, centerY + size, -centerZ - size);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-centerX + size, centerY + size, -centerZ + size);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(-centerX + size, centerY - size, -centerZ + size);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(-centerX + size, centerY - size, -centerZ - size);
		GL11.glEnd();
	}
}
