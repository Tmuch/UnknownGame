package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import render.Camera;
import render.Window;
import static org.lwjgl.opengl.GL11.*;
import input.InputManager;

public class Game
{



	private Camera	cam;



	/* Draw String testing */
	TrueTypeFont	font;


	public Game()
	{
		cam = new Camera(70, (float) Window.getWidth() / (float) Window.getHeight(), 0.3f, 1000);

		/* Draw String testing */

		Font awtFont = new Font("Arial", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);

		InputStream instream = ResourceLoader.getResourceAsStream("res/arial.ttf");
		try
		{
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, instream);
			awtFont2 = awtFont2.deriveFont(24f);
		} catch (FontFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public void render()
	{
		/* should this be in update instead? */
		cam.useView();

		glPushMatrix();
		{
			//glColor3f(1.0f, 0.5f, 0f);
			glTranslatef(0, 0, -10);
			//glRotatef(x, 1, 1.5f, 0.75f);
			glBegin(GL_QUADS);
			{
				glColor3f(1f, 0f, 0f);
				//Left face
				glVertex3f(-1, 1, 1);
				glVertex3f(-1, -1, 1);
				glVertex3f(-1, -1, -1);
				glVertex3f(-1, 1, -1);

				glColor3f(0f, 1f, 0f);
				//Front face
				glVertex3f(-1, 1, 1);
				glVertex3f(-1, -1, 1);
				glVertex3f(1, -1, 1);
				glVertex3f(1, 1, 1);

				glColor3f(1f, 0f, 0f);
				//right face
				glVertex3f(1, 1, 1);
				glVertex3f(1, 1, -1);
				glVertex3f(1, -1, -1);
				glVertex3f(1, -1, 1);


				glColor3f(0f, 1f, 0f);
				//back face
				glVertex3f(-1, -1, -1);
				glVertex3f(-1, 1, -1);
				glVertex3f(1, 1, -1);
				glVertex3f(1, -1, -1);

				glColor3f(0f, 0f, 1f);
				//top face
				glVertex3f(-1, 1, 1);
				glVertex3f(-1, 1, -1);
				glVertex3f(1, 1, -1);
				glVertex3f(1, 1, 1);

				glColor3f(0f, 0f, 1f);
				//bottom face
				glVertex3f(-1, -1, 1);
				glVertex3f(-1, -1, -1);
				glVertex3f(1, -1, -1);
				glVertex3f(1, -1, 1);
			}
			glEnd();
		}
		glPopMatrix();



		//font.drawString(100, 50, "Hello there", Color.white);
	}

	public void update()
	{

	}

	public void input()
	{
		if (InputManager.getKey(Keyboard.KEY_W))
		{
			cam.move(0.1f, 1);
		}
		if (InputManager.getKey(Keyboard.KEY_S))
		{
			cam.move(-0.1f, 1);
		}
		if (InputManager.getKey(Keyboard.KEY_A))
		{
			cam.move(0.1f, 0);
		}
		if (InputManager.getKey(Keyboard.KEY_D))
		{
			cam.move(-0.1f, 0);
		}
		if (InputManager.getKey(Keyboard.KEY_LEFT))
		{
			cam.rotateY(-0.25f);
		}
		if (InputManager.getKey(Keyboard.KEY_RIGHT))
		{
			cam.rotateY(0.25f);
		}
	}
}
