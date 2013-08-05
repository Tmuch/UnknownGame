package main;

import org.lwjgl.input.Keyboard;

import render.Camera;
import render.Window;
import static org.lwjgl.opengl.GL11.*;
import input.InputManager;

public class Game
{



	private Camera	cam;

	public Game()
	{
		cam = new Camera(70, (float) Window.getWidth() / (float) Window.getHeight(), 0.3f, 1000);
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
