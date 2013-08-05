package main;

import org.lwjgl.input.Keyboard;
import render.Camera;
import render.RenderManager;
import render.Window;
import utils.Timer;
import world.World;
import static org.lwjgl.opengl.GL11.*;
import input.InputManager;

public class Game
{

	private static Game			theGame	= null;

	volatile private boolean	isRunning;
	private boolean				isPaused;
	private Timer				timer;

	private boolean				fullscreen;
	private World				theWorld;
	//TODO: private Render renderer;


	int							frameCounter;
	long						prevFrameTime;



	private Game()
	{
		System.out.println("Initializing...");
		this.isPaused = false;
		this.isRunning = false;
		fullscreen = false;
		timer = new Timer(120F); //60 updates per second
		/* Init camera with FOV = 70 and render distance 1000 */

		frameCounter = 0;
		prevFrameTime = -1L;

		theWorld = new World();
	}

	public void start()
	{
		if (isRunning) return;
		System.out.println("Starting...");
		run();
	}

	public void stop()
	{
		if (!isRunning) return;
		System.out.println("Stopping...");
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
				//TODO:do something special
			} else
			{
				this.timer.updateTimer();
			}

			/* Generate 'elapsedTicks' number of ticks and return elapsedTicks to zero */
			for (; timer.elapsedTicks > 0; timer.elapsedTicks--)
			{
				this.input();
				this.update();
				this.render();
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

		RenderManager.getInstance().render();
		Window.render();
	}

	public void update()
	{
	}

	public void input()
	{
		InputManager.update();
		handleInput();
	}

	private void handleInput()
	{
		if (InputManager.getKey(Keyboard.KEY_W))
		{
			RenderManager.getInstance().camInstance.move(0.1f, 1);
		}
		if (InputManager.getKey(Keyboard.KEY_S))
		{
			RenderManager.getInstance().camInstance.move(-0.1f, 1);
		}
		if (InputManager.getKey(Keyboard.KEY_A))
		{
			RenderManager.getInstance().camInstance.move(0.1f, 0);
		}
		if (InputManager.getKey(Keyboard.KEY_D))
		{
			RenderManager.getInstance().camInstance.move(-0.1f, 0);
		}
		if (InputManager.getKey(Keyboard.KEY_LEFT))
		{
			RenderManager.getInstance().camInstance.rotateY(-0.25f);
		}
		if (InputManager.getKey(Keyboard.KEY_RIGHT))
		{
			RenderManager.getInstance().camInstance.rotateY(0.25f);
		}
		if (InputManager.getKey(Keyboard.KEY_ESCAPE))
		{
			InputManager.unGrabMouseCursor();
		}
		if (InputManager.getKey(Keyboard.KEY_TAB))
		{
			InputManager.grabMouseCursor();
		}
		if (InputManager.mouseXYChange())
		{
			if (InputManager.deltaX < 0)
			{ //if I moved the mouse left or right at all
				RenderManager.getInstance().camInstance.rotateY(-0.5f);
			} else if (InputManager.deltaX > 0)
			{
				RenderManager.getInstance().camInstance.rotateY(0.5f);
			}
		}
	}

	public World getWorld()
	{
		return theWorld;
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
