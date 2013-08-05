package main;

import render.Window;
import utils.Time;

public class Main
{

	public static final int		WIDTH	= 800;
	public static final int		HEIGHT	= 600;
	public static final String	TITLE	= "GAME";
	public static final int		TPS		= 150;		//ticks per second
	private boolean				isRunning;
	public long					ticks;
	public long					frames;

	public static void main(String[] args)
	{
		Window.createWindow(WIDTH, HEIGHT, TITLE);

		Main game = new Main();
		game.start();
	}

	public Main()
	{
		isRunning = false;
		ticks = 0;
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
		frames = 0;
		ticks = 0;
		long timer = System.currentTimeMillis(); //for tick and fps counter

		long prevTime = System.currentTimeMillis();
		long elapsed = 1; //1 ms

		while (isRunning)
		{
			if (Window.isCloseRequested()) stop();
			long now = Time.getTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;

			/* Happens TPS times per second */
			while (delta >= 1)
			{
				update();
				delta -= 1;
				long curr = System.currentTimeMillis();
				elapsed += curr - prevTime;
				prevTime = curr;
				Window.setTitle(((((long) ticks) * Time.SECOND_MS) / elapsed) + " tps");
			}



			render();

			if ((System.currentTimeMillis() - timer) > 1000)
			{
				timer += 1000;
				ticks = frames = 0;

				/* Reset tps and fps counters */
				elapsed = 1;
				prevTime = System.currentTimeMillis();
			}

		}

		cleanup();
	}

	private void update()
	{
		ticks++;
	}

	private void render()
	{
		frames++;
		Window.render();
	}

	private void cleanup()
	{
		Window.dispose();
	}

}
