package main;

import input.InputManager;
import render.Window;
import utils.Time;
import static org.lwjgl.opengl.GL11.*;

public class Main
{

	public static final int		WIDTH	= 800;
	public static final int		HEIGHT	= 600;
	public static final String	TITLE	= "GAME";
	public static final int		TPS		= 150;		//ticks per second
	private boolean				isRunning;
	public long					ticks;
	public long					frames;
	private Game				game;

	public static void main(String[] args)
	{
		Window.createWindow(WIDTH, HEIGHT, TITLE);

		Main m = new Main();
		m.start();
	}

	public Main()
	{
		isRunning = false;
		ticks = 0;
		frames = 0;
		game = new Game();
	}

	public void start()
	{
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

		long lastTime = Time.getTime();
		final double nsPerUpdate = Time.SECOND_NS / TPS; //nanoseconds between each update
		double delta = 0;

		/* timer is used to trigger a fps and tps variable resets ever second to prevent overflowing */
		long timer = System.currentTimeMillis(); //for tick and fps counter

		/* Used for calculating tps and fps counters */
		/* Can possibly be eventually replaced by just keeping a counter of how many ticks have occurred and once
		 * that counter reaches TPS then trigger the variable reset. */
		long prevTime = System.currentTimeMillis();

		/* 1 ms to prevent divide by zero when counters first begin.
		 * Will have no significant impact on counters */
		long elapsed = 1;

		while (isRunning)
		{

			if (Window.isCloseRequested()) stop();



			long now = Time.getTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;

			/* Happens TPS times per second */
			while (delta >= 1)
			{
				input();
				update();
				delta -= 1; //return to (near) 0


				/* Calculate tps and fps values and display in game title
				 * TODO: Display this on the screen with slickutils */
				long curr = System.currentTimeMillis();
				elapsed += curr - prevTime;
				prevTime = curr;
				Window.setTitle(((((long) ticks) * Time.SECOND_MS) / elapsed) + " tps");
			} //end while



			render();


			//if 1 second elapsed
			if ((System.currentTimeMillis() - timer) > 1000)
			{
				/* Add 1 more second to timer */
				timer += 1000;

				/* Reset variables used for tps and fps calculations */
				ticks = frames = 0;
				elapsed = 1;
				prevTime = System.currentTimeMillis();
			} //end if 1 second elapsed

		} //end while isRunning



		cleanup();
	}

	private void update()
	{
		ticks++;
		game.update();
	}

	private void input()
	{
		InputManager.update();
		game.input();
	}

	private void render()
	{
		frames++;

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		/* Game renders as well as all of its components */
		game.render();
		/* The window renders, actually displaying to the screen */
		Window.render();
	}

	private void cleanup()
	{
		Window.dispose();
	}

}
