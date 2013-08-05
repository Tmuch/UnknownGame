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
import utils.Time;
import world.World;
import static org.lwjgl.opengl.GL11.*;
import input.InputManager;

public class Game
{

	private static Game			theGame			= null;

	private Camera				cam;
	volatile private boolean	isRunning;
	private boolean				isPaused;
	private Time				timer;					;

	private boolean				fullscreen;
	private int					displayWidth;
	private int					displayHeight;
	private World				theWorld;
	//TODO: private Render renderer;


	int							frameCounter;
	long						prevFrameTime	= -1L;


	private Game()
	{
		System.out.println("Initializing...");
		//theGame = this;
		this.isPaused = false;
		this.isRunning = false;
		timer = new Time(60F); //60 updates per second
		this.cam = new Camera(70, (float) Window.getWidth() / (float) Window.getHeight(), 0.3f, 1000);
		//TODO init other fields
	}

	public void start()
	{
		System.out.println("Starting...");
		if (isRunning) return;
		run();
	}

	public void stop()
	{
		if (!isRunning) return;
		isRunning = false;
	}

	private void run()
	{
		isRunning = true;
		while (isRunning)
		{
			if (Window.isCloseRequested()) stop();

			if (this.isPaused)
			{
				//do something special
			} else
			{
				this.timer.updateTimer();
			}

			/* Generate 'elapsedTicks' number of ticks and return elapsedTicks to zero */
			for (; timer.elapsedTicks > 0; timer.elapsedTicks--)
			{
				this.input();
				this.update();
			}

			this.render();
		}

		cleanup();
	}

	private void cleanup()
	{
		Window.dispose();
	}

	public void render()
	{
		/* Clear the screen before each frame */
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

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


		//Display the frame
		Window.render();
	}

	public void update()
	{
		System.out.println("update");
	}

	public void input()
	{
		InputManager.update();
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

	public static Game getGameInstance()
	{
		if (theGame == null)
		{
			theGame = new Game();
		}
		return theGame;
	}
}
